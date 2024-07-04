package it.thomas.myapps.MainAdapter.MyNote;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import it.thomas.myapps.databinding.ItemNoteBinding;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> noteList;
    private NoteItemClickListener noteItemClickListener;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.binding.tvTitle.setText(note.getTitle());
        holder.binding.tvDescription.setText(note.getDescription());
        var created = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(note.getCreated());
        holder.binding.tvCreated.setText(created);

        holder.binding.btEdit.setOnClickListener(v -> {
            noteItemClickListener.onUpdate(note);
        });

        holder.binding.btDelete.setOnClickListener(v -> {
            noteItemClickListener.onDelete(note);
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    public void setNoteItemClickListener(NoteItemClickListener noteItemClickListener) {
        this.noteItemClickListener = noteItemClickListener;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{
        public ItemNoteBinding binding;

        public NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
