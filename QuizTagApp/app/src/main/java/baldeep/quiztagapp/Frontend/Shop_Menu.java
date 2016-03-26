package baldeep.quiztagapp.Frontend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Listeners.ShopButtonListener;
import baldeep.quiztagapp.R;
import baldeep.quiztagapp.backend.GameSaver;
import baldeep.quiztagapp.backend.PowerUps;

public class Shop_Menu extends AppCompatActivity {

    private PowerUps powerUps;
    private LinearLayout buy_skips;
    private LinearLayout buy_hints;

    private TextView hints;
    private TextView skips;
    private TextView coins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_menu_activity);
        Intent previousActivity = getIntent();

        // Get powerups from previous Activity
        powerUps = (PowerUps) previousActivity.getSerializableExtra(Constants.POWERUPS);

        // Skips button
        buy_skips = (LinearLayout) findViewById(R.id.shop_buy_skips_btn);

        // Text views for numbers
        hints = (TextView) findViewById(R.id.hints_count_text);
        skips = (TextView) findViewById(R.id.skips_count_text);
        coins = (TextView) findViewById(R.id.coins_count_text);

        // Set up buy hints button
        TextView hint_text = (TextView) findViewById(R.id.shop_button_text);
        TextView hint_subtext = (TextView) findViewById(R.id.shop_button_subtext);
        ImageView hint_image = (ImageView) findViewById(R.id.shop_button_image);

        hint_text.setText(getResources().getString(R.string.buy_hints));
        String hintSubtext = "(" + powerUps.getHintsCost() + " " + getResources().getString(R.string.points) + ")";
        hint_subtext.setText(hintSubtext);
        hint_image.setImageResource(R.drawable.hint_icon);

        buy_hints = (LinearLayout) findViewById(R.id.shop_buy_hints_btn);


        // Set listeners for the buttons
        Bundle hintsBundle = new Bundle();
        hintsBundle.putString(Constants.MESSAGE, Constants.HINTS);
        hintsBundle.putSerializable(Constants.POWERUPS, powerUps);
        buy_hints.setOnClickListener(new ShopButtonListener(this, hintsBundle));

        Bundle skipsBundle = new Bundle();
        skipsBundle.putString(Constants.MESSAGE, Constants.SKIPS);
        skipsBundle.putSerializable(Constants.POWERUPS, powerUps);
        buy_skips.setOnClickListener(new ShopButtonListener(this, skipsBundle));

        update(); // Update method sets the contents of the text views
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Load up Action Bar Menu
        getMenuInflater().inflate(R.menu.back_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle Action Bar buttons
        int id = item.getItemId();
        if(id == R.id.toolbar_back_button){
            Intent goingBack = new Intent();
            goingBack.putExtra(Constants.POWERUPS, powerUps);
            setResult(RESULT_OK, goingBack);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        // Return power ups to previous activity for updating
        Intent goingBack = new Intent();
        goingBack.putExtra(Constants.POWERUPS, powerUps);
        setResult(RESULT_OK, goingBack);

        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When a button comes back from it's listener, it will return a result, set that result
        // here and update.
        if(requestCode == 1) {
            this.powerUps = (PowerUps) data.getSerializableExtra(Constants.POWERUPS);
            update();
        }

    }

    public void update() {
        // Dave the powerups when they change
        Bundle saveGameData = new Bundle();
        saveGameData.putSerializable(Constants.POWERUPS, powerUps);
        new GameSaver().savePowerUps(this, saveGameData);

        hints.setText(powerUps.getHintsAsString());
        skips.setText(powerUps.getSkipsAsString());
        coins.setText(powerUps.getPointsAsString());
    }



}
