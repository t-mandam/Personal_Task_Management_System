package com.taskmanagement.persistence.activity;

import com.taskmanagement.observer.Activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.taskmanagement.persistence.DatabaseConnection;

/**
 * Table Data Gateway for Activity records
 * Encapsulates all SQL operations for the activities table
 */
public class ActivityTableDataGateway {
    private final DatabaseConnection dbConnection;
    private final ActivityMapper mapper;

    public ActivityTableDataGateway(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        this.mapper = new ActivityMapper();
    }

    /**
     * Inserts a new activity into the database
     * @param activity the activity to insert
     * @throws SQLException if database operation fails
     */
    public void insert(Activity activity) throws SQLException {
        String sql = "INSERT INTO activities (id, timestamp, task_id, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Object[] values = mapper.getInsertValues(activity);
            stmt.setString(1, (String) values[0]);
            stmt.setTimestamp(2, (java.sql.Timestamp) values[1]);
            stmt.setString(3, (String) values[2]);
            stmt.setString(4, (String) values[3]);

            stmt.executeUpdate();
        }
    }

    /**
     * Finds all activities for a specific task.
     * @param taskId the task ID
     * @return activities linked to the task, newest first
     * @throws SQLException if database operation fails
     */
    public List<Activity> findByTaskId(String taskId) throws SQLException {
        List<Activity> activities = new ArrayList<>();
        String sql = "SELECT * FROM activities WHERE task_id = ? ORDER BY timestamp DESC";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    activities.add(mapper.mapRowToActivity(rs));
                }
            }
        }

        return activities;
    }

    /**
     * Finds all activities in the database
     * @return a list of all Activity objects
     * @throws SQLException if database operation fails
     */
    public List<Activity> findAll() throws SQLException {
        List<Activity> activities = new ArrayList<>();
        String sql = "SELECT * FROM activities ORDER BY timestamp DESC";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                activities.add(mapper.mapRowToActivity(rs));
            }
        }

        return activities;
    }

    /**
     * Gets the count of all activities
     * @return the number of activities in the database
     * @throws SQLException if database operation fails
     */
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM activities";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return 0;
    }

    /**
     * Clears all activities from the database
     * @throws SQLException if database operation fails
     */
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM activities";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
        }
    }

    /**
     * Deletes activities older than a certain number of days
     * @param days the number of days to keep
     * @throws SQLException if database operation fails
     */
    public void deleteOlderThan(int days) throws SQLException {
        String sql = "DELETE FROM activities WHERE timestamp < datetime('now', '-' || ? || ' days')";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, days);
            stmt.executeUpdate();
        }
    }
}
