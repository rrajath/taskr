package com.example.rrajath.todo.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

/**
 * Created by rrajath on 12/23/14.
 */
@Data
public class TaskItem {

    private int id;
    private String taskName;
    private String taskDescription;
    private Date creationDate;
    private boolean isTaskComplete;

    public TaskItem(String taskName, String taskDescription) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.creationDate = new Date();
        this.isTaskComplete = false;
    }

    public TaskItem() {
        Date parsedDate = null;
        String dateInString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            parsedDate = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.creationDate = parsedDate;
        this.isTaskComplete = false;
    }
}
