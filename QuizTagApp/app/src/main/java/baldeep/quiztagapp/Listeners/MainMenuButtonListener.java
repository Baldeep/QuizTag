package baldeep.quiztagapp.Listeners;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import baldeep.quiztagapp.Frontend.ConnectDialog;
import baldeep.quiztagapp.Frontend.Game_Menu;
import baldeep.quiztagapp.Frontend.Main_Menu;
import baldeep.quiztagapp.Frontend.Scan_Screen;

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
        String message = arguments.getString("message");

        if(message.equals("play")){

            Intent gameScreen = new Intent(menu, Game_Menu.class);
            menu.startActivity(gameScreen);

        } else if(message.equals("connect")){
            new DialogCreator().smartCaseConnectionDialog(menu.getFragmentManager(), new Bundle());

        } else if(message.equals("scan")){
            Intent nfcExample = new Intent(menu, Scan_Screen.class);
            menu.startActivity(nfcExample);
        }
    }
}