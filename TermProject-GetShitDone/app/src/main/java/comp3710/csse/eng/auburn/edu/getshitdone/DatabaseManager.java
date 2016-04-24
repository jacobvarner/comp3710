package comp3710.csse.eng.auburn.edu.getshitdone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                    " categoryId INTEGER NOT NULL CONSTRAINT categoryId REFERENCES categories(id) " +
                    "ON DELETE CASCADE, createdAt DATETIME, completed BOOLEAN, deleted BOOLEAN, " +
                    "description TEXT, dueDate DATE, dueTime TIME);");
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
}
