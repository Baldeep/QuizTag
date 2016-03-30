package baldeep.quiztagapp.Frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.R;


public class Smart_Case_Screen extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.smart_case_activity);

        Button back_btn = (Button) findViewById(R.id.smart_case_back_btn);
        Button next_btn = (Button) findViewById(R.id.smart_case_next_btn);

        final TextView titleText = (TextView) findViewById(R.id.smart_case_title_text);
        TextView text = (TextView) findViewById(R.id.smart_case_text);
        
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Smart_Case_Screen.this, "Back", Toast.LENGTH_SHORT).show();
                titleText.setText(getResources().getString(R.string.back) + " was pressed");
            }
        });
        
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Smart_Case_Screen.this, "Next", Toast.LENGTH_SHORT).show();
                titleText.setText(getResources().getString(R.string.next) + " was pressed");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle Action Bar buttons
        int id = item.getItemId();
        if(id == R.id.toolbar_back_button){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
