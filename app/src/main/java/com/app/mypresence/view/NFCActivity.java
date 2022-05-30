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
import com.app.mypresence.model.utils.nfc.NdefMessageParser;
import com.app.mypresence.model.utils.nfc.NfcVerifier;
import com.app.mypresence.model.utils.nfc.ParsedNdefRecord;
import com.app.mypresence.presenter.LoginPresenter;
import com.app.mypresence.presenter.LoginPresenterInterface;
import com.app.mypresence.presenter.NFCActivityPresenter;
import com.app.mypresence.presenter.NFCPresenterInterface;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class NFCActivity extends AppCompatActivity {

    private NFCPresenterInterface presenter;

    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private Vibrator myVib;
    private String username;
    private  LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcactivity);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (nfcAdapter == null) {
            Toast.makeText(this, "This phone doesn't support NFC", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        this.lottieAnimationView = (LottieAnimationView) findViewById(R.id.animationView);

        this.username =getIntent().getExtras().getString("username");


        this.myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, this.getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
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
        }else{

            Toast.makeText(this, "NFC scanner is not authorized", Toast.LENGTH_SHORT).show();

        }

        this.myVib.vibrate(100);
        finish();
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