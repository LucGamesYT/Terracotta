package org.terracotta.level;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public interface Vector3 {

    /**
     * Multiplies this Vector with a given value
     *
     * @param value which is the multiplier
     *
     * @return a fresh {@link Vector3} with values that were multiplied by @code value
     */
    Vector3 multiply(final float value);

    /**
     * Divides this Vector with a given value
     *
     * @param value which is the multiplier
     *
     * @return a fresh {@link Vector3} with values that were divided by @code value
     */
    Vector3 divide(final float value);

    /**
     * Adds the given x y and z values to this Vector
     *
     * @param x value, which should be added
     * @param y value, which should be added
     * @param z value, which should be added
     *
     * @return a fresh {@link Vector3} with values that were changed by @code x, y, z parameters
     */
    Vector3 add(final float x, final float y, final float z);

    /**
     * Removes the given x y and z values from this Vector
     *
     * @param x value, which should be removed
     * @param y value, which should be removed
     * @param z value, which should be removed
     *
     * @return a fresh {@link Vector3} with values that were changed by @code x, y, z parameters
     */
    Vector3 subtract(final float x, final float y, final float z);

    /**
     * Rounds off this Vector
     *
     * @return a fresh {@link Vector3}
     */
    Vector3 ceil();

    /**
     * Rounds up this Vector
     *
     * @return a fresh {@link Vector3}
     */
    Vector3 floor();

    /**
     * Rounds this Vector commercially
     *
     * @return a fresh {@link Vector3}
     */
    Vector3 round();
}