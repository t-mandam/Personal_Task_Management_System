package com.taskmanagement.domain;

/**
 * Represents a senior level collaborator
 */
public class Senior extends Collaborator {
    private static final int MAX_OPEN_TASKS = 2;

    public Senior() {
        super();
    }

    public Senior(String name) {
        super(name);
    }

    public int getMaxOpenTasks() {
        return MAX_OPEN_TASKS;
    }

    public boolean canAcceptOpenTask(int currentOpenTasks) {
        return currentOpenTasks < MAX_OPEN_TASKS;
    }
}