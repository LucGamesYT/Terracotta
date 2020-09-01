package org.terracotta;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.terracotta.network.ProtocolInfo;
import org.terracotta.server.TerracottaServer;
import org.terracotta.util.io.FileCreationUtil;
import org.terracotta.util.logging.MainLogger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
@NoArgsConstructor
public class Terracotta {

    public static final MainLogger LOGGER = new MainLogger();
    public static TerracottaServer server;

    @SneakyThrows
    public static void main(final String[] args) {
        LOGGER.info("Terracotta is starting up...");
        LOGGER.info("Creating Server files..");

        FileCreationUtil.createDirectories();
        FileCreationUtil.createFiles();

        LOGGER.info(FileCreationUtil.getCreatedFiles().size() != 0 ? "Server files created. (" + String.join(", ", FileCreationUtil.getCreatedFiles()) + ")" : "Server files loaded.");

        server = new TerracottaServer();

        LOGGER.info("This Terracotta server is running at v" + ProtocolInfo.getMinecraftVersion() + " (Protocol " + ProtocolInfo.getProtocolVersion() + ")");

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // just to keep the terminal alive as long as possible ...
        while (true) {
            final String consoleMessage = bufferedReader.readLine();

            if (consoleMessage.equalsIgnoreCase("stop") && server.close()) {
                bufferedReader.close();
                LOGGER.info("Server is shutting down...");
                return;
            }
        }
    }
}