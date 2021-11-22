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
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.longtran.todo_java.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private TodoHelper db;
    private List<TodoModel> todoModelList;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        BottomNavigationView navigation = binding.bottomNavigation;
        navigation.setOnItemSelectedListener(itemSelectedListener);

        db = new TodoHelper(MainActivity.this);
        todoModelList = new ArrayList<>();
        todoAdapter = new TodoAdapter(db,MainActivity.this);

        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        todoModelList = db.getALlTodo();
        todoAdapter.setTodo(todoModelList);
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

    private BottomNavigationView.OnItemSelectedListener itemSelectedListener = new BottomNavigationView.OnItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.page_1:{
                    Intent intent = new Intent(MainActivity.this,UncompleteActivity.class);
                    startActivity(intent);
                    return true;
                }
                case R.id.page_2:{
                    return true;
                }
                case R.id.page_3:{
                    Intent intent = new Intent(MainActivity.this,CompleteActivity.class);
                    startActivity(intent);
                    return true;
                }
            }
            return false;
        }
    };

    private void insertTodo(){
        String title = binding.editText.getText().toString();
        TodoModel todoModel = new TodoModel();
        todoModel.setTitle(title);
        todoModel.setIsChecked(0);
        db.insertTodo(todoModel);
        binding.editText.setText("");
        closeKeyboard();
    }

    private void refreshAdapter(){
        todoModelList = db.getALlTodo();
        todoAdapter.setTodo(todoModelList);
        todoAdapter.notifyDataSetChanged();
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}