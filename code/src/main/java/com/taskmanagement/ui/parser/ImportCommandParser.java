package com.taskmanagement.ui.parser;

import com.taskmanagement.command.Command;
import com.taskmanagement.command.ImportCommand;
import com.taskmanagement.repository.AssignmentCatalog;
import com.taskmanagement.repository.CollaboratorCatalog;
import com.taskmanagement.repository.ProjectCatalog;
import com.taskmanagement.repository.TaskCatalog;

/**
 * Parses CLI arguments for import command.
 */
public class ImportCommandParser {

    public Command parse(String args) {
        if (args == null || args.trim().isEmpty()) {
            throw new IllegalArgumentException("Usage: import <csv-file-path>");
        }

        String importSource = args.trim();
        return new ImportCommand(
                TaskCatalog.getInstance(),
                ProjectCatalog.getInstance(),
                CollaboratorCatalog.getInstance(),
                AssignmentCatalog.getInstance(),
                importSource
        );
    }
}
