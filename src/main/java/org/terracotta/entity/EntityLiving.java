package org.terracotta.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Copyright (c) 2020, TerracottaMC and Kaooot
 * <p>
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @author Kaooot
 * @version 1.0
 */
public class EntityLiving extends Entity {

    @Getter
    @Setter
    private float health;
    @Getter
    @Setter
    private float maxHealth;
    @Getter
    private boolean onFire;
}