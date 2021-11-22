package com.longtran.todo_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.longtran.todo_java.databinding.ActivityCompleteBinding;
import com.longtran.todo_java.databinding.ActivityMainBinding;

import java.util.List;

public class CompleteActivity extends AppCompatActivity {

    private ActivityCompleteBinding binding;
    private TodoHelper db;
    private List<TodoModel> todoModelList;
    private TodoAdapter todoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompleteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        BottomNavigationView navigation = binding.bottomNavigation;
        navigation.setOnItemSelectedListener(itemSelectedListener);
    }

    private BottomNavigationView.OnItemSelectedListener itemSelectedListener = new BottomNavigationView.OnItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.page_1:{
                    Intent intent = new Intent(CompleteActivity.this, UncompleteActivity.class);
                    startActivity(intent);
                    return true;
                }
                case R.id.page_2:{
                    Intent intent = new Intent(CompleteActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                case R.id.page_3:{
                    return true;
                }
            }
            return false;
        }
    };
}