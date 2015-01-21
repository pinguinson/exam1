package ru.ifmo.md.exam1;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by pinguinson on 21.01.2015.
 */
public class Task {
    String title;
    String description;
    String date;
    String[] tags;

    public Task(String title, String description, String[] tags) {
        this.title = title;
        this.description = description;
        this.tags = tags;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        this.date = sdf.format(c.getTime());
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.tags = new String[]{"a", "b"};
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        this.date = sdf.format(c.getTime());
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String[] getTags() {
        return tags;
    }

    public String getTagsSingleString() {
        String res = "";
        for (String tag : tags) {
            res += " | " + tag;
        }
        return res;
    }
}
