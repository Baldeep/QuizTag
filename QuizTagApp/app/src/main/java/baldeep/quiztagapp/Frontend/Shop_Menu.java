package baldeep.quiztagapp.Frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;

import baldeep.quiztagapp.R;
import baldeep.quiztagapp.backend.PowerUps;

public class Shop_Menu extends AppCompatActivity {

    private PowerUps pu;
    private Layout buy_skips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_screen_activity);
        Intent previousActivity = getIntent();
        pu = (PowerUps) previousActivity.getSerializableExtra("powerUps");

        //buy_skips = getLayoutInflater().inflate(R.layout.buy_skips_button);




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

    @Override
    public void onBackPressed(){
        Intent goingBack = new Intent();
        goingBack.putExtra("powerUps", pu);
        setResult(RESULT_OK, goingBack);

        super.onBackPressed();
    }
}
