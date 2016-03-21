package baldeep.quiztagapp.Frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import baldeep.quiztagapp.Listeners.MainMenuButtonListener;
import baldeep.quiztagapp.R;
import baldeep.quiztagapp.backend.FileHandler;

/**
 * This is the first screen which runs on app launch, it displays the main menu and lets the user go
 * to the scan screen, the connect to smart case, and the quiz
 */
public class Main_Menu extends AppCompatActivity {

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

        Bundle playBundle = new Bundle();
        playBundle.putString("message", "play");
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
}
