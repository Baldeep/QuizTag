package baldeep.quiztagapp.Frontend;

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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import baldeep.quiztagapp.Listeners.ShopButtonListener;
import baldeep.quiztagapp.backend.PowerUps;
import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.Listeners.QuestionScreenButtonListener;
import baldeep.quiztagapp.R;


public class Question_Screen extends AppCompatActivity implements Observer {

    private TextView questionField;
    //TextView hintsField;
    private TextView hints;
    private TextView skips;
    private TextView coins;

    private ImageButton hintsButton;
    private ImageButton skipButton;
    private Button hint1;
    private Button hint2;
    private Button hint3;
    private Button hint4;

    private PowerUps pu;
    private QuizMaster qm;

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter intentFileters[];
    Tag tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_screen_activity);

        // Set up QuizMaster
        Intent previousActivity = getIntent();
        //qm = (QuizMaster) previousActivity.getSerializableExtra("quizMaster");
        //pu = (PowerUps) previousActivity.getSerializableExtra("powerUps");
        //pu = new PowerUps(0, 100, 100);
        qm = (QuizMaster) previousActivity.getSerializableExtra("quizMaster");
        pu = qm.getPowerUps();

        qm.attach(this);

        questionField = (TextView) findViewById(R.id.question_field);
        //hintsField = (TextView) findViewById(R.id.hints_field);

        hints = (TextView) findViewById(R.id.hints_count_text);
        skips = (TextView) findViewById(R.id.skips_count_text);
        coins = (TextView) findViewById(R.id.coins_count_text);

        hintsButton = (ImageButton) findViewById(R.id.hints_btn);
        skipButton = (ImageButton) findViewById(R.id.skips_btn);

        hint1 = (Button) findViewById(R.id.hint1);
        hint2 = (Button) findViewById(R.id.hint2);
        hint3 = (Button) findViewById(R.id.hint3);
        hint4 = (Button) findViewById(R.id.hint4);

        Bundle hintsBundle = new Bundle();
        hintsBundle.putSerializable("quizMaster", qm);
        hintsBundle.putString("message", "hint");
        hintsButton.setOnClickListener(new QuestionScreenButtonListener(this, hintsBundle));

        Bundle skipBundle = new Bundle();
        skipBundle.putSerializable("quizMaster", qm);
        skipBundle.putString("message", "skip");
        skipButton.setOnClickListener(new QuestionScreenButtonListener(this, skipBundle));

        // Hints get their listeners attached in the display hints method

        update(qm, null);

        setNFCIntents();
        /*************************** This needs updating each time ********************************/
        // Set the title of the screen as the question number
        /*if(qm.getCurrentQuestionNumber() <= 0){
            setTitle(qm.getQuizName());
        } else {
            setTitle("Question " + qm.getCurrentQuestionNumber());
        }

        // use observer pattern for these here
        String question = "Question " + qm.getCurrentQuestionNumber() + ": " +
                qm.getQuestionString();
        questionField.setText(question);
        String hintString = qm.getHintCount() + "";
        hints.setText(hintString);
        String skipString = qm.getSkipCount() + "";
        skips.setText(skipString);
        String coinsString = qm.getPoints() + "";
        coins.setText(coinsString);

        displayHints();*/
        /******************************************************************************************/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.question_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.quit_button_dropdown){
            Intent goingBack = new Intent();
            goingBack.putExtra("powerUps", pu);
            setResult(RESULT_OK, goingBack);

            DialogFragment df = new QuitDialog();
            df.show(getFragmentManager(), "Quit Dialog");

            return true;
        }
        if(id == R.id.buy_hints_dropdown){
            /** copied from ShopButtonListener **/
            if(pu.getPoints() > pu.getHintsCost()){
                pu.setPoints(pu.getPoints() - pu.getHintsCost());
                pu.setHints(pu.getHints() + 1);
                update(pu, null);
            } else {
                DialogFragment noPoint = new InformationDialog();
                Bundle noPointBundle = new Bundle();
                noPointBundle.putString("title", "Not enough points");
                noPointBundle.putString("message", "You don't have enough points to buy more hints" +
                        "\nPlay the game to earn more points!");
                noPoint.setArguments(noPointBundle);
                noPoint.show(getFragmentManager(), "No Point");
            }
        }

        if(id == R.id.buy_skips_dropdown){
            /** copied from ShopButtonListener **/
            if(pu.getPoints() > pu.getSkipsCost()){
                pu.setPoints(pu.getPoints()-pu.getSkipsCost());
                pu.setSkips(pu.getSkips() + 1);
                update(pu, null);
            } else {
                DialogFragment noPoint = new InformationDialog();
                Bundle noPointBundle = new Bundle();
                noPointBundle.putString("title", "Not enough points");
                noPointBundle.putString("message", "You don't have enough points to buy more skips" +
                        "\nPlay the game to earn more points!");
                noPoint.setArguments(noPointBundle);
                noPoint.show(getFragmentManager(), "No Point");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayHints() {
        List<String> hints = qm.getHints();
        if (hints.size() >= 1) {
            hint1.setText(hints.get(0));
        }
        if (hints.size() >= 2) {
            hint2.setText(hints.get(1));
        }
        if (hints.size() >= 3) {
            hint3.setText(hints.get(2));
        }
        if (hints.size() >= 4) {
            hint4.setText(hints.get(3));
        }

        // Need to redefine listeners in order to change the text set
        Bundle answerBundle = new Bundle();
        answerBundle.putSerializable("quizMaster", qm);
        answerBundle.putString("message", "answer");
        answerBundle.putString("questionNo", qm.getCurrentQuestionNumber() + "");
        answerBundle.putString("button", "hint1");
        answerBundle.putString("text", String.valueOf(hint1.getText()));
        hint1.setOnClickListener(new QuestionScreenButtonListener(this, answerBundle));

        answerBundle = new Bundle();
        answerBundle.putSerializable("quizMaster", qm);
        answerBundle.putString("message", "answer");
        answerBundle.putString("questionNo", qm.getCurrentQuestionNumber() + "");
        answerBundle.putString("button", "hint2");
        answerBundle.putString("text", String.valueOf(hint2.getText()));
        hint2.setOnClickListener(new QuestionScreenButtonListener(this, answerBundle));

        answerBundle = new Bundle();
        answerBundle.putSerializable("quizMaster", qm);
        answerBundle.putString("message", "answer");
        answerBundle.putString("questionNo", qm.getCurrentQuestionNumber() + "");
        answerBundle.putString("button", "hint3");
        answerBundle.putString("text", String.valueOf(hint3.getText()));
        hint3.setOnClickListener(new QuestionScreenButtonListener(this, answerBundle));

        answerBundle = new Bundle();
        answerBundle.putSerializable("quizMaster", qm);
        answerBundle.putString("message", "answer");
        answerBundle.putString("questionNo", qm.getCurrentQuestionNumber() + "");
        answerBundle.putString("button", "hint4");
        answerBundle.putString("text", String.valueOf(hint4.getText()));
        hint4.setOnClickListener(new QuestionScreenButtonListener(this, answerBundle));

        if(qm.hintsAvailable()) {
            switch(hints.size()){
                case 4: hint4.setVisibility(View.VISIBLE);
                case 3: hint3.setVisibility(View.VISIBLE);
                case 2: hint2.setVisibility(View.VISIBLE);
                case 1: hint1.setVisibility(View.VISIBLE);
            }
        } else {
            hint1.setVisibility(View.INVISIBLE);
            hint2.setVisibility(View.INVISIBLE);
            hint3.setVisibility(View.INVISIBLE);
            hint4.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void update(Observable observable, Object data) {
        if(qm.getCurrentQuestionNumber() <= 0){
            setTitle(qm.getQuizName());
        } else {
            setTitle("Question " + qm.getCurrentQuestionNumber());
        }

        // use observer pattern for these here
        String question = "Question " + qm.getCurrentQuestionNumber() + ": " +
                qm.getQuestionString();
        questionField.setText(question);

        hints.setText(pu.getHintsAsString());
        skips.setText(pu.getSkipsAsString());
        coins.setText(pu.getPointsAsString());

        displayHints();
    }

    @Override
    public void onBackPressed(){
        Intent goingBack = new Intent();
        goingBack.putExtra("powerUps", pu);
        setResult(RESULT_OK, goingBack);

        super.onBackPressed();
    }

    private void setNFCIntents(){
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(!nfcAdapter.isEnabled()){
            DialogFragment df = new NFCInfoDialog();
            Bundle nfcBundle = new Bundle();
            nfcBundle.putString("title", "NFC Hardware");
            nfcBundle.putString("message", "Check NFC is enabled");
            nfcBundle.putString("type", "nfcOff");
            df.setArguments(nfcBundle);
            df.show(getFragmentManager(), "NFC Info");
        }
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        intentFileters = new IntentFilter[] { tagDetected };
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(nfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String tagContents = readTag(tag);
            Toast.makeText(this, tagContents, Toast.LENGTH_SHORT).show();
        }
        super.onNewIntent(intent);
    }

    private String readTag(Tag tag){
        String text = "";

        if(tag != null) {
            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                Toast.makeText(this, "Tag not Ndef formatted", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    ndef.connect();
                    NdefMessage message = ndef.getNdefMessage();
                    NdefRecord[] records = message.getRecords();

                    for (NdefRecord nr : records) {
                        if (nr.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(nr.getType(), NdefRecord.RTD_TEXT)) {
                            text = decodeTag(nr);
                        }
                    }
                    ndef.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FormatException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(this, "Error Reading Tag. Try Again.", Toast.LENGTH_SHORT).show();
        }
        return text;
    }
    private String decodeTag(NdefRecord record) {
        /*See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         */

        byte[] payload = record.getPayload();

        String textEncoding = "";
        if((payload[0] & 128) == 0){
            textEncoding = "UTF-8";
        } else {
            textEncoding = "UTF-16";
        }

        int languageCodeLength = payload[0] & 0063;

        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        // e.g. "en"
        String text = "";
        try {
            text = new String(payload, languageCodeLength + 1, payload.length -
                    languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("Decode Tag", "Unsupported Encoding");
        }

        return text;
    }
}
