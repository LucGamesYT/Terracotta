package org.terracotta.util.math;

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
public class BoundingBox implements IBoundingBox {

    private float minX;
    private float minY;
    private float minZ;
    private float maxX;
    private float maxY;
    private float maxZ;

    public BoundingBox(final Vector3f positionA, final Vector3f positionB) {
        this.minX = positionA.getX();
        this.minY = positionA.getY();
        this.minZ = positionA.getZ();

        this.maxX = positionB.getX();
        this.maxY = positionB.getY();
        this.maxZ = positionB.getZ();
    }

    @Override
    public BoundingBox setBounds(final float minX, final float minY, final float minZ, final float maxX, final float maxY, final float maxZ) {
        return new BoundingBox(new Vector3f(minX, minY, minZ), new Vector3f(maxX, maxY, maxZ));
    }

    @Override
    public BoundingBox changeSize(final float x, final float y, final float z, final boolean decrease) {
        if (decrease) {
            this.minX += x;
            this.minY += y;
            this.minZ += z;

            this.maxX -= x;
            this.maxY -= y;
            this.maxZ -= z;
        } else {
            this.minX -= x;
            this.minY -= y;
            this.minZ -= z;

            this.maxX += x;
            this.maxY += y;
            this.maxZ += z;
        }

        return this;
    }


    @Override
    public BoundingBox addOffset(final float x, final float y, final float z) {
        this.minX += x;
        this.minY += y;
        this.minZ += z;

        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;

        return this;
    }
}