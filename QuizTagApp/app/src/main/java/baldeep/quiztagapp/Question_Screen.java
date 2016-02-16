package baldeep.quiztagapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import baldeep.quiztagapp.backend.IObserver;
import baldeep.quiztagapp.backend.QuizMaster;


public class Question_Screen extends AppCompatActivity implements IObserver {

    TextView questionField;
    //TextView hintsField;
    TextView hints;
    TextView skips;
    TextView coins;

    ImageButton hintsButton;
    ImageButton skipButton;
    Button hint1;
    Button hint2;
    Button hint3;
    Button hint4;

    QuizMaster qm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_screen_activity);

        // Set up QuizMaster
        Intent previousActivity = getIntent();
        qm = (QuizMaster) previousActivity.getSerializableExtra("quizMaster");
        //QuizMaster qm = new QuizMaster("Example Quiz", "Quiz.txt");

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

        hintsButton.setOnClickListener(new QuestionScreenButtonListener(this, "hint", qm));
        skipButton.setOnClickListener(new QuestionScreenButtonListener(this, "skip", qm));
        hint1.setOnClickListener(new QuestionScreenButtonListener(this, "hint", qm));
        hint2.setOnClickListener(new QuestionScreenButtonListener(this, "hint", qm));
        hint3.setOnClickListener(new QuestionScreenButtonListener(this, "hint", qm));
        hint4.setOnClickListener(new QuestionScreenButtonListener(this, "hint", qm));

        //update();

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

    public void displayHints() {
        if(qm.hintsAvailable()) {
            List<String> hints = qm.revealHints();

            if (hints.size() > 0) {
                hint1.setText(hints.get(0));
                hint1.setVisibility(View.VISIBLE);
            }
            if (hints.size() > 1) {
                hint2.setText(hints.get(1));
                hint2.setVisibility(View.VISIBLE);
            }
            if (hints.size() > 2) {
                hint3.setText(hints.get(2));
                hint3.setVisibility(View.VISIBLE);
            }
            if (hints.size() > 3) {
                hint4.setText(hints.get(3));
                hint4.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void update() {
        if(qm.getCurrentQuestionNumber() <= 0){
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

        displayHints();
    }
}
