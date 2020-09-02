package org.terracotta.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public class Entities {

    private static final List<Integer> usedRuntimeIds = new ArrayList<>();

    public static void addUsedRuntimeId(final int runtimeId) {
        Entities.usedRuntimeIds.add(runtimeId);
    }

    public static void releaseRuntimeId(final int runtimeId) {
        Entities.usedRuntimeIds.remove(runtimeId - 1);
    }

    public static boolean isRuntimeIdUsed(final int runtimeId) {
        return Entities.usedRuntimeIds.contains(runtimeId);
    }
}