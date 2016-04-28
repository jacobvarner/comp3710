package comp3710.csse.eng.auburn.edu.getshitdone;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "GetShitDone.db";
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE " + TABLE_CATEGORIES + " (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT UNIQUE);";
    private static final String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + " (id INTEGER PRIMARY KEY AUTOINCREMENT , taskName TEXT," +
            " category TEXT DEFAULT 'default', completed TEXT DEFAULT 'false', description TEXT);";
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean databaseExists;
        File dbFile = getApplicationContext().getDatabasePath(DATABASE_NAME);
        if (dbFile.exists()) {
            databaseExists = true;
            Log.i("DATABASE", "Database Exists");
        } else {
            databaseExists = false;
            Log.i("DATABASE", "Database Doesn't Exist");
        }

        if (!databaseExists)
        {
            mDatabase = openOrCreateDatabase(DATABASE_NAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);
            Log.i("DATABASE", "Database created.");
            mDatabase.setLocale(Locale.getDefault()); // Set the locale
            mDatabase.setVersion(1); // Sets the database version.
            mDatabase.execSQL(CREATE_CATEGORIES_TABLE);
            mDatabase.execSQL(CREATE_TASKS_TABLE);
            mDatabase.execSQL("INSERT INTO " + TABLE_CATEGORIES + " (id, title) VALUES (1, 'Default');"); // Add Default category to start with
        }

        EditText taskTitle = (EditText) findViewById(R.id.editTextTaskTitle);
        taskTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    LinearLayout hidden = (LinearLayout) findViewById(R.id.extraTaskOptions);
                    hidden.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.addTaskCategory) {
            DialogFragment newFragment = new AddCategoryFragment();
            newFragment.show(getSupportFragmentManager(), "Add Category");
        }

        return super.onOptionsItemSelected(item);
    }

}
