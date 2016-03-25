package baldeep.quiztagapp.Listeners;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Frontend.Quiz_Tag_Screen;
import baldeep.quiztagapp.Frontend.Shop_Menu;
import baldeep.quiztagapp.Backend.PowerUps;
import baldeep.quiztagapp.Backend.QuizMaster;
import baldeep.quiztagapp.Frontend.Game_Menu;
import baldeep.quiztagapp.Frontend.Question_Screen;

/**
 * Created by skb12156 on 05/02/2016.
 */
public class GameMenuButtonListener extends Game_Menu implements View.OnClickListener {

    private Bundle arguments;
    private Game_Menu menu;

    public GameMenuButtonListener(Game_Menu menu, Bundle arguments){
        this.arguments = arguments;
        this.menu = menu;
    }


    @Override
    public void onClick(View v) {
        String message = arguments.getString(Constants.MESSAGE);

        if(message.equals(Constants.PLAY)){
            // When Start Quiz is pressed, start the Question Screen
            Intent questionScreen = new Intent(menu, Question_Screen.class);
            questionScreen.putExtra(Constants.QUIZMASTER,
                    (QuizMaster) arguments.getSerializable(Constants.QUIZMASTER));
            menu.startActivityForResult(questionScreen, arguments.getInt(Constants.RESULT));

        } else if(message.equals(Constants.QUIZTAG)){
            // When QuizTag is pressed, start the QuizTag screen
            Intent quizTagScreen = new Intent(menu, Quiz_Tag_Screen.class);
            menu.startActivityForResult(quizTagScreen, arguments.getInt(Constants.RESULT));

        } else if(message.equals(Constants.POINTS_SHOP)){
            // When Points Shop is pressed, start the Points Shop screen
            Intent shopScreen = new Intent(menu, Shop_Menu.class);
            shopScreen.putExtra(Constants.POWERUPS,
                    (PowerUps) arguments.getSerializable(Constants.POWERUPS));
            menu.startActivityForResult(shopScreen, arguments.getInt(Constants.RESULT));

        }
    }


}
