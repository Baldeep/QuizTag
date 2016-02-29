package baldeep.quiztagapp.Frontend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import baldeep.quiztagapp.Listeners.MainMenuButtonListener;
import baldeep.quiztagapp.R;
import baldeep.quiztagapp.backend.FileReader;
import baldeep.quiztagapp.backend.PowerUps;
import baldeep.quiztagapp.backend.QuestionPool;

public class Main_Menu extends AppCompatActivity {

    private PowerUps pu;
    private QuestionPool qp;

    private Button play_button;
    private Button connect_button;
    private Button scan_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);

        play_button = (Button) findViewById(R.id.play_button);
        connect_button = (Button) findViewById(R.id.connect_button);
        scan_button = (Button) findViewById(R.id.scan_button);

        readSaveFile();

        qp = new FileReader().getQuestionPoolFromFile(this);
        if(qp == null){
            System.out.println("QUESTION POOL IS NULL IN MAIN MENU *****");
        }

        Bundle playBundle = new Bundle();
        playBundle.putString("message", "play");
        playBundle.putSerializable("powerUps", pu);
        playBundle.putSerializable("questionPool", qp);
        play_button.setOnClickListener(new MainMenuButtonListener(this, playBundle));

        Bundle connectBundle = new Bundle();
        connectBundle.putString("message", "connect");
        connect_button.setOnClickListener(new MainMenuButtonListener(this, connectBundle));

        Bundle scanBundle = new Bundle();
        scanBundle.putString("message", "scan");
        scan_button.setOnClickListener(new MainMenuButtonListener(this, scanBundle));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.exit_menu_option){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PowerUps pu = (PowerUps) data.getSerializableExtra("powerUps");
        this.pu = pu;

        Bundle playBundle = new Bundle();
        playBundle.putString("message", "play");
        playBundle.putSerializable("powerUps", pu);
        playBundle.putSerializable("questionPool", qp);
        play_button.setOnClickListener(new MainMenuButtonListener(this, playBundle));

        SharedPreferences saveGame = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor saver = saveGame.edit();
        saver.putInt("skips", pu.getSkips());
        saver.putInt("hints", pu.getHints());
        saver.putInt("points", pu.getPoints());

        saver.commit();
    }

    private void readSaveFile(){
        SharedPreferences saveGame = getPreferences(MODE_PRIVATE);
        int points = saveGame.getInt("points", 120);
        int hints = saveGame.getInt("hints", 10);
        int skips = saveGame.getInt("skips", 10);

        System.out.println("Power ups(points: " + points + ", hints: " + hints +
                ", skips: " + skips + ")");

        pu = new PowerUps(points, hints, skips);
    }

}
