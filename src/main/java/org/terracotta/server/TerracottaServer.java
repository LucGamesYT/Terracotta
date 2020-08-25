package org.terracotta.server;

import com.nukkitx.protocol.bedrock.BedrockPong;
import com.nukkitx.protocol.bedrock.BedrockServer;
import com.nukkitx.protocol.bedrock.BedrockServerEventHandler;
import com.nukkitx.protocol.bedrock.BedrockServerSession;
import com.nukkitx.protocol.bedrock.v408.Bedrock_v408;
import lombok.Data;
import lombok.SneakyThrows;
import org.terracotta.network.BedrockPacketHandler;

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
    private final int playerCount = 0;
    private final int maxPlayerCount = 50;
    private final String defaultGameMode = "Creative";
    private final InetSocketAddress address = new InetSocketAddress(this.hostname, this.port);

    private BedrockServer bedrockServer;
    private BedrockServerSession bedrockServerSession;

    /**
     * Creates a new Terracotta Bedrock Edition server
     */
    @SneakyThrows
    public TerracottaServer() {
        this.bedrockServer = new BedrockServer(new InetSocketAddress(this.hostname, this.port));
        final BedrockPong bedrockPong = new BedrockPong();

        bedrockPong.setEdition("MCPE");
        bedrockPong.setMotd(this.motd);
        bedrockPong.setPlayerCount(this.playerCount);
        bedrockPong.setMaximumPlayerCount(this.maxPlayerCount);
        bedrockPong.setGameType(this.defaultGameMode);
        bedrockPong.setProtocolVersion(Bedrock_v408.V408_CODEC.getProtocolVersion());

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
                bedrockServerSession = serverSession;
                serverSession.addDisconnectHandler(disconnectReason -> System.out.println("Session Disconnected"));
                serverSession.setPacketHandler(new BedrockPacketHandler(serverSession, bedrockServer));
            }
        });
        this.bedrockServer.bind().join();
    }

    public void close() {
        if (!this.bedrockServer.isClosed()) {
            this.bedrockServer.close();
        }
    }
}