package baldeep.quiztagapp.Listeners;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import baldeep.quiztagapp.Frontend.ConnectDialog;
import baldeep.quiztagapp.Frontend.Shop_Menu;
import baldeep.quiztagapp.backend.PowerUps;
import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.Frontend.Game_Menu;
import baldeep.quiztagapp.Frontend.Question_Screen;

/**
 * Created by skb12156 on 05/02/2016.
 */
public class GameMenuButtonListener implements View.OnClickListener {

    private Bundle arguments;
    private Game_Menu menu;

    public GameMenuButtonListener(Game_Menu menu, Bundle arguments){
        this.arguments = arguments;
        this.menu = menu;
    }


    @Override
    public void onClick(View v) {
        String message = arguments.getString("message");

        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();

        if(message.equals("start")){
            Intent questionScreen = new Intent(menu, Question_Screen.class);
            questionScreen.putExtra("quizMaster", (QuizMaster) arguments.getSerializable("quizMaster"));

            menu.startActivityForResult(questionScreen, arguments.getInt("result"));
        } else if(message.equals("quiztag")){
            DialogFragment df = new ConnectDialog();

            Bundle connectArgs = new Bundle();
            connectArgs.putString("title", "Download Quiz");
            connectArgs.putString("message",
                    "To download a new quiz please place phone on QuizTag and press ok");
            connectArgs.putString("type", "QuizTag");
            df.setArguments(connectArgs);

            df.show(menu.getFragmentManager(), "Connect Dialog");
        } else if(message.equals("shop")){
            Intent shopScreen = new Intent(menu, Shop_Menu.class);

            PowerUps pu = (PowerUps) arguments.getSerializable("powerUps");
            shopScreen.putExtra("powerUps", pu);

            menu.startActivityForResult(shopScreen, arguments.getInt("result"));
        }
    }


}
