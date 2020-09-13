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

    @Getter
    protected long id;
    @Getter
    protected EntityType entityType;
    @Getter
    protected Position position;
    @Getter
    protected Vector3f velocity;
}