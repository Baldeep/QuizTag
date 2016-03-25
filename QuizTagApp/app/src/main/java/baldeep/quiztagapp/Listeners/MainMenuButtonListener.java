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
public class MainMenuButtonListener implements View.OnClickListener {

    private Bundle arguments;
    private Main_Menu menu;

    public MainMenuButtonListener(Main_Menu menu, Bundle arguments){
        this.menu = menu;
        this.arguments = arguments;
    }


    @Override
    public void onClick(View v) {
        String message = arguments.getString(Constants.MESSAGE);

        if(message.equals(Constants.PLAY)){
            Intent gameScreen = new Intent(menu, Game_Menu.class);
            menu.startActivity(gameScreen);

        } else if(message.equals(Constants.CONNECT)){
            Intent smartCase = new Intent(menu, SmartScreen.class);
            menu.startActivity(smartCase);

        } else if(message.equals(Constants.SCAN)){
            Intent scanScreen = new Intent(menu, Scan_Screen.class);
            menu.startActivity(scanScreen);
        }
    }
}