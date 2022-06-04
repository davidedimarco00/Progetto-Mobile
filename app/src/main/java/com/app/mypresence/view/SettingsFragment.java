package com.app.mypresence.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.mypresence.R;
import com.app.mypresence.model.database.MyPresenceViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MyPresenceViewModel mpvm;
    // TODO: Rename and change types of parameters
    private String username;
    private String password;

    private SharedPreferences sharedPreferences;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters



    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mpvm = new MyPresenceViewModel(getActivity().getApplication());
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM1);
            password = getArguments().getString(ARG_PARAM2);
            this.sharedPreferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        Button saveBtn = view.findViewById(R.id.saveBtn);
        EditText bio = view.findViewById(R.id.bio_input);
        String actualBio = this.mpvm.getUserFromUsernameAndPassword(this.username, this.password).get(0).getBio();
        bio.setText(actualBio);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String information = bio.getText().toString();
                System.out.println(information + " =? " + mpvm.getUserFromUsernameAndPassword(username, password).get(0).getBio());
                mpvm.updateUserBio(username, password, information);
            }
        });




        return view;
    }
}