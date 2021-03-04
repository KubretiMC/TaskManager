package com.mariyan.taskmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button newTask;
    private Button tasks;
    public static List updatedTasks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newTask = findViewById(R.id.NewTaskButton);
        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewTaskActivity();
            }
        });

        tasks = findViewById(R.id.TasksButton);
        tasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTasksActivity();
            }
        });

        TakeTasksFromSQL();
    }

    private void openNewTaskActivity() {
        Intent intent=new Intent(getApplicationContext(),NewTasksActivity.class);
        startActivity(intent);
    }
    private void openTasksActivity() {
        Intent intent=new Intent(getApplicationContext(),TasksActivity.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!updatedTasks.isEmpty())
        {
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File(getFilesDir().getPath() + "/" + "tasks.db"), null);
            ContentValues cv = new ContentValues();
            Integer j;
            for(int i=0;i<updatedTasks.size();i++)
            {
                j= (Integer) updatedTasks.get(i);
                cv.put("name",Task.list.get(j).getName());
                cv.put("date",Task.list.get(j).getDate());
                cv.put("notes",Task.list.get(j).getNotes());
                db.update("tasks", cv, "ID=?", new String[]{(++j).toString()});
            }
        }
        if(!Task.list.isEmpty()) {
            updateTasksSQL();
            Task.list.clear();
        }
    }



    public void createTableIfNotExistsTasks(SQLiteDatabase db)
    {
        String q = "CREATE TABLE if not exists tasks(";
        q += "ID integer primary key AUTOINCREMENT, ";
        q += "name text not null, ";
        q += "date String not null, ";
        q += "notes text not null);";
        db.execSQL(q);
    }

    public void updateTasksSQL()
    {
        String q="";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/" + "tasks.db", null);;
        long count =  DatabaseUtils.queryNumEntries(db, "Tasks");
        int count2 = Integer.valueOf((int) count);
        if(Task.list.size()>count) {
            for(int i=count2;i<Task.list.size();i++) {
                q = "INSERT INTO tasks(name,date,notes) VALUES(?,?,?);";
                db.execSQL(q, new Object[]{Task.list.get(i).getName(), Task.list.get(i).getDate(),
                        Task.list.get(i).getNotes()});
            }
        }
        db.close();
    }

    public void TakeTasksFromSQL() {
        String q="";

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/" + "tasks.db", null);
        createTableIfNotExistsTasks(db);

        //db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/" + "geroiOpit1.db", null);
        q = "SELECT * FROM tasks";
        Cursor c = db.rawQuery(q, null);
        while (c.moveToNext()) {
            Integer id = c.getInt(c.getColumnIndex("ID"));
            String name = c.getString(c.getColumnIndex("name"));
            String date = c.getString(c.getColumnIndex("date"));
            String notes = c.getString(c.getColumnIndex("notes"));
            Task task = new Task(id, name, date, notes);
            Task.list.add(task);
        }

        c.close();
        db.close();

    }
}
