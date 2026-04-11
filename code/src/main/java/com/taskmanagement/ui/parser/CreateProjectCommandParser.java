package com.taskmanagement.ui.parser;

import com.taskmanagement.command.Command;
import com.taskmanagement.command.CreateProjectCommand;

/**
 * Parses CLI arguments for create-project command.
 */
public class CreateProjectCommandParser {

    public Command parse(String args) {
        if (args == null || args.trim().isEmpty()) {
            throw new IllegalArgumentException("Usage: create-project <project-name> [| <description>]");
        }

        String[] parts = args.split("\\|", 2);
        String projectName = parts[0].trim();
        String description = parts.length > 1 ? parts[1].trim() : null;

        if (projectName.isEmpty()) {
            throw new IllegalArgumentException("Usage: create-project <project-name> [| <description>]");
        }

        if (description != null && description.isEmpty()) {
            description = null;
        }

        return new CreateProjectCommand(projectName, description);
    }
}
