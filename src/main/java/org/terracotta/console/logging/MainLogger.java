package org.terracotta.console.logging;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.terracotta.util.ChatColor;

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
        LOGGER.info(ChatColor.GRAY + "[" + ChatColor.AQUA + "INFO" + ChatColor.GRAY + "]" + ChatColor.WHITE + " (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void warn(final String s) {
        LOGGER.info(ChatColor.GRAY + "[" + ChatColor.ORANGE + "WARN" + ChatColor.GRAY + "]" + ChatColor.WHITE + " (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void debug(final String s) {
        LOGGER.info(ChatColor.GRAY + "[" + ChatColor.YELLOW + "DEBUG" + ChatColor.GRAY + "]" + ChatColor.WHITE + " (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void error(final String s) {
        LOGGER.info(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "ERROR" + ChatColor.GRAY + "]" + ChatColor.WHITE + " (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void dev(final String s) {
        LOGGER.info(ChatColor.GRAY + "[" + ChatColor.PURPLE + "DEVELOPMENT" + ChatColor.GRAY + "]" + ChatColor.WHITE + " (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }
}