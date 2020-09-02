package org.terracotta.network;

import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.BedrockClientSession;
import lombok.SneakyThrows;
import org.terracotta.Terracotta;
import org.terracotta.network.handler.BedrockClientPacketHandler;
import org.terracotta.server.TerracottaServer;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public class Client {

    private final TerracottaServer server;
    private final BedrockClient bedrockClient;

    /**
     * Creates a new Client
     */
    @SneakyThrows
    public Client(final TerracottaServer server) {
        this.server = server;

        this.bedrockClient = new BedrockClient(new InetSocketAddress("0.0.0.0", ThreadLocalRandom.current().nextInt(20000, 60000)));
        this.bedrockClient.setRakNetVersion(10);
        this.bedrockClient.bind().join();
    }

    /**
     * Builds a new connection to the Server data given in the constructor of this class
     */
    public void connect() {
        this.bedrockClient.directConnect(this.server.getAddress()).whenComplete((bedrockClientSession, throwable) -> {
            if (throwable != null) {
                return;
            }

            bedrockClientSession.addDisconnectHandler(disconnectReason -> {
                this.close();
                Terracotta.LOGGER.info(this.bedrockClient.getBindAddress().getHostName() + ":" + this.bedrockClient.getBindAddress().getPort() + " logged out with reason: " + disconnectReason.name().toLowerCase().replace("_", ""));
            });
            bedrockClientSession.setPacketCodec(ProtocolInfo.getPacketCodec());
            bedrockClientSession.setPacketHandler(new BedrockClientPacketHandler(this.getSession()));
        });
    }

    /**
     * Retrieves the current client session
     *
     * @return a fresh {@link BedrockClientSession}
     */
    public BedrockClientSession getSession() {
        return this.bedrockClient.getSession();
    }

    /**
     * Closes the client-side connection to the server
     */
    public void close() {
        if (!this.getSession().isClosed()) {
            this.bedrockClient.close();
        }
    }
}