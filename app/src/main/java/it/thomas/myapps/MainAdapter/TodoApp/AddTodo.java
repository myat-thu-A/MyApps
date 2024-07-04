package it.thomas.myapps.MainAdapter.TodoApp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Objects;

import it.thomas.myapps.R;
import it.thomas.myapps.databinding.ActivityAddTodoBinding;

public class AddTodo extends AppCompatActivity {
    private ActivityAddTodoBinding binding;
    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.saveToolbar);
        initUI();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null){
            //Update todo
            todo = (Todo) intent.getSerializableExtra("todo");
            if (todo != null) {
                binding.etTitle.setText(todo.getTitle());
                binding.etDescription.setText(todo.getDescription());
                binding.numberPicker.setValue(todo.getPriority());
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note) {
            saveTodo();
        }
        return true;
    }

    private void saveTodo() {
        String title = binding.etTitle.getText().toString();
        String desc = binding.etDescription.getText().toString();
        int priority = binding.numberPicker.getValue();
        if (!title.isEmpty() && !desc.isEmpty()) {
            if (todo == null) {
                todo = new Todo(priority, desc, title);
            } else {
                todo.setTitle(title);
                todo.setDescription(desc);
                todo.setPriority(priority);
            }
            Intent intent = new Intent();
            intent.putExtra("todo", todo);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Fill in the blanks ... ", Toast.LENGTH_SHORT).show();
        }
    }

    private void initUI() {
        binding.numberPicker.setMaxValue(10);
        binding.numberPicker.setMinValue(0);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_arrow);
    }
}