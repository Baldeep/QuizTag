package baldeep.quiztagapp.Frontend;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import baldeep.quiztagapp.R;


public class SmartScreen extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        
        Button back_btn = (Button) findViewById(R.id.smart_case_back_btn);
        Button next_btn = (Button) findViewById(R.id.smart_case_next_btn);

        TextView text = (TextView) findViewById(R.id.smart_case_text);
        
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SmartScreen.this, "Back", Toast.LENGTH_SHORT).show();
            }
        });
        
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SmartScreen.this, "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
