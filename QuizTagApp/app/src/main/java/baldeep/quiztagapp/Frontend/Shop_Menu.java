package baldeep.quiztagapp.Frontend;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Observable;
import java.util.Observer;

import baldeep.quiztagapp.Listeners.ShopButtonListener;
import baldeep.quiztagapp.R;
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
        powerUps = (PowerUps) previousActivity.getSerializableExtra("powerUps");

        buy_skips = (LinearLayout) findViewById(R.id.shop_buy_hints_btn);

        TextView hint_text = (TextView) findViewById(R.id.shop_button_text);
        TextView hint_subtext = (TextView) findViewById(R.id.shop_button_subtext);
        ImageView hint_image = (ImageView) findViewById(R.id.shop_button_image);

        hint_text.setText("But hints");
        String hintSubtext = "(" + powerUps.getHintsCost() + " points)";
        hint_subtext.setText(hintSubtext);
        hint_image.setImageResource(R.drawable.hint_icon);

        buy_hints = (LinearLayout) findViewById(R.id.shop_buy_skips_btn);

        Bundle hintsBundle = new Bundle();
        hintsBundle.putString("message", "hints");
        hintsBundle.putSerializable("powerUps", powerUps);

        Bundle skipsBundle = new Bundle();
        skipsBundle.putString("message", "skips");
        skipsBundle.putSerializable("powerUps", powerUps);

        buy_hints.setOnClickListener(new ShopButtonListener(this, hintsBundle));
        buy_skips.setOnClickListener(new ShopButtonListener(this, skipsBundle));


        hints = (TextView) findViewById(R.id.hints_count_text);
        skips = (TextView) findViewById(R.id.skips_count_text);
        coins = (TextView) findViewById(R.id.coins_count_text);

        update();
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
            Intent goingBack = new Intent();
            goingBack.putExtra("powerUps", powerUps);
            setResult(RESULT_OK, goingBack);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        Intent goingBack = new Intent();
        goingBack.putExtra("powerUps", powerUps);
        setResult(RESULT_OK, goingBack);

        super.onBackPressed();
    }

    public void update() {
        Bundle saveGameData = new Bundle();
        saveGameData.putSerializable("powerUps", powerUps);
        new GameSaver().saveGame(this, saveGameData);

        hints.setText(powerUps.getHintsAsString());
        skips.setText(powerUps.getSkipsAsString());
        coins.setText(powerUps.getPointsAsString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.powerUps = (PowerUps) data.getSerializableExtra("powerUps");

        update();
    }

}
