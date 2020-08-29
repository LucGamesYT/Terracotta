package org.terracotta.util.logging;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public interface ILogger {

    /**
     * Sends a message on the terminal with an information prefix
     *
     * @param s message which should be sent
     */
    void info(final String s);

    /**
     * Sends a message on the terminal with a warn prefix
     *
     * @param s message which should be sent
     */
    void warn(final String s);

    /**
     * Sends a message on the terminal with a debug prefix
     *
     * @param s message which should be printed
     */
    void debug(final String s);

    /**
     * Sends a message on the terminal with an error prefix
     *
     * @param s message which should be sent
     */
    void error(final String s);

    /**
     * Sends a message on the terminal with a development prefix
     *
     * @param s message which should be sent
     */
    void dev(final String s);
}