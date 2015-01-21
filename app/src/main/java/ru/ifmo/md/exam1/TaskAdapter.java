package ru.ifmo.md.exam1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pinguinson on 21.01.2015.
 */
public class TaskAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private List<Task> tasks;
    private Context context;

    public TaskAdapter(Context context, List<Task> tasks) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tasks = tasks;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = (View) convertView;
        if (view == null) {
            view = (View) inflater.inflate(R.layout.task_list_item, parent, false);
        }
        String title = tasks.get(position).getTitle();
        String date = tasks.get(position).getDate();
        TextView viewTitle = (TextView) view.findViewById(R.id.task_name);
        TextView viewDate = (TextView) view.findViewById(R.id.task_date);
        viewTitle.setText(title);
        viewDate.setText(date);

        return view;
    }
}
