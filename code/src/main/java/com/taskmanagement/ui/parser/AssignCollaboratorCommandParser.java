package com.taskmanagement.ui.parser;

import com.taskmanagement.command.AssignCollaboratorCommand;
import com.taskmanagement.command.Command;

/**
 * Parses CLI arguments for assign-collaborator command.
 */
public class AssignCollaboratorCommandParser {

    public Command parse(String args) {
        if (args == null || args.trim().isEmpty()) {
            throw new IllegalArgumentException("Usage: assign-collaborator <task-id> <collaborator-name>");
        }

        String[] parts = args.trim().split("\\s+", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Usage: assign-collaborator <task-id> <collaborator-name>");
        }

        return new AssignCollaboratorCommand(parts[0], parts[1]);
    }
}
