package it.thomas.myapps.MainAdapter.Mp3Playlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import it.thomas.myapps.R;
import it.thomas.myapps.databinding.ActivityMp3PlaylistBinding;

public class Mp3Playlist extends AppCompatActivity {

    private ActivityMp3PlaylistBinding binding;
    private SongAdapter songAdapter;
    static SongData[] songDataList = {
            new SongData("Annie Marie", "2002", R.drawable.anne_marie, R.raw.anne_marie_twozerozerotwo),
            new SongData("Maroon5", "Memories", R.drawable.maroon, R.raw.memories_maroon),
            new SongData("Dua Lipa", "New Rules", R.drawable.dualipa, R.raw.newrules_dualipa),
            new SongData("Ariana Grande", "Positions", R.drawable.ariana, R.raw.positions_ariana),
            new SongData("Austin Mahone", "Send it to my phone", R.drawable.austin, R.raw.sendit),
            new SongData("Black Pink", "As If It's Your Love", R.drawable.blackpink, R.raw.as_if_its_your_love_blackpink),
            new SongData("Justin Bieber", "Better", R.drawable.justin, R.raw.better_jb),
            new SongData("Double J", "Candy", R.drawable.doublej, R.raw.candy_doublej),
            new SongData("Justin Bieber", "Cold Water", R.drawable.justin, R.raw.cold_water_jb),
            new SongData("Black Pink", "Duu Du Duu Du", R.drawable.blackpink, R.raw.duu_du_duu_du_blackpink),
            new SongData("Ed Shreen", "Happier", R.drawable.edshreen, R.raw.happier_edshreen),
            new SongData("Justin Bieber", "Hard To Face Reality", R.drawable.justin, R.raw.hard_to_face_jb),
            new SongData("Dj Snake", "Let Me Love You", R.drawable.djsnake, R.raw.let_me_love_you_djsnake),
            new SongData("Dj Snake", "Loco Contigo", R.drawable.djsnake, R.raw.loco_contigo_djsnake),
            new SongData("Bruno Mars", "Marry You", R.drawable.bruno, R.raw.marry_you_bruno),
            new SongData("Ed Shreen", "Perfect", R.drawable.edshreen, R.raw.perfect_edshreen),
            new SongData("Ed Shreen", "Shape Of You", R.drawable.edshreen, R.raw.shape_of_you_edshreen),
            new SongData("Ariana Grande", "Side To Side", R.drawable.ariana, R.raw.side_to_side_ariana),
            new SongData("Double J", "Take Care", R.drawable.doublej, R.raw.takecare_doublej),
            new SongData("Yair Yint Aung", "Waiting For You", R.drawable.yya, R.raw.waitingforu_yya)
    };;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMp3PlaylistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
        //initSongs();
        initListener();
    }

    private void initListener() {
        Intent intent = new Intent(this, PlaySong.class);
        songAdapter.setSongClickListener(new SongClickListener() {
            @Override
            public void onClick(int index) {
                intent.putExtra("artist_index", index);
                intent.putExtra("artist", songDataList[index]);
                startActivity(intent);
            }
        });
    }

    private void initSongs() {
        songDataList = new SongData[]{
                new SongData("Annie Marie", "2002", R.drawable.anne_marie, R.raw.anne_marie_twozerozerotwo),
                new SongData("Maroon5", "Memories", R.drawable.maroon, R.raw.memories_maroon),
                new SongData("Dua Lipa", "New Rules", R.drawable.dualipa, R.raw.newrules_dualipa),
                new SongData("Ariana Grande", "Positions", R.drawable.ariana, R.raw.positions_ariana),
                new SongData("Austin Mahone", "Send it to my phone", R.drawable.austin, R.raw.sendit),
                new SongData("Black Pink", "As If It's Your Love", R.drawable.blackpink, R.raw.as_if_its_your_love_blackpink),
                new SongData("Justin Bieber", "Better", R.drawable.justin, R.raw.better_jb),
                new SongData("Double J", "Candy", R.drawable.doublej, R.raw.candy_doublej),
                new SongData("Justin Bieber", "Cold Water", R.drawable.justin, R.raw.cold_water_jb),
                new SongData("Black Pink", "Duu Du Duu Du", R.drawable.blackpink, R.raw.duu_du_duu_du_blackpink),
                new SongData("Ed Shreen", "Happier", R.drawable.edshreen, R.raw.happier_edshreen),
                new SongData("Justin Bieber", "Hard To Face Reality", R.drawable.justin, R.raw.hard_to_face_jb),
                new SongData("Dj Snake", "Let Me Love You", R.drawable.djsnake, R.raw.let_me_love_you_djsnake),
                new SongData("Dj Snake", "Loco Contigo", R.drawable.djsnake, R.raw.loco_contigo_djsnake),
                new SongData("Bruno Mars", "Marry You", R.drawable.bruno, R.raw.marry_you_bruno),
                new SongData("Ed Shreen", "Perfect", R.drawable.edshreen, R.raw.perfect_edshreen),
                new SongData("Ed Shreen", "Shape Of You", R.drawable.edshreen, R.raw.shape_of_you_edshreen),
                new SongData("Ariana Grande", "Side To Side", R.drawable.ariana, R.raw.side_to_side_ariana),
                new SongData("Double J", "Take Care", R.drawable.doublej, R.raw.takecare_doublej),
                new SongData("Yair Yint Aung", "Waiting For You", R.drawable.yya, R.raw.waitingforu_yya)
        };
    }

    private void initUI() {
        songAdapter = new SongAdapter();
        binding.rvSongs.setAdapter(songAdapter);
        binding.rvSongs.setLayoutManager(new LinearLayoutManager(this));
        songAdapter.setSongDataList(List.of(songDataList));
    }
}