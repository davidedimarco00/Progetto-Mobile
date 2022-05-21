package com.app.mypresence.model.database.user;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.app.mypresence.model.database.DateInfo;
import com.app.mypresence.model.database.UserAndStats;

import java.util.Date;
import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addUser(final User user);

    @Transaction
    @Query("SELECT * FROM user ORDER BY userId DESC")
    LiveData<List<User>> getAllUsers();

    @Transaction
    @Query("DELETE FROM user")
    void deleteAll();

    @Transaction
    @Query("SELECT * FROM user")
    LiveData<List<UserAndStats>> getUsersWithStats(); //Ti ritorna oggetti in cui è presente [user e tutte le sue stat nello stesso oggetto]

    @Transaction
    @Query("SELECT * FROM user WHERE username=:username AND password=:password")
    List<User> checkIfUsernameAndPasswordAreCorrect(String username, String password);

    @Insert
    void addDateInfo(DateInfo userDateInfo);

    //Get stats from a user on a specific day
    @Transaction
    @Query("SELECT * FROM user, dateinfo WHERE user.username=:username AND user.password=:password AND dateInfo.date=:date")
    List<UserAndStats> getUserStatsOnGivenDay(final String username, final String password, final Date date);

}
