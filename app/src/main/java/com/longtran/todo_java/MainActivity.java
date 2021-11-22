package com.longtran.todo_java;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.longtran.todo_java.databinding.ActivityMainBinding;
import com.longtran.todo_java.databinding.BottomNavigationBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BottomNavigationBinding bottomBinding;
    private TodoHelper db;
    private List<TodoModel> todoModelList;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        bottomBinding = binding.includeBottomNavigation;
        View view = binding.getRoot();
        setContentView(view);

        db = new TodoHelper(MainActivity.this);
        todoModelList = new ArrayList<>();
        todoModelList = db.getALlTodo();
        todoAdapter = new TodoAdapter(db, MainActivity.this);
        todoAdapter.setTodo(todoModelList);

        BottomNavigationView navigation = bottomBinding.bottomNavigation;
        navigation.setSelectedItemId(R.id.main);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.uncompleted: {
                        Intent intent = new Intent(MainActivity.this, UncompleteActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.main:
                        break;
                    case R.id.completed: {
                        Intent intent = new Intent(MainActivity.this, CompleteActivity.class);
                        startActivity(intent);
                        break;
                    }
                    default:
                        return false;
                }
                return false;
            }
        });


        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todoAdapter);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                insertTodo();
                refreshAdapter();
            }
        });
    }

    private void insertTodo() {
        String title = binding.editText.getText().toString();
        TodoModel todoModel = new TodoModel();
        todoModel.setTitle(title);
        todoModel.setIsChecked(0);
        db.insertTodo(todoModel);
        binding.editText.setText("");
        closeKeyboard();
    }

    private void refreshAdapter() {
        todoModelList = db.getALlTodo();
        todoAdapter.setTodo(todoModelList);
        todoAdapter.notifyDataSetChanged();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}