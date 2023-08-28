package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomeAdapterTaskList extends RecyclerView.Adapter<CustomeAdapterTaskList.TaskViewHolder> {

    private ArrayList<TaskClass> taskList;

    public CustomeAdapterTaskList(ArrayList<TaskClass> taskList){
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taskcard,parent,false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TaskClass taskClass = taskList.get(position);



        holder.textViewTaskName.setText(taskClass.getTaskName());
        holder.textViewTaskDescription.setText(taskClass.getTaskDescription());

        if(taskClass.getIsCompleted().equals("Completed")){
            holder._isCompleted.setChecked(true);
            holder.textViewTaskName.setPaintFlags(holder.textViewTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        holder._imgDeletetask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseForTasks database = new DatabaseForTasks(view.getContext());

                holder.ll_main.setVisibility(View.GONE);
                holder.ll_second.setVisibility(View.VISIBLE);

                holder.YesBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        database.deleteTask(taskClass.getTaskName());
                        taskList.remove(position);
                        notifyDataSetChanged();
                        holder.ll_main.setVisibility(View.VISIBLE);
                        holder.ll_second.setVisibility(View.GONE);
                    }
                });

                holder.NoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.ll_second.setVisibility(View.GONE);
                        holder.ll_main.setVisibility(View.VISIBLE);
                    }
                });

            }
        });

        holder._imgEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddTaskScreen.class);
                intent.putExtra("TaskName",taskClass.getTaskName());
                intent.putExtra("TaskDesc",taskClass.getTaskDescription());
                intent.putExtra("ForEdit",1);
                view.getContext().startActivity(intent);
            }
        });

        holder._isCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    DatabaseForTasks database = new DatabaseForTasks(holder._isCompleted.getContext());
                    database.updateProgress(holder.textViewTaskName.getText().toString());
                    holder.textViewTaskName.setPaintFlags(holder.textViewTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    holder.textViewTaskName.setPaintFlags(holder.textViewTaskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    DatabaseForTasks database = new DatabaseForTasks(holder._isCompleted.getContext());
                    database.undoProgress(holder.textViewTaskName.getText().toString());
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTaskName;
        public TextView textViewTaskDescription;
        public TextView YesBTN , NoBtn;
        public ImageView _imgDeletetask, _imgEditTask;
        public LinearLayout ll_main, ll_second;

        public CheckBox _isCompleted;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTaskName = itemView.findViewById(R.id.taskName_txt);
            textViewTaskDescription = itemView.findViewById(R.id.taskDesc_txt);

            _imgDeletetask = itemView.findViewById(R.id.img_deleteTask);
            _imgEditTask = itemView.findViewById(R.id.img_EditTask);

            ll_main = itemView.findViewById(R.id.LL_main);
            ll_second = itemView.findViewById(R.id.LL_yesORno);

            YesBTN = itemView.findViewById(R.id.txt_delete);
            NoBtn = itemView.findViewById(R.id.txt_dontDelete);

            _isCompleted = itemView.findViewById(R.id.checkbox_completed);


        }
    }
}
