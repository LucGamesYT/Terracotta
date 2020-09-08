package org.terracotta.server;

import com.nukkitx.protocol.bedrock.BedrockPong;
import com.nukkitx.protocol.bedrock.BedrockServer;
import com.nukkitx.protocol.bedrock.BedrockServerEventHandler;
import com.nukkitx.protocol.bedrock.BedrockServerSession;
import lombok.Getter;
import lombok.SneakyThrows;
import org.terracotta.Terracotta;
import org.terracotta.network.ProtocolInfo;
import org.terracotta.network.handler.BedrockServerPacketHandler;
import org.terracotta.util.io.FileDataManager;
import org.terracotta.util.io.ServerFile;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletionException;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public class TerracottaServer {

    @Getter
    private final String hostname;
    @Getter
    private final int port;
    @Getter
    private final String motd;
    @Getter
    private final int maxPlayerSize;
    @Getter
    private final int defaultGameMode;
    @Getter
    private final InetSocketAddress address;
    @Getter
    private final int maxEntitiesSize;

    private final int onlinePlayerSize = 0;

    private final BedrockServer bedrockServer;

    /**
     * Creates a new Terracotta Bedrock Edition server
     */
    @SneakyThrows
    public TerracottaServer() {
        this.hostname = (String) FileDataManager.getProperty(ServerFile.PROPERTIES_SERVER, "ip");
        this.port = Integer.parseInt((String) FileDataManager.getProperty(ServerFile.PROPERTIES_SERVER, "port"));
        this.motd = (String) FileDataManager.getProperty(ServerFile.PROPERTIES_SERVER, "motd");
        this.maxPlayerSize = Integer.parseInt((String) FileDataManager.getProperty(ServerFile.PROPERTIES_SERVER, "maxPlayers"));
        this.defaultGameMode = Integer.parseInt((String) FileDataManager.getProperty(ServerFile.PROPERTIES_SERVER, "defaultGameMode"));
        this.address = new InetSocketAddress(this.hostname, this.port);
        this.maxEntitiesSize = this.maxPlayerSize + 101;

        this.bedrockServer = new BedrockServer(this.address);

        try {
            final BedrockPong bedrockPong = new BedrockPong();

            bedrockPong.setEdition("MCPE");
            bedrockPong.setMotd(this.motd);
            bedrockPong.setPlayerCount(this.onlinePlayerSize);
            bedrockPong.setMaximumPlayerCount(this.maxPlayerSize);

            switch (this.defaultGameMode) {
                case 0:
                    bedrockPong.setGameType("Survival");
                    break;
                case 1:
                    bedrockPong.setGameType("Creative");
                    break;
                case 2:
                    bedrockPong.setGameType("Adventure");
                    break;
                case 3:
                    bedrockPong.setGameType("Spectator");
                    break;
            }

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
            this.bedrockServer.bind().join();
        } catch (final CompletionException ignored) {
            Terracotta.LOGGER.error("Failed to bind to " + this.address.getHostName() + ":" + this.address.getPort());
            Terracotta.LOGGER.warn("Perhaps another instance is already running on that port?");
        }
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