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

@Database(entities = {User.class, DateInfo.class}, version = 33)
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
                            .createFromAsset("database/user.db")
                            .allowMainThreadQueries()
                            .build();
                    //prepopulateDBwithUsers();
                }
            }
        }
        return INSTANCE;
    }

    private static void prepopulateDBwithUsers(){
        executor.execute(() -> {
            UserDAO uDao = INSTANCE.userDAO();
            uDao.deleteAll();
            User user = new User("Davide", "Di Marco", "dima", "PMA","dima1","./", true, "Project Manager Associate", "TAYGA s.r.l.");
            User user2 = new User("Stefano", "Scolari", "scola", "Principal SWE","scola1","./", false, "Software Engineer", "TAYGA s.r.l.");
            User user3 = new User("Lorenzo", "Miccoli", "lore", "I'm an HR guy.","miccoli1","./", false, "HR Manager", "TAYGA s.r.l.");
            User user4 = new User("Chiara", "Cubeddu", "chia", "IT Specialist :)","cubeddu1","./", false, "IT Specialist", "TAYGA s.r.l.");
            User user5 = new User("Giorgia", "Verdi", "gio", "I love ML","verdi1","./", false, "Machine Learning SWE", "TAYGA s.r.l.");
            User user6 = new User("Michela", "Arrigoni", "miche", "I love 3D","arrigoni1","./", false, "Rendering SWE", "TAYGA s.r.l.");
            uDao.addUser(user);
            uDao.addUser(user2);
            uDao.addUser(user3);
            uDao.addUser(user4);
            uDao.addUser(user5);
            uDao.addUser(user6);
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
            uDao.deleteAllDateInfoData();
            List<List<DateInfo>> dates = new ArrayList<>();

            for(int i=0; i<6;i++){
                dates.add(generateDatesForMonth(1, 15, 5, 2022));
            }

            int user1ID = uDao.checkIfUsernameAndPasswordAreCorrect("scola", "scola1").get(0).getUserId();
            int user2ID = uDao.checkIfUsernameAndPasswordAreCorrect("dima", "dima1").get(0).getUserId();
            int user3ID = uDao.checkIfUsernameAndPasswordAreCorrect("lore", "miccoli1").get(0).getUserId();
            int user4ID = uDao.checkIfUsernameAndPasswordAreCorrect("chia", "cubeddu1").get(0).getUserId();
            int user5ID = uDao.checkIfUsernameAndPasswordAreCorrect("gio", "verdi1").get(0).getUserId();
            int user6ID = uDao.checkIfUsernameAndPasswordAreCorrect("miche", "arrigoni1").get(0).getUserId();

            List<Integer> ids = new ArrayList<>();
            ids.add(user1ID);
            ids.add(user2ID);
            ids.add(user3ID);
            ids.add(user4ID);
            ids.add(user5ID);
            ids.add(user6ID);

            for(int i=0; i<6;i++){
                dates.add(generateDatesForMonth(1, 15, 5, 2022));
            }

            for(int i=0; i<6; i++){
                for(DateInfo date: dates.get(i)){
                    date.userOwnerOfStat = ids.get(i);
                    uDao.addDateInfo(date);
                }
            }

        });
    }

    public static String getMyPresNFCcode(){
        return "8a7fce4857";
    }
}
