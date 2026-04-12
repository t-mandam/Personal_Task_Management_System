package com.taskmanagement.observer;

/**
 * Abstraction for recording activity events.
 */
public interface ActivityRecorder {
    void record(Activity activity);
}
