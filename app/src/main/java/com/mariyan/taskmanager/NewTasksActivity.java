package com.mariyan.taskmanager;
import android.app.Notification;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class NewTasksActivity extends AppCompatActivity {

    private TextView taskName;
    private TextView taskDate;
    private TextView taskNote;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        create = findViewById(R.id.CreateButton);
        create.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                createTask();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void createTask() {
        taskName = findViewById(R.id.taskNamePlainText);
        taskDate = findViewById(R.id.taskDatePlainText);
        taskNote = findViewById(R.id.taskNotePlainText);

        String name = taskName.getText().toString().trim();
        String date = taskDate.getText().toString().trim();
        String note = taskNote.getText().toString().trim();

        if (name.isEmpty() || date.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty field", Toast.LENGTH_LONG).show();
            Notification notify = new Notification.Builder(getApplicationContext())
                    .setContentTitle("Empty field!")
                    .setContentText(name)
                    .build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
        } else {
            try {
                Integer id = Integer.valueOf(Task.list.size()) + 1;
                Task hero = new Task(id, name, date, note);
                Task.list.add(hero);


                String q = "";
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/" + "tasks.db", null);

                q = "INSERT INTO tasks(name,date,notes) VALUES(?,?,?);";
                db.execSQL(q, new Object[]{name, date, note});
                db.close();

                Toast.makeText(getApplicationContext(), "Task created successful!", Toast.LENGTH_LONG).show();
                Notification notify = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Task created successful")
                        .setContentText(name)
                        .build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                finish();
            } catch (SQLiteException e) {
                Notification notify = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Error while working with database!")
                        .build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
            } catch (Exception e) {
                Notification notify = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Error while working with database!")
                        .build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
            }
        }
    }
}