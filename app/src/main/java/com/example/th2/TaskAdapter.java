package com.example.th2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.databinding.ItemBinding;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private final OnClickListener listener;

    public TaskAdapter(OnClickListener listener) {
        this.listener = listener;
    }
    public void reloadData(List<Task> list) {
        this.taskList = list;
        notifyDataSetChanged();
    }
    public interface OnClickListener {
        void OnItemClick(Task task, View view);
    }
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBinding binding = ItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(position);
        holder.binding.item.setOnClickListener(view -> listener.OnItemClick(taskList.get(position), view));
    }

    @Override
    public int getItemCount() {
        if (taskList != null){
            return taskList.size();
        }
        return 0;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{
        private ItemBinding binding;
        public TaskViewHolder(@NonNull ItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(int position){
            Task task = taskList.get(position);
            if(task == null){
                return;
            }
            binding.tvName.setText(task.getName());
            binding.tvContent.setText(task.getContent());
            binding.tvStatus.setText(task.getStatus());
            binding.tvDate.setText(task.getDate());

            if(task.getColab() != 0){
                binding.tvColab.setText("Team");
            }
            else{
                binding.tvColab.setText("Alone");
            }
        }
    }
}
