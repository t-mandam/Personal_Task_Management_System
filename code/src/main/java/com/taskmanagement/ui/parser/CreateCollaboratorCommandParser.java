package com.taskmanagement.ui.parser;

import com.taskmanagement.command.Command;
import com.taskmanagement.command.CreateCollaboratorCommand;

/**
 * Parses CLI arguments for create-collaborator command.
 */
public class CreateCollaboratorCommandParser {

    public Command parse(String args) {
        if (args == null || args.trim().isEmpty()) {
            throw new IllegalArgumentException("Usage: create-collaborator <type> <name>");
        }

        String[] parts = args.trim().split("\\s+", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("Usage: create-collaborator <type> <name>");
        }

        return new CreateCollaboratorCommand(parts[0], parts[1]);
    }
}
