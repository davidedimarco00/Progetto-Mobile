package com.app.mypresence.model.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.app.mypresence.model.database.user.User;
import com.app.mypresence.model.database.user.UserDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, DateInfo.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDAO userDAO();

    private static volatile AppDatabase INSTANCE;

    public static final ExecutorService executor = Executors.newFixedThreadPool(4);

    public static AppDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database")
                            .addCallback(sRoomDatabasePopulatorCallback)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabasePopulatorCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            executor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UserDAO uDao = INSTANCE.userDAO();

                uDao.deleteAll();

                User user = new User("Davide", "Di Marco", "dima", "dima1", "./");
                uDao.addUser(user);
            });
        }
    };

}
