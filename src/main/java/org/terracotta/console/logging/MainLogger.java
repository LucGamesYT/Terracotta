package org.terracotta.console.logging;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class MainLogger implements ILogger {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void info(final String s) {
        LOGGER.info("§7[§bINFO§7]§f (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void warn(final String s) {
        LOGGER.info("§7[§6WARN§7]§f (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void debug(final String s) {
        LOGGER.info("§7[§eDEBUG§7]§f (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void error(final String s) {
        LOGGER.info("§7[§4INFO§7]§f (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void dev(final String s) {
        LOGGER.info("§7[§5DEVELOPMENT§7]§f (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }
}