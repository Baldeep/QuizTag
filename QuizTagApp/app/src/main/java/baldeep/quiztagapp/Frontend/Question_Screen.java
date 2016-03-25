package baldeep.quiztagapp.Frontend;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Backend.NFC_Reader;
import baldeep.quiztagapp.Backend.QuizMaster;

import baldeep.quiztagapp.Listeners.QuestionScreenButtonListener;
import baldeep.quiztagapp.R;


public class Question_Screen extends AppCompatActivity implements Observer {

    private TextView questionField;
    // Text views are global to let them be updated
    private TextView hints;
    private TextView skips;
    private TextView coins;

    private TextView hint1;
    private TextView hint2;
    private TextView hint3;
    private TextView hint4;

    private QuizMaster qm;

    // nfcAdapter and intent filters need to be here
    private NfcAdapter nfcAdapter;
    // Intent needs to be declared programmatically otherwise
    // the tag will open this activity if declared in the manifest
    private PendingIntent pendingIntent;
    private IntentFilter intentFileters[];
    private Tag tag;

    // To create dialog pop ups
    DialogCreator dialogCreator = new DialogCreator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_screen_activity);

        // Get the QuizMaster
        Intent previousActivity = getIntent();
        qm = (QuizMaster) previousActivity.getSerializableExtra(Constants.QUIZMASTER);

        qm.attach(this); // Attach this to be an Observer

        Log.i("Question Screen", "Quiz Name: " + qm.getQuizName());
        Log.i("Question Screen", "Number of Questions: " + qm.getQuestionPool().getQuestionPoolSize());
        Log.i("Question Screen", "Question: " + qm.getCurrentQuestionNumber());



        //  Initialise the GUI objects
        hints = (TextView) findViewById(R.id.hints_count_text);
        skips = (TextView) findViewById(R.id.skips_count_text);
        coins = (TextView) findViewById(R.id.coins_count_text);

        ImageButton hintsButton = (ImageButton) findViewById(R.id.hints_btn);
        ImageButton skipButton = (ImageButton) findViewById(R.id.skips_btn);

        questionField = (TextView) findViewById(R.id.question_field);
        questionField.setMovementMethod(new ScrollingMovementMethod());
        hint1 = (TextView) findViewById(R.id.hint1);
        hint1.setMovementMethod(new ScrollingMovementMethod());
        hint2 = (TextView) findViewById(R.id.hint2);
        hint2.setMovementMethod(new ScrollingMovementMethod());
        hint3 = (TextView) findViewById(R.id.hint3);
        hint3.setMovementMethod(new ScrollingMovementMethod());
        hint4 = (TextView) findViewById(R.id.hint4);
        hint4.setMovementMethod(new ScrollingMovementMethod());

        // Set up the Hint button listener
        Bundle hintsBundle = new Bundle();
        hintsBundle.putSerializable(Constants.QUIZMASTER, qm);
        hintsBundle.putString(Constants.MESSAGE, Constants.HINTS);
        hintsButton.setOnClickListener(new QuestionScreenButtonListener(this, hintsBundle));

        // Set up the Skip button Listener
        Bundle skipBundle = new Bundle();
        skipBundle.putSerializable(Constants.QUIZMASTER, qm);
        skipBundle.putString(Constants.MESSAGE, Constants.SKIPS);
        skipButton.setOnClickListener(new QuestionScreenButtonListener(this, skipBundle));

        // Call the update method to fill the text fields
        update(qm, null);

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
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.question_screen_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle Action Bar buttons
        int id = item.getItemId();
        // Quit Buttom
        if(id == R.id.quit_button_dropdown){
            Intent goingBack = new Intent();
            goingBack.putExtra(Constants.POWERUPS, qm.getPowerUps());
            goingBack.putExtra(Constants.QUIZNAME, qm.getQuizName());
            goingBack.putExtra(Constants.CURRENTQUESTIONNO, qm.getCurrentQuestionNumber());
            setResult(RESULT_OK, goingBack);

            dialogCreator.quitConfirmationDialog(getFragmentManager(), new Bundle());

            return true;
        }
        // Buy Hints
        if(id == R.id.buy_hints_dropdown){
            Bundle bundle = new Bundle();
            bundle.putString(Constants.MESSAGE, Constants.HINTS);
            if(!qm.buyHints()){
                dialogCreator.failedBuyDialog(getFragmentManager(), bundle);
            }
        }
        // Buy Skips
        if(id == R.id.buy_skips_dropdown){

            Bundle bundle = new Bundle();
            bundle.putString(Constants.MESSAGE, Constants.SKIPS);
            if(!qm.buySkips()){
                dialogCreator.failedBuyDialog(getFragmentManager(), bundle);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void update(Observable observable, Object data) {

        // Set the title of the Activity
        if(qm.getCurrentQuestionNumber() == 0){
            setTitle(qm.getQuizName());
        } else if(qm.getCurrentQuestionNumber() < 0){
            Bundle endBundle = new Bundle();
            endBundle.putSerializable(Constants.POWERUPS, qm.getPowerUps());
            qm.resetQuiz();
            dialogCreator.quizFinishedDialog(getFragmentManager(), endBundle);
        } else {
            setTitle(R.string.question_prenumber_text + " " + qm.getCurrentQuestionNumber());
        }

        // Set the Quesion Field text
        String question = R.string.question_prenumber_text + " "
                + qm.getCurrentQuestionNumber() + ": " +
                qm.getQuestionString();
        questionField.setText(question);

        // Set the number of power ups
        hints.setText(qm.getPowerUps().getHintsAsString());
        skips.setText(qm.getPowerUps().getSkipsAsString());
        coins.setText(qm.getPowerUps().getPointsAsString());

        // Display the hints
        displayHints();
    }

    /**
     * This method helps set up the hints, as less than 4 hints can be available, they have
     * to be added one by one, and if no hints are to be revealed, the textfields are hidden
     */
    private void displayHints() {
        List<String> hints = qm.getHints();

        if(qm.hintsAvailable()) {
            // Set hints to fields
            if (hints.size() >= 1) {
                hint1.setText(hints.get(0));
                hint1.setVisibility(View.VISIBLE);
            }
            if (hints.size() >= 2) {
                hint2.setText(hints.get(1));
                hint2.setVisibility(View.VISIBLE);
            }
            if (hints.size() >= 3) {
                hint3.setText(hints.get(2));
                hint3.setVisibility(View.VISIBLE);
            }
            if (hints.size() >= 4) {
                hint4.setText(hints.get(3));
                hint4.setVisibility(View.VISIBLE);
            }
        } else {
            // Display Hints to user
            hint1.setVisibility(View.INVISIBLE);
            hint2.setVisibility(View.INVISIBLE);
            hint3.setVisibility(View.INVISIBLE);
            hint4.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed(){
        Intent goingBack = new Intent();
        goingBack.putExtra(Constants.POWERUPS, qm.getPowerUps());
        goingBack.putExtra(Constants.QUIZNAME, qm.getQuizName());
        goingBack.putExtra(Constants.CURRENTQUESTIONNO, qm.getCurrentQuestionNumber());
        setResult(RESULT_OK, goingBack);

        super.onBackPressed();
    }

    /**
     * When a tag is read the onNewIntent method is called. Hence this method needs to read the tag
     * @param intent An intent for this Activity, This method is overriden to handle NFC intents
     */
    @Override
    protected void onNewIntent(Intent intent) {
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // Delay vibration until the tag is fully read
        v.cancel();
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            // get the Tag
            Toast.makeText(this, R.string.tag_found, Toast.LENGTH_SHORT).show();

            // Read the answer from the tag
            String answerFromTag = new NFC_Reader().readNameFromTag(this, tag);

            // now the app can vibrate
            Toast.makeText(this, answerFromTag, Toast.LENGTH_SHORT).show();
            long[] pattern = {0, 200, 100, 200};
            v.vibrate(pattern, -1);

            // Check answer
            if(!answerFromTag.equals("")){
                checkAnswer(answerFromTag);
            }
        }
        super.onNewIntent(intent);
    }

    /**
     * This method checks the answers
     * @param answer The answer to be checked for the current question
     */
    private void checkAnswer(String answer){
        // Toast.makeText(this, answer, Toast.LENGTH_SHORT).show();
        Bundle confirmBundle = new Bundle();
        confirmBundle.putSerializable(Constants.QUIZMASTER, qm);
        confirmBundle.putString(Constants.ANSWER, answer);
        dialogCreator.confirmAnswerDialog(getFragmentManager(), confirmBundle);

    }


    /**
     * It is important to pause the nfc adapter when it is paused (when it's busy reading a tag or
     * doing something else) or exceptions will be thrown crashing the application ...
     */
    @Override
    public void onPause(){
        super.onPause();
        if(nfcAdapter!=null) // for testing on emulator
        nfcAdapter.disableForegroundDispatch(this);

    }

    /**
     * ... and to restart it once it resumes from being paused
     */
    @Override
    public void onResume(){
        super.onResume();
        if(nfcAdapter!=null) // for testing on emulator
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFileters, null);

    }
}
