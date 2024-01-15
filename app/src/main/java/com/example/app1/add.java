package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class add extends AppCompatActivity {
    EditText taskObjectInput, wtdInput;
    DatePicker datePicker;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        Toolbar toolbar = findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        taskObjectInput = findViewById(R.id.TaskObject_input);
        wtdInput = findViewById(R.id.wtd_input);
        datePicker = findViewById(R.id.datePicker);
        addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                String dayOfWeekString = getDayOfWeekString(dayOfWeek);

                db myDB = new db(add.this);
                long result = myDB.addTask(taskObjectInput.getText().toString().trim(), dayOfWeekString, wtdInput.getText().toString().trim());

                MainActivity.dataInArrays();
                if (result != -1) {
                    Toast.makeText(add.this, "Task added successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(add.this, "Failed to add task.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getDayOfWeekString(int dayOfWeek) {
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return daysOfWeek[dayOfWeek - 1];
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
