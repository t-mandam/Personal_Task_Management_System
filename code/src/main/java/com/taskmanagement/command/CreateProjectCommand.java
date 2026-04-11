package com.taskmanagement.command;

import com.taskmanagement.domain.Project;
import com.taskmanagement.repository.ProjectCatalog;
import com.taskmanagement.repository.ProjectRepository;

/**
 * Command to create a project.
 */
public class CreateProjectCommand implements Command {
    private final ProjectRepository projectRepository;
    private final String projectName;
    private final String projectDescription;

    public CreateProjectCommand(String projectName) {
        this(ProjectCatalog.getInstance(), projectName, null);
    }

    public CreateProjectCommand(String projectName, String projectDescription) {
        this(ProjectCatalog.getInstance(), projectName, projectDescription);
    }

    public CreateProjectCommand(ProjectRepository projectRepository, String projectName, String projectDescription) {
        this.projectRepository = projectRepository;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
    }

    @Override
    public void execute() {
        if (projectRepository == null) {
            throw new IllegalStateException("Project repository cannot be null");
        }
        if (projectName == null || projectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty");
        }

        String normalizedName = projectName.trim();
        String normalizedDescription = projectDescription == null ? null : projectDescription.trim();

        Project project = new Project(normalizedName, normalizedDescription);
        projectRepository.addProject(project);

        if (normalizedDescription == null || normalizedDescription.isEmpty()) {
            System.out.println("Project created: " + project.getName());
        } else {
            System.out.println("Project created: " + project.getName() + " | Description: " + project.getDescription());
        }
    }
}
