package com.app.mypresence.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mypresence.R;
import com.app.mypresence.model.StatisticsContainer;
import com.app.mypresence.model.database.DateInfo;
import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.model.database.UserAndStats;

import java.util.ArrayList;
import java.util.List;

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
        this.setView(getActivity());
    }

    private void setView(final Activity activity){


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