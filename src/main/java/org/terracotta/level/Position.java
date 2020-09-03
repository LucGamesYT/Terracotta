package org.terracotta.level;

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
public class Position extends Vector3f {

    @Getter
    private float yaw;
    @Getter
    private float pitch;

    public Position() {
        new Position(0.0f, 0.0f, 0.0f);
    }

    public Position(final float x, final float y, final float z) {
        new Position(x, y, z, 0.0f, 0.0f);
    }

    public Position(final float x, final float y, final float z, final float yaw, final float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
}