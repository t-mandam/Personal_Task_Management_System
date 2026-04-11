package com.taskmanagement.ui.parser;

import com.taskmanagement.command.Command;
import com.taskmanagement.command.ListProjectsCommand;

/**
 * Parses CLI arguments for list-projects command.
 */
public class ListProjectsCommandParser {

    public Command parse(String args) {
        if (args != null && !args.trim().isEmpty()) {
            throw new IllegalArgumentException("Usage: list-projects");
        }

        return new ListProjectsCommand();
    }
}
