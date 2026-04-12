package com.taskmanagement.command;

import com.taskmanagement.domain.Task;
import com.taskmanagement.repository.AssignmentCatalog;
import com.taskmanagement.repository.AssignmentRepository;
import com.taskmanagement.repository.TaskRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Command to export tasks to an external destination
 */
public class ExportCommand implements Command {
    private TaskRepository taskRepository;
    private String exportDestination;
    private ExportData exportData = new ExportData();
    private AssignmentRepository assignmentRepository = AssignmentCatalog.getInstance();
    private List<Task> tasksOverride;
    private boolean useImportCsvFormat;

    public ExportCommand() {}

    public ExportCommand(TaskRepository taskRepository, String exportDestination) {
        this.taskRepository = taskRepository;
        this.exportDestination = exportDestination;
    }

    public ExportCommand(TaskRepository taskRepository, String exportDestination, ExportData exportData) {
        this.taskRepository = taskRepository;
        this.exportDestination = exportDestination;
        this.exportData = exportData != null ? exportData : new ExportData();
    }

    @Override
    public void execute() {
        if (taskRepository == null) {
            throw new IllegalStateException("Task repository cannot be null");
        }
        if (exportDestination == null || exportDestination.trim().isEmpty()) {
            throw new IllegalStateException("Export destination cannot be null or empty");
        }

        try {
            List<Task> tasksToExport = tasksOverride != null ? new ArrayList<>(tasksOverride) : taskRepository.findAll();
            if (useImportCsvFormat) {
                exportData.writeTasksToImportCsv(exportDestination, tasksToExport, assignmentRepository);
            } else {
                exportData.writeTasksToCsv(exportDestination, tasksToExport);
            }

            System.out.println(tasksToExport.size() + " task(s) exported to: " + exportDestination);
        } catch (IOException e) {
            throw new RuntimeException("Failed to export tasks to: " + exportDestination, e);
        }
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public String getExportDestination() {
        return exportDestination;
    }

    public void setExportDestination(String exportDestination) {
        this.exportDestination = exportDestination;
    }

    public ExportData getExportData() {
        return exportData;
    }

    public void setExportData(ExportData exportData) {
        this.exportData = exportData;
    }

    public AssignmentRepository getAssignmentRepository() {
        return assignmentRepository;
    }

    public void setAssignmentRepository(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Task> getTasksOverride() {
        return tasksOverride == null ? null : new ArrayList<>(tasksOverride);
    }

    public void setTasksOverride(List<Task> tasksOverride) {
        this.tasksOverride = tasksOverride == null ? null : new ArrayList<>(tasksOverride);
    }

    public boolean isUseImportCsvFormat() {
        return useImportCsvFormat;
    }

    public void setUseImportCsvFormat(boolean useImportCsvFormat) {
        this.useImportCsvFormat = useImportCsvFormat;
    }
}
