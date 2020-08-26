package org.terracotta;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.server.TerracottaServer;
import org.terracotta.util.io.FileCreationUtil;

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
public class Terracotta {

    public static TerracottaServer SERVER;

    @SneakyThrows
    public static void main(final String[] args) {
        final Logger logger = LoggerFactory.getLogger(Terracotta.class);

        logger.info("Terracotta is starting up...");
        logger.info("Creating Server files..");

        FileCreationUtil.createDirectories();
        FileCreationUtil.createFiles();

        logger.info(FileCreationUtil.getCreatedFiles().size() != 0 ? "Server files created. (" + String.join(", ", FileCreationUtil.getCreatedFiles()) + ")" : "Server files loaded.");

        SERVER = new TerracottaServer();

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            // just to keep the terminal alive as long as possible ...
            final String consoleMessage = bufferedReader.readLine();

            if (consoleMessage.equalsIgnoreCase("stop")) {
                SERVER.close();
                logger.info("Server is shutting down...");
                return;
            }
        }
    }
}