package com.taskmanagement.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates short sequential string identifiers for newly created objects.
 */
public final class SimpleIdGenerator {
    private static final AtomicLong COUNTER = new AtomicLong(0);

    private SimpleIdGenerator() {
    }

    public static String nextId() {
        return String.valueOf(COUNTER.incrementAndGet());
    }

    public static void initializeAtLeast(long minimumValue) {
        COUNTER.updateAndGet(currentValue -> Math.max(currentValue, minimumValue));
    }

    public static void initializeFromId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return;
        }

        try {
            initializeAtLeast(Long.parseLong(id.trim()));
        } catch (NumberFormatException ignored) {
            // Keep the counter unchanged for non-numeric identifiers.
        }
    }
}