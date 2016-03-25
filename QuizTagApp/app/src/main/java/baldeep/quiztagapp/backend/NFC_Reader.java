package baldeep.quiztagapp.Backend;

import android.app.Activity;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

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
        ExhibitTag et = readExhibitFromTag(activity, tag);
        return et.getName();
    }

    /**
     * This method reads the first record of the tag and creates an exhibit object from it, it makes
     * the usual null checks for NFC reading and additionally checks
     * @param activity The activity calling this method
     * @param tag The tag to be read
     * @return An ExhibitTag object containing name, description and web url of the object scanned, returns null if the object wasn't read properly
     */
    public ExhibitTag readExhibitFromTag(Activity activity, Tag tag){
        ExhibitTag exhibit = new ExhibitTag();

        String text = readTag(activity, tag);

        if(!text.equals("") && !text.equals(null)){
            try {
                Gson gson = new Gson();
                exhibit = gson.fromJson(text, ExhibitTag.class);
            } catch (JsonParseException e){
                Toast.makeText(activity, "Json format error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        if(exhibit.getName() != null && exhibit.getDescription() != null && exhibit.getUrl() != null)
            return exhibit;
        return null;
    }

    public QuestionPool readQuestionPoolFromTag(Activity activity, Tag tag){
        QuestionPool qp = null;

        String text = readTag(activity, tag);

        if(!text.equals("") && !text.equals(null)){
            try {
                Gson gson = new Gson();
                qp = gson.fromJson(text, QuestionPool.class);
            } catch (JsonParseException e){
                Toast.makeText(activity, "Tag in wrong format, contact tech support", Toast.LENGTH_SHORT).show();
            }
        }

        for(Question q : qp.getQuestionPool())
            q.resetHints();

        return qp;
    }

    /**
     * This method just read all the contents of the Tag and returns a String
     * @param activity
     * @param tag
     * @return
     */
    public String readTag(Activity activity, Tag tag) {
        String text = "";

        if (tag != null) {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                try {
                    ndef.connect();
                    NdefMessage message = ndef.getNdefMessage();
                    if (message != null) {
                        NdefRecord[] record = message.getRecords();
                        for(NdefRecord r : record)
                            text = decodeTag(r);
                    } else {
                        Toast.makeText(activity, "Didn't manage to read the tag, try again.", Toast.LENGTH_SHORT).show();
                    }
                    ndef.close();
                } catch (IOException | FormatException e) {
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
        String textEncoding;
        if((payload[0] & 128) == 0){
            textEncoding = "UTF-8";
        } else {
            textEncoding = "UTF-16";
        }

        // Get the Language Code
        int languageCodeLength = payload[0] & 0063;

        String text;
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
