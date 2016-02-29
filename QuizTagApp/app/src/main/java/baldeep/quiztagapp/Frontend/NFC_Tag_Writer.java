package baldeep.quiztagapp.Frontend;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import baldeep.quiztagapp.R;

/**
 * Created by Baldeep on 28/02/2016.
 */
public class NFC_Tag_Writer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nfc_writer);

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            DialogFragment noNfc = new DialogFragment();
            Bundle noNFCBundle = new Bundle();
            noNFCBundle.putString("title", "NFC Error");
            noNFCBundle.putString("message", "NFC not supported on this device");
            noNfc.setArguments(noNFCBundle);
            noNfc.show(getFragmentManager(), "NFC OFF");
        }
        if(!nfcAdapter.isEnabled()){
            DialogFragment nfcOff = new DialogFragment();
            Bundle noNFCBundle = new Bundle();
            noNFCBundle.putString("title", "NFC Error");
            noNFCBundle.putString("message", "Please turn on NFC");
            nfcOff.setArguments(noNFCBundle);
            nfcOff.show(getFragmentManager(), "NFC OFF");
        }



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
