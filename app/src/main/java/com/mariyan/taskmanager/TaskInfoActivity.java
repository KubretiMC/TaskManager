package com.mariyan.taskmanager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TaskInfoActivity extends AppCompatActivity {
    private TextView taskName;
    private TextView taskDate;
    private TextView taskNotes;
    private Integer taskID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_info);

        taskName = findViewById(R.id.taskNameTextView);
        taskDate = findViewById(R.id.taskDateTextView);
        taskNotes = findViewById(R.id.taskNotesTextView);

        taskID = Integer.valueOf(getIntent().getIntExtra("taskID", 0));
        String name = Task.list.get(taskID).getName();
        String date = Task.list.get(taskID).getDate();
        String notes = Task.list.get(taskID).getNotes();

        taskName.setText(name);
        taskDate.setText(date);
        taskNotes.setText(notes);

    }
}