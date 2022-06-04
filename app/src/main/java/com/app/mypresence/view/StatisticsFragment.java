package com.app.mypresence.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mypresence.R;
import com.app.mypresence.model.StatisticsContainer;
import com.app.mypresence.model.database.Converters;
import com.app.mypresence.model.database.DateInfo;
import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.model.database.UserAndStats;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.joda.time.DateTime;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kotlin.Pair;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "username";
    private static final String ARG_PARAM2 = "password";
    private RecyclerView recyclerView;
    private CompactCalendarView calendarView;
    private TextView txtmonth;


    // TODO: Rename and change types of parameters
    private String username;
    private String password;

    public StatisticsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.username = getArguments().getString(ARG_PARAM1);
            this.password = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MyPresenceViewModel model = new MyPresenceViewModel(getActivity().getApplication());


        this.calendarView = (CompactCalendarView) view.findViewById(R.id.compactcalendar);
        this.txtmonth = (TextView) view.findViewById(R.id.month_textview);
        calendarView.setUseThreeLetterAbbreviation(true);
        calendarView.shouldScrollMonth(true);

        List<Event> listEvent = new ArrayList<Event>();

        Map<String, List<Long>> mapList = model.getMonthStatus(this.username, this.password, 5);

        /*Event ev1 = new Event(Color.BLACK, 1477040400000L, "Teachers' Professional Day");
        compactCalendar.addEvent(ev1);*/

        for (String color : mapList.keySet()) {

            List<Long> list = mapList.get(color);

            for (int i = 0;i<list.size()-1;i++) {

                if (color.equals("red")){
                    Event event = new Event(Color.RED, list.get(i), "You work too much");
                    listEvent.add(event);

                } else if (color.equals("green")) {
                    Event event = new Event(Color.GREEN, list.get(i), "You work too much");
                    listEvent.add(event);
                }else {
                    Event event = new Event(Color.BLACK, list.get(i), "You work too much");
                    listEvent.add(event);

                }
            }
        }

        for (Event e : listEvent){
            calendarView.addEvent(e);
        }

        SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Animation myAnim = AnimationUtils.loadAnimation(getActivity(), androidx.preference.R.anim.abc_fade_in);
                calendarView.startAnimation(myAnim);

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);

                builderSingle.setView(R.layout.custom_dialog);

                arrayAdapter.add("Stats1");
                arrayAdapter.add("Stats2");
                builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = arrayAdapter.getItem(which);
                        switch (strName) {
                            case "Choose from gallery":

                                break;
                            case "Take a picture":
                               /* Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, );*/
                                break;
                        }
                    }
                });
                builderSingle.show();

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                txtmonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        this.txtmonth.setText(dateFormatForMonth.format(calendarView.getFirstDayOfCurrentMonth()));





        System.out.println(mapList);

        this.setView(getActivity());
    }

    private void setView(final Activity activity) {


        MyPresenceViewModel mpvm = new MyPresenceViewModel(this.getActivity().getApplication());

        Runnable statsThread1 = () -> {

            int workedHoursThisMonth = mpvm.totalWorkedHoursThisMonth(username, password);
            TextView totalWorkedHours = this.getView().findViewById(R.id.numberTotWorkedHours);
            totalWorkedHours.setText(String.valueOf(workedHoursThisMonth));

            int averageWorkedHoursNumber = mpvm.averageHoursWorkedThisMonth(username, password);
            TextView averageWorkedHours = this.getView().findViewById(R.id.avgWorkedHours);
            averageWorkedHours.setText(String.valueOf(averageWorkedHoursNumber));

        };
        statsThread1.run();

        Runnable statsThread2 = () -> {
            Pair<String, Pair<Integer, Integer>> mostWorked = mpvm.mostWorkedHoursInDay(username, password);
            TextView dateMostWorkedH = this.getView().findViewById(R.id.mostWorkedHoursInADayDate);
            TextView mostWorkedH = this.getView().findViewById(R.id.mostWorkedHoursInADay);

            dateMostWorkedH.setText(String.valueOf(mostWorked.getFirst()));
            mostWorkedH.setText(String.valueOf(mostWorked.getSecond().getFirst()) + "H " + String.valueOf(mostWorked.getSecond().getSecond()) + "mins");
        };
        statsThread2.run();

        Runnable statsThread3 = () -> {
            String earliestArrivalTime = mpvm.earliestArrival(username, password);
            String latestLeaveTime = mpvm.latestLeave(username, password);
            TextView earliestArrival = this.getView().findViewById(R.id.earliestArrival);
            TextView latestLeave = this.getView().findViewById(R.id.latestLeave);

            earliestArrival.setText(earliestArrivalTime);
            latestLeave.setText(latestLeaveTime);
        };
        statsThread3.run();
    }


}