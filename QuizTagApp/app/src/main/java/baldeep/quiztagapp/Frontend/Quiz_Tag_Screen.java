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
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import baldeep.quiztagapp.Listeners.DialogCreator;
import baldeep.quiztagapp.R;
import baldeep.quiztagapp.backend.NFC_Reader;
import baldeep.quiztagapp.backend.QuestionPool;

public class Quiz_Tag_Screen extends AppCompatActivity{

    TextView nameField;
    TextView questionNoField;
    TextView typeField;
    TextView typeExplaination;

    DialogCreator dialogCreator;

    String quizAsString;
    QuestionPool questionPool;

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
        setContentView(R.layout.quiz_tag_activity);

        dialogCreator = new DialogCreator();

        nameField = (TextView) findViewById(R.id.quizTag_name_field);
        questionNoField = (TextView) findViewById(R.id.quizTag_QuestionNo_field);
        typeField = (TextView) findViewById(R.id.quizTag_quizType_field);
        typeExplaination = (TextView) findViewById(R.id.quizTag_quiztype_Explaination);
        typeExplaination.setMovementMethod(new ScrollingMovementMethod());

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

            questionPool = new NFC_Reader().readQuestionPoolFromTag(this, tag);

            long[] pattern = {0, 200, 100, 200};
            v.vibrate(pattern, -1);
            updateFields();
        }
        super.onNewIntent(intent);
    }

    private void updateFields(){

        if(questionPool != null){
            nameField.setText(questionPool.getQuizName());
            questionNoField.setText(questionPool.getQuestionPoolSize());
            if(questionPool.isRandom()){
                typeField.setText(R.string.quiz_type_random);
                typeExplaination.setText(R.string.quiz_type_random_explaination);
            } else {
                typeField.setText(R.string.quiz_type_story);
                typeExplaination.setText(R.string.quiz_type_story_explaination);
            }
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
            Intent goingBack = new Intent();
            goingBack.putExtra("questionPool", questionPool);
            setResult(RESULT_OK, goingBack);

            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent goingBack = new Intent();
        goingBack.putExtra("questionPool", questionPool);
        setResult(RESULT_OK, goingBack);
        super.onBackPressed();
    }
}
