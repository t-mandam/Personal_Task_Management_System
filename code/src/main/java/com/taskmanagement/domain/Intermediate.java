package com.taskmanagement.domain;

/**
 * Represents an intermediate level collaborator
 */
public class Intermediate extends Collaborator {
    private static final int MAX_OPEN_TASKS = 5;

    public Intermediate() {
        super();
    }

    public Intermediate(String name) {
        super(name);
    }

    public int getMaxOpenTasks() {
        return MAX_OPEN_TASKS;
    }

    public boolean canAcceptOpenTask(int currentOpenTasks) {
        return currentOpenTasks < MAX_OPEN_TASKS;
    }
}