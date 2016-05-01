package comp3710.csse.eng.auburn.edu.getshitdone;

import android.content.ContentValues;
import java.lang.String;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public final class DatabaseManager {

    private static final String DATABASE_NAME = "GetShitDone.db";
    private static final String DATABASE_PATH = "//data/data/comp3710.csse.eng.auburn.edu.getshitdone/databases/";

    public DatabaseManager() {}

    public static void addCategory(String title) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("title", title);
        db.insert("categories", "null", values);
    }

    public static void addTask(String name, String category, String description) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("taskName", name);
        values.put("category", category);
        values.put("completed", "false");
        values.put("description", description);
        db.insert("tasks", "null", values);
    }

    // Flags: 0 = all, 1 = incomplete
    public static ArrayList<String[]> getTasks(String category, int flag) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        ArrayList<String[]> output = new ArrayList<>();

        if (category == "All") {
            String[] selectionArgs;
            String[] projection = {"id", "taskName", "category", "completed", "description"};
            String selection;
            if (flag == 1) {
                selection = "completed = ?";
                String[] selectionArgs1 = {"false"};
                selectionArgs = selectionArgs1;
            } else {
                selection = "completed = ? OR completed = ?";
                String[] selectionArgs2 = {"false", "true"};
                selectionArgs = selectionArgs2;
            }

            Cursor c = db.query("tasks", projection, selection, selectionArgs, null, null, "id ASC");

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
        } else {
            String[] selectionArgs;
            String[] projection = {"id", "taskName", "category", "completed", "description"};
            String selection;
            if (flag == 1) {
                selection = "category = ? AND completed = ?";
                String[] selectionArgs1 = {category, "false"};
                selectionArgs = selectionArgs1;
            } else {
                selection = "completed IS NOT NULL AND category = ?";
                String[] selectionArgs1 = {category};
                selectionArgs = selectionArgs1;
            }

            Cursor c = db.query("tasks", projection, selection, selectionArgs, null, null, "id ASC");


            c.moveToFirst();
            while (!c.isAfterLast()) {
                String[] row = new String[c.getColumnCount()];
                row[0] = c.getString(0); // id
                row[1] = c.getString(1); // taskName
                row[2] = c.getString(2); // category
                row[3] = c.getString(3); // completed
                row[4] = c.getString(4); // description
                output.add(row);
                c.moveToNext();
            }
        }

        return output;
    }

    public static ArrayList<String> getCategories() {
        ArrayList<String> output = new ArrayList<>();

        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        String projection[] = { "title" };
        Cursor c = db.query("categories", projection, null, null, null, null, "title ASC");
        c.moveToFirst();
        while(!c.isAfterLast()) {
            String row = c.getString(0);
            output.add(row);
            c.moveToNext();
        }

        return output;
    }

    public static void markComplete(int taskId) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);

        db.execSQL("UPDATE tasks SET completed = \"true\" WHERE id = " + taskId);
    }

    public static void markIncomplete(int taskId) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);

        db.execSQL("UPDATE tasks SET completed = \"false\" WHERE id = " + taskId);
    }

    public static void deleteTask(int taskId) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);

        String[] idArg = {String.valueOf(taskId)};

        db.delete("tasks", "id = ?", idArg);
    }

    public static void removeCategory(int categoryId) {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);

        String[] idArg = {String.valueOf(categoryId)};

        db.delete("categories", "id = ?", idArg);
    }
}
