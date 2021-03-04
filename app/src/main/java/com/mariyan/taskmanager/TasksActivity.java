package com.mariyan.taskmanager;

import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity {
    private TextView res;
    private Integer taskID =-1;
    private Button showInfo;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        ShowAllTasks();
        ListView tasksList = ShowAllTasks();

        tasksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                taskID = position;
            }
        });

        showInfo = findViewById(R.id.SeeDetailsButton);
        showInfo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (taskID == -1) {
                    Toast.makeText(getApplicationContext(), "No task chosen!", Toast.LENGTH_LONG).show();
                    Notification notify = new Notification.Builder(getApplicationContext())
                            .setContentTitle("Empty field!")
                            .setContentText("No task chosen!")
                            .build();
                    notify.flags |= Notification.FLAG_AUTO_CANCEL;
                } else {
                    openTaskInfoActivity();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private ListView ShowAllTasks() {
        res = findViewById(R.id.result);
        final ListView simpleList = findViewById(R.id.simpleListView);
        ArrayList<String> listResults = new ArrayList<>();

        for(int i = 0; i<Task.list.size(); i++) {

            listResults.add(Task.list.get(i).getName());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.activity_list_view,
                R.id.result,
                listResults
        );
        simpleList.setAdapter(arrayAdapter);
        return simpleList;
    }

    private void openTaskInfoActivity() {
        Intent intent=new Intent(getApplicationContext(),TaskInfoActivity.class);
        intent.putExtra("taskID", taskID);
        startActivity(intent);
    }
}
