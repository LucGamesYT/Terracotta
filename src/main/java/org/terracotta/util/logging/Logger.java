package org.terracotta.util.logging;

import lombok.NoArgsConstructor;

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
public class Logger implements ILogger {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";

    @Override
    public void info(final String s) {
        System.out.println("[" + Logger.ANSI_CYAN + "INFO" + Logger.ANSI_RESET + "] (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void warn(final String s) {
        System.out.println("[" + Logger.ANSI_YELLOW + "WARN" + Logger.ANSI_RESET + "] (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void debug(final String s) {
        System.out.println("[" + Logger.ANSI_YELLOW + "DEBUG" + Logger.ANSI_RESET + "] (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void error(final String s) {
        System.out.println("[" + Logger.ANSI_RED + "ERROR" + Logger.ANSI_RESET + "] (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void dev(final String s) {
        System.out.println("[" + Logger.ANSI_PURPLE + "DEVELOPMENT" + Logger.ANSI_RESET + "] (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }
}