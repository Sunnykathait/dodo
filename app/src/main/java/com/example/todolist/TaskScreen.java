package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskScreen extends AppCompatActivity {

    private ArrayList<TaskClass> arrayList; // for storing the task class fetched from the database
    private CustomeAdapterTaskList customeAdapterTaskList;

    SwitchCompat switchCompat_mode;

    private static final int REQUEST_CODE_SECOND_ACTIVITY = 1;

    int _isUpdated;

    RecyclerView recyclerView_task;

    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_screen);

        DatabaseForTasks databaseForTasks = new DatabaseForTasks(getApplicationContext());

        arrayList = new ArrayList<>();

        CardView cardView_btn = findViewById(R.id.createTaskBTN);
        recyclerView_task = findViewById(R.id.recyclerViewTasks);
        

        try {
            UpdateUI();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Some error occurred",Toast.LENGTH_SHORT).show();
        }


        cardView_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTaskScreen.class);
                startActivityForResult(intent,REQUEST_CODE_SECOND_ACTIVITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                UpdateUI();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateUI();
    }


    void UpdateUI(){
        DatabaseForTasks databaseForTasks = new DatabaseForTasks(getApplicationContext());
        arrayList = databaseForTasks.showTasks();
        customeAdapterTaskList = new CustomeAdapterTaskList(arrayList);
        customeAdapterTaskList.notifyDataSetChanged();

        recyclerView_task.setAdapter(customeAdapterTaskList);
        recyclerView_task.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}