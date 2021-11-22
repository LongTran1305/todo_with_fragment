package com.longtran.todo_java;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longtran.todo_java.databinding.TodoItemBinding;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoModel> todoModelList;
    private MainActivity activity;
    private TodoHelper db ;


    public TodoAdapter (TodoHelper db,MainActivity activity){
        this.activity = activity;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(TodoItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoModel todo = todoModelList.get(position);
        holder.todoItemBinding.tvTitle.setText(todo.getTitle());
        if(todo.getIsChecked() == 1){
            holder.todoItemBinding.tvTitle.setPaintFlags(holder.todoItemBinding.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.todoItemBinding.checkBox.setChecked(toBoolean(todo.getIsChecked()));
        holder.todoItemBinding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    db.updateIsChecked(todo.getId(),1);
                    holder.todoItemBinding.tvTitle.setPaintFlags(holder.todoItemBinding.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    db.updateIsChecked(todo.getId(),0);
                    holder.todoItemBinding.tvTitle.setPaintFlags(holder.todoItemBinding.tvTitle.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(todoModelList != null){
            return todoModelList.size();
        }else{
            return 0;
        }
    }

    public void setTodo(List<TodoModel> todoModelList){
        this.todoModelList = todoModelList;
        notifyDataSetChanged();
    }

    public boolean toBoolean(int number){
        return number!=0;
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TodoItemBinding todoItemBinding;

        public ViewHolder(TodoItemBinding binding){
            super(binding.getRoot());
            this.todoItemBinding = binding;
        }
    }
}
