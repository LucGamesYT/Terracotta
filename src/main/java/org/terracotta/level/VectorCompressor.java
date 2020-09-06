package org.terracotta.level;

import lombok.NoArgsConstructor;

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
public class VectorCompressor {

    /**
     * Compresses a protocol Vector to a Terracotta Vector
     *
     * @param vector3f which should be compressed
     *
     * @return a fresh {@link org.terracotta.level.Vector3f}
     */
    public static Vector3f compressFromProtocolVector(final com.nukkitx.math.vector.Vector3f vector3f) {
        return new Vector3f(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    /**
     * Compresses a Terracotta Vector to a Protocol Vector
     *
     * @param vector3f which should be compressed
     *
     * @return a fresh {@link com.nukkitx.math.vector.Vector3f}
     */
    public static com.nukkitx.math.vector.Vector3f compressToProtocolVector(final Vector3f vector3f) {
        return com.nukkitx.math.vector.Vector3f.ZERO.add(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }
}