package ru.ifmo.md.exam1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by 107476 on 13.01.2015.
 */
public class TasksProvider extends ContentProvider {
    static final String DB_NAME = "dbtasks";
    static final int DB_VERSION = 1;

    private static final int LINKS = 0;
    private static final int LINKS_ID = 1;


    public static final String LINKS_TABLE = "tasks";
    public static final String FIELD_ID = "id";
    public static final String FIELD_TAGS = "tags";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_DATE = "date";

    static final String AUTHORITY = "md.ifmo.ru";

    static final String LINKS_PATH = LINKS_TABLE;


    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        matcher.addURI(AUTHORITY, LINKS_PATH, LINKS);
        matcher.addURI(AUTHORITY, LINKS_PATH + "/#", LINKS_ID);
    }


    public static final Uri LINKS_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + LINKS_PATH);


    static final String LINKS_TABLE_CREATE = "create table " + LINKS_TABLE + "("
            + FIELD_ID + " integer primary key autoincrement, "
            + FIELD_TITLE + " text, "
            + FIELD_DATE + " text, "
            + FIELD_DESCRIPTION + " text, "
            + FIELD_TAGS + " text" + ");";


    DBHelper dbHelper;
    SQLiteDatabase db;

    public boolean onCreate() {
        dbHelper = new DBHelper(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (matcher.match(uri)) {
            case LINKS:
                queryBuilder.setTables(LINKS_TABLE);
                break;
            case LINKS_ID:
                queryBuilder.setTables(LINKS_TABLE);
                queryBuilder.appendWhere("id=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),
                uri);
        return cursor;
    }

    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        long rowID;
        switch (matcher.match(uri)) {
            case LINKS:
                rowID = db.insert(LINKS_TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        Uri resultUri = Uri.withAppendedPath(uri, "" + rowID);
        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int deleted;
        switch (matcher.match(uri)) {
            case LINKS:
                deleted = db.delete(LINKS_TABLE, null, null);
                break;
            case LINKS_ID:
                String id = uri.getLastPathSegment();
                deleted = db.delete(LINKS_TABLE, FIELD_ID + "=" + id, null);
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return deleted;
    }

    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        db = dbHelper.getWritableDatabase();
        int updated;
        if (matcher.match(uri) == LINKS_ID) {
            String id = uri.getLastPathSegment();
            updated = db.update(LINKS_TABLE, values, FIELD_ID + "=" + id, null);
        } else {
            throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return updated;
    }

    public String getType(Uri uri) {
        return Integer.toString(matcher.match(uri));
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("PRAGMA foreign_keys=ON;");
            db.execSQL(LINKS_TABLE_CREATE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists tasks");
            onCreate(db);
        }
    }

}