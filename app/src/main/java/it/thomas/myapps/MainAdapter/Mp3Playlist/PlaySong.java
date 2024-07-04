package it.thomas.myapps.MainAdapter.Mp3Playlist;

import static it.thomas.myapps.MainAdapter.Mp3Playlist.Mp3Playlist.songDataList;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.RawResourceDataSource;
import androidx.media3.exoplayer.ExoPlayer;

import it.thomas.myapps.R;
import it.thomas.myapps.databinding.ActivityPlaySongBinding;

public class PlaySong extends AppCompatActivity {

    private ActivityPlaySongBinding binding;
    private SongData songData;
    private ExoPlayer exoPlayer;
    private Handler handler;
    private Runnable updateSeekbar;
    private int artistIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaySongBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initDataFromIntent();
        initPlayer();
        initSeekbar();
        initListener();
    }

    private void initListener() {
        binding.btNext.setOnClickListener(v -> {
            if (artistIndex == songDataList.length -1) {
                artistIndex = 0;
            } else {
                artistIndex++;
            }
            songData = songDataList[artistIndex];
            updatePlayer();
            binding.seekbar.setProgress(0);
        });

        binding.btPause.setOnClickListener(v -> {
            if (exoPlayer.isPlaying()) {
                exoPlayer.pause();
                binding.btPause.setImageResource(R.drawable.play);
                handler.removeCallbacks(updateSeekbar);
            } else {
                exoPlayer.play();
                binding.btPause.setImageResource(R.drawable.pause);
                handler.postDelayed(updateSeekbar, 1000);
            }
        });

        binding.btPrev.setOnClickListener(v -> {
            if (artistIndex == 0) {
                artistIndex = songDataList.length - 1;
            } else {
                artistIndex--;
            }
            songData = songDataList[artistIndex];
            updatePlayer();
            binding.seekbar.setProgress(0);
        });

        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int newPosition = (int) (exoPlayer.getDuration() * progress /100);
                    exoPlayer.seekTo(newPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (exoPlayer.isPlaying())
                    exoPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                exoPlayer.play();
            }
        });
    }

    private void initSeekbar() {
        handler = new Handler();
        updateSeekbar = new Runnable() {
            @Override
            public void run() {
                if(exoPlayer != null) {
                    int currentPosition = (int) exoPlayer.getCurrentPosition();
                    int totalDuration = (int) exoPlayer.getDuration();
                    binding.seekbar.setProgress(currentPosition * 100 / totalDuration);
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    private void initPlayer() {
        if (exoPlayer == null) {
            exoPlayer = new ExoPlayer.Builder(this).build();
        }
        updatePlayer();
    }

    private void updatePlayer() {
        binding.tvArtist.setText(songData.artistName());
        binding.tvSong.setText(songData.songName());
        binding.ivArtist.setImageResource(songData.artistPhoto());
        @OptIn(markerClass = UnstableApi.class)
        Uri song = RawResourceDataSource.buildRawResourceUri(songData.artistSong());

        exoPlayer.setMediaItem(MediaItem.fromUri(song));
        exoPlayer.prepare();
        exoPlayer.play();
        exoPlayer.seekTo(0);
    }

    private void initDataFromIntent() {
        if (getIntent() != null) {
            artistIndex = getIntent().getIntExtra("artist_index", 0);
            songData = (SongData) getIntent().getSerializableExtra("artist");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
        handler.removeCallbacks(updateSeekbar);
    }
}