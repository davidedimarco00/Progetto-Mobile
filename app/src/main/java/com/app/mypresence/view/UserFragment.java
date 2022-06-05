package com.app.mypresence.view;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.preference.PreferenceManager;

import android.os.CountDownTimer;
import android.os.Environment;
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
import com.app.mypresence.model.database.Converters;
import com.app.mypresence.model.database.DateInfo;
import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.model.database.UserAndStats;
import com.app.mypresence.model.database.user.User;
import com.app.mypresence.model.database.user.UserDAO;

import org.joda.time.DateTime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private TextView txtBio;
    private TextView txtRole;
    private TextView labelStartTurn;

    private CircleImageView imgProfile;
    private ImageView ballImg;
    private CardView btnStartTurn;
    private CalendarView calendarView;
    private ImageView signatureImg;
    private Bundle bundle;
    private CardView btnLoadDoc;
    private CardView btnGoToWork;


    private User user;
    private MyPresenceViewModel model;
    int SELECT_PICTURE = 200;
    int SELECT_FROM_CAMERA = 201;
    SharedPreferences sharedPreferences;

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
        this.model = new MyPresenceViewModel(getActivity().getApplication());
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
        if (container != null) {
            this.txtUsername = view.findViewById(R.id.txtUsername);
            this.imgProfile = view.findViewById(R.id.imgProfile);
            this.btnStartTurn = view.findViewById(R.id.btnStartTurn);
            this.btnLoadDoc = view.findViewById(R.id.btnLoadDoc);
            this.signatureImg = view.findViewById(R.id.signatureIcon);
            this.txtBio = view.findViewById(R.id.txtBio1);
            this.txtRole = view.findViewById(R.id.txtRole);
            this.ballImg = view.findViewById(R.id.imgBall);
            this.labelStartTurn = view.findViewById(R.id.labelStartYourTurn);
            this.btnGoToWork = view.findViewById(R.id.btnGoToWork);
        }

        if (getArguments() != null) {
            this.bundle = this.getArguments().getBundle("userInfo");

            String name = this.bundle.getString("name");
            String surname = this.bundle.getString("surname");
            String password = this.bundle.getString("password");
            String username = this.bundle.getString("username");
            String bio = this.model.getUserFromUsernameAndPassword(username, password).get(0).getBio();
            String role = this.bundle.getString("userRole");
            this.user = (User) this.bundle.get("user");

            this.model = new MyPresenceViewModel(getActivity().getApplication());
            Log.e("user", this.user.toString());

            this.txtUsername.setText(name + " " + surname);
            this.txtBio.setText(bio);
            this.txtRole.setText(role);
            try {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
                String mImageUri = preferences.getString("image", null);

                if (mImageUri != null) {

                    imgProfile.setImageURI(Uri.parse(mImageUri));
                } else {
                    imgProfile.setImageResource(R.drawable.user_icon);
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }



            UserAndStats userModel = model.getUserStats(this.user.getUsername(), this.user.getPassword()).get(0);
            System.out.println("actualstate: " + userModel.stats.get(userModel.stats.size() - 1).getStatus() + " " + this.user.getUsername() + " " + this.user.getPassword());

            switch (userModel.stats.get(userModel.stats.size() - 1).getStatus()) {
                case "active":
                    this.ballImg.setImageDrawable(getActivity().getDrawable(R.drawable.green_circle));
                    this.labelStartTurn.setText("End your turn");
                    break;
                case "over":
                    this.ballImg.setImageDrawable(getActivity().getDrawable(R.drawable.red_circle));
                    this.labelStartTurn.setText("Start your turn");
                    break;
                default:
                    break;

            }

            Log.e("USERS", model.getAllUsers().toString());
        }


        this.btnStartTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getBaseContext(), NFCActivity.class);
                intent.putExtra("username", bundle.getString("username"));
                intent.putExtra("password", bundle.getString("password"));
                startActivity(intent);
            }
        });

        this.imgProfile.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent;
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");

                } else {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");

                }
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 3);
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
                        switch (strName) {
                            case "Choose from gallery":

                                Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                startActivityForResult(intent,100);



                                break;
                            case "Take a picture":
                                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(cameraIntent, 100 );
                                break;
                        }
                    }
                });
                builderSingle.show();
            }
        });

        this.btnGoToWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=44.1485056,12.2333975&mode=d") );
                intent.setPackage("com.google.android.apps.maps");
              // if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
              //  }

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==3  && resultCode==RESULT_OK) {
            Uri uri=data.getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                getActivity().getContentResolver().takePersistableUriPermission (uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            // Saves image URI as string to Default Shared Preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("image", String.valueOf(uri));
            editor.apply();

            // Sets the ImageView with the Image URI
            imgProfile.setImageURI(uri);
            imgProfile.invalidate();
            this.imgProfile.setImageURI(uri);
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        MyPresenceViewModel model1 = new MyPresenceViewModel(getActivity().getApplication());
        UserAndStats userModel = model1.getUserStats(this.user.getUsername(), this.user.getPassword()).get(0);
        System.out.println("actualstate: " + userModel.stats.get(userModel.stats.size() - 1).getStatus() + " " + this.user.getUsername() + " " + this.user.getPassword());

        switch (userModel.stats.get(userModel.stats.size() - 1).getStatus()) {
            case "active":
                System.out.println("sono qui active");
                this.ballImg.setImageDrawable(getActivity().getDrawable(R.drawable.green_circle));
                this.signatureImg.setImageDrawable(getActivity().getDrawable(R.drawable.cross));
                this.labelStartTurn.setText("End your turn");
                break;
            case "over":
                System.out.println("sono qui over");
                if (checkTurnTime()) {
                    this.ballImg.setImageDrawable(getActivity().getDrawable(R.drawable.yellow_circle));
                    this.signatureImg.setImageDrawable(getActivity().getDrawable(R.drawable.signature_icon));
                    this.labelStartTurn.setText("Start your turn");
                } else {
                    this.ballImg.setImageDrawable(getActivity().getDrawable(R.drawable.red_circle));
                    this.signatureImg.setImageDrawable(getActivity().getDrawable(R.drawable.signature_icon));
                    this.labelStartTurn.setText("End your turn");
                }
                break;
            default:
                System.out.println("sono qui over");
                this.ballImg.setImageDrawable(getActivity().getDrawable(R.drawable.red_circle));
                this.labelStartTurn.setText("Start your turn");
                break;
        }
    }


    private boolean checkTurnTime() {
        try {

            String string1 = "09:00";
            Date time1 = new SimpleDateFormat("HH:mm").parse(string1);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            String string2 = "18:00";
            Date time2 = new SimpleDateFormat("HH:mm").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");  //it will give you the date in the formate that is given in the image
            String datetime = dateformat.format(c.getTime()); // it will give you the date
            System.out.println(datetime);


            Date d = new SimpleDateFormat("HH:mm").parse(datetime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);
            if (calendar3.getTime().after(calendar1.getTime()) && calendar3.getTime().before(calendar2.getTime())) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
