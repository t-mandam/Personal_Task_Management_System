package com.taskmanagement.util;

import java.util.UUID;

/**
 * Generates non-numeric identifiers for non-task entities.
 */
public final class EntityIdGenerator {

    private EntityIdGenerator() {
    }

    public static String nextId() {
        return UUID.randomUUID().toString();
    }
}