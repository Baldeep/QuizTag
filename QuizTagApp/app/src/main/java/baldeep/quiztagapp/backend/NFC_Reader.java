package baldeep.quiztagapp.backend;

import android.app.Activity;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * This class contains methods which help read the NFC tags. The tags contain two records,
 *
 * The first record contains the whole tag as a JSon file,
 * while the second contains just the name of the exhibit
 */
public class NFC_Reader {


    /**
     * This method reads the name field from the NFC tag. It also makes complete null checks and
     * will return an empty string if no answer is read properly. This method was made independent
     * from the NFC_Reader#readExhibitFromTag() in order to make the reading process a little faster
     * @param activity The activity which is calling the method, this is needed for Toasts
     * @param tag The tag which needs to be read
     * @return Returns the name from the second record in the tag, if it can't be read it will
     *         return an empty string.
     */
    public String readNameFromTag(Activity activity, Tag tag){
        String text = "";

        if(tag!=null){
            Ndef ndef = Ndef.get(tag);
            if(ndef!=null){
                try {
                    ndef.connect();
                    NdefMessage message = ndef.getNdefMessage();

                    if(message != null) {
                        NdefRecord[] record = message.getRecords();

                        if (record.length > 1) {
                            text = decodeTag(record[1]);
                        }
                    } else {
                        Toast.makeText(activity, "Didn't manage to read the tag, try again.", Toast.LENGTH_SHORT).show();
                    }

                    ndef.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FormatException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(activity, "The tag is in the wrong format, contact tech support", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Didn't manage to read the tag, try again.", Toast.LENGTH_SHORT).show();
        }

        return text;
    }

    public ExhibitTag readExhibitFromTag(Activity activity, Tag tag){
        ExhibitTag exhibit = new ExhibitTag();
        String text = "";

        if(tag!=null){
            Ndef ndef = Ndef.get(tag);
            if(ndef!=null){
                try {
                    ndef.connect();
                    NdefMessage message = ndef.getNdefMessage();

                    if(message != null) {
                        NdefRecord[] record = message.getRecords();

                        if (record.length > 1) {
                            text = decodeTag(record[1]);
                        }
                    } else {
                        Toast.makeText(activity, "Didn't manage to read the tag, try again.", Toast.LENGTH_SHORT).show();
                    }

                    ndef.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FormatException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(activity, "The tag is in the wrong format, contact tech support", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Didn't manage to read the tag, try again.", Toast.LENGTH_SHORT).show();
        }
        return exhibit;
    }

    private String decodeTag(NdefRecord record) {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

        byte[] payload = record.getPayload();

        // Get the Text Encoding
        String textEncoding = " ";
        if((payload[0] & 128) == 0){
            textEncoding = "UTF-8";
        } else {
            textEncoding = "UTF-16";
        }

        // Get the Language Code
        int languageCodeLength = payload[0] & 0063;

        String text = null;
        try {
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            text = "";
        }

        // Get the Text
        return text;
    }

}
