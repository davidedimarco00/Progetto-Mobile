package com.app.mypresence.view;

import static android.app.Activity.RESULT_OK;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mypresence.R;
import com.app.mypresence.model.database.AppDatabase;
import com.app.mypresence.model.database.DateInfo;
import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.model.database.UserAndStats;
import com.app.mypresence.model.database.user.User;
import com.app.mypresence.model.database.user.UserDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    /*private TextView txtBio;*/
    private TextView txtRole;
    private CircleImageView imgProfile;
    private ImageView ballImg;
    private AppCompatButton btnStartTurn;
    private CalendarView calendarView;
    private Bundle bundle;
    private CardView btnLoadDoc;


    private User user;

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
            this.btnLoadDoc = view.findViewById(R.id.btnLoadDoc);
            //this.txtBio = view.findViewById(R.id.txtBio1);
            this.txtRole = view.findViewById(R.id.txtRole);
            this.ballImg = view.findViewById(R.id.imgBall);
        }

        if (getArguments() != null){
            this.bundle = this.getArguments().getBundle("userInfo");

            String name = this.bundle.getString("name");
            String surname = this.bundle.getString("surname");
            String bio = this.bundle.getString("userBio");
            String role = this.bundle.getString("userRole");
            this.user = (User) this.bundle.get("user");


            Log.e("user", this.user.toString());



            this.txtUsername.setText(name + " " + surname);
            //this.txtBio.setText(bio);
            this.txtRole.setText(role);

            int id = getResources().getIdentifier((name + surname)
                                   .toLowerCase()
                                   .replace(" ",""), "drawable", this.getActivity().getPackageName());
            this.imgProfile.setImageResource(id);
        }


        this.btnStartTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getBaseContext(), NFCActivity.class);
                intent.putExtra("username", bundle.getString("username"));
                startActivity(intent);
            }
        });

        this.imgProfile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, 3);


                return true;
            }
        });
        
        this.btnLoadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(getContext());
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
                arrayAdapter.add("Choose from gallery");
                arrayAdapter.add("Take a picture");
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
                        switch (strName){
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
        });


     /*   CountDownTimer t = new CountDownTimer(Long.MAX_VALUE ,10000) { // interval 1s

            public void onTick(long millisUntilFinished) {

                //check actual datetime and change ball imageview
               /* MyPresenceViewModel model = new MyPresenceViewModel(getActivity().getApplication());

                System.out.println(model.getAllUsers().toString());




                /*checkVisible(ArrayList visible);// make visible button
                checkInvisible(ArrayList invisible); // make invisible button*/

            //}
           /* public void onFinish() {
                System.out.println("finished");
                this.onFinish();
            }
        }.start();*/


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
          //change image with the selected from gallery
            // /*TODO: NOT STORE IMAGE IN DRAWABLE BECAUSE AT RUNTIME IT IS READONLY*/
        }
    }
}