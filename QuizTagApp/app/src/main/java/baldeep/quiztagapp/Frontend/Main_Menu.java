package baldeep.quiztagapp.Frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Listeners.MainMenuButtonListener;
import baldeep.quiztagapp.R;

/**
 * This is the first screen which runs on app launch, it displays the main menu and lets the user go
 * to the scan screen, the connect to smart case, and the quiz
 */
public class Main_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);

        Button play_button = (Button) findViewById(R.id.play_button);
        Button connect_button = (Button) findViewById(R.id.connect_button);
        Button scan_button = (Button) findViewById(R.id.scan_button);

        Bundle playBundle = new Bundle();
        playBundle.putString(Constants.MESSAGE, Constants.PLAY);
        play_button.setOnClickListener(new MainMenuButtonListener(playBundle));

        Bundle connectBundle = new Bundle();
        connectBundle.putString(Constants.MESSAGE, Constants.CONNECT);
        connect_button.setOnClickListener(new MainMenuButtonListener(connectBundle));

        Bundle scanBundle = new Bundle();
        scanBundle.putString(Constants.MESSAGE, Constants.SCAN);
        scan_button.setOnClickListener(new MainMenuButtonListener(scanBundle));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Load up Action Bar
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle Action Bar buttons
        int id = item.getItemId();
        if(id == R.id.exit_menu_option){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
