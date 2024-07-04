package it.thomas.myapps.MainAdapter.MyNote;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Date;
import java.util.List;

import it.thomas.myapps.R;
import it.thomas.myapps.databinding.ActivityEditNoteBinding;
import it.thomas.myapps.databinding.ActivityMyNoteBinding;

public class EditNote extends AppCompatActivity {

    private ActivityEditNoteBinding binding;
    private NoteDao noteDao;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        iniDatabase();
        createOrUpdate();
        initListener();
    }

    private void initListener() {
        binding.btSaveNote.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString();
            String desc = binding.etDescription.getText().toString();
            if (!title.isEmpty() && !desc.isEmpty()) {
                if (note == null) {
                    note = new Note(title, desc, new Date());
                } else {
                    note.setTitle(title);
                    note.setDescription(desc);
                    note.setCreated(new Date());
                }
                Intent intent = new Intent();
                intent.putExtra("note", note);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Fill in the blanks ... ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void createOrUpdate() {
        Intent intent = getIntent();
        if (intent != null) {
            note = (Note) intent.getSerializableExtra("note");
            //Update
            if (note != null) {
                binding.etTitle.setText(note.getTitle());
                binding.etDescription.setText(note.getDescription());
                binding.btSaveNote.setText("Update");
            }
        }

    }

    private void iniDatabase() {
        noteDao = NoteDatabase.getInstance(this).getNoteDao();
    }

}

