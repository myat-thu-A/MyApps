package it.thomas.myapps.MainAdapter.Mp3Playlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import it.thomas.myapps.databinding.ItemSongBinding;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    List<SongData> songDataList;
    SongClickListener songClickListener;

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var binding = ItemSongBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SongViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        SongData songData = songDataList.get(position);
        holder.bind(songData);

        holder.binding.getRoot().setOnClickListener(v -> {
            songClickListener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return songDataList.size();
    }

    public void setSongDataList(List<SongData> songDataList) {
        this.songDataList = songDataList;
        notifyDataSetChanged();
    }

    public void setSongClickListener(SongClickListener songClickListener) {
        this.songClickListener = songClickListener;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder{
        ItemSongBinding binding;

        public SongViewHolder(ItemSongBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SongData songData) {
            binding.ivArtist.setImageResource(songData.artistPhoto());
            binding.tvArtist.setText(songData.artistName());
            binding.tvSong.setText(songData.songName());
        }
    }
}
