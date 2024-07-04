package it.thomas.myapps.MainAdapter.TodoApp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.thomas.myapps.R;
import it.thomas.myapps.databinding.ActivityTodoAppBinding;

public class TodoApp extends AppCompatActivity implements OnItemClickListener{
    private ActivityTodoAppBinding binding;
    private TodoDao todoDao;
    private TodoAdapter todoAdapter;
    private List<Todo> todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodoAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        initDatabase();
        initListener();
    }

    private void initListener() {
        binding.fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTodo.class);
            startActivityForResult(intent, 123);
        });

        deleteTodo();
    }

    private void deleteTodo() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                todoDao.deleteTodo(todoAdapter.getTodoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TodoApp.this, "Todo Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.rvNotes);
        refreshTodo();
    }

    private void refreshTodo() {
        todoList = todoDao.getAllTodo();
        todoAdapter.setTodoList(todoList);

    }

    private void initDatabase() {
        todoDao = TodoDatabase.getInstance(this).getTodoDao();
        todoAdapter = new TodoAdapter();
        todoAdapter.setOnItemClickListener(this);
        binding.rvNotes.setAdapter(todoAdapter);
        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));
        refreshTodo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_delete_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.notes_delete) {
            todoDao.deleteAll();
            refreshTodo();
        }
        return true;
    }

    @Override
    public void onClick(Todo todo) {
        Intent intent = new Intent(this, AddTodo.class);
        intent.putExtra("todo", todo);
        startActivityForResult(intent, 234);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 123 && data != null) {
                Todo todo;
                //Add New Todo;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    todo = data.getSerializableExtra("todo", Todo.class);
                } else {
                    todo = (Todo) data.getSerializableExtra("todo");
                }
                todoDao.insertTodo(todo);
                Toast.makeText(this, "Todo saved", Toast.LENGTH_SHORT).show();
            } else if (requestCode == 234) {
                //Update Existing Todo
                Todo todo;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                    assert data != null;
                    todo = data.getSerializableExtra("todo", Todo.class);
                } else {
                    assert data != null;
                    todo = (Todo) data.getSerializableExtra("note");
                }
                todoDao.updateTodo(todo);
                Toast.makeText(this, "Todo Updated", Toast.LENGTH_SHORT).show();
            }
            refreshTodo();
        }
    }
}