package baldeep.quiztagapp.Frontend;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import baldeep.quiztagapp.Listeners.QuizTagDialogCreator;
import baldeep.quiztagapp.R;
import baldeep.quiztagapp.backend.ExhibitTag;
import baldeep.quiztagapp.backend.NFC_Reader;


public class Scan_Screen extends AppCompatActivity{

    private QuizTagDialogCreator dialogCreator = new QuizTagDialogCreator();
    private ExhibitTag exhibitTag;

    private TextView name;
    private TextView description;
    private TextView url;

    // nfcAdapter and intent filters need to be here
     private NfcAdapter nfcAdapter;
    // Intent needs to be declared programmatically otherwise
    // the tag will open this activity if declared in the manifest
    private PendingIntent pendingIntent;
    private IntentFilter intentFileters[];
    private Tag tag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_screen_activity);

        name = (TextView) findViewById(R.id.scan_screen_name_field);
        description = (TextView) findViewById(R.id.scan_screen_description_field);
        url = (TextView) findViewById(R.id.scan_screen_url_field);


        // Check NFC is enabled
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null || !nfcAdapter.isEnabled()){
            dialogCreator.nfcDisabledDialog(getFragmentManager(), new Bundle());
        }

        ExhibitTag exhibitTag = new NFC_Reader().readExhibitFromTag(this, tag);

        // Set the intent filter
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        intentFileters = new IntentFilter[] { tagDetected };
    }

    @Override
    public void onPause(){
        super.onPause();
        if(nfcAdapter!=null) // for testing on emulator
            nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(nfcAdapter!=null) // for testing on emulator
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFileters, null);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        if(nfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Toast.makeText(this, "Tag found", Toast.LENGTH_SHORT).show();
            exhibitTag = new NFC_Reader().readExhibitFromTag(this, tag);
            updateFields();
        }
        super.onNewIntent(intent);
    }

    private void updateFields(){

        if(exhibitTag != null){
            String nameString = getResources().getString(R.string.scan_screen_name_field) + exhibitTag.getName();
            name.setText(nameString);

            description.setText(exhibitTag.getDescription());

            url.setMovementMethod((LinkMovementMethod.getInstance()));
            String urlText = "<a href='" + exhibitTag.getUrl() + "'>" + getResources().getString(R.string.scan_screen_url_text) + "</a>";
            url.setText(Html.fromHtml(urlText));
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
