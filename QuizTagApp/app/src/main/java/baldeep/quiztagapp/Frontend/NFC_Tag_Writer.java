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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import baldeep.quiztagapp.R;

/**
 * Created by Baldeep on 28/02/2016.
 */
public class NFC_Tag_Writer extends AppCompatActivity {

    private TextView textView;
    private EditText editText;


    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
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
        IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        intentFileters = new IntentFilter[]{intentFilter};

        // Just put this here for now for the write button
        // based on tutorial:
        // http://www.framentos.com/en/android-tutorial/2012/07/31/write-hello-world-into-a-nfc-tag-with-a/
        write_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(tag != null){
                        // Take the tag no matter the format
                        writeTag(editText.getText().toString(), tag);
                    } else {
                        Toast.makeText(NFC_Tag_Writer.this, "No Tag Detected", Toast.LENGTH_SHORT).show();
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

        // Check the tag is in Ndef format
        Ndef ndef = Ndef.get(tag);
        if(ndef != null){
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
            if(formattable != null){
                try {
                    formattable.connect();
                    formattable.format(message);
                    Toast.makeText(NFC_Tag_Writer.this, "Ndef Format and write successful", Toast.LENGTH_SHORT).show();
                    formattable.close();
                }  catch (IOException e) {
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
    private NdefRecord createRecord(String text){
        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], text.getBytes());
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
}
