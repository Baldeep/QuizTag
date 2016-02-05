package baldeep.quiztagapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by skb12156 on 05/02/2016.
 */
public class Game_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_menu_activity);

        Intent previousActivity = getIntent();
        String previous = previousActivity.getExtras().getString("playMessage");

        Toast.makeText(this, previous, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
