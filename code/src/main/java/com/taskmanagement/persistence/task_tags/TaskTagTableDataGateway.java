package com.taskmanagement.persistence.task_tags;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.taskmanagement.persistence.DatabaseConnection;

/**
 * Table Data Gateway for Task-Tag associations
 * Encapsulates all SQL operations for the task_tags junction table
 */
public class TaskTagTableDataGateway {
    private final DatabaseConnection dbConnection;

    public TaskTagTableDataGateway(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Adds a tag to a task
     * @param taskId the task ID
     * @param tagId the tag ID
     * @throws SQLException if database operation fails
     */
    public void addTagToTask(String taskId, String tagId) throws SQLException {
        String sql = "INSERT OR IGNORE INTO task_tags (task_id, tag_id) VALUES (?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            stmt.setString(2, tagId);
            stmt.executeUpdate();
        }
    }

    /**
     * Removes a tag from a task
     * @param taskId the task ID
     * @param tagId the tag ID
     * @throws SQLException if database operation fails
     */
    public void removeTagFromTask(String taskId, String tagId) throws SQLException {
        String sql = "DELETE FROM task_tags WHERE task_id = ? AND tag_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            stmt.setString(2, tagId);
            stmt.executeUpdate();
        }
    }

    /**
     * Finds all tags associated with a task
     * @param taskId the task ID
     * @return a list of tag IDs
     * @throws SQLException if database operation fails
     */
    public List<String> findTagsByTask(String taskId) throws SQLException {
        List<String> tagIds = new ArrayList<>();
        String sql = "SELECT tag_id FROM task_tags WHERE task_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tagIds.add(rs.getString("tag_id"));
                }
            }
        }

        return tagIds;
    }

    /**
     * Finds all tasks associated with a tag
     * @param tagId the tag ID
     * @return a list of task IDs
     * @throws SQLException if database operation fails
     */
    public List<String> findTasksByTag(String tagId) throws SQLException {
        List<String> taskIds = new ArrayList<>();
        String sql = "SELECT task_id FROM task_tags WHERE tag_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tagId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    taskIds.add(rs.getString("task_id"));
                }
            }
        }

        return taskIds;
    }

    /**
     * Checks if a task has a specific tag
     * @param taskId the task ID
     * @param tagId the tag ID
     * @return true if the association exists, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean hasTag(String taskId, String tagId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM task_tags WHERE task_id = ? AND tag_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            stmt.setString(2, tagId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    /**
     * Removes all tags from a task (when task is deleted)
     * @param taskId the task ID
     * @throws SQLException if database operation fails
     */
    public void removeAllTagsFromTask(String taskId) throws SQLException {
        String sql = "DELETE FROM task_tags WHERE task_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            stmt.executeUpdate();
        }
    }

    /**
     * Gets the count of tags for a task
     * @param taskId the task ID
     * @return the number of tags
     * @throws SQLException if database operation fails
     */
    public int countTagsForTask(String taskId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM task_tags WHERE task_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }
}
