package com.app.mypresence.model.database;

import android.content.Context;
import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.airbnb.lottie.utils.Utils;
import com.app.mypresence.model.database.user.User;
import com.app.mypresence.model.database.user.UserDAO;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Database(entities = {User.class, DateInfo.class}, version = 18)
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
                    prepopulateDBwithUsers();
                }
            }
        }
        return INSTANCE;
    }

    private static void prepopulateDBwithUsers(){
        executor.execute(() -> {
            UserDAO uDao = INSTANCE.userDAO();
            uDao.deleteAll();
            User user = new User("Davide", "Di Marco", "dima", "Mi chiamo Davide Di Marco","dima1","./", false, "Project Manager Associate");
            User user2 = new User("Stefano", "Scolari", "scola", "I'm the principal SWE working on a new project.","scola1","./", false, "Software Engineer");
            uDao.addUser(user);
            uDao.addUser(user2);
        });

    }

    static class CalendarBuilder{
        private Calendar c = Calendar.getInstance();

        CalendarBuilder month(final int month){
            c.set(Calendar.MONTH, month);
            return this;
        }

        CalendarBuilder day(final int day){
            c.set(Calendar.DATE, day);
            return this;
        }

        CalendarBuilder year(final int year){
            c.set(Calendar.YEAR, year);
            return this;
        }

        Date buildDate(){
            return c.getTime();
        }
    }

    private static Pair<String,String> randomWorkShiftGenerator(int minimumTime, int maximumTime){

        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");

        Hours startHours = Hours.hours(ThreadLocalRandom.current().nextInt(minimumTime, 9));
        Minutes startMinutes = Minutes.minutes(ThreadLocalRandom.current().nextInt(10, 59 + 1));

        Hours endHours = Hours.hours(ThreadLocalRandom.current().nextInt(12, maximumTime + 1));
        Minutes endMinutes = Minutes.minutes(ThreadLocalRandom.current().nextInt(10, 59 + 1));

        String startShift = "0" + String.valueOf(startHours.getHours()) + ":" + String.valueOf(startMinutes.getMinutes());
        String endShift = String.valueOf(endHours.getHours()) + ":" + String.valueOf(endMinutes.getMinutes());

        return new Pair<>(startShift,endShift);

    }

    private static List<DateInfo> generateDatesForMonth(int startDay, int endDay, int month, int year){
        CalendarBuilder calendarBuilder = new CalendarBuilder();

        List<Pair<Date,Pair<String, String>>> datesForMonth = new ArrayList<>();

        for(int i=startDay; i<endDay; i++){
            datesForMonth.add(new Pair<>(calendarBuilder.day(i).month(month).year(year).buildDate(), randomWorkShiftGenerator(7, 21)));
        }
        List<DateInfo> datesToAdd = new ArrayList<>();

        for(Pair<Date,Pair<String, String>> stamp:datesForMonth){
            datesToAdd.add(new DateInfo("over", stamp.first, stamp.second.first, stamp.second.second));
        }

        return datesToAdd;
    }

    public static void prepopulateDBwithDateInfo(){
        executor.execute(() -> {
            UserDAO uDao = AppDatabase.INSTANCE.userDAO();
            List<DateInfo> dates = generateDatesForMonth(1, 16, 5, 2022);

            int user1ID = uDao.checkIfUsernameAndPasswordAreCorrect("scola", "scola1").get(0).getUserId();

            for(DateInfo date:dates){
                date.userOwnerOfStat = user1ID;
                uDao.addDateInfo(date);
            }

        });
    }

    public static String getMyPresNFCcode(){
        return "8a7fce4857";
    }
}
