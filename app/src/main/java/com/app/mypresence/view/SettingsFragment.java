package com.app.mypresence.view;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.app.mypresence.R;
import com.app.mypresence.model.database.MyPresenceViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

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
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private MyPresenceViewModel mpvm;
    // TODO: Rename and change types of parameters
    private String username;
    private String password;
    private String name;
    private String surname;

    private SharedPreferences sharedPreferences;
    private CircleImageView propic;
    ImageView idCardImage;
    ImageView cfCardImage;

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



    public static SettingsFragment newInstance(String param1, String param2, String param3, String param4) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
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
            name = getArguments().getString(ARG_PARAM3);
            surname = getArguments().getString(ARG_PARAM4);
            this.sharedPreferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        propic = view.findViewById(R.id.profileImg);
        Switch switch1 = (Switch) view.findViewById(R.id.switch1);
        cfCardImage = (ImageView) view.findViewById(R.id.cfCardImage);
        idCardImage = (ImageView) view.findViewById(R.id.idCardImage);

        try {
            SharedPreferences preferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
            boolean loginAuto = preferences.getBoolean("loggedIn", false);
            switch1.setChecked(loginAuto);
        }catch (Exception ex){
            ex.printStackTrace();

        }
        
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
            Set<String> mImageUriSet = preferences.getStringSet(this.name, null);

            List<String> uris = new ArrayList<>();
            uris.addAll(mImageUriSet);

            if (uris.get(0) != null) {
                propic.setImageURI(Uri.parse(uris.get(0)));
            } else {
                propic.setImageResource(R.drawable.user_icon);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        //ID CARD
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
            String mImageUri = preferences.getString("idImage", null);

            if (mImageUri != null) {

                idCardImage.setImageURI(Uri.parse(mImageUri));

            } else {
                idCardImage.setImageResource(R.drawable.icon_id_card);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        //CF
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
            String mImageUri = preferences.getString("cfImage", null);

            if (mImageUri != null) {

                cfCardImage.setImageURI(Uri.parse(mImageUri));
            } else {
                cfCardImage.setImageResource(R.drawable.icon_id_card);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }


        Button saveBtn = view.findViewById(R.id.saveBtn);
        Button changeBtn = view.findViewById(R.id.btnChange);
        Button logOuuBtn = view.findViewById(R.id.btnLogOut);

        EditText bio = view.findViewById(R.id.bio_input);
        String actualBio = this.mpvm.getUserFromUsernameAndPassword(this.username, this.password).get(0).getBio();
        bio.setText(actualBio);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String information = bio.getText().toString();
                System.out.println(information + " =? " + mpvm.getUserFromUsernameAndPassword(username, password).get(0).getBio());
                mpvm.updateUserBio(username, password, information);
                Toast.makeText(getContext(), "Bio Updated!", Toast.LENGTH_SHORT).show();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(bio.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });

        idCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 4);

            }
        });

        cfCardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 5);

            }
        });

        logOuuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getActivity().getSharedPreferences("loginPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("logged in", false);
                editor.apply();

                Intent intentToLogin = new Intent(getActivity().getBaseContext(), LoginActivity.class);
                startActivity(intentToLogin);
                Toast.makeText(getContext(), "Logged out succesfully!", Toast.LENGTH_SHORT).show();

                getActivity().finish();

            }
        });
        return view;



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //PROFILE PHOTO
        if(requestCode==3  && resultCode==RESULT_OK) {
            Uri uri=data.getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                getActivity().getContentResolver().takePersistableUriPermission (uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            // Saves image URI as string to Default Shared Preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("image", String.valueOf(uri));
            editor.putStringSet(this.name, Set.of(String.valueOf(uri)));
            editor.apply();
            propic.invalidate();
            propic.setImageURI(uri);
        }
/*
        //ID CARD
        if(requestCode==4  && resultCode==RESULT_OK) {
            Uri uri=data.getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                getActivity().getContentResolver().takePersistableUriPermission (uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            // Saves image URI as string to Default Shared Preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("idImage", String.valueOf(uri));
            editor.apply();

            idCardImage.invalidate();
            idCardImage.setImageURI(uri);
        }

        //CF

        if(requestCode==5  && resultCode==RESULT_OK) {
            Uri uri=data.getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                getActivity().getContentResolver().takePersistableUriPermission (uri, Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            // Saves image URI as string to Default Shared Preferences
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putStringSet(
            editor.putString("cfImage", String.valueOf(uri));
            editor.apply();

            cfCardImage.setImageURI(uri);


            cfCardImage.invalidate();
            cfCardImage.setImageURI(uri);
        }*/


    }
}