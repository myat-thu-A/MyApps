package it.thomas.myapps.MainAdapter.UserDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase myDatabase;
    public abstract UserDao getUserDao();

    public MyDatabase() {

    }

    public static MyDatabase getInstance(Context context) {
        if (myDatabase == null) {
            myDatabase = Room.databaseBuilder(context, MyDatabase.class, "my_database").allowMainThreadQueries().build();
        }
        return myDatabase;
    }
}
