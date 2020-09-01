package org.terracotta.network.handler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.common.base.Preconditions;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
import com.nimbusds.jwt.SignedJWT;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.BedrockServerSession;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket;
import com.nukkitx.protocol.bedrock.util.EncryptionUtils;
import io.netty.util.AsciiString;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;
import org.terracotta.Terracotta;
import org.terracotta.entity.Entities;
import org.terracotta.network.Client;
import org.terracotta.network.ProtocolInfo;
import org.terracotta.network.session.AuthData;
import org.terracotta.network.session.PlayerSession;
import org.terracotta.util.Utils;

import java.security.interfaces.ECPublicKey;
import java.util.UUID;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
@RequiredArgsConstructor
public class BedrockServerPacketHandler implements com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler {

    private final BedrockServerSession serverSession;

    @SneakyThrows
    @Override
    public boolean handle(final LoginPacket packet) {
        final int protocolVersion = packet.getProtocolVersion();

        // check for compatible protocol version
        if (protocolVersion != ProtocolInfo.getProtocolVersion()) {
            final PlayStatusPacket playStatusPacket = new PlayStatusPacket();

            playStatusPacket.setStatus(protocolVersion > ProtocolInfo.getProtocolVersion() ? PlayStatusPacket.Status.LOGIN_FAILED_SERVER_OLD : PlayStatusPacket.Status.LOGIN_FAILED_CLIENT_OLD);

            this.serverSession.sendPacket(playStatusPacket);
            return false;
        }
        this.serverSession.setPacketCodec(ProtocolInfo.getPacketCodec());

        final ObjectMapper jsonMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        final JsonNode certificateData = jsonMapper.readTree(packet.getChainData().toByteArray());
        final JsonNode certificateChainData = certificateData.get("chain");

        final boolean validChain = this.validateChainData(certificateChainData);

        // check whether chain data is valid
        if (!validChain) {
            Terracotta.LOGGER.warn("Could not validate the chainData of an incoming connection");
            return false;
        }

        final JWSObject jwt = JWSObject.parse(certificateChainData.get(certificateChainData.size() - 1).asText());
        final JsonNode payload = jsonMapper.readTree(jwt.getPayload().toBytes());
        final JSONObject extraData = (JSONObject) jwt.getPayload().toJSONObject().get("extraData");
        final ECPublicKey identityPublicKey = EncryptionUtils.generateKey(payload.get("identityPublicKey").textValue());
        final JWSObject clientJwt = JWSObject.parse(packet.getSkinData().toString());

        final boolean verified = this.verifyJwt(clientJwt, identityPublicKey);

        // check whether the identityPublicKey is valid
        if (!verified) {
            Terracotta.LOGGER.warn("Could not verify the identityPublicKey of an incoming connection");
            return false;
        }

        final String displayName = extraData.getAsString("displayName");
        final String uniqueId = extraData.getAsString("identity");
        final String xuid = extraData.getAsString("XUID");

        int entityRuntimeId = -1;

        for (int i = 1; i < Terracotta.server.getMaxEntitiesSize(); i++) {
            if (!Entities.isRuntimeIdUsed(i)) {
                entityRuntimeId = i;
                Entities.addUsedRuntimeId(i);
                break;
            }
        }

        // check if entityRuntimeId could be given to the incoming player connection
        if (entityRuntimeId == -1) {
            Terracotta.LOGGER.warn("Could not create entityRuntimeId for an incoming connection");
            return false;
        }

        final PlayStatusPacket status = new PlayStatusPacket();
        status.setStatus(PlayStatusPacket.Status.LOGIN_SUCCESS);

        this.serverSession.sendPacket(status);

        final SetEntityMotionPacket motion = new SetEntityMotionPacket();
        motion.setRuntimeEntityId(entityRuntimeId);
        motion.setMotion(Vector3f.ZERO);

        this.serverSession.sendPacket(motion);

        final ResourcePacksInfoPacket resourcePacksInfo = new ResourcePacksInfoPacket();
        resourcePacksInfo.setForcedToAccept(false);
        resourcePacksInfo.setScriptingEnabled(false);

        this.serverSession.sendPacket(resourcePacksInfo);

        Terracotta.LOGGER.info(displayName + " logged in with entityId " + entityRuntimeId + " [uniqueId: " + uniqueId + "]");

        final Client client = new Client(Terracotta.server);
        client.connect();

        final BedrockClientSession clientSession = client.getSession();
        final PlayerSession playerSession = new PlayerSession(this.serverSession, clientSession, new AuthData(displayName, UUID.fromString(uniqueId), xuid));
        final LoginPacket loginPacket = new LoginPacket();

        // forge authentication and skin data
        final SignedJWT authDataForged = Utils.forgeAuthData(playerSession.getKeyPair(), extraData);
        final JWSObject skinDataForged = Utils.forgeSkinData(playerSession.getKeyPair(), clientJwt.getPayload().toJSONObject());

        ((ArrayNode) certificateChainData).remove(certificateChainData.size() - 1);
        ((ArrayNode) certificateChainData).add(authDataForged.serialize());

        final JsonNode jsonNode = jsonMapper.createObjectNode().set("chain", certificateChainData);
        final AsciiString chainData = new AsciiString(jsonMapper.writeValueAsBytes(jsonNode));

        loginPacket.setProtocolVersion(ProtocolInfo.getProtocolVersion());
        loginPacket.setChainData(chainData);
        loginPacket.setSkinData(AsciiString.of(skinDataForged.serialize()));

        clientSession.sendPacketImmediately(loginPacket);
        this.serverSession.setBatchHandler(playerSession.getUpstreamBatchHandler());
        clientSession.setBatchHandler(playerSession.getDownstreamBatchHandler());
        clientSession.setLogging(true);

        Terracotta.LOGGER.info("Client connected");

        return true;
    }

    /**
     * Validates the given {@link JsonNode} chain data
     *
     * @param data which should be validated
     *
     * @return true, when the chain data could be verified, otherwise false
     */
    @SneakyThrows
    private boolean validateChainData(final JsonNode data) {
        ECPublicKey lastKey = null;
        boolean validChain = false;

        for (JsonNode jsonNode : data) {
            final JWSObject jwt = JWSObject.parse(jsonNode.asText());

            if (!validChain) {
                validChain = jwt.verify(new DefaultJWSVerifierFactory().createJWSVerifier(jwt.getHeader(), EncryptionUtils.getMojangPublicKey()));
            }

            if (lastKey != null) {
                jwt.verify(new DefaultJWSVerifierFactory().createJWSVerifier(jwt.getHeader(), lastKey));
            }

            final ObjectMapper jsonMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            final JsonNode payloadNode = jsonMapper.readTree(jwt.getPayload().toString());
            final JsonNode ipkNode = payloadNode.get("identityPublicKey");

            Preconditions.checkState(ipkNode != null && ipkNode.getNodeType() == JsonNodeType.STRING);
            lastKey = EncryptionUtils.generateKey(ipkNode.asText());
        }

        return validChain;
    }

    /**
     * Verifies the given {@link JWSObject} header and the given {@link ECPublicKey}
     *
     * @param jwt       data, which should be verified
     * @param publicKey key, which should be verified
     *
     * @return whether the data could be verified
     */
    @SneakyThrows
    private boolean verifyJwt(final JWSObject jwt, final ECPublicKey publicKey) {
        return jwt.verify(new DefaultJWSVerifierFactory().createJWSVerifier(jwt.getHeader(), publicKey));
    }
}