package com.taskmanagement.command;

import com.taskmanagement.domain.Task;
import com.taskmanagement.factory.TaskFactory;
import com.taskmanagement.repository.TaskCatalog;

/**
 * Command to create a new task
 */
public class CreateTaskCommand implements Command {
    private TaskFactory taskFactory;
    private String title;
    private Task createdTask;

    public CreateTaskCommand() {
        // Initialize with the singleton TaskCatalog
        this.taskFactory = new TaskFactory(TaskCatalog.getInstance());
    }

    public CreateTaskCommand(String title) {
        this();
        this.title = title;
    }

    @Override
    public void execute() {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }

        this.createdTask = taskFactory.createTask(title);
        System.out.println("Task created: " + createdTask.getTitle());
    }

    /**
     * Gets the created task after execution
     * @return the created Task object
     */
    public Task getCreatedTask() {
        return createdTask;
    }

    /**
     * Sets the title for the task to be created
     * @param title the task title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the title
     * @return the task title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the task factory
     * @param taskFactory the factory to use
     */
    public void setTaskFactory(TaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    /**
     * Gets the task factory
     * @return the task factory
     */
    public TaskFactory getTaskFactory() {
        return taskFactory;
    }
}