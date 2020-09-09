package org.terracotta.entity;

import lombok.Getter;
import org.terracotta.level.Position;
import org.terracotta.level.Vector3f;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public abstract class Entity {

    protected int NETWORK_ID = -1;

    @Getter
    private int runtimeId;
    @Getter
    private Position position;
    @Getter
    private Vector3f velocity;

    public abstract int getNetworkId();
}