package org.terracotta.level;

import lombok.Getter;
import lombok.ToString;

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
@ToString
@Getter
public class Vector3f implements Vector3, Comparable<Vector3f>, Serializable, Cloneable {

    protected float x;
    protected float y;
    protected float z;

    public Vector3f() {
        new Vector3f(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Vector3 multiply(final float value) {
        return new Vector3f(this.x * value, this.y * value, this.z * value);
    }

    @Override
    public Vector3 divide(final float value) {
        return new Vector3f(this.x / value, this.y / value, this.z / value);
    }

    @Override
    public Vector3 add(final float x, final float y, final float z) {
        return new Vector3f(this.x + x, this.y + y, this.z + z);
    }

    @Override
    public Vector3 subtract(final float x, final float y, final float z) {
        return new Vector3f(this.x - x, this.y - y, this.z - z);
    }

    @Override
    public Vector3 ceil() {
        return new Vector3f((float) Math.ceil(this.x), (float) Math.ceil(this.y), (float) Math.ceil(this.z));
    }

    @Override
    public Vector3 floor() {
        return new Vector3f((float) Math.floor(this.x), (float) Math.floor(this.y), (float) Math.floor(this.z));
    }

    @Override
    public Vector3 round() {
        return new Vector3f((float) Math.round(this.x), (float) Math.round(this.y), (float) Math.round(this.z));
    }

    @Override
    public int compareTo(@Nonnull final Vector3f vector3f) {
        return (int) Math.signum(this.lengthSquared() - vector3f.lengthSquared());
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
        return this.compareTo((Vector3f) other) == 0;
    }

    private float lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }
}