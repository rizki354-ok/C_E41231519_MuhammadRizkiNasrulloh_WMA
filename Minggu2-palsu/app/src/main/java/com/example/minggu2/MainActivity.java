package com.example.minggu2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText todoInput;
    private RecyclerView recyclerView;
    private RecyclerView completedRecyclerView;
    private TodoAdapter todoAdapter;
    private TodoAdapter completedTodoAdapter;
    private List<String> todoList;
    private List<String> completedTodoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoInput = findViewById(R.id.todoInput);
        recyclerView = findViewById(R.id.recyclerView);
        completedRecyclerView = findViewById(R.id.completedRecyclerView);

        todoList = new ArrayList<>();
        completedTodoList = new ArrayList<>();

        todoAdapter = new TodoAdapter(todoList, this::onTodoComplete);
        completedTodoAdapter = new TodoAdapter(completedTodoList, null);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todoAdapter);

        completedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        completedRecyclerView.setAdapter(completedTodoAdapter);

        todoInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                addTodo();
                return true;
            }
            return false;
        });
    }

    private void addTodo() {
        String todoText = todoInput.getText().toString().trim();
        if (!TextUtils.isEmpty(todoText)) {
            todoList.add(todoText);
            todoAdapter.notifyItemInserted(todoList.size() - 1);
            todoInput.setText("");  // Membersihkan input field setelah menambahkan
        } else {
            Toast.makeText(this, "Masukkan tugas terlebih dahulu!", Toast.LENGTH_SHORT).show();
        }
    }

    private void onTodoComplete(String todoText) {
        if (todoList.contains(todoText)) {
            todoList.remove(todoText);
            todoAdapter.notifyDataSetChanged();
        }

        if (!completedTodoList.contains(todoText)) {
            completedTodoList.add(todoText);
            completedTodoAdapter.notifyItemInserted(completedTodoList.size() - 1);
        }
    }
}
