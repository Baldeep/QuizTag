package baldeep.quiztagapp.Listeners;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Frontend.Game_Menu;
import baldeep.quiztagapp.Frontend.Main_Menu;
import baldeep.quiztagapp.Frontend.Scan_Screen;
import baldeep.quiztagapp.Frontend.SmartScreen;

/**
 * A basic listener for the main menu, on creation it asks for a string which should be either
 * play, scan, or connect as this is what's used to change the view
 */
public class MainMenuButtonListener extends Main_Menu implements View.OnClickListener {

    private Bundle arguments;

    public MainMenuButtonListener(Bundle arguments){
        this.arguments = arguments;
    }


    @Override
    public void onClick(View v) {
        String message = arguments.getString(Constants.MESSAGE);

        if(message.equals(Constants.PLAY)){
            Intent gameScreen = new Intent(getApplicationContext(), Game_Menu.class);
            startActivity(gameScreen);

        } else if(message.equals(Constants.CONNECT)){
            Intent smartCase = new Intent(this.getApplicationContext(), SmartScreen.class);
            startActivity(smartCase);

        } else if(message.equals(Constants.SCAN)){
            Intent scanScreen = new Intent(this.getApplicationContext(), Scan_Screen.class);
            startActivity(scanScreen);
        }
    }
}