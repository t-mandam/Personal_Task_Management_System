package com.taskmanagement.ui.parser;

import com.taskmanagement.command.Command;
import com.taskmanagement.command.ExportCommand;
import com.taskmanagement.repository.AssignmentCatalog;
import com.taskmanagement.repository.TaskCatalog;

/**
 * Parses CLI arguments for export command.
 */
public class ExportCommandParser {
    private final SearchTaskCommandParser searchTaskCommandParser;

    public ExportCommandParser(SearchTaskCommandParser searchTaskCommandParser) {
        this.searchTaskCommandParser = searchTaskCommandParser;
    }

    public Command parse(String args) {
        if (args == null || args.trim().isEmpty()) {
            throw new IllegalArgumentException("Usage: export <csv-file-path>");
        }
        if (searchTaskCommandParser == null || !searchTaskCommandParser.hasExecutedSearch()) {
            throw new IllegalStateException("No search results available. Run search-task first, then export.");
        }

        String exportDestination = args.trim();
        ExportCommand exportCommand = new ExportCommand(TaskCatalog.getInstance(), exportDestination);
        exportCommand.setTasksOverride(searchTaskCommandParser.getLastSearchResults());
        exportCommand.setUseImportCsvFormat(true);
        exportCommand.setAssignmentRepository(AssignmentCatalog.getInstance());
        return exportCommand;
    }
}
