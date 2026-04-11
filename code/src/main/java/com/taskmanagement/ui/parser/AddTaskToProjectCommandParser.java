package com.taskmanagement.ui.parser;

import com.taskmanagement.command.AddTaskToProjectCommand;
import com.taskmanagement.command.Command;

/**
 * Parses CLI arguments for add-task-to-project command.
 */
public class AddTaskToProjectCommandParser {

    public Command parse(String args) {
        if (args == null || args.trim().isEmpty()) {
            throw new IllegalArgumentException("Usage: add-task-to-project <task-id> <project-name>");
        }

        String[] parts = args.trim().split("\\s+", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Usage: add-task-to-project <task-id> <project-name>");
        }

        return new AddTaskToProjectCommand(parts[0], parts[1]);
    }
}
