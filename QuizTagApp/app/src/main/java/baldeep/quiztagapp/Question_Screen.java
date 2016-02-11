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
import android.widget.Toast;

import java.util.List;

import baldeep.quiztagapp.backend.QuizMaster;

/**
 * Created by Baldeep on 07/02/2016.
 */
public class Question_Screen extends AppCompatActivity {

    TextView questionField;
    TextView hintsField;
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

        // get the question
        setTitle("Question " + qm.getCurrentQuestionNumber());

        // use observer pattern for these here
        questionField.setText("Question " + qm.getCurrentQuestionNumber() + ": " +
                qm.getQuestionString());
        hints.setText(qm.getHintCount() + "");
        skips.setText(qm.getSkipCount() + "");
        coins.setText(qm.getPoints() + "");
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

    public void displayHints(){
        List<String> hints = qm.getHints();

        if(hints.get(0) != null){
            hint1.setText(hints.get(0));
            hint1.setVisibility(View.VISIBLE);
        }
        if(hints.get(1) != null){
            hint2.setText(hints.get(1));
            hint2.setVisibility(View.VISIBLE);
        }
        if(hints.get(2) != null){
            hint3.setText(hints.get(2));
            hint3.setVisibility(View.VISIBLE);
        }
        if(hints.get(3) != null){
            hint4.setText(hints.get(3));
            hint4.setVisibility(View.VISIBLE);
        }
    }

    public void skipQuestion() {
        qm.setNextQuestion();
        questionField.setText(qm.getQuestionString());
        hint1.setVisibility(View.INVISIBLE);
        hint2.setVisibility(View.INVISIBLE);
        hint3.setVisibility(View.INVISIBLE);
        hint4.setVisibility(View.INVISIBLE);
    }
}
