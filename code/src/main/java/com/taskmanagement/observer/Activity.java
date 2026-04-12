package com.taskmanagement.observer;

import java.util.Date;

/**
 * Represents an activity/change in the system
 */
public class Activity {
    private Date timestamp;
    private String taskId;
    private String description;

    public Activity() {
        this.timestamp = new Date();
    }

    public Activity(String description) {
        this();
        this.description = description;
    }

    // Getters and setters
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        if (taskId == null || taskId.trim().isEmpty()) {
            return "[" + timestamp + "] " + description;
        }
        return "[" + timestamp + "] [Task " + taskId + "] " + description;
    }
}