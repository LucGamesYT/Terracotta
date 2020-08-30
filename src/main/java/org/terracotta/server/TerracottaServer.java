package org.terracotta.server;

import com.nukkitx.protocol.bedrock.BedrockPong;
import com.nukkitx.protocol.bedrock.BedrockServer;
import com.nukkitx.protocol.bedrock.BedrockServerEventHandler;
import com.nukkitx.protocol.bedrock.BedrockServerSession;
import lombok.Data;
import lombok.SneakyThrows;
import org.terracotta.Terracotta;
import org.terracotta.network.ProtocolInfo;
import org.terracotta.network.handler.BedrockServerPacketHandler;

import java.net.InetSocketAddress;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
@Data
public class TerracottaServer {

    private final String hostname = "127.0.0.1";
    private final int port = 19132;
    private final String motd = "Terracotta Server v1.0.0";
    private final int onlinePlayerSize = 0;
    private final int maxPlayerSize = 50;
    private final String defaultGameMode = "Creative";
    private final InetSocketAddress address = new InetSocketAddress(this.hostname, this.port);
    private final int maxEntitiesSize = this.maxPlayerSize + 101;

    private BedrockServer bedrockServer;

    /**
     * Creates a new Terracotta Bedrock Edition server
     */
    @SneakyThrows
    public TerracottaServer() {
        this.bedrockServer = new BedrockServer(this.address);
        final BedrockPong bedrockPong = new BedrockPong();

        bedrockPong.setEdition("MCPE");
        bedrockPong.setMotd(this.motd);
        bedrockPong.setPlayerCount(this.onlinePlayerSize);
        bedrockPong.setMaximumPlayerCount(this.maxPlayerSize);
        bedrockPong.setGameType(this.defaultGameMode);
        bedrockPong.setProtocolVersion(ProtocolInfo.getProtocolVersion());

        this.bedrockServer.setHandler(new BedrockServerEventHandler() {
            @Override
            public boolean onConnectionRequest(final InetSocketAddress address) {
                return true;
            }

            @Override
            public BedrockPong onQuery(final InetSocketAddress address) {
                return bedrockPong;
            }

            @Override
            public void onSessionCreation(final BedrockServerSession serverSession) {
                serverSession.addDisconnectHandler(disconnectReason -> {
                    if (bedrockServer.isClosed()) {
                        Terracotta.LOGGER.info("Server Session terminated! Server is shutting down...");
                    }
                });
                serverSession.setPacketCodec(ProtocolInfo.getPacketCodec());
                serverSession.setPacketHandler(new BedrockServerPacketHandler(serverSession));
            }
        });
        this.bedrockServer.bind();
    }

    /**
     * Shutdown this Terracotta server
     *
     * @return @code true, when the shutdown executed successfully, otherwise @code false
     */
    public boolean close() {
        if (!this.bedrockServer.isClosed()) {
            this.bedrockServer.close();
            return true;
        }
        return false;
    }
}