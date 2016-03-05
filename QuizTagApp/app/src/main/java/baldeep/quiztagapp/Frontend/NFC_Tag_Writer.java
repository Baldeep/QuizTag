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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import baldeep.quiztagapp.R;

/**
 * Created by Baldeep on 28/02/2016.
 */
public class NFC_Tag_Writer extends AppCompatActivity {

    private IntentFilter[] intentFilters;
    private PendingIntent pendingIntent;
    private Tag nfcTag;

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nfc_writer);

        tv = (TextView) findViewById(R.id.nfc_text_view);

        /** Can copy this to Main_Menu once complete 1 -- **/
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            DialogFragment noNfc = new NFCInfoDialog();
            Bundle noNFCBundle = new Bundle();
            noNFCBundle.putString("title", "NFC Error");
            noNFCBundle.putString("message", "NFC not supported on this device");
            noNFCBundle.putString("type", "noNFC");
            noNfc.setArguments(noNFCBundle);
            noNfc.show(getFragmentManager(), "NFC OFF");
        }
        if(!nfcAdapter.isEnabled()){
            DialogFragment nfcOff = new NFCInfoDialog();
            Bundle NFCoffBundle = new Bundle();
            NFCoffBundle.putString("title", "NFC Error");
            NFCoffBundle.putString("message", "Please turn on NFC");
            NFCoffBundle.putString("type", "nfcOff");
            nfcOff.setArguments(NFCoffBundle);
            nfcOff.show(getFragmentManager(), "NFC OFF");
        }
        /** -- 1 **/

        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(this, getClass()).
                        addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter NdefIntent = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        intentFilters = new IntentFilter[]{NdefIntent};

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.back_button_menu, menu);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent){
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            nfcTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Toast.makeText(this, "Tag Detected" + nfcTag.toString(), Toast.LENGTH_LONG ).show();
            readTag(intent);
        }
    }

    private void readTag(Intent intent){
        Ndef ndef = Ndef.get(nfcTag);
        if(ndef != null){
            try {
                ndef.connect();
                NdefMessage message = ndef.getNdefMessage();
                NdefRecord[] record = message.getRecords();

                String text = "";
                for(int i = 0; i < record.length; i++){
                    System.out.println(record[i].toString());
                    text += record[i].toString();
                }

                tv.setText(text);


            } catch (IOException e) {
                Toast.makeText(this, "Unable to read NFC tag, try again.", Toast.LENGTH_SHORT).show();

            } catch (FormatException e) {
                e.printStackTrace();
            }
        }
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
