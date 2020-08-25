package org.terracotta.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.common.base.Preconditions;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.factories.DefaultJWSVerifierFactory;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockServer;
import com.nukkitx.protocol.bedrock.BedrockServerSession;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket;
import com.nukkitx.protocol.bedrock.util.EncryptionUtils;
import com.nukkitx.protocol.bedrock.v408.Bedrock_v408;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.minidev.json.JSONObject;

import java.security.interfaces.ECPublicKey;

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
public class BedrockPacketHandler implements com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler {

    private final BedrockServerSession serverSession;
    private final BedrockServer bedrockServer;

    @SneakyThrows
    @Override
    public boolean handle(final LoginPacket packet) {
        final int protocolVersion = packet.getProtocolVersion();

        if (protocolVersion != Bedrock_v408.V408_CODEC.getProtocolVersion()) {
            final PlayStatusPacket playStatusPacket = new PlayStatusPacket();

            if (protocolVersion > Bedrock_v408.V408_CODEC.getProtocolVersion()) {
                playStatusPacket.setStatus(PlayStatusPacket.Status.LOGIN_FAILED_SERVER_OLD);
            } else {
                playStatusPacket.setStatus(PlayStatusPacket.Status.LOGIN_FAILED_CLIENT_OLD);
            }
            this.serverSession.sendPacket(playStatusPacket);
        }
        this.serverSession.setPacketCodec(Bedrock_v408.V408_CODEC);

        final ObjectMapper jsonMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        final JsonNode certificateData = jsonMapper.readTree(packet.getChainData().toByteArray());
        final JsonNode certificateChainData = certificateData.get("chain");

        final boolean validChain = validateChainData(certificateChainData);
        final JWSObject jwt = JWSObject.parse(certificateChainData.get(certificateChainData.size() - 1).asText());
        final JsonNode payload = jsonMapper.readTree(jwt.getPayload().toBytes());
        final JSONObject extraData = (JSONObject) jwt.getPayload().toJSONObject().get("extraData");
        final ECPublicKey identityPublicKey = EncryptionUtils.generateKey(payload.get("identityPublicKey").textValue());
        final JWSObject clientJwt = JWSObject.parse(packet.getSkinData().toString());

        this.verifyJwt(clientJwt, identityPublicKey);

        final String name = extraData.getAsString("displayName");
        final String uuid = extraData.getAsString("identity");

        System.out.println("uuid: " + uuid + " , name: " + name);

        final PlayStatusPacket status = new PlayStatusPacket();
        status.setStatus(PlayStatusPacket.Status.LOGIN_SUCCESS);
        this.serverSession.sendPacket(status);

        final SetEntityMotionPacket motion = new SetEntityMotionPacket();
        motion.setRuntimeEntityId(1);
        motion.setMotion(Vector3f.ZERO);
        this.serverSession.sendPacket(motion);

        final ResourcePacksInfoPacket resourcePacksInfo = new ResourcePacksInfoPacket();
        resourcePacksInfo.setForcedToAccept(false);
        resourcePacksInfo.setScriptingEnabled(false);

        this.serverSession.sendPacket(resourcePacksInfo);
        return true;
    }

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

    @SneakyThrows
    private boolean verifyJwt(final JWSObject jwt, final ECPublicKey publicKey) {
        return jwt.verify(new DefaultJWSVerifierFactory().createJWSVerifier(jwt.getHeader(), publicKey));
    }
}