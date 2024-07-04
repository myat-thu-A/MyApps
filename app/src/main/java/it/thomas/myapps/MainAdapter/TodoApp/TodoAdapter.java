package it.thomas.myapps.MainAdapter.TodoApp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.thomas.myapps.databinding.ItemTodoBinding;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> todoList;
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TodoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.binding.tvTitle.setText(todo.getTitle());
        holder.binding.tvDescription.setText(todo.getDescription());
        holder.binding.tvPriority.setText(String.valueOf(todo.getPriority()));

        holder.binding.getRoot().setOnClickListener(v -> {
            onItemClickListener.onClick(todo);
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public Todo getTodoAt(int position) {
        return todoList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder{
        ItemTodoBinding binding;

        public TodoViewHolder(ItemTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
