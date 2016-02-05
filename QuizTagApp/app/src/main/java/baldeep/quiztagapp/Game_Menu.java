package baldeep.quiztagapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

}
