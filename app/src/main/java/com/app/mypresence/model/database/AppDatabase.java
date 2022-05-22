package com.app.mypresence.model.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.airbnb.lottie.utils.Utils;
import com.app.mypresence.model.database.user.User;
import com.app.mypresence.model.database.user.UserDAO;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, DateInfo.class}, version = 9)
@TypeConverters({Converters.class})
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
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                    prepopulateDB();
                }
            }
        }
        return INSTANCE;
    }

    private static void prepopulateDB(){
        executor.execute(() -> {
            UserDAO uDao = INSTANCE.userDAO();

            uDao.deleteAll();

            User user = new User("Davide", "Di Marco", "dima", "dima1", "./", false);
            User user2 = new User("Stefano", "Scolari", "scola", "scola1", "./", false);
            User user3 = new User("Gianmarco", "Rosellini Maria Franco", "gian", "gian1", "./", false);
            uDao.addUser(user);
            uDao.addUser(user2);
            uDao.addUser(user3);

            Calendar c1 = Calendar.getInstance();
            c1.set(Calendar.MONTH, 4);
            c1.set(Calendar.DATE, 11);
            c1.set(Calendar.YEAR, 2022);

            DateInfo dateInfo = new DateInfo("active", c1.getTime(), 6);
            dateInfo.userOwnerOfStat = user.getUserId();

            uDao.addDateInfo(dateInfo);
        });

    }

}
