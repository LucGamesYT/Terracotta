package org.terracotta.util.math;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public interface IBoundingBox extends Cloneable {

    /**
     * Creates the bounds of this {@link BoundingBox}
     *
     * @param minX which is the minimum x value
     * @param minY which is the minimum y value
     * @param minZ which is the minimum z value
     * @param maxX which is the maximum x value
     * @param maxY which is the maximum y value
     * @param maxZ which is the maximum z value
     *
     * @return a fresh bounded {@link BoundingBox}
     */
    BoundingBox setBounds(final float minX, final float minY, final float minZ, final float maxX, final float maxY, final float maxZ);

    /**
     * Changes the size of this {@link BoundingBox}
     *
     * @param x        value which should be changed
     * @param y        value which should be changed
     * @param z        value which should be changed
     * @param decrease whether the BoundingBox should be decreased
     *
     * @return a fresh size changed {@link BoundingBox}
     */
    BoundingBox changeSize(final float x, final float y, final float z, final boolean decrease);

    /**
     * Adds an offset to this {@link BoundingBox}
     *
     * @param x value which represents the x offset
     * @param y value which represents the y offset
     * @param z value which represents the z offset
     *
     * @return a fresh calculated {@link BoundingBox}
     */
    BoundingBox addOffset(final float x, final float y, final float z);
}