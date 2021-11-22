package com.longtran.todo_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.longtran.todo_java.databinding.ActivityCompleteBinding;
import com.longtran.todo_java.databinding.ActivityUncompleteBinding;

import java.util.List;

public class UncompleteActivity extends AppCompatActivity {

    private ActivityUncompleteBinding binding;
    private TodoHelper db;
    private List<TodoModel> todoModelList;
    private TodoAdapter todoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUncompleteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        BottomNavigationView navigation = binding.bottomNavigation;
        navigation.setOnItemSelectedListener(itemSelectedListener);

    }

    private BottomNavigationView.OnItemSelectedListener itemSelectedListener = new BottomNavigationView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page_1: {
                    return true;
                }
                case R.id.page_2: {
                    Intent intent = new Intent(UncompleteActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                case R.id.page_3: {
                    Intent intent = new Intent(UncompleteActivity.this, CompleteActivity.class);
                    startActivity(intent);
                    return true;
                }
            }
            return false;
        }
    };
}