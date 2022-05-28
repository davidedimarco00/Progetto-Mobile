package com.app.mypresence.model.utils.nfc;

import com.app.mypresence.model.database.AppDatabase;
import com.app.mypresence.model.database.MyPresenceViewModel;
import com.app.mypresence.model.utils.SHA1;

public class NfcVerifier {
    public static boolean checkScan(final String username, final String codeFromScanner) {
        return SHA1.SHA1(AppDatabase.getMyPresNFCcode() + SHA1.SHA1(username)) == SHA1.SHA1(codeFromScanner + SHA1.SHA1(username));
    }
}
