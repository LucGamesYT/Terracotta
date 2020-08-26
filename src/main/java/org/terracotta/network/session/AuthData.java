package org.terracotta.network.session;

import lombok.Data;

import java.util.UUID;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
@Data
public class AuthData {

    private final String displayName;
    private final UUID uniqueId;
    private final String xuid;
}