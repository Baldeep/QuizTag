package baldeep.quiztagapp.Listeners;

import android.app.DialogFragment;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import baldeep.quiztagapp.Frontend.ConnectDialog;
import baldeep.quiztagapp.Frontend.Game_Menu;
import baldeep.quiztagapp.Frontend.Main_Menu;
import baldeep.quiztagapp.backend.PowerUps;

/**
 * A basic listener for the main menu, on creation it asks for a string which should be either
 * play, scan, or connect as this is what's used to change the view
 */
public class MainMenuButtonListener implements View.OnClickListener {

    String message;
    Main_Menu menu;
    PowerUps pu;

    public MainMenuButtonListener(Main_Menu menu, String msg){
        this.menu = menu;
        this.message = msg;
        pu = menu.getPowerUps();
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(menu, message, Toast.LENGTH_SHORT).show();

        if(message.equals("play")){
            Intent gameScreen = new Intent(menu, Game_Menu.class);
            gameScreen.putExtra("playMessage", "play was pressed");
            //gameScreen.putExtra("PowerUps", pu);

            menu.startActivity(gameScreen);
        } else if(message.equals("connect")){
            DialogFragment df = new ConnectDialog();
            System.out.println("Made fragment");
            df.show(menu.getFragmentManager(), "Connect Dialog");
        }
    }
}