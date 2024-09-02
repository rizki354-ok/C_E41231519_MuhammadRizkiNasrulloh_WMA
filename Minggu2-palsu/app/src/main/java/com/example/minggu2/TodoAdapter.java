package com.example.minggu2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private final List<String> todoList;
    private final OnTodoCompleteListener onTodoCompleteListener;

    public TodoAdapter(List<String> todoList, OnTodoCompleteListener onTodoCompleteListener) {
        this.todoList = todoList;
        this.onTodoCompleteListener = onTodoCompleteListener;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        String todo = todoList.get(position);
        holder.todoText.setText(todo);
        holder.completeButton.setOnClickListener(v -> {
            if (onTodoCompleteListener != null) {
                onTodoCompleteListener.onTodoComplete(todo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView todoText;
        Button completeButton;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            todoText = itemView.findViewById(R.id.todoText);
            completeButton = itemView.findViewById(R.id.completeButton);
        }
    }

    public interface OnTodoCompleteListener {
        void onTodoComplete(String todoText);
    }
}
