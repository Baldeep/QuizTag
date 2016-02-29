package baldeep.quiztagapp.Listeners;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import baldeep.quiztagapp.Frontend.ConnectDialog;
import baldeep.quiztagapp.Frontend.Game_Menu;
import baldeep.quiztagapp.Frontend.Main_Menu;
import baldeep.quiztagapp.Frontend.NFC_Tag_Writer;
import baldeep.quiztagapp.backend.PowerUps;

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

        Toast.makeText(menu, message, Toast.LENGTH_SHORT).show();

        if(message.equals("play")){

            Intent gameScreen = new Intent(menu, Game_Menu.class);
            gameScreen.putExtra("playMessage", "play was pressed");
            gameScreen.putExtra("powerUps", arguments.getSerializable("powerUps"));
            gameScreen.putExtra("questionPool", arguments.getSerializable("questionPool"));
            gameScreen.putExtra("result", arguments.getInt("result"));

            menu.startActivityForResult(gameScreen, 1);

        } else if(message.equals("connect")){
            DialogFragment df = new ConnectDialog();

            Bundle connectArgs = new Bundle();
            connectArgs.putString("title", "Connect to SmartCase");
            connectArgs.putString("message",
                    "Connecting to SmartCase, please place the phone on the case and press OK");
            connectArgs.putString("type", "SmartCase");
            df.setArguments(connectArgs);
            df.show(menu.getFragmentManager(), "Connect Dialog");

        } else if(message.equals("scan")){
            Intent nfcExample = new Intent(menu, NFC_Tag_Writer.class);

            menu.startActivity(nfcExample);
        }
    }
}