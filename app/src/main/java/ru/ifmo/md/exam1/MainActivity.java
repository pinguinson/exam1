package ru.ifmo.md.exam1;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    List<Task> tasks;
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tasks = new ArrayList<>();

        String[] tags = {"study", "university"};
        tasks.add(new Task("math", "do math", tags));
        tasks.add(new Task("android", "do hw", tags));
        adapter = new TaskAdapter(this, tasks);
        ListView listView = (ListView) findViewById(R.id.list_tasks);
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            TextView editTitle = (TextView) findViewById(R.id.edit_title);
            TextView editDescription = (TextView) findViewById(R.id.edit_description);
            String newTitle = editTitle.getEditableText().toString();
            String newDescription = editDescription.getEditableText().toString();
            Task task = new Task(newTitle, newDescription);
            tasks.add(task);
            ContentValues cv = new ContentValues();
            cv.put(TasksProvider.FIELD_TITLE, task.getTitle());
            cv.put(TasksProvider.FIELD_DESCRIPTION, task.getDescription());
            cv.put(TasksProvider.FIELD_DATE, task.getDate());
            cv.put(TasksProvider.FIELD_TAGS, task.getTagsSingleString());
            adapter = new TaskAdapter(this, tasks);
            ListView listView = (ListView) findViewById(R.id.list_tasks);
            listView.setAdapter(adapter);
            Toast.makeText(this, "added", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
