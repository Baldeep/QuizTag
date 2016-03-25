package baldeep.quiztagapp.Frontend;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import baldeep.quiztagapp.R;
import baldeep.quiztagapp.backend.ExhibitTag;
import baldeep.quiztagapp.backend.NFC_Reader;


public class Scan_Screen extends AppCompatActivity{

    private DialogCreator dialogCreator = new DialogCreator();
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
        description.setMovementMethod(new ScrollingMovementMethod());

        url = (TextView) findViewById(R.id.scan_screen_url_field);
        url.setClickable(true);
        url.setMovementMethod(LinkMovementMethod.getInstance());

        // Check NFC is enabled
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null || !nfcAdapter.isEnabled()){
            dialogCreator.nfcDisabledDialog(getFragmentManager(), new Bundle());
        }

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
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        v.cancel();
        if(nfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            //long startTime = System.currentTimeMillis();
            exhibitTag = new NFC_Reader().readExhibitFromTag(this, tag);
            /*long endTime = System.currentTimeMillis();
            long timeTaken = endTime-startTime;
            Log.d("ReadExhibitName", "Time taken" + timeTaken);*/
            long[] pattern = {0, 200, 100, 200};
            v.vibrate(pattern, -1);
            updateFields();
        }
        super.onNewIntent(intent);
    }

    /**
     * This method updates the text fields on this screen
     */
    private void updateFields(){

        if(exhibitTag != null){
            String nameString = getResources().getString(R.string.scan_screen_name_field) + exhibitTag.getName() + " " + exhibitTag.getYear();
            name.setText(nameString);


            description.setText(exhibitTag.getDescription());


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
