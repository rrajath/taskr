package com.example.rrajath.app.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.rrajath.app.R;
import com.example.rrajath.app.adapter.TaskAdapter;
import com.example.rrajath.app.data.Singleton;
import com.example.rrajath.app.database.TasksDatasource;


public class MainActivity extends ListActivity {

//    List<TaskItem> taskItems;
    TaskAdapter taskAdapter;
    ListView lvItems;
    TasksDatasource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        datasource = new TasksDatasource(this);
        lvItems = (ListView) findViewById(android.R.id.list);

        datasource.open();
        Singleton.getInstance().setTaskItems(datasource.getAllTasks());
        datasource.close();

        taskAdapter = new TaskAdapter(getApplicationContext(), Singleton.getInstance().getTaskItems());
        lvItems.setAdapter(taskAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        taskAdapter.notifyDataSetChanged();
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
                Singleton.getInstance().getTaskItems().clear();
                Singleton.getInstance().setTaskItems(datasource.getAllTasks());
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
        startActivity(intent);
    }
}
