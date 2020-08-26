package org.terracotta.network.handler;

import com.nimbusds.jwt.SignedJWT;
import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.packet.ClientToServerHandshakePacket;
import com.nukkitx.protocol.bedrock.packet.ServerToClientHandshakePacket;
import com.nukkitx.protocol.bedrock.util.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.crypto.SecretKey;
import java.net.URI;
import java.security.interfaces.ECPublicKey;
import java.util.Base64;

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
public class BedrockClientPacketHandler implements BedrockPacketHandler {

    private final BedrockClientSession clientSession;

    @SneakyThrows
    public boolean handle(final ServerToClientHandshakePacket packet) {
        final SignedJWT saltJwt = SignedJWT.parse(packet.getJwt());
        final URI x5u = saltJwt.getHeader().getX509CertURL();
        final ECPublicKey publicKey = EncryptionUtils.generateKey(x5u.toASCIIString());
        final SecretKey secretKey = EncryptionUtils.getSecretKey(EncryptionUtils.createKeyPair().getPrivate(), publicKey, Base64.getDecoder().decode(saltJwt.getJWTClaimsSet().getStringClaim("salt")));

        this.clientSession.enableEncryption(secretKey);

        final ClientToServerHandshakePacket clientToServerHandshakePacket = new ClientToServerHandshakePacket();

        this.clientSession.sendPacketImmediately(clientToServerHandshakePacket);

        return true;
    }
}