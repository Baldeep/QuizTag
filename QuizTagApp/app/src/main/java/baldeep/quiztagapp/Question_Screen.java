package baldeep.quiztagapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_screen_activity);

        // Set up QuizMaster
        Intent previousActivity = getIntent();
        //QuizMaster qm = (QuizMaster) previousActivity.getSerializableExtra("quizMaster");
        QuizMaster qm = new QuizMaster("Example Quiz", "Quiz.txt");

        questionField = (TextView) findViewById(R.id.question_field);
        hintsField = (TextView) findViewById(R.id.hints_field);

        hints = (TextView) findViewById(R.id.hints_count_text);
        skips = (TextView) findViewById(R.id.skips_count_text);
        coins = (TextView) findViewById(R.id.coins_count_text);

        System.out.println("Made things ****************************************************");
        // get the question
        qm.setNextQuestion();
        System.out.println("Set Question ****************************************************");
        setTitle("Question " + qm.getCurrentQuestionNumber());

        System.out.println("Setting title ****************************************************");

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
}
