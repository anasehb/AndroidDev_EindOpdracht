package com.example.app1;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    private TextView taskObjectTextView, dayOfWeekTextView, wtdTextView;

    public void onDeleteButtonClick(View view) {
        // Handle the delete button click here
        deleteTask();  // Call your deleteTask() method or handle deletion logic
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        taskObjectTextView = findViewById(R.id.taskObjectDetail);
        dayOfWeekTextView = findViewById(R.id.dayOfWeekDetail);
        wtdTextView = findViewById(R.id.wtdDetail);

        // Receive the task ID from the intent
        String taskId = getIntent().getStringExtra("taskId");

        Log.d("DetailActivity", "Received task ID: " + taskId);

        // Assuming you have a method in your db class to fetch details by task ID
        Cursor cursor = MainActivity.myDB.getTaskDetailsById(taskId);

        // Check if the cursor is not null
        if (cursor != null) {
            // Move to the first row of the cursor
            if (cursor.moveToFirst()) {
                // Retrieve details from the cursor
                int taskObjectIndex = cursor.getColumnIndex("TaskObject");
                int dayOfWeekIndex = cursor.getColumnIndex("DayOfWeek");
                int wtdIndex = cursor.getColumnIndex("whatToDo");

                if (taskObjectIndex != -1 && dayOfWeekIndex != -1 && wtdIndex != -1) {
                    String taskObject = cursor.getString(taskObjectIndex);
                    String dayOfWeek = cursor.getString(dayOfWeekIndex);
                    String wtd = cursor.getString(wtdIndex);

                    // Now you have all the details, update your UI accordingly
                    taskObjectTextView.setText("Task object: " + taskObject);
                    dayOfWeekTextView.setText("Day of week: " + dayOfWeek);
                    wtdTextView.setText("What to do: " + wtd);
                } else {
                    // Log a message if the column indexes are -1
                    Log.d("DetailActivity", "Column indexes are -1");
                }
            } else {
                // Log a message if the cursor is empty
                Log.d("DetailActivity", "Cursor is empty");
            }

            // Close the cursor when done with it
            cursor.close();
        } else {
            // Log a message if the cursor is null
            Log.d("DetailActivity", "Cursor is null");
        }
    }
    private void deleteTask() {
        // Receive the task ID from the intent
        String taskId = getIntent().getStringExtra("taskId");

        Log.d("DetailActivity", "Deleting task with ID: " + taskId);

        // Assuming you have a method in your db class to delete a task by ID
        boolean isDeleted = MainActivity.myDB.deleteTaskById(taskId);

        if (isDeleted) {
            // Task deleted successfully, you can finish the activity or take any other action
            finish();
        } else {
            // Handle deletion failure if needed
            Toast.makeText(this, "Failed to delete task", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is the ID of the back button in the toolbar
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
