package comp3710.csse.eng.auburn.edu.getshitdone;

import android.content.ContentValues;
import android.content.Context;
import java.lang.String;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public final class DatabaseManager {

    public DatabaseManager() {}

    public class DatabaseHelper extends SQLiteOpenHelper {
        // Increment if schema is changed
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "GetShitDone.db";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE categories (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT);");
            db.execSQL("CREATE TABLE tasks (id INTEGER PRIMARY KEY AUTOINCREMENT , taskName TEXT," +
                    " category TEXT, completed TEXT, description TEXT);");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS categories");
            db.execSQL("DROP TABLE IF EXISTS tasks");
            onCreate(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    public static void addCategory(String title) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase("GetShitDone.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("title", title);
        db.insert("categories", "null", values);
    }

    public static void addTask(String name, String category, String description) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase("GetShitDone.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("taskName", name);
        values.put("category", category);
        values.put("completed", "false");
        values.put("description", description);
        db.insert("tasks", "null", values);
    }

    // Flags: 0 = all, 1 = incomplete
    public static ArrayList<String[]> getTasks(String category, int flag) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase("GetShitDone.db", null, SQLiteDatabase.OPEN_READWRITE);
        String completed;
        String[] projection = {"id", "taskName", "category", "completed", "description"};
        String selection = "category = ? AND flag = ?";
        if (flag == 1) {
            completed = "false";
        } else {
            completed = "true || false";
        }
        String[] selectionArgs = {category, completed};

        Cursor c = db.query("tasks", projection, selection, selectionArgs, null, null, "ASC");

        ArrayList<String[]> output = new ArrayList<>();

        c.moveToFirst();
        while(!c.isAfterLast()) {
            String[] row = new String[c.getColumnCount()];
            row[0] = c.getString(0); // id
            row[1] = c.getString(1); // taskName
            row[2] = c.getString(2); // category
            row[3] = c.getString(3); // completed
            row[4] = c.getString(4); // description
            output.add(row);
            c.moveToNext();
        }

        return output;
    }

    public static ArrayList<String[]> getCategories() {
        ArrayList<String[]> output = new ArrayList<>();

        SQLiteDatabase db = SQLiteDatabase.openDatabase("GetShitDone.db", null, SQLiteDatabase.OPEN_READWRITE);
        String[] projection = {"id", "title"};
        Cursor c = db.query("categories", projection, null, null, null, null, null, "ASC");
        c.moveToFirst();
        while(!c.isAfterLast()) {
            String[] row = new String[c.getColumnCount()];
            row[0] = c.getString(0);
            row[1] = c.getString(1);
            output.add(row);
            c.moveToNext();
        }

        return output;
    }

    public static void markComplete(int taskId) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase("GetShitDone.db", null, SQLiteDatabase.OPEN_READWRITE);

        db.execSQL("UPDATE tasks SET completed = \"true\" WHERE id = " + taskId);
    }

    public static void markIncomplete(int taskId) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase("GetShitDone.db", null, SQLiteDatabase.OPEN_READWRITE);

        db.execSQL("UPDATE tasks SET completed = \"false\" WHERE id = " + taskId);
    }

    public static void deleteTask(int taskId) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase("GetShitDone.db", null, SQLiteDatabase.OPEN_READWRITE);

        String[] idArg = {String.valueOf(taskId)};

        db.delete("tasks", "id = ?", idArg);
    }

    public static void removeCategory(int categoryId) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase("GetShitDone.db", null, SQLiteDatabase.OPEN_READWRITE);

        String[] idArg = {String.valueOf(categoryId)};

        db.delete("categories", "id = ?", idArg);
    }
}
