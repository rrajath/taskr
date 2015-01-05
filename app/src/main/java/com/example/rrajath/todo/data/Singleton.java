package com.example.rrajath.todo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rrajath on 1/4/15.
 */
public class Singleton {
    private static Singleton mInstance = null;
    private static List<TaskItem> taskItems;

    public Singleton() {
        taskItems = new ArrayList<>();
    }

    public static Singleton getInstance() {
        if (mInstance == null) {
            mInstance = new Singleton();
        }
        return mInstance;
    }

    public List<TaskItem> getTaskItems() {
        return taskItems;
    }

    public void setTaskItems(List<TaskItem> taskItems) {
        this.taskItems = taskItems;
    }
}
