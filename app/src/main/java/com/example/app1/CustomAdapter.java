package com.example.app1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> task_id, taskObject, dayOfWeek, wtd;

    public interface OnDetailsClickListener {
        void onDetailsClick(int position);
    }

    private OnDetailsClickListener onDetailsClickListener;

    public void setOnDetailsClickListener(OnDetailsClickListener listener) {
        this.onDetailsClickListener = listener;
    }
    public void updateData(ArrayList<String> task_id, ArrayList<String> taskObject, ArrayList<String> dayOfWeek, ArrayList<String> wtd) {
        this.task_id = task_id;
        this.taskObject = taskObject;
        this.dayOfWeek = dayOfWeek;
        this.wtd = wtd;
        notifyDataSetChanged();
    }

    CustomAdapter(Context context, ArrayList<String> task_id, ArrayList<String> taskObject, ArrayList<String> dayOfWeek, ArrayList<String> wtd) {
        this.context = context;
        this.task_id = task_id;
        this.taskObject = taskObject;
        this.dayOfWeek = dayOfWeek;
        this.wtd = wtd;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.task_id_txt.setText(String.valueOf(task_id.get(position)));
        holder.taskObject_txt.setText(String.valueOf(taskObject.get(position)));
        holder.dayOfWeek_txt.setText(String.valueOf(dayOfWeek.get(position)));
        holder.wtd_txt.setText(String.valueOf(wtd.get(position)));

        // Use holder.getAdapterPosition() to obtain the current position
        if (holder.details_btn != null) {
            holder.details_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = holder.getAdapterPosition();

                    if (clickedPosition != RecyclerView.NO_POSITION && onDetailsClickListener != null) {
                        Log.d("CustomAdapter", "Button clicked at position: " + clickedPosition);
                        String taskId = task_id.get(clickedPosition);

                        // Start the DetailActivity and pass the task ID
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("taskId", taskId);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView task_id_txt, taskObject_txt, dayOfWeek_txt, wtd_txt;
        Button details_btn; // Add this line


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_id_txt = itemView.findViewById(R.id.Task_id_txt);
            taskObject_txt = itemView.findViewById(R.id.TaskObject_txt);
            dayOfWeek_txt = itemView.findViewById(R.id.DayOfWeek_txt);
            wtd_txt = itemView.findViewById(R.id.wtd_txt);
            details_btn = itemView.findViewById(R.id.details_btn); // Initialize details_btn
        }
    }
}
