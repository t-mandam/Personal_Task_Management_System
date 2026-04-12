package com.taskmanagement.persistence.activity;

import com.taskmanagement.observer.Activity;
import com.taskmanagement.observer.ActivityRecorder;
import com.taskmanagement.persistence.DatabaseConnection;

import java.sql.SQLException;

/**
 * Persists activity entries in the activities table.
 */
public class DatabaseActivityRecorder implements ActivityRecorder {
    private final ActivityTableDataGateway activityGateway;

    public DatabaseActivityRecorder(DatabaseConnection databaseConnection) {
        this.activityGateway = new ActivityTableDataGateway(databaseConnection);
    }

    @Override
    public void record(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity cannot be null");
        }

        try {
            activityGateway.insert(activity);
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to record activity", ex);
        }
    }
}
