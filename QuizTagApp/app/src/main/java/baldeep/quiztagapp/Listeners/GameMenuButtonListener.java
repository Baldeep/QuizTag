package baldeep.quiztagapp.Listeners;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Frontend.Quiz_Tag_Screen;
import baldeep.quiztagapp.Frontend.Shop_Menu;
import baldeep.quiztagapp.backend.PowerUps;
import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.Frontend.Game_Menu;
import baldeep.quiztagapp.Frontend.Question_Screen;


public class GameMenuButtonListener extends Game_Menu implements View.OnClickListener {

    private Bundle arguments;

    public GameMenuButtonListener(Bundle arguments){
        this.arguments = arguments;
    }


    @Override
    public void onClick(View v) {
        String message = arguments.getString(Constants.MESSAGE);

        if(message.equals(Constants.PLAY)){
            // When Start Quiz is pressed, start the Question Screen
            Intent questionScreen = new Intent(this.getApplicationContext(), Question_Screen.class);
            questionScreen.putExtra(Constants.QUIZMASTER,
                    (QuizMaster) arguments.getSerializable(Constants.QUIZMASTER));
            startActivityForResult(questionScreen, arguments.getInt(Constants.RESULT));

        } else if(message.equals(Constants.QUIZTAG)){
            // When QuizTag is pressed, start the QuizTag screen
            Intent quizTagScreen = new Intent(this.getApplicationContext(), Quiz_Tag_Screen.class);
            startActivityForResult(quizTagScreen, arguments.getInt(Constants.RESULT));

        } else if(message.equals(Constants.POINTS_SHOP)){
            // When Points Shop is pressed, start the Points Shop screen
            Intent shopScreen = new Intent(this.getApplicationContext(), Shop_Menu.class);
            shopScreen.putExtra(Constants.POWERUPS,
                    (PowerUps) arguments.getSerializable(Constants.POWERUPS));
            startActivityForResult(shopScreen, arguments.getInt(Constants.RESULT));

        }
    }


}
