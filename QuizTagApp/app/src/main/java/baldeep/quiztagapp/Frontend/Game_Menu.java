package baldeep.quiztagapp.Frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import baldeep.quiztagapp.backend.FileHandler;
import baldeep.quiztagapp.backend.GameSaver;
import baldeep.quiztagapp.backend.PowerUps;
import baldeep.quiztagapp.backend.QuestionPool;
import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Exceptions.NullObjectException;
import baldeep.quiztagapp.Listeners.GameMenuButtonListener;
import baldeep.quiztagapp.R;

/**
 * This class implements the game menu of the app, it displays the hints, skips and points to the
 * user and buttons to the shop, quiz download and game activities.
 */
public class Game_Menu extends AppCompatActivity {
    private TextView hints;
    private TextView skips;
    private TextView coins;

    private Button start_button;
    private Button quiz_tag_button;
    private Button shop_button;

    private QuizMaster qm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_menu_activity);

        loadGame();
/*        // Load the previous game data
        Bundle saveGameData = new GameSaver().loadGame(this);

        try {
            PowerUps powerUps = (PowerUps) saveGameData.getSerializable(Constants.POWERUPS);
            QuestionPool questionPool = new FileHandler().readQuestionPoolfromAssets(this);
            qm = new QuizMaster(questionPool, powerUps);

            // Start the QuizMaster to either continue the saved quiz or start anew
            String quizName = saveGameData.getString(Constants.QUIZNAME);
            // If it's the same quiz name then load up the data for it
            if(quizName != null && quizName.equals(qm.getQuestionPool().getQuizName())){
                int currentQuestion = saveGameData.getInt(Constants.CURRENTQUESTIONNO);
                Log.d("Load Quiz", "Question No: " + currentQuestion);
                // if the number saved was less than 1, then the quiz had ended on a previous game,
                if(currentQuestion <= 0){
                    currentQuestion = 1; // restart quiz
                }
                qm.goToQuestion(currentQuestion);
            } else {
                qm.setNextQuestion();
            }

        } catch(NullObjectException e){
            e.printStackTrace();
            Bundle errorBundle = new Bundle();
            errorBundle.putString(Constants.TITLE, getResources().getString(R.string.null_object_error_title));
            errorBundle.putString(Constants.MESSAGE, getResources().getString(R.string.null_object_error));
            new DialogCreator().errorDialog(getFragmentManager(), errorBundle);
        }*/


        // Initialise GUI
        start_button = (Button) findViewById(R.id.start_button);
        quiz_tag_button = (Button) findViewById(R.id.quiz_tag_button);
        shop_button = (Button) findViewById(R.id.shop_button);

        hints = (TextView) findViewById(R.id.hints_count_text);
        skips = (TextView) findViewById(R.id.skips_count_text);
        coins = (TextView) findViewById(R.id.coins_count_text);

        update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Load up Action Bar Menu
        getMenuInflater().inflate(R.menu.back_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle Action Bar buttons
        int id = item.getItemId();
        if(id == R.id.toolbar_back_button){
            Intent goingBack = new Intent();
            goingBack.putExtra(Constants.POWERUPS, qm.getPowerUps());
            setResult(RESULT_OK, goingBack);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Request 0 was sent to Question Screen
        if(requestCode == 0){
            PowerUps pu = (PowerUps) data.getSerializableExtra(Constants.POWERUPS);
            if(pu != null){
                qm.setPowerUps(pu);
            }
            Bundle saveGame = new Bundle();
            saveGame.putSerializable(Constants.POWERUPS, qm.getPowerUps());
            saveGame.putString(Constants.QUIZNAME, data.getStringExtra(Constants.QUIZNAME));
            saveGame.putInt(Constants.CURRENTQUESTIONNO,
                    data.getIntExtra(Constants.CURRENTQUESTIONNO, 1));
            new GameSaver().saveQuiz(this, saveGame);
            loadGame(); // reload game with new values
            update();
        }
        // Request 1 was sent to the Points Shop
        else if (requestCode == 1) {
            PowerUps pu = (PowerUps) data.getSerializableExtra(Constants.POWERUPS);
            if(pu != null){
                qm.setPowerUps(pu);
            }
            Bundle saveGame = new Bundle();
            saveGame.putSerializable(Constants.POWERUPS, qm.getPowerUps());
            new GameSaver().savePowerUps(this, saveGame);
            update();
        }
        // Request 2 was sent to the QuizTag screen
        else if(requestCode == 2){
            QuestionPool qp = (QuestionPool) data.getSerializableExtra(Constants.QUESTIONPOOL);
            if(qp!= null) {
                qm.setNewQuiz(qp);
            }
            loadGame(); // load the new quiz in
            update();
        }
    }

    /**
     *  This class could not be made an observer as the powerups need to be passed down to the next
     *  activities which would require this class and the inherited AppCompatActivity class to be
     *  Serialisable. Therefore this class needs to manually be updated on activity result.
     */
    private void update() {
        hints.setText(qm.getPowerUps().getHintsAsString());
        skips.setText(qm.getPowerUps().getSkipsAsString());
        coins.setText(qm.getPowerUps().getPointsAsString());
        setButtonListeners();
    }

    private void loadGame(){
        // Load the previous game data
        Bundle saveGameData = new GameSaver().loadGame(this);

        try {
            PowerUps powerUps = (PowerUps) saveGameData.getSerializable(Constants.POWERUPS);
            //QuestionPool questionPool = new FileHandler().readQuestionPoolfromAssets(this);
            QuestionPool questionPool = new FileHandler().readQuestionPoolFromFile(this);
            qm = new QuizMaster(questionPool, powerUps);

            // Start the QuizMaster to either continue the saved quiz or start anew
            String quizName = saveGameData.getString(Constants.QUIZNAME);
            // If it's the same quiz name then load up the data for it
            if(quizName != null && quizName.equals(qm.getQuestionPool().getQuizName())){
                int currentQuestion = saveGameData.getInt(Constants.CURRENTQUESTIONNO);
                Log.d("Load Quiz", "Question No: " + currentQuestion);
                // if the number saved was less than 1, then the quiz had ended on a previous game,
                if(currentQuestion <= 0){
                    currentQuestion = 1; // restart quiz
                }
                qm.goToQuestion(currentQuestion);
            } else {
                qm.setNextQuestion();
            }
            Log.d("Game Menu", "starting quiz master from question " + qm.getCurrentQuestionNumber());
        } catch(NullObjectException e){
            e.printStackTrace();
            Bundle errorBundle = new Bundle();
            errorBundle.putString(Constants.TITLE, getResources().getString(R.string.null_object_error_title));
            errorBundle.putString(Constants.MESSAGE, getResources().getString(R.string.null_object_error));
            new DialogCreator().errorDialog(getFragmentManager(), errorBundle);
        }
    }

    /**
     * As the power ups and the quiz master may be changed by multiple classes, the listeners for
     * each button need to be updated also otherwise the powerups will changes will not carry on.
     */
    private void setButtonListeners(){
        Bundle quizTagBundle = new Bundle();
        quizTagBundle.putString(Constants.MESSAGE, Constants.QUIZTAG);
        quizTagBundle.putInt(Constants.RESULT, 2);

        quiz_tag_button.setOnClickListener(new GameMenuButtonListener(this, quizTagBundle));

        setShopButtonListener();
        setStartButtonListener();
    }

    /**
     * Set the start quiz button listeners
     */
    private void setStartButtonListener(){
        Bundle startBundle = new Bundle();
        startBundle.putString(Constants.MESSAGE, Constants.PLAY);
        startBundle.putSerializable(Constants.QUIZMASTER, qm);
        startBundle.putInt(Constants.RESULT, 0);

        start_button.setOnClickListener(new GameMenuButtonListener(this, startBundle));
    }

    /**
     * Set the shop button listeners
     */
    private void setShopButtonListener(){
        Bundle shopBundle = new Bundle();
        shopBundle.putString(Constants.MESSAGE, Constants.POINTS_SHOP);
        shopBundle.putInt(Constants.RESULT, 1);
        shopBundle.putSerializable(Constants.POWERUPS, qm.getPowerUps());

        shop_button.setOnClickListener(new GameMenuButtonListener(this, shopBundle));
    }

}