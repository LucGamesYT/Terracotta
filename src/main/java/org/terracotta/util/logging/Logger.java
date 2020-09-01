package org.terracotta.util.logging;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

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
@Log4j2
public class Logger implements ILogger {

    @Override
    public void info(final String s) {
        Logger.log.info("§7[§bINFO§7]§f (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void warn(final String s) {
        Logger.log.info("§7[§6WARN§7]§f (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void debug(final String s) {
        Logger.log.info("§7[§eDEBUG§7]§f (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void error(final String s) {
        Logger.log.info("§7[§4INFO§7]§f (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }

    @Override
    public void dev(final String s) {
        Logger.log.info("§7[§aDEVELOPMENT§7]§f (Thread: " + Thread.currentThread().getName() + ")  " + s);
    }
}