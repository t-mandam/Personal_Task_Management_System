package com.taskmanagement.command;

import com.taskmanagement.observer.Activity;
import com.taskmanagement.persistence.DatabaseConnection;
import com.taskmanagement.persistence.activity.ActivityTableDataGateway;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Command to list activities, optionally filtered by task ID.
 */
public class ListActivitiesCommand implements Command {
    private final ActivityTableDataGateway activityGateway;
    private final String taskId;

    public ListActivitiesCommand() {
        this(null);
    }

    public ListActivitiesCommand(String taskId) {
        this(new ActivityTableDataGateway(DatabaseConnection.getInstance()), taskId);
    }

    public ListActivitiesCommand(ActivityTableDataGateway activityGateway, String taskId) {
        this.activityGateway = activityGateway;
        this.taskId = normalize(taskId);
    }

    @Override
    public void execute() {
        if (activityGateway == null) {
            throw new IllegalStateException("Activity gateway cannot be null");
        }

        try {
            List<Activity> activities = taskId == null
                    ? activityGateway.findAll()
                    : activityGateway.findByTaskId(taskId);

            if (activities.isEmpty()) {
                if (taskId == null) {
                    System.out.println("No activities found.");
                } else {
                    System.out.println("No activities found for task ID '" + taskId + "'.");
                }
                return;
            }

            if (taskId == null) {
                System.out.println("Found " + activities.size() + " activit(y/ies):");
            } else {
                System.out.println("Found " + activities.size() + " activit(y/ies) for task ID '" + taskId + "':");
            }

            printActivities(activities);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to load activities", ex);
        }
    }

    private void printActivities(List<Activity> activities) {
        int timestampWidth = 19;
        int taskIdWidth = 12;
        int descriptionWidth = 72;

        String divider = "+" + repeat("-", timestampWidth + 2)
                + "+" + repeat("-", taskIdWidth + 2)
                + "+" + repeat("-", descriptionWidth + 2)
                + "+";

        System.out.println(divider);
        System.out.println("| " + pad("Timestamp", timestampWidth)
                + " | " + pad("Task ID", taskIdWidth)
                + " | " + pad("Description", descriptionWidth)
                + " |");
        System.out.println(divider);

        for (Activity activity : activities) {
            String timestamp = activity != null && activity.getTimestamp() != null
                    ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(activity.getTimestamp())
                    : "-";
            String activityTaskId = activity == null ? "-" : safe(activity.getTaskId());
            String description = activity == null ? "-" : safe(activity.getDescription());

            System.out.println("| " + pad(timestamp, timestampWidth)
                    + " | " + pad(activityTaskId, taskIdWidth)
                    + " | " + pad(description, descriptionWidth)
                    + " |");
        }

        System.out.println(divider);
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String safe(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }

    private String pad(String value, int width) {
        String text = value == null ? "-" : value;
        if (text.length() > width) {
            if (width <= 1) {
                return text.substring(0, width);
            }
            return text.substring(0, width - 1) + "~";
        }
        return text + repeat(" ", width - text.length());
    }

    private String repeat(String text, int count) {
        if (count <= 0) {
            return "";
        }
        return String.valueOf(text).repeat(count);
    }
}
