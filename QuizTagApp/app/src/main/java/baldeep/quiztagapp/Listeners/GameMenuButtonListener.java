package baldeep.quiztagapp.Listeners;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import baldeep.quiztagapp.Fragments.ConnectDialog;
import baldeep.quiztagapp.Frontend.Scan_Screen;
import baldeep.quiztagapp.Frontend.Quiz_Tag_Screen;
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

        if(message.equals("start")){
            Intent questionScreen = new Intent(menu, Question_Screen.class);
            questionScreen.putExtra("quizMaster", (QuizMaster) arguments.getSerializable("quizMaster"));
            menu.startActivityForResult(questionScreen, arguments.getInt("result"));

        } else if(message.equals("quiztag")){



            Intent quizTagScreen = new Intent(menu, Quiz_Tag_Screen.class);
            menu.startActivityForResult(quizTagScreen, arguments.getInt("result"));

        } else if(message.equals("shop")){
            Intent shopScreen = new Intent(menu, Shop_Menu.class);
            shopScreen.putExtra("powerUps", (PowerUps) arguments.getSerializable("powerUps"));
            menu.startActivityForResult(shopScreen, arguments.getInt("result"));
        }
    }


}
