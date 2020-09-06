package org.terracotta.util.io;

import lombok.Getter;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public enum ServerFile {

    JSON_BANNED_PLAYERS("banned-players.json"),
    JSON_BANNED_PLAYERS_IP("banned-players-ip.json"),
    JSON_OPERATORS("operators.json"),
    JSON_WHITELIST("whitelist.json"),
    PROPERTIES_SERVER("server.properties"),
    YML_TERRACOTTA("terracotta.yml");

    @Getter
    private final String name;

    ServerFile(final String name) {
        this.name = name;
    }
}