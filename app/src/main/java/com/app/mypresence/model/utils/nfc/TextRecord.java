package com.app.mypresence.model.utils.nfc;

import android.nfc.NdefRecord;

import java.io.UnsupportedEncodingException;


/**
 * An NFC Text Record
 */
public class TextRecord implements ParsedNdefRecord {

    /** ISO/IANA language code */
    private final String mLanguageCode;

    private final String mText;

    public TextRecord(String languageCode, String text) {
        mLanguageCode = languageCode;
        mText = text;
    }

    public String str() {
        return mText;
    }

    public String getText() {
        return mText;
    }

    /**
     * Returns the ISO/IANA language code associated with this text element.
     */
    public String getLanguageCode() {
        return mLanguageCode;
    }

    // TODO: deal with text fields which span multiple NdefRecords
    public static TextRecord parse(NdefRecord record) {

        try {
            byte[] payload = record.getPayload();
            String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
            int languageCodeLength = payload[0] & 0077;
            String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            String text = new String(payload, languageCodeLength + 1,
                            payload.length - languageCodeLength - 1, "UTF-8");

            return new TextRecord(languageCode, text);
        } catch (UnsupportedEncodingException e) {
            // should never happen unless we get a malformed tag.
            throw new IllegalArgumentException(e);
        }
    }

    public static boolean isText(NdefRecord record) {
        try {
            parse(record);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}