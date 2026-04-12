package com.taskmanagement.ui.parser;

import com.taskmanagement.command.Command;
import com.taskmanagement.command.ListActivitiesCommand;

/**
 * Parses CLI arguments for activity-log command.
 */
public class ListActivitiesCommandParser {

    public Command parse(String args) {
        if (args == null || args.trim().isEmpty()) {
            return new ListActivitiesCommand();
        }

        String normalized = args.trim();
        String[] parts = normalized.split("\\s+");
        if (parts.length != 1) {
            throw new IllegalArgumentException("Usage: activity-log [task-id]");
        }

        return new ListActivitiesCommand(parts[0]);
    }
}
