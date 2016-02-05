package baldeep.quiztagapp;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A basic listener for the main menu, on creation it asks for a string which should be either
 * play, scan, or connect as this is what's used to change the view
 */
public class MainMenuButtonListener implements View.OnClickListener {

    String message;

    public MainMenuButtonListener(String msg){
        this.message = msg;
    }


    @Override
    public void onClick(View v) {
        //Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();

        if(message.equals("play")){
            Intent gameScreen = new Intent(v.getContext(), Game_Menu.class);
            gameScreen.putExtra("playMessage", "play was pressed");

            v.getContext().startActivity(gameScreen);
        }
    }
}