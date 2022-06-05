package com.app.mypresence.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.app.mypresence.R;
import com.app.mypresence.model.database.DateInfo;
import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.model.database.UserAndStats;
import com.app.mypresence.model.database.user.User;
import com.app.mypresence.model.utils.nfc.NdefMessageParser;
import com.app.mypresence.model.utils.nfc.NfcVerifier;
import com.app.mypresence.model.utils.nfc.ParsedNdefRecord;
import com.app.mypresence.presenter.LoginPresenter;
import com.app.mypresence.presenter.LoginPresenterInterface;
import com.app.mypresence.presenter.NFCActivityPresenter;
import com.app.mypresence.presenter.NFCPresenterInterface;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

public class NFCActivity extends AppCompatActivity {

    private NFCPresenterInterface presenter;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private Vibrator myVib;  //vibration feedback
    private String username;
    private String password;
    private  LottieAnimationView lottieAnimationView;
    private MyPresenceViewModel mpvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getCurrentTime();
        this.mpvm = new MyPresenceViewModel(getApplication());
        setContentView(R.layout.activity_nfcactivity);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            Toast.makeText(this, "This phone doesn't support NFC", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        this.lottieAnimationView = (LottieAnimationView) findViewById(R.id.animationView);

        this.username = getIntent().getExtras().getString("username");
        this.password = getIntent().getExtras().getString("password");

        this.myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        this.pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, this.getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_MUTABLE);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        resolveIntent(intent);
    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs = null;

            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            }
            if (msgs != null ) {
                displayMsgs(msgs);
            }
        }
    }

    private void displayMsgs(NdefMessage[] msgs) {
        String readerCode = "";
        this.lottieAnimationView.setAnimation(R.raw.read_nfc_done_animation);
        this.lottieAnimationView.setSpeed(3);
        this.lottieAnimationView.playAnimation();
        if (msgs == null || msgs.length == 0)
            return;
        StringBuilder builder = new StringBuilder();
        List<ParsedNdefRecord> records = NdefMessageParser.parse(msgs[0]);
        final int size = records.size();
        for (ParsedNdefRecord r : records) {
            Log.e("record", r.str());
            readerCode = r.str();
        }

        if (NfcVerifier.checkScan(getIntent().getExtras().getString("username"), readerCode)){
            Log.e("READER:", "OK");
            Runnable getUserStatusAndUpdateIt = () -> {

                Log.e("username", this.username);
                Log.e("password", this.password);

                UserAndStats user = this.mpvm.getUserStats(this.username, this.password).get(0);

                if(user.stats.get(user.stats.size()-1).getStatus().equals("over")){

                    DateInfo dateInfo = new DateInfo("active", new Date(), this.getCurrentTime(), "23:59");
                    dateInfo.userOwnerOfStat = user.user.getUserId();
                    this.mpvm.addStats(dateInfo);


                }else{

                    Log.e("statusPrima", user.stats.get(user.stats.size()-1).getStatus());
                    user.stats.get(user.stats.size() - 1).setStatus("over");
                    user.stats.get(user.stats.size() -1).setEndShiftTime(this.getCurrentTime());
                    this.mpvm.updateDateInfo(user.stats.get(user.stats.size() - 1));

                    Log.e("dateinfo", user.stats.get(user.stats.size()-1).getDate().toString());
                    Log.e("status", user.stats.get(user.stats.size()-1).getStatus());
                    Log.e("startshift", user.stats.get(user.stats.size() -1 ).getStartShiftTime());
                    Log.e("endshiftr", user.stats.get(user.stats.size() -1).getEndShiftTime());
                }

            };
            getUserStatusAndUpdateIt.run();


        }else{
            Toast.makeText(this, "NFC scanner is not authorized", Toast.LENGTH_SHORT).show();

        }
        this.myVib.vibrate(500);
        this.finish();

        //here must exit from activity after saving the arrival time into database.

    }

    private String getCurrentTime(){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");
        DateTime current = new DateTime();
        return current.toString().substring(11,16);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled())
                showWirelessSettings();
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    private void showWirelessSettings() {
        Toast.makeText(this, "You need to enable NFC", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }


}