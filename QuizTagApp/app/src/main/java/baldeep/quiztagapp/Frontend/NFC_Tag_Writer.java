package baldeep.quiztagapp.Frontend;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import baldeep.quiztagapp.R;

/**
 * Created by Baldeep on 28/02/2016.
 */
public class NFC_Tag_Writer extends AppCompatActivity {

    private TextView textView;
    private EditText editText;

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    boolean writeMode;
    IntentFilter intentFileters[];
    Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_writer);

        textView = (TextView) findViewById(R.id.nfc_text_view);
        editText = (EditText) findViewById(R.id.nfc_text);

        Button read_btn = (Button) findViewById(R.id.nfc_read_button);
        Button write_btn = (Button) findViewById(R.id.nfc_write_button);

        // Check the adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null || !nfcAdapter.isEnabled()){
            DialogFragment df = new NFCInfoDialog();
            Bundle nfcBundle = new Bundle();
            nfcBundle.putString("title", "NFC Hardware");
            nfcBundle.putString("message", "Check NFC is enabled");
            nfcBundle.putString("type", "nfcOff");
            df.setArguments(nfcBundle);
            df.show(getFragmentManager(), "NFC Info");
        }
        // Set an intent filter
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        intentFileters = new IntentFilter[] { tagDetected };
        // Just put this here for now for the write button
        // based on tutorial:
        // http://www.framentos.com/en/android-tutorial/2012/07/31/write-hello-world-into-a-nfc-tag-with-a/
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(tag != null){
                        // Take the tag no matter the format
                        writeTag(String.valueOf(editText.getText()), tag);
                    } else {
                        Toast.makeText(NFC_Tag_Writer.this, "No Tag Detected", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag != null) {
                    String text = readTag(tag);
                    textView.setText(text);
                    Toast.makeText(NFC_Tag_Writer.this, "Reading", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * As this deals with NFC tags, the OnNewIntent method needs to be overridden in order to be
     * able to get the new tag.
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        if(nfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
        super.onNewIntent(intent);
    }

    /**
     * Order of Ndef goes:
     * Tag --contains--> NdefMessages --contain--> NdefRecords
     * and and NdefRecord is of the format:
     * [Type Name Format], [Record Type Definition], [Payload Length], [Payload]
     *
     * We want to write text, therefore we use TNF_WELL_KNOWN, and RTD_TEXT
     * @param text - The text to be written
     * @param tag - The tag onto which writing needs to be done
     */
    private void writeTag(String text, Tag tag){
        NdefRecord record = createRecord(text);
        NdefRecord[] records = new NdefRecord[]{record};
        NdefMessage message = new NdefMessage(records);
        if(tag != null) {
            // Check the tag is in Ndef format
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                try {
                    ndef.connect(); // establish connection
                    ndef.writeNdefMessage(message);
                    Toast.makeText(NFC_Tag_Writer.this, "Ndef Write successful", Toast.LENGTH_SHORT).show();
                    ndef.close(); //close connection
                } catch (IOException e) {
                    Toast.makeText(NFC_Tag_Writer.this, "Failed to write, tag may have moved", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (FormatException e) {
                    Toast.makeText(NFC_Tag_Writer.this, "Tag is of invalid format", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else {
                // If it's not in Ndef Format, format it if possible
                NdefFormatable formattable = NdefFormatable.get(tag);
                if (formattable != null) {
                    try {
                        formattable.connect();
                        formattable.format(message);
                        Toast.makeText(NFC_Tag_Writer.this, "Ndef Format and write successful", Toast.LENGTH_SHORT).show();
                        formattable.close();
                    } catch (IOException e) {
                        Toast.makeText(NFC_Tag_Writer.this, "Failed to write, tag may have moved", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (FormatException e) {
                        Toast.makeText(NFC_Tag_Writer.this, "Tag is of invalid format", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(NFC_Tag_Writer.this, "Invalid Tag format", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private NdefRecord createRecord(String text){
        String lang       = "en";
        byte[] textBytes  = text.getBytes();
        int    textLength = textBytes.length;

        byte[] langBytes  = new byte[0];
        try {
            langBytes = lang.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int    langLength = langBytes.length;

        byte[] payload    = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1,              langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        NdefRecord recordNFC = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,  NdefRecord.RTD_TEXT,  new byte[0], payload);

        return recordNFC;
    }

    private String readTag(Tag tag){
        String text = "";

        Ndef ndef = Ndef.get(tag);
        if(ndef == null){
            Toast.makeText(NFC_Tag_Writer.this, "Tag not Ndef formatted", Toast.LENGTH_SHORT).show();
        } else {
            try {
                ndef.connect();
                NdefMessage message = ndef.getNdefMessage();
                NdefRecord[] records = message.getRecords();

                for(NdefRecord nr : records){
                    if (nr.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(nr.getType(), NdefRecord.RTD_TEXT)) {
                        try {
                            text = readText(nr);
                        } catch (UnsupportedEncodingException e) {
                            Log.e("TAG", "Unsupported Encoding", e);
                        }
                    }
                }
                ndef.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            }
        }
        return text;
    }
    private String readText(NdefRecord record) throws UnsupportedEncodingException {
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

        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        // e.g. "en"

        // Get the Text
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.back_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.toolbar_back_button){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause(){
        super.onPause();
        WriteModeOff();
    }

    @Override
    public void onResume(){
        super.onResume();
        WriteModeOn();
    }

    private void WriteModeOn(){
        writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFileters, null);
    }

    private void WriteModeOff(){
        writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }
}
