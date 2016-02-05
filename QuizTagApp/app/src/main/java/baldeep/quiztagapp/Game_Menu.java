package baldeep.quiztagapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by skb12156 on 05/02/2016.
 */
public class Game_Menu extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_menu_activity);

        Intent previousActivity = getIntent();
        String previous = previousActivity.getExtras().getString("playMessage");
        Toast.makeText(this, previous, Toast.LENGTH_SHORT).show();

    }
}
