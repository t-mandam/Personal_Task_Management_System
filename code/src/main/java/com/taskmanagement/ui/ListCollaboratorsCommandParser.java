package com.taskmanagement.ui;

import com.taskmanagement.command.Command;
import com.taskmanagement.command.ListCollaboratorsCommand;

/**
 * Parses CLI arguments for list-collaborators command.
 */
public class ListCollaboratorsCommandParser {

    public Command parse(String args) {
        if (args != null && !args.trim().isEmpty()) {
            throw new IllegalArgumentException("Usage: list-collaborators");
        }

        return new ListCollaboratorsCommand();
    }
}
