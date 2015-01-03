package com.example.rrajath.todo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rrajath.todo.R;
import com.example.rrajath.todo.data.TaskItem;
import com.example.rrajath.todo.database.TasksDatasource;

/**
 * Created by rrajath on 1/2/15.
 */
public class AddTaskDialog extends Activity {

    TasksDatasource datasource;
    EditText taskName;
    EditText taskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_layout);
        taskName = (EditText) findViewById(R.id.new_task_name);
        taskDescription = (EditText) findViewById(R.id.new_task_desc);
    }

    public void onCancelTapped(View view) {
        finish();
    }

    public void createTask(View view) {
        datasource = new TasksDatasource(getApplicationContext());
        TaskItem item = new TaskItem();
        String newTaskName = taskName.getText().toString();
        String newTaskDesc = taskDescription.getText().toString();
        item.setTaskName(newTaskName);
        item.setTaskDescription(newTaskDesc);

        datasource.open();
        datasource.createTask(newTaskName, newTaskDesc);
        datasource.close();

        Toast.makeText(getApplicationContext(), "New Task added", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.putExtra("taskName", item.getTaskName());
        intent.putExtra("taskDesc", item.getTaskDescription());
        setResult(RESULT_OK, intent);
        finish();
    }
}
