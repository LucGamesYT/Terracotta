package org.terracotta.network;

import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.BedrockPong;
import com.nukkitx.protocol.bedrock.v408.Bedrock_v408;
import lombok.SneakyThrows;
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

    private final BedrockClient bedrockClient;
    private final TerracottaServer server;

    /**
     * Creates a new Client
     *
     * @param server which is the server where the client should connect to
     */
    @SneakyThrows
    public Client(final TerracottaServer server) {
        this.server = server;

        this.bedrockClient = new BedrockClient(new InetSocketAddress("0.0.0.0", ThreadLocalRandom.current().nextInt(20000, 60000)));
        this.bedrockClient.bind().join();
    }

    /**
     * Retrieves the Pong data from the ping execution
     *
     * @return a fresh {@link BedrockPong}
     */
    public BedrockPong executePing() {
        return this.bedrockClient.ping(this.server.getAddress()).join();
    }

    /**
     * Builds a new connection to the Server data given in the constructor of this class
     */
    public void connect() {
        this.bedrockClient.connect(this.server.getAddress()).whenComplete((bedrockClientSession, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();
                return;
            }

            bedrockClientSession.setPacketCodec(Bedrock_v408.V408_CODEC);
            bedrockClientSession.setPacketHandler(new BedrockPacketHandler(this.server.getBedrockServerSession(), this.server.getBedrockServer()));
        }).join();
    }

    /**
     * Closes the client-side connection to the server
     */
    public void close() {
        this.bedrockClient.close();
    }
}