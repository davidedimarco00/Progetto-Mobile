package com.app.mypresence.model.Database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(final User user);

    @Transaction
    @Query("SELECT * FROM user ORDER BY userId DESC")
    MutableLiveData<List<User>> getAllUsers();

    @Transaction
    @Query("SELECT * FROM user")
    MutableLiveData<List<UserAndStats>> getUsersWithStats(); //Ti ritorna oggetti in cui è presente [user e tutte le sue stat nello stesso oggetto]
}
