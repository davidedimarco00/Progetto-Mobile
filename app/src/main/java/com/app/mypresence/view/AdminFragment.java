package com.app.mypresence.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.mypresence.R;
import com.app.mypresence.model.ClickListener;
import com.app.mypresence.model.RecyclerViewAdapter;
import com.app.mypresence.model.UserCard;
import com.app.mypresence.model.database.user.User;

import java.util.ArrayList;
import java.util.List;

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
        recyclerViewAdapter.setOnItemClickListener(new ClickListener<UserCard>() {
            @Override
            public void onItemClick(UserCard data) {
                Toast.makeText(getActivity(), data.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    private void prepareMovie(){
        UserCard movie = new UserCard("Davide","Di marco", null);
        this.listUserCard.add(movie);
    }
}