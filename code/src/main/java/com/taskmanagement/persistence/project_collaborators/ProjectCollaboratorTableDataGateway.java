package com.taskmanagement.persistence.project_collaborators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.taskmanagement.persistence.DatabaseConnection;

/**
 * Table Data Gateway for Project-Collaborator associations
 * Encapsulates all SQL operations for the project_collaborators junction table
 */
public class ProjectCollaboratorTableDataGateway {
    private final DatabaseConnection dbConnection;

    public ProjectCollaboratorTableDataGateway(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Adds a collaborator to a project
     * @param projectId the project ID
     * @param collaboratorId the collaborator ID
     * @throws SQLException if database operation fails
     */
    public void addCollaboratorToProject(String projectId, String collaboratorId) throws SQLException {
        String sql = "INSERT OR IGNORE INTO project_collaborators (project_id, collaborator_id) VALUES (?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projectId);
            stmt.setString(2, collaboratorId);
            stmt.executeUpdate();
        }
    }

    /**
     * Removes a collaborator from a project
     * @param projectId the project ID
     * @param collaboratorId the collaborator ID
     * @throws SQLException if database operation fails
     */
    public void removeCollaboratorFromProject(String projectId, String collaboratorId) throws SQLException {
        String sql = "DELETE FROM project_collaborators WHERE project_id = ? AND collaborator_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projectId);
            stmt.setString(2, collaboratorId);
            stmt.executeUpdate();
        }
    }

    /**
     * Finds all collaborators for a project
     * @param projectId the project ID
     * @return a list of collaborator IDs
     * @throws SQLException if database operation fails
     */
    public List<String> findCollaboratorsByProject(String projectId) throws SQLException {
        List<String> collaboratorIds = new ArrayList<>();
        String sql = "SELECT collaborator_id FROM project_collaborators WHERE project_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    collaboratorIds.add(rs.getString("collaborator_id"));
                }
            }
        }

        return collaboratorIds;
    }

    /**
     * Finds all projects for a collaborator
     * @param collaboratorId the collaborator ID
     * @return a list of project IDs
     * @throws SQLException if database operation fails
     */
    public List<String> findProjectsByCollaborator(String collaboratorId) throws SQLException {
        List<String> projectIds = new ArrayList<>();
        String sql = "SELECT project_id FROM project_collaborators WHERE collaborator_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, collaboratorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    projectIds.add(rs.getString("project_id"));
                }
            }
        }

        return projectIds;
    }

    /**
     * Checks if a collaborator is assigned to a project
     * @param projectId the project ID
     * @param collaboratorId the collaborator ID
     * @return true if the association exists, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean isCollaboratorInProject(String projectId, String collaboratorId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM project_collaborators WHERE project_id = ? AND collaborator_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projectId);
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
     * Removes all collaborators from a project (when project is deleted)
     * @param projectId the project ID
     * @throws SQLException if database operation fails
     */
    public void removeAllCollaboratorsFromProject(String projectId) throws SQLException {
        String sql = "DELETE FROM project_collaborators WHERE project_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projectId);
            stmt.executeUpdate();
        }
    }

    /**
     * Gets the count of collaborators for a project
     * @param projectId the project ID
     * @return the number of collaborators
     * @throws SQLException if database operation fails
     */
    public int countCollaboratorsInProject(String projectId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM project_collaborators WHERE project_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projectId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }
}
