package org.terracotta.network.session;

import com.nukkitx.network.util.DisconnectReason;
import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockServerSession;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.handler.BedrockPacketHandler;
import com.nukkitx.protocol.bedrock.util.EncryptionUtils;
import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public class PlayerSession {

    private final BedrockServerSession serverSession;
    private final BedrockClientSession clientSession;
    @Getter
    private final AuthData authData;
    @Getter
    private final KeyPair keyPair;

    public PlayerSession(final BedrockServerSession serverSession, final BedrockClientSession clientSession, final AuthData authData) {
        this.serverSession = serverSession;
        this.clientSession = clientSession;
        this.authData = authData;
        this.keyPair = EncryptionUtils.createKeyPair();

        this.serverSession.addDisconnectHandler(disconnectReason -> {
            if (!disconnectReason.equals(DisconnectReason.DISCONNECTED)) {
                this.clientSession.disconnect();
            }
        });
    }

    public BatchHandler getUpstreamBatchHandler() {
        return new BatchHandler(this.serverSession);
    }

    public BatchHandler getDownstreamBatchHandler() {
        return new BatchHandler(this.clientSession);
    }

    @RequiredArgsConstructor
    private class BatchHandler implements com.nukkitx.protocol.bedrock.handler.BatchHandler {

        private final BedrockSession session;

        @Override
        public void handle(final BedrockSession bedrockSession, final ByteBuf byteBuf, final Collection<BedrockPacket> packets) {
            final List<BedrockPacket> unhandledPackets = new ArrayList<>();
            boolean batchHandled = false;

            for (final BedrockPacket packet : packets) {
                final BedrockPacketHandler handler = this.session.getPacketHandler();

                if (handler != null && packet.handle(handler)) {
                    batchHandled = true;
                } else {
                    unhandledPackets.add(packet);
                }

                if (!batchHandled) {
                    byteBuf.resetReaderIndex();
                    this.session.sendWrapped(byteBuf, true);
                } else if (!unhandledPackets.isEmpty()) {
                    this.session.sendWrapped(byteBuf, true);
                }
            }
        }
    }
}