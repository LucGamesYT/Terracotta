package org.terracotta.level;

import lombok.Getter;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public class Position implements Vector3, Comparable<Position>, Serializable, Cloneable {

    @Getter
    private float x;
    @Getter
    private float y;
    @Getter
    private float z;
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

    @Override
    public Vector3 multiply(float value) {
        return new Position(this.x * value, this.y * value, this.z * value);
    }

    @Override
    public Vector3 divide(float value) {
        return new Position(this.x / value, this.y / value, this.z / value);
    }

    @Override
    public Vector3 add(float x, float y, float z) {
        return new Position(this.x + x, this.y + y, this.z + z);
    }

    @Override
    public Vector3 subtract(float x, float y, float z) {
        return new Position(this.x - x, this.y - y, this.z - z);
    }

    @Override
    public Vector3 ceil() {
        return new Position((float) Math.ceil(this.x), (float) Math.ceil(this.y), (float) Math.ceil(this.z));
    }

    @Override
    public Vector3 floor() {
        return new Position((float) Math.floor(this.x), (float) Math.floor(this.y), (float) Math.floor(this.z));
    }

    @Override
    public Vector3 round() {
        return new Position((float) Math.round(this.x), (float) Math.round(this.y), (float) Math.round(this.z));
    }

    @Override
    public int compareTo(@Nonnull final Position position) {
        return (int) Math.signum(this.lengthSquared() - position.lengthSquared());
    }

    @Override
    public boolean equals(final Object other) {
        // null acceptance | identical types
        if (other == null || !this.getClass().equals(other.getClass())) {
            return false;
        }

        // Reflexivity
        if (this == other) {
            return true;
        }
        return this.compareTo((Position) other) == 0;
    }

    private float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }
}