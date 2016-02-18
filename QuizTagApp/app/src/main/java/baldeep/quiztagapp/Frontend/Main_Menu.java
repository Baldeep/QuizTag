package baldeep.quiztagapp.Frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import baldeep.quiztagapp.Listeners.MainMenuButtonListener;
import baldeep.quiztagapp.R;
import baldeep.quiztagapp.backend.PowerUps;

public class Main_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);

        Button play_button = (Button) findViewById(R.id.play_button);
        Button connect_button = (Button) findViewById(R.id.connect_button);
        Button scan_button = (Button) findViewById(R.id.scan_button);

        Bundle playBundle = new Bundle();
        playBundle.putString("message", "play");
        PowerUps pu = new PowerUps(0, 100, 100);
        playBundle.putSerializable("powerUps", pu);

        Bundle connectBundle = new Bundle();
        connectBundle.putString("message", "connect");

        Bundle scanBundle = new Bundle();
        scanBundle.putString("message", "scan");

        play_button.setOnClickListener(new MainMenuButtonListener(this, playBundle));
        connect_button.setOnClickListener(new MainMenuButtonListener(this, connectBundle));
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
