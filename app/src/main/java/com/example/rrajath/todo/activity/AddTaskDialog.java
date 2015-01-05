package com.example.rrajath.todo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rrajath.todo.R;
import com.example.rrajath.todo.adapter.TaskAdapter;
import com.example.rrajath.todo.data.Singleton;
import com.example.rrajath.todo.data.TaskItem;
import com.example.rrajath.todo.database.TasksDatasource;

import java.util.Date;

/**
 * Created by rrajath on 1/2/15.
 */
public class AddTaskDialog extends Activity {

    TasksDatasource datasource;
    EditText taskName;
    EditText taskDescription;
    boolean editMode;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_layout);
        taskName = (EditText) findViewById(R.id.new_task_name);
        taskDescription = (EditText) findViewById(R.id.new_task_desc);

        if (getIntent().getStringExtra("taskName") != null) {
            taskName.setText(getIntent().getStringExtra("taskName"));
        }
        if (getIntent().getStringExtra("taskDesc") != null) {
            taskDescription.setText(getIntent().getStringExtra("taskDesc"));
        }
        editMode = getIntent().getBooleanExtra("editMode", false);
    }

    public void onCancelTapped(View view) {
        finish();
    }

    public void createTask(View view) {
        datasource = new TasksDatasource(getApplicationContext());
        TaskItem item = new TaskItem();
        String newTaskName = taskName.getText().toString().trim();
        String newTaskDesc = taskDescription.getText().toString().trim();
        item.setTaskName(newTaskName);
        item.setTaskDescription(newTaskDesc);

        datasource.open();
        if (editMode) {
            datasource.updateTaskItem(item);
            Singleton.getInstance().getTaskItems().clear();
            Singleton.getInstance().setTaskItems(datasource.getAllTasks());
            Toast.makeText(getApplicationContext(), "Task Updated", Toast.LENGTH_SHORT).show();
        } else {
            datasource.createTask(item);
            item.setCreationTime(new Date().getTime());
            Singleton.getInstance().getTaskItems().add(0, item);
            Toast.makeText(getApplicationContext(), "New Task added", Toast.LENGTH_SHORT).show();
        }
        datasource.close();

        taskAdapter = new TaskAdapter(getApplicationContext(), Singleton.getInstance().getTaskItems());
        taskAdapter.notifyDataSetChanged();

        finish();
    }
}
