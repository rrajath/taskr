package com.example.rrajath.todo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.rrajath.todo.R;
import com.example.rrajath.todo.data.Singleton;
import com.example.rrajath.todo.data.TaskItem;
import com.example.rrajath.todo.database.TasksDatasource;
import com.example.rrajath.todo.util.DateUtils;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by rrajath on 12/23/14.
 */
public class TaskAdapter extends ArrayAdapter<TaskItem> {

    private Context mContext;
    private TasksDatasource datasource;
    private final String TASK_EDIT = "Edit";
    private final String TASK_DELETE = "Delete";

    public TaskAdapter(Context context, List<TaskItem> items) {
        super(context, R.layout.list_item, items);
        mContext = context;
        datasource = new TasksDatasource(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();

            convertView = inflater.inflate(R.layout.list_item, parent, false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final TaskItem item = Singleton.getInstance().getTaskItems().get(position);

        viewHolder.tvTaskName = (TextView) convertView.findViewById(R.id.tv_list_item);
        viewHolder.tvTaskName.setText(item.getTaskName());

        viewHolder.tvDateCreated = (TextView) convertView.findViewById(R.id.tvDateCreated);
        viewHolder.tvDateCreated.setText(DateUtils.getTimeElapsed(item.getCreationTime()));

        handleTaskComplete(viewHolder, item);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskItem item = Singleton.getInstance().getTaskItems().get(position);
                if (!item.isTaskComplete()) {
                    viewHolder.tvTaskName.setPaintFlags(viewHolder.tvTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    item.setTaskComplete(true);
                } else {
                    viewHolder.tvTaskName.setPaintFlags(viewHolder.tvTaskName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    item.setTaskComplete(false);
                }
                datasource.open();
                datasource.updateTaskItem(item);
                datasource.close();
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(mContext, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getTitle().toString()) {
                            case TASK_DELETE:
                                datasource.open();
                                datasource.deleteTaskItem(item);
                                Singleton.getInstance().getTaskItems().remove(item);
                                notifyDataSetChanged();
                                datasource.close();
                                break;

                            case TASK_EDIT:
                                Intent intent = new Intent();
                                intent.putExtra("taskName", item.getTaskName());
                                intent.putExtra("taskDesc", item.getTaskDescription());
                                intent.putExtra("editMode", true);
                                intent.setAction("com.example.rrajath.todo.activity.AddTaskDialog");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                        }
                        return true;
                    }
                });

                popupMenu.show();
                return true;
            }
        });
        return convertView;
    }

    private void handleTaskComplete(ViewHolder viewHolder, TaskItem item) {
        if (item.isTaskComplete()) {
            viewHolder.tvTaskName.setPaintFlags(viewHolder.tvTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tvTaskName.setTextColor(Color.LTGRAY);
            item.setTaskComplete(true);
        } else {
            viewHolder.tvTaskName.setPaintFlags(viewHolder.tvTaskName.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tvTaskName.setTextColor(Color.BLACK);
            item.setTaskComplete(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public int getCount() {
        return Singleton.getInstance().getTaskItems().size();
    }

    static class ViewHolder {
        @InjectView(R.id.tv_list_item)
        TextView tvTaskName;

        @InjectView(R.id.tvDateCreated)
        TextView tvDateCreated;
    }

}