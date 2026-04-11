package com.taskmanagement.command;

import com.taskmanagement.domain.Collaborator;
import com.taskmanagement.domain.Intermediate;
import com.taskmanagement.domain.Junior;
import com.taskmanagement.domain.Senior;
import com.taskmanagement.repository.CollaboratorCatalog;
import com.taskmanagement.repository.CollaboratorRepository;

/**
 * Command to create a collaborator.
 */
public class CreateCollaboratorCommand implements Command {
    private final CollaboratorRepository collaboratorRepository;
    private final String collaboratorType;
    private final String collaboratorName;

    public CreateCollaboratorCommand(String collaboratorType, String collaboratorName) {
        this(CollaboratorCatalog.getInstance(), collaboratorType, collaboratorName);
    }

    public CreateCollaboratorCommand(CollaboratorRepository collaboratorRepository, String collaboratorType, String collaboratorName) {
        this.collaboratorRepository = collaboratorRepository;
        this.collaboratorType = collaboratorType;
        this.collaboratorName = collaboratorName;
    }

    @Override
    public void execute() {
        if (collaboratorRepository == null) {
            throw new IllegalStateException("Collaborator repository cannot be null");
        }
        if (collaboratorType == null || collaboratorType.trim().isEmpty()) {
            throw new IllegalArgumentException("Collaborator type cannot be null or empty");
        }
        if (collaboratorName == null || collaboratorName.trim().isEmpty()) {
            throw new IllegalArgumentException("Collaborator name cannot be null or empty");
        }

        Collaborator collaborator = createCollaborator(collaboratorType.trim().toLowerCase(), collaboratorName.trim());
        collaboratorRepository.addCollaborator(collaborator);
        System.out.println("Collaborator created: " + collaborator);
    }

    private Collaborator createCollaborator(String type, String name) {
        switch (type) {
            case "junior":
                return new Junior(name);
            case "intermediate":
                return new Intermediate(name);
            case "senior":
                return new Senior(name);
            default:
                throw new IllegalArgumentException("Invalid collaborator type. Valid values: junior, intermediate, senior");
        }
    }
}
