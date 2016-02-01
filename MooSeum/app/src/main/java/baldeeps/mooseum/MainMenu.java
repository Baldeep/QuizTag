package baldeeps.mooseum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button play_button = (Button) findViewById(R.id.play_button);
        Button connect_button = (Button) findViewById(R.id.connect_button);
        Button scan_button = (Button) findViewById(R.id.scan_button);

        play_button.setOnClickListener(new MainMenuButtonListener("play"));
        connect_button.setOnClickListener(new MainMenuButtonListener("connect"));
        scan_button.setOnClickListener(new MainMenuButtonListener("scan"));
    }
}
