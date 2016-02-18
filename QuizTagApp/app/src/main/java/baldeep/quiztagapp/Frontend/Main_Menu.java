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

    PowerUps pu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);

        pu = new PowerUps(0, 100, 100);

        Button play_button = (Button) findViewById(R.id.play_button);
        Button connect_button = (Button) findViewById(R.id.connect_button);
        Button scan_button = (Button) findViewById(R.id.scan_button);

        play_button.setOnClickListener(new MainMenuButtonListener(this, "play"));
        connect_button.setOnClickListener(new MainMenuButtonListener(this, "connect"));
        scan_button.setOnClickListener(new MainMenuButtonListener(this, "scan"));
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

    public PowerUps getPowerUps(){
        return pu;
    }
}