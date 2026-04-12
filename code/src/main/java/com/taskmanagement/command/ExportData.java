package com.taskmanagement.command;

import com.taskmanagement.domain.Assignment;
import com.taskmanagement.domain.Collaborator;
import com.taskmanagement.domain.Subtask;
import com.taskmanagement.domain.Tag;
import com.taskmanagement.domain.Task;
import com.taskmanagement.enums.Status;
import com.taskmanagement.repository.AssignmentRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Handles writing task data to a CSV destination.
 */
public class ExportData {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DUE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void writeTasksToCsv(String exportDestination, List<Task> tasks) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportDestination))) {
            writer.write("id,title,description,creationDate,dueDate,priority,status,tags");
            writer.newLine();

            for (Task task : tasks) {
                writer.write(toCsvRow(task));
                writer.newLine();
            }
        }
    }

    public void writeTasksToImportCsv(String exportDestination,
                                      List<Task> tasks,
                                      AssignmentRepository assignmentRepository) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(exportDestination))) {
            writer.write("TaskName,Description,Subtask,Status,Priority,DueDate,ProjectName,ProjectDescription,Collaborator,CollaboratorCategory");
            writer.newLine();

            for (Task task : tasks) {
                for (String row : toImportCsvRows(task, assignmentRepository)) {
                    writer.write(row);
                    writer.newLine();
                }
            }
        }
    }

    private String toCsvRow(Task task) {
        return String.join(",",
                escape(task.getId()),
                escape(task.getTitle()),
                escape(task.getDescription()),
                escape(formatDateTime(task.getCreationDate())),
                escape(formatDueDate(task.getDueDate())),
                escape(task.getPriority() != null ? task.getPriority().name() : ""),
                escape(task.getStatus() != null ? task.getStatus().name() : ""),
                escape(formatTags(task))
        );
    }

    private List<String> toImportCsvRows(Task task, AssignmentRepository assignmentRepository) {
        if (task == null) {
            return Collections.emptyList();
        }

        List<String> subtaskTitles = getSubtaskTitles(task);
        List<Assignment> assignments = getAssignments(task, assignmentRepository);
        if (assignments.isEmpty()) {
            assignments = Collections.singletonList(null);
        }

        List<String> rows = new ArrayList<>();
        for (String subtaskTitle : subtaskTitles) {
            for (Assignment assignment : assignments) {
                Collaborator collaborator = assignment == null ? null : assignment.getCollaborator();
                String collaboratorName = collaborator == null ? "" : collaborator.getName();
                String collaboratorCategory = collaborator == null
                        ? ""
                        : collaborator.getClass().getSimpleName().toLowerCase(Locale.ROOT);

                rows.add(String.join(",",
                        escape(task.getTitle()),
                        escape(task.getDescription()),
                        escape(subtaskTitle),
                        escape(task.getStatus() != null ? task.getStatus().name() : Status.OPEN.name()),
                        escape(task.getPriority() != null ? task.getPriority().name() : ""),
                        escape(formatDueDate(task.getDueDate())),
                        escape(task.getProject() != null ? task.getProject().getName() : ""),
                        escape(task.getProject() != null ? task.getProject().getDescription() : ""),
                        escape(collaboratorName),
                        escape(collaboratorCategory)
                ));
            }
        }

        return rows;
    }

    private List<String> getSubtaskTitles(Task task) {
        List<String> subtaskTitles = new ArrayList<>();

        if (task.getSubtasks() != null) {
            for (Subtask subtask : task.getSubtasks()) {
                if (subtask == null || subtask.getTitle() == null || subtask.getTitle().trim().isEmpty()) {
                    continue;
                }
                subtaskTitles.add(subtask.getTitle());
            }
        }

        if (subtaskTitles.isEmpty()) {
            subtaskTitles.add("");
        }

        return subtaskTitles;
    }

    private List<Assignment> getAssignments(Task task, AssignmentRepository assignmentRepository) {
        if (assignmentRepository == null || task.getId() == null || task.getId().trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<Assignment> assignments = assignmentRepository.findByTaskId(task.getId().trim());
        return assignments == null ? new ArrayList<>() : assignments;
    }

    private String formatDateTime(java.util.Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(date);
    }

    private String formatDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            return "";
        }
        return dueDate.format(DUE_DATE_FORMATTER);
    }

    private String formatTags(Task task) {
        if (task.getTags() == null || task.getTags().isEmpty()) {
            return "";
        }

        return task.getTags()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.joining("|"));
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }

        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }
}
