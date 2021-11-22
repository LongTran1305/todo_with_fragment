package com.longtran.todo_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.longtran.todo_java.databinding.ActivityCompleteBinding;
import com.longtran.todo_java.databinding.ActivityMainBinding;
import com.longtran.todo_java.databinding.BottomNavigationBinding;

import java.util.ArrayList;
import java.util.List;

public class CompleteActivity extends AppCompatActivity {

    private ActivityCompleteBinding binding;
    private BottomNavigationBinding bottomBinding;

    private TodoHelper db;
    private List<TodoModel> todoModelList;
    private TodoAdapter todoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompleteBinding.inflate(getLayoutInflater());
        bottomBinding = binding.includeBottomNavigation;
        View view = binding.getRoot();
        setContentView(view);

        db = new TodoHelper(CompleteActivity.this);
        todoModelList = new ArrayList<>();
        todoModelList = db.getTodoChecked();
        todoAdapter = new TodoAdapter(db, CompleteActivity.this);
        todoAdapter.setTodo(todoModelList);
        todoAdapter.notifyDataSetChanged();

        BottomNavigationView navigation = bottomBinding.bottomNavigation;
        navigation.setSelectedItemId(R.id.completed);
        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.uncompleted: {
                        Intent intent = new Intent(CompleteActivity.this, UncompleteActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.main: {
                        Intent intent = new Intent(CompleteActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.completed:
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(todoAdapter);
    }
}