package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class AddTaskScreen extends AppCompatActivity{

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_screen);

        Intent intent = getIntent();
        int _forEditINT = intent.getIntExtra("ForEdit", 0);
        String TaskNameForEdit = intent.getStringExtra("TaskName");
        String TaskDescForEdit = intent.getStringExtra("TaskDesc");

        CardView textView_add = findViewById(R.id.addTaskBTN);
        TextInputEditText edt_taskName = findViewById(R.id.taskName_edt);
        TextInputEditText edt_taskDesc = findViewById(R.id.taskDesc_edt);


        DatabaseForTasks databaseForTasks = new DatabaseForTasks(getApplicationContext());

        if(_forEditINT == 1){
            TextView textView = (TextView) textView_add.getChildAt(0);
            textView.setText("Edit Task");

            Toast.makeText(getApplicationContext(), TaskNameForEdit + " " + TaskDescForEdit , Toast.LENGTH_SHORT).show();

            edt_taskName.setText(TaskNameForEdit); edt_taskDesc.setText(TaskDescForEdit);

            textView_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String newTaskName = edt_taskName.getText().toString();
                    String newTaskDesc = edt_taskDesc.getText().toString();

                    databaseForTasks.updateTask(TaskNameForEdit, newTaskName, newTaskDesc);
                    Intent intent = new Intent(getApplicationContext(), TaskScreen.class);
                    intent.putExtra("TaskUpdate",1);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });

        }else if (_forEditINT == 0){
            textView_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String _taskName = edt_taskName.getText().toString();
                    String _taskDesc = edt_taskDesc.getText().toString();

                    if(_taskName.trim().length() == 0 || _taskDesc.trim().length() == 0){
                        Toast.makeText(getApplicationContext(), "Both the fields are required.",Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        TaskClass taskClass = new TaskClass(Objects.requireNonNull(edt_taskName.getText()).toString(), Objects.requireNonNull(edt_taskDesc.getText()).toString(),
                                "Pending");
                        try {
                            databaseForTasks.AddTaskInDB(taskClass);
                            Intent intent = new Intent(getApplicationContext(), TaskScreen.class);
                            setResult(RESULT_OK, intent);
                            finish();
                            Toast.makeText(getApplicationContext(),"added",Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Some error occurred while adding the task , please try again later",Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            });
        }

    }
}