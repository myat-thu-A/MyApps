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

import java.util.List;

import it.thomas.myapps.R;
import it.thomas.myapps.databinding.ActivityMyNoteBinding;

public class MyNote extends AppCompatActivity implements NoteItemClickListener {

    private ActivityMyNoteBinding binding;
    private NoteDao noteDao;
    private NoteAdapter noteAdapter;
    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDatabase();
        initListener();
    }
    private void initListener() {
        binding.fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditNote.class);
            startActivityForResult(intent, 123);
        });
    }

    private void initDatabase() {
        noteDao = NoteDatabase.getInstance(this).getNoteDao();
        noteAdapter = new NoteAdapter();
        noteAdapter.setNoteItemClickListener(this);
        binding.rvNote.setAdapter(noteAdapter);
        binding.rvNote.setLayoutManager(new LinearLayoutManager(this));
        refreshNote();
    }

    private void refreshNote() {
        notes = noteDao.getAllNotes();
        noteAdapter.setNoteList(notes);
    }

    @Override
    public void onUpdate(Note note) {
        Intent intent = new Intent(this, EditNote.class);
        intent.putExtra("note", note);
        startActivityForResult(intent, 234);
    }

    @Override
    public void onDelete(Note note) {
        noteDao.deleteNote(note);
        setResult(RESULT_OK);
        refreshNote();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 123 && data != null) {
                Note note;
                //Add new note
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    note = data.getSerializableExtra("note", Note.class);
                } else {
                    note = (Note) data.getSerializableExtra("note");
                }
                noteDao.addNote(note);
                refreshNote();
                Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
            } else if (requestCode == 234) {
                //Update existing note
                Note note;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    note = data.getSerializableExtra("note", Note.class);
                } else {
                    note = (Note) data.getSerializableExtra("note");
                }
                noteDao.updateNote(note);
                Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
            }
            refreshNote();
        }
    }
}
