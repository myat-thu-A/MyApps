package it.thomas.myapps.MainAdapter.Mp3Playlist;

import java.io.Serializable;

public record SongData(
        String artistName,
        String songName,
        Integer artistPhoto,
        Integer artistSong
) implements Serializable {
}
