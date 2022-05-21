package com.app.mypresence.view;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mypresence.R;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**/

    private TextView txtUsername;
    private CircleImageView imgProfile;
    private AppCompatButton btnStartTurn;

    private CalendarView calendarView;
    private Bundle bundle;


    public UserFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
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

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        // Inflate the layout for this fragment
        if (container != null){
            this.txtUsername = view.findViewById(R.id.txtUsername);
            this.imgProfile = view.findViewById(R.id.imgProfile);
            this.btnStartTurn = view.findViewById(R.id.btnStartTurn);
        }

        if (getArguments() != null){
            this.bundle = this.getArguments().getBundle("userInfo");

            String name = this.bundle.getString("name");
            String surname = this.bundle.getString("surname");

            this.txtUsername.setText(name + " " + surname);
            int id = getResources().getIdentifier((name + surname)
                                   .toLowerCase()
                                   .replace(" ",""), "drawable", this.getActivity().getPackageName());
            this.imgProfile.setImageResource(id);
        }
        this.btnStartTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog, null);
                builder.setView(customLayout);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
   

        return view;
    }


}