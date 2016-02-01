package baldeeps.mooseum;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    private TextView textbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        this.textbox = (TextView) findViewById(R.id.textView);
        Button play_button = (Button) findViewById(R.id.play_button);
        Button connect_button = (Button) findViewById(R.id.connect_button);
        Button scan_button = (Button) findViewById(R.id.scan_button);

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textbox.setText("You pressed play!");
            }
        });

        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textbox.setText("You pressed scan!");
            }
        });
    }

    public void scanButtonClick(View view){
        textbox.setText("You pressed connect!");
    }

    public void connectButtonClick(View view) {
        String message = "hello";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void playButtonClick(View view) {
    }
}
