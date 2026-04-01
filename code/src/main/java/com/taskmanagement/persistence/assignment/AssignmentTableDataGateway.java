package com.taskmanagement.persistence.assignment;

import com.taskmanagement.persistence.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Table Data Gateway for Assignment entities
 * Encapsulates all SQL operations for the assignments table
 * Assignments represent the many-to-many relationship between Tasks and Collaborators
 */
public class AssignmentTableDataGateway {
    private final DatabaseConnection dbConnection;
    private final AssignmentMapper mapper;

    public AssignmentTableDataGateway(DatabaseConnection dbConnection, AssignmentMapper mapper) {
        this.dbConnection = dbConnection;
        this.mapper = mapper;
    }

    /**
     * Creates an assignment between a task and a collaborator
     * @param taskId the task ID
     * @param collaboratorId the collaborator ID
     * @throws SQLException if database operation fails
     */
    public void create(String taskId, String collaboratorId) throws SQLException {
        String sql = "INSERT INTO assignments (id, task_id, collaborator_id) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Object[] values = mapper.getInsertValues(taskId, collaboratorId);
            for (int i = 0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }

            stmt.executeUpdate();
        }
    }

    /**
     * Removes an assignment between a task and a collaborator
     * @param taskId the task ID
     * @param collaboratorId the collaborator ID
     * @throws SQLException if database operation fails
     */
    public void remove(String taskId, String collaboratorId) throws SQLException {
        String sql = "DELETE FROM assignments WHERE task_id = ? AND collaborator_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            stmt.setString(2, collaboratorId);
            stmt.executeUpdate();
        }
    }

    /**
     * Checks if an assignment exists between a task and collaborator
     * @param taskId the task ID
     * @param collaboratorId the collaborator ID
     * @return true if assignment exists, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean exists(String taskId, String collaboratorId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM assignments WHERE task_id = ? AND collaborator_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            stmt.setString(2, collaboratorId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    /**
     * Finds all collaborators assigned to a specific task
     * @param taskId the task ID
     * @return a list of collaborator IDs assigned to the task
     * @throws SQLException if database operation fails
     */
    public List<String> findCollaboratorsByTask(String taskId) throws SQLException {
        List<String> collaboratorIds = new ArrayList<>();
        String sql = "SELECT collaborator_id FROM assignments WHERE task_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    collaboratorIds.add(rs.getString("collaborator_id"));
                }
            }
        }

        return collaboratorIds;
    }

    /**
     * Finds all tasks assigned to a specific collaborator
     * @param collaboratorId the collaborator ID
     * @return a list of task IDs assigned to the collaborator
     * @throws SQLException if database operation fails
     */
    public List<String> findTasksByCollaborator(String collaboratorId) throws SQLException {
        List<String> taskIds = new ArrayList<>();
        String sql = "SELECT task_id FROM assignments WHERE collaborator_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, collaboratorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    taskIds.add(rs.getString("task_id"));
                }
            }
        }

        return taskIds;
    }

    /**
     * Retrieves all assignments
     * @return a list of all assignments as maps
     * @throws SQLException if database operation fails
     */
    public List<Map<String, String>> findAll() throws SQLException {
        List<Map<String, String>> assignments = new ArrayList<>();
        String sql = "SELECT * FROM assignments";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                assignments.add(mapper.mapRowToAssignment(rs));
            }
        }

        return assignments;
    }

    /**
     * Gets the count of all assignments
     * @return the number of assignments
     * @throws SQLException if database operation fails
     */
    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM assignments";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    /**
     * Removes all assignments for a task (when task is deleted)
     * @param taskId the task ID
     * @throws SQLException if database operation fails
     */
    public void removeByTaskId(String taskId) throws SQLException {
        String sql = "DELETE FROM assignments WHERE task_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            stmt.executeUpdate();
        }
    }

    /**
     * Removes all assignments for a collaborator (when collaborator is deleted)
     * @param collaboratorId the collaborator ID
     * @throws SQLException if database operation fails
     */
    public void removeByCollaboratorId(String collaboratorId) throws SQLException {
        String sql = "DELETE FROM assignments WHERE collaborator_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, collaboratorId);
            stmt.executeUpdate();
        }
    }
}
