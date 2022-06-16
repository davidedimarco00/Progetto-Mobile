package com.app.mypresence.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mypresence.R;
import com.app.mypresence.model.ClickListener;
import com.app.mypresence.model.RecyclerViewAdapter;
import com.app.mypresence.model.UserCard;
import com.app.mypresence.model.database.DateInfo;
import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.model.database.UserAndStats;
import com.app.mypresence.model.database.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<UserCard> listUserCard = new ArrayList<>();
    private MyPresenceViewModel mpvm;
    private Bundle bundle;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;

    public AdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminFragment newInstance(String param1, String param2) {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bundle = this.getArguments().getBundle("userInfo");
        this.mpvm = new MyPresenceViewModel(getActivity().getApplication());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        prepareMovie();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerViewAdapter = new RecyclerViewAdapter(listUserCard);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(layoutManager);
        TextView companyName = view.findViewById(R.id.companyName);
        String companyNameString = this.bundle.getString("company");
        Log.e("company name", companyNameString);
        companyName.setText(companyNameString);



        recyclerViewAdapter.setOnItemClickListener(new ClickListener<UserCard>() {
            @Override
            public void onItemClick(UserCard data) {

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);

                arrayAdapter.add("Name: " + data.getName() + " " + data.getSurname());
                arrayAdapter.add("Role: " + data.getRole());
                arrayAdapter.add("H.rs this month: " + String.valueOf(data.getHoursAndMinutesWorkedThisMonth().first) + "h : " + String.valueOf(data.getHoursAndMinutesWorkedThisMonth().second) + "mins");

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
                                break;
                        }
                    }
                });
                builderSingle.show();

            }
        });
        recyclerView.setAdapter(recyclerViewAdapter);
        view.findViewById(R.id.btnLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("loggedIn", false);
                editor.putString("username", "DEFAULT");
                editor.putString("password", "DEFAULT");
                editor.apply();

                Intent intentToLogin = new Intent(getActivity().getBaseContext(), LoginActivity.class);
                startActivity(intentToLogin);
                Toast.makeText(getContext(), "Logged out succesfully!", Toast.LENGTH_SHORT).show();

                getActivity().finish();

            }
        });

        return view;
    }

    private void prepareMovie(){

        Runnable getUsers = () -> {
            List<UserAndStats> users = this.mpvm.getAllUsersAndStats();
            for(UserAndStats userAndStats : users){
                User user = userAndStats.user;
                List<DateInfo> dateInfos = userAndStats.stats;
                Drawable drawableImg = getResources().getDrawable( R.drawable.icon_user );
                if(user.getRole().equals("Software Engineer") ||
                        user.getRole().equals("IT Specialist") ||
                        user.getRole().equals("Machine Learning SWE") ||
                        user.getRole().equals("Rendering SWE")){
                    drawableImg = getResources().getDrawable( R.drawable.swe );
                }else if(user.getRole().equals("HR Manager")){
                    drawableImg = getResources().getDrawable( R.drawable.hr );
                } else{
                    drawableImg = getResources().getDrawable( R.drawable.ceo );
                }
                this.listUserCard.add(new UserCard(user.getName(), user.getSurname(), drawableImg, user, dateInfos));
            }
        };

        getUsers.run();

    }
}