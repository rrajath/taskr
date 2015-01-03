package com.example.rrajath.todo.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.rrajath.todo.R;
import com.example.rrajath.todo.adapter.TaskAdapter;
import com.example.rrajath.todo.data.TaskItem;
import com.example.rrajath.todo.database.TasksDatasource;

import java.util.List;


public class MainActivity extends ListActivity {

    List<TaskItem> taskItems;
    TaskAdapter taskAdapter;
    ListView lvItems;
    TasksDatasource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        datasource = new TasksDatasource(this);
        datasource.open();

        lvItems = (ListView) findViewById(android.R.id.list);
        taskItems = datasource.getAllTasks();
        datasource.close();
        taskAdapter = new TaskAdapter(getApplicationContext(), taskItems);
        lvItems.setAdapter(taskAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.clear_completed_tasks:
                datasource.open();
                datasource.deleteCompletedTasks();
                taskItems.clear();
                taskItems.addAll(datasource.getAllTasks());
                taskAdapter.notifyDataSetChanged();
                datasource.close();
                break;
            case R.id.action_settings:
                break;
        }
        return true;
    }

    public void addTask(View view) {
        Intent intent = new Intent(this, AddTaskDialog.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                TaskItem item = new TaskItem();
                item.setTaskName(intent.getStringExtra("taskName"));
                item.setTaskDescription(intent.getStringExtra("taskDesc"));
                taskItems.add(item);
                taskAdapter.notifyDataSetChanged();
            }
        }
    }
}
