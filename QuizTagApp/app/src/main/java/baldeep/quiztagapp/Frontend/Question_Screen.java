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

import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import baldeep.quiztagapp.Listeners.ShopButtonListener;
import baldeep.quiztagapp.backend.PowerUps;
import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.backend.ExhibitTag;

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

    @Override
    protected void onNewIntent(Intent intent) {
        if(nfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Log.d("NFC INTENT", "nfc intent discovered");

            String answerFromTag = readAnswerFromTag(tag);
            /*Gson gson = new Gson();
            ExhibitTag exhibit = new ExhibitTag();

            exhibit = gson.fromJson(tagContents, ExhibitTag.class);

            System.out.println("Name: " + exhibit.getName() +
                    "Description: " + exhibit.getDescription() +
                    "url: " + exhibit.getUrl());*/
            if(!answerFromTag.equals("")){
                checkAnswer(answerFromTag);
            } else {
                Toast.makeText(this, "Answer wasn't read properly, try again", Toast.LENGTH_SHORT).show();
            }
        }
        super.onNewIntent(intent);
    }
    
    private void checkAnswer(String answer){
        
        Toast.makeText(this, answer, Toast.LENGTH_SHORT).show();
        
        Bundle answerBundle = new Bundle();
        answerBundle.putSerializable("quizMaster", qm);
        answerBundle.putString("message", "answer");
        answerBundle.putString("questionNo", qm.getCurrentQuestionNumber() + "");
        answerBundle.putString("button", "hint4");
        answerBundle.putString("text", answer);
        new QuestionScreenButtonListener(this, answerBundle).onClick(hint4);
    }

    private String readAnswerFromTag(Tag tag){
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
                        Toast.makeText(this, "Didn't manage to read the tag, try again.", Toast.LENGTH_SHORT).show();
                    }
                    
                    ndef.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (FormatException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "The tag is in the wrong format, contact tech support", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Didn't manage to read the tag, try again.", Toast.LENGTH_SHORT).show();
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
}
