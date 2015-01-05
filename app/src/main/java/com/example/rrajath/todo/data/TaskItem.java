package com.example.rrajath.todo.data;

import lombok.Data;

/**
 * Created by rrajath on 12/23/14.
 */
@Data
public class TaskItem {

    private int id;
    private String taskName;
    private String taskDescription;
    private long creationTime;
    private boolean isTaskComplete;

    public TaskItem() {
        this.isTaskComplete = false;
    }
}
