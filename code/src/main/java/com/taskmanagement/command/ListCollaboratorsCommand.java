package com.taskmanagement.command;

import com.taskmanagement.domain.Collaborator;
import com.taskmanagement.repository.CollaboratorCatalog;
import com.taskmanagement.repository.CollaboratorRepository;

import java.util.List;

/**
 * Command to list all collaborators.
 */
public class ListCollaboratorsCommand implements Command {
    private final CollaboratorRepository collaboratorRepository;

    public ListCollaboratorsCommand() {
        this(CollaboratorCatalog.getInstance());
    }

    public ListCollaboratorsCommand(CollaboratorRepository collaboratorRepository) {
        this.collaboratorRepository = collaboratorRepository;
    }

    @Override
    public void execute() {
        if (collaboratorRepository == null) {
            throw new IllegalStateException("Collaborator repository cannot be null");
        }

        List<Collaborator> collaborators = collaboratorRepository.findAll();
        if (collaborators.isEmpty()) {
            System.out.println("No collaborators found.");
            return;
        }

        System.out.println("Collaborators:");
        for (Collaborator collaborator : collaborators) {
            System.out.println("- " + collaborator);
        }
    }
}
