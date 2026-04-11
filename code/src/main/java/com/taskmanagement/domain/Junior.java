package com.taskmanagement.domain;

/**
 * Represents a junior level collaborator
 */
public class Junior extends Collaborator {
    private static final int MAX_OPEN_TASKS = 10;

    public Junior() {
        super();
    }

    public Junior(String name) {
        super(name);
    }

    public int getMaxOpenTasks() {
        return MAX_OPEN_TASKS;
    }

    public boolean canAcceptOpenTask(int currentOpenTasks) {
        return currentOpenTasks < MAX_OPEN_TASKS;
    }
}