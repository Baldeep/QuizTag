package baldeep.quiztagapp.Frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import baldeep.quiztagapp.Listeners.DialogCreator;
import baldeep.quiztagapp.backend.FileHandler;
import baldeep.quiztagapp.backend.GameSaver;
import baldeep.quiztagapp.backend.PowerUps;
import baldeep.quiztagapp.backend.Question;
import baldeep.quiztagapp.backend.QuestionPool;
import baldeep.quiztagapp.backend.QuizMaster;
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

    private PowerUps powerUps;
    private QuestionPool questionPool;
    private QuizMaster qm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_menu_activity);

        Bundle saveGameData = new GameSaver().loadGame(this);
        powerUps = (PowerUps) saveGameData.getSerializable("powerUps");

        questionPool = new FileHandler().getQuestionPoolFromFile(this);
        if(questionPool.getQuestionPool() == null){
           Log.e("Reading QuestioPool", "Reading questionpool resulted in null");
        }


        qm = new QuizMaster(questionPool, powerUps);

        String quizName = saveGameData.getString("quizName");
        if(quizName != null && quizName.equals(questionPool.getQuizName())){
            int currentQuestion = saveGameData.getInt("currentQuestionNo");
            Log.i("Load Quiz", "Question No: " + currentQuestion);
            qm.goToQuestion(currentQuestion);
        } else {
            qm.setNextQuestion();
        }
        Log.i("Game Menu", "starting quiz master from question " + qm.getCurrentQuestionNumber());



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
        getMenuInflater().inflate(R.menu.back_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.toolbar_back_button){
            Intent goingBack = new Intent();
            goingBack.putExtra("powerUps", powerUps);
            setResult(RESULT_OK, goingBack);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 0){
            this.powerUps = (PowerUps) data.getSerializableExtra("powerUps");
            Bundle saveGame = new Bundle();
            saveGame.putSerializable("powerUps", powerUps);
            saveGame.putString("quizName", data.getStringExtra("quizName"));
            saveGame.putInt("currentQuestionNo", data.getIntExtra("currentQuestionNo", 1));
            new GameSaver().saveQuiz(this, saveGame);
            update();
        }
        else if (requestCode == 1) {
            this.powerUps = (PowerUps) data.getSerializableExtra("powerUps");
            Bundle saveGame = new Bundle();
            saveGame.putSerializable("powerUps", powerUps);
            new GameSaver().savePowerUps(this, saveGame);
            update();
        }
        else if(requestCode == 2){
            QuestionPool qp = (QuestionPool) data.getSerializableExtra("questionPool");
            qm.setNewQuiz(qp);
            update();
        }
    }

    /**
     *  This class could not be made an observer as the powerups need to be passed down to the next
     *  activities which would require this class and the inherited AppCompatActivity class to be
     *  Serialisable. Therefore this class needs to manually be updated on activity result.
     */
    private void update() {
        hints.setText(powerUps.getHintsAsString());
        skips.setText(powerUps.getSkipsAsString());
        coins.setText(powerUps.getPointsAsString());
        setButtonListeners();
    }

    /**
     * As the power ups and the quiz master may be changed by multiple classes, the listeners for
     * each button need to be updated also otherwise the powerups will changes will not carry on.
     */
    private void setButtonListeners(){
        Bundle quizTagBundle = new Bundle();
        quizTagBundle.putString("message", "quiztag");
        quizTagBundle.putInt("result", 2);

        quiz_tag_button.setOnClickListener(new GameMenuButtonListener(this, quizTagBundle));

        setShopButtonListener();
        setStartButtonListener();
    }

    /**
     * Set the start quiz button listeners
     */
    private void setStartButtonListener(){
        Bundle startBundle = new Bundle();
        startBundle.putString("message", "start");
        startBundle.putSerializable("quizMaster", qm);
        startBundle.putInt("result", 0);

        start_button.setOnClickListener(new GameMenuButtonListener(this, startBundle));
    }

    /**
     * Set the shop button listeners
     */
    private void setShopButtonListener(){
        Bundle shopBundle = new Bundle();
        shopBundle.putString("message", "shop");
        shopBundle.putInt("result", 1);
        shopBundle.putSerializable("powerUps", powerUps);

        shop_button.setOnClickListener(new GameMenuButtonListener(this, shopBundle));
    }

}
