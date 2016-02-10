package baldeep.quiztagapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import baldeep.quiztagapp.backend.QuizMaster;

/**
 * Created by skb12156 on 05/02/2016.
 */
public class Game_Menu extends AppCompatActivity {
    TextView hints;
    TextView skips;
    TextView coins;

    Button start_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_menu_activity);

        // will load this from file in actual
       // QuizMaster qm = new QuizMaster("Example Quiz", "Quiz.txt");

        start_button = (Button) findViewById(R.id.start_button);
        start_button.setOnClickListener(new GameMenuButtonListener("start"));

        hints = (TextView) findViewById(R.id.hints_count_text);
        skips = (TextView) findViewById(R.id.skips_count_text);
        coins = (TextView) findViewById(R.id.coins_count_text);

        // use observer pattern for these here
       /* hints.setText(qm.getHintCount());
        skips.setText(qm.getSkipCount());
        coins.setText(qm.getPoints());*/

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
