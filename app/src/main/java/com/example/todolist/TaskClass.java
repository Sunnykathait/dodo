package com.example.todolist;

public class TaskClass {
    private String taskName;
    private String taskDescription;

    private String isCompleted;

    public TaskClass(String taskName, String taskDescription, String isCompleted) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.isCompleted = isCompleted;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
}
