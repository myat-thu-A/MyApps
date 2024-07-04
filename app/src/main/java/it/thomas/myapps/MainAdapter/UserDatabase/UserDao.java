package it.thomas.myapps.MainAdapter.UserDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao

public interface UserDao {
    @Insert
    void addUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}
