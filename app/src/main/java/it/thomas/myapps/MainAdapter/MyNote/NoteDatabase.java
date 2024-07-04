package it.thomas.myapps.MainAdapter.MyNote;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase noteDatabase;
    public abstract NoteDao getNoteDao();
    private static final String DATABASE_NAME = "note_app";

    public static synchronized NoteDatabase getInstance(Context context) {
        if (noteDatabase == null) {
            noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return noteDatabase;
    }
}
