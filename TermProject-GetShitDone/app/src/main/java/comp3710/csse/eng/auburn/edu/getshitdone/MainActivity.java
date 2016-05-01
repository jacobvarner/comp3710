package comp3710.csse.eng.auburn.edu.getshitdone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
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
                    ArrayList<String> categories = DatabaseManager.getCategories();

                    Spinner spinner = (Spinner) findViewById(R.id.spinnerTaskCategories);
                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, categories);

                    // Specify the layout to use when the list of choices appears
                    adapter.setDropDownViewResource(R.layout.spinner_item);
                    // Apply the adapter to the spinner
                    spinner.setAdapter(adapter);
                }else {
                    Toast.makeText(getApplicationContext(), "lost the focus", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ArrayList<String> categoriesView = DatabaseManager.getCategories();

        Spinner spinnerCategories = (Spinner) findViewById(R.id.spinnerCategorySelect);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, categoriesView);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_item);
        adapter.insert("All", 0);
        adapter.remove("Default");
        // Apply the adapter to the spinner
        spinnerCategories.setAdapter(adapter);

        ListView taskList = (ListView) findViewById(R.id.listView);
        String category = spinnerCategories.getSelectedItem().toString();
        final ArrayList<String[]> tasks = DatabaseManager.getTasks(category, 1);
        final ArrayList<String> taskNames = new ArrayList<>();
        for (String[] t : tasks) {
            taskNames.add(t[1]);
        }
        ArrayAdapter<String> tasksAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, taskNames);
        taskList.setAdapter(tasksAdapter);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder taskAlertBuilder = new AlertDialog.Builder(MainActivity.this);
                taskAlertBuilder.setTitle(tasks.get(position)[1].toString());
                taskAlertBuilder.setMessage(tasks.get(position)[4].toString());
                taskAlertBuilder.setPositiveButton("Completed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseManager.markComplete(Integer.parseInt(tasks.get(position)[0]));
                        dialog.dismiss();
                    }
                });
                taskAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog taskAlert = taskAlertBuilder.create();
                taskAlert.show();
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

    public boolean addTask(View view) {
        EditText taskName = (EditText) findViewById(R.id.editTextTaskTitle);
        EditText taskDescription = (EditText) findViewById(R.id.editTextTaskDescription);
        Spinner taskCategory = (Spinner) findViewById(R.id.spinnerTaskCategories);

        String taskNameStr = taskName.getText().toString();
        String taskDescriptionStr = taskDescription.getText().toString();
        String taskCategoryStr = taskCategory.getSelectedItem().toString();

        if (taskNameStr == null || taskNameStr == "") {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getApplicationContext());
            alertBuilder.setMessage("Task Name may not be blank.");
            alertBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertBuilder.show();
            return false;
        } else {
            DatabaseManager.addTask(taskNameStr, taskCategoryStr, taskDescriptionStr);
            LinearLayout hidden = (LinearLayout) findViewById(R.id.extraTaskOptions);
            taskName.setText("");
            taskDescription.setText("");
            hidden.setVisibility(View.GONE);
            return true;
        }

    }

}
