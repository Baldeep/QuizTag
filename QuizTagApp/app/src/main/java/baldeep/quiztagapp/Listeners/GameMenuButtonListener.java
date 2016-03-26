package baldeep.quiztagapp.Listeners;

import android.app.Activity;
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


public class GameMenuButtonListener implements View.OnClickListener {

    private Bundle arguments;
    private Activity activity;
    public GameMenuButtonListener(Activity activity, Bundle arguments){
        this.arguments = arguments;
        this.activity = activity;
    }


    @Override
    public void onClick(View v) {
        String message = arguments.getString(Constants.MESSAGE);

        if(message.equals(Constants.PLAY)){
            // When Start Quiz is pressed, start the Question Screen
            Intent questionScreen = new Intent(activity, Question_Screen.class);
            questionScreen.putExtra(Constants.QUIZMASTER,
                    (QuizMaster) arguments.getSerializable(Constants.QUIZMASTER));
            activity.startActivityForResult(questionScreen, arguments.getInt(Constants.RESULT));

        } else if(message.equals(Constants.QUIZTAG)){
            // When QuizTag is pressed, start the QuizTag screen
            Intent quizTagScreen = new Intent(activity, Quiz_Tag_Screen.class);
            activity.startActivityForResult(quizTagScreen, arguments.getInt(Constants.RESULT));

        } else if(message.equals(Constants.POINTS_SHOP)){
            // When Points Shop is pressed, start the Points Shop screen
            Intent shopScreen = new Intent(activity, Shop_Menu.class);
            shopScreen.putExtra(Constants.POWERUPS,
                    (PowerUps) arguments.getSerializable(Constants.POWERUPS));
            activity.startActivityForResult(shopScreen, arguments.getInt(Constants.RESULT));

        }
    }


}
