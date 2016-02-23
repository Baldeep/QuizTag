package baldeep.quiztagapp.Frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import baldeep.quiztagapp.backend.PowerUps;
import baldeep.quiztagapp.backend.QuestionPool;
import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.Listeners.GameMenuButtonListener;
import baldeep.quiztagapp.R;

/**
 * Created by skb12156 on 05/02/2016.
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.game_menu_activity);

        Intent previousActivity = getIntent();
        powerUps = (PowerUps) previousActivity.getSerializableExtra("powerUps");
        questionPool = (QuestionPool) previousActivity.getSerializableExtra("questionPool");
        if(questionPool == null){
            System.out.println("QUESTION POOL IS NULL IN GAME MENU **********");
        }
        QuizMaster qm = new QuizMaster(questionPool, powerUps);

        if(powerUps == null){
            System.out.println("POWERUPS IS NULL\n" +
                    "**************************************************");
        }
        if(powerUps == null){
            System.out.println("POWERUPS IS NULL\n" +
                    "**************************************************");
        }

        //powerUps = new PowerUps(0, 100, 100);

        start_button = (Button) findViewById(R.id.start_button);
        quiz_tag_button = (Button) findViewById(R.id.quiz_tag_button);
        shop_button = (Button) findViewById(R.id.shop_button);

        Bundle startBundle = new Bundle();
        startBundle.putString("message", "start");
        startBundle.putSerializable("quizMaster", qm);
        final int result = 1;
        startBundle.putInt("result", result);

        Bundle quizTagBundle = new Bundle();
        quizTagBundle.putString("message", "quiztag");

        Bundle shopBundle = new Bundle();
        shopBundle.putString("message", "shop");
        shopBundle.putSerializable("powerUps", powerUps);

        start_button.setOnClickListener(new GameMenuButtonListener(this, startBundle));
        quiz_tag_button.setOnClickListener(new GameMenuButtonListener(this, quizTagBundle));
        shop_button.setOnClickListener(new GameMenuButtonListener(this, shopBundle));


        hints = (TextView) findViewById(R.id.hints_count_text);
        skips = (TextView) findViewById(R.id.skips_count_text);
        coins = (TextView) findViewById(R.id.coins_count_text);

        update(powerUps, null);
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

    public void update(Observable observable, Object data) {
        hints.setText(powerUps.getHintsAsString());
        skips.setText(powerUps.getSkipsAsString());
        coins.setText(powerUps.getPointsAsString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PowerUps pu = (PowerUps) data.getSerializableExtra("powerUps");
        this.powerUps = pu;

        update(null, null);
    }
}
