package com.example.app1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton add_button;
    private Spinner spinnerDayFilter;
    private Button btnFilter;

    static db myDB;
    private static CustomAdapter customAdapter;
    private static ArrayList<String> task_id, taskObject, dayOfWeek, wtd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, add.class);
                startActivity(intent);
            }
        });

        spinnerDayFilter = findViewById(R.id.spinnerDayFilter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDayFilter.setAdapter(adapter);

        // Filterknop
        btnFilter = findViewById(R.id.btnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterTasksByDay();
            }
        });

        myDB = new db(MainActivity.this);
        task_id = new ArrayList<>();
        taskObject = new ArrayList<>();
        dayOfWeek = new ArrayList<>();
        wtd = new ArrayList<>();

        customAdapter = new CustomAdapter(MainActivity.this, task_id, taskObject, dayOfWeek, wtd);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        customAdapter.setOnDetailsClickListener(new CustomAdapter.OnDetailsClickListener() {
            @Override
            public void onDetailsClick(int position) {
                openDetailsActivity(task_id.get(position), taskObject.get(position), dayOfWeek.get(position), wtd.get(position));
            }
        });

        dataInArrays();  // Voeg deze regel toe
    }

    private void filterTasksByDay() {
        // Haal de geselecteerde dag op
        String selectedDay = spinnerDayFilter.getSelectedItem().toString();

        // Haal de taken op basis van de geselecteerde dag
        Cursor cursor = myDB.getTasksByDay(selectedDay);

        // Bijwerken van de weergegeven taken
        updateTasks(cursor);
    }

    private void updateTasks(Cursor cursor) {
        task_id.clear();
        taskObject.clear();
        dayOfWeek.clear();
        wtd.clear();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                taskObject.add(cursor.getString(1));
                dayOfWeek.add(cursor.getString(2));
                wtd.add(cursor.getString(3));
            }
        }

        // Notify the adapter that the data has changed
        customAdapter.updateData(task_id, taskObject, dayOfWeek, wtd);
    }

    private void openDetailsActivity(String taskId, String taskObject, String dayOfWeek, String wtd) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("taskId", taskId);
        startActivity(intent);
    }

    static void dataInArrays() {
        Cursor cursor = myDB.readAllData();
        task_id.clear();
        taskObject.clear();
        dayOfWeek.clear();
        wtd.clear();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                taskObject.add(cursor.getString(1));
                dayOfWeek.add(cursor.getString(2));
                wtd.add(cursor.getString(3));
            }
        }

        // Notify the adapter that the data has changed
        customAdapter.updateData(task_id, taskObject, dayOfWeek, wtd);
    }
}
