package it.thomas.myapps.MainAdapter.TodoApp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {
    public abstract TodoDao getTodoDao();
    private static TodoDatabase todoDatabase;
    private static final String DATABASE_NAME = "todo_app";

    public static synchronized TodoDatabase getInstance(Context context) {
        if (todoDatabase == null) {
            todoDatabase = Room.databaseBuilder(context, TodoDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return todoDatabase;
    }
}
