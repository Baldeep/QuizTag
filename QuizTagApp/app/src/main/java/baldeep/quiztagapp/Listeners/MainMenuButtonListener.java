package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Frontend.Game_Menu;
import baldeep.quiztagapp.Frontend.Scan_Screen;
import baldeep.quiztagapp.Frontend.Smart_Case_Screen;

/**
 * A basic listener for the main menu, on creation it asks for a string which should be either
 * play, scan, or connect as this is what's used to change the view
 */
public class MainMenuButtonListener implements View.OnClickListener {

    private Bundle arguments;
    private Activity activity;

    public MainMenuButtonListener(Activity activity, Bundle arguments){
        this.arguments = arguments;
        this.activity = activity;
    }


    @Override
    public void onClick(View v) {
        String message = arguments.getString(Constants.MESSAGE);

        if(message.equals(Constants.PLAY)){
            Intent gameScreen = new Intent(activity, Game_Menu.class);
            activity.startActivity(gameScreen);

        } else if(message.equals(Constants.CONNECT)){
            Intent smartCase = new Intent(activity, Smart_Case_Screen.class);
            activity.startActivity(smartCase);

        } else if(message.equals(Constants.SCAN)){
            Intent scanScreen = new Intent(activity, Scan_Screen.class);
            activity.startActivity(scanScreen);
        }
    }
}