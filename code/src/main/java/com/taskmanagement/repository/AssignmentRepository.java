package com.taskmanagement.repository;

import com.taskmanagement.domain.Assignment;

import java.util.List;

/**
 * Repository interface for assignment operations.
 */
public interface AssignmentRepository {
    void addAssignment(Assignment assignment);

    void updateAssignment(Assignment assignment);

    void removeAssignment(Assignment assignment);

    List<Assignment> findByTaskId(String taskId);

    List<Assignment> findByCollaboratorName(String collaboratorName);

    List<Assignment> findAll();
}
