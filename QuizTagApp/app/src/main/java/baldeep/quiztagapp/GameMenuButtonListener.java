package baldeep.quiztagapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import baldeep.quiztagapp.backend.QuizMaster;

/**
 * Created by skb12156 on 05/02/2016.
 */
public class GameMenuButtonListener implements View.OnClickListener {

    String message;
    QuizMaster quizMaster;
    Game_Menu menu;

    public GameMenuButtonListener(String msg, QuizMaster qm, Game_Menu menu){
        this.message = msg;
        this.quizMaster = qm;
        this.menu = menu;
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();

        if(message.equals("start")){
            Intent questionScreen = new Intent(menu, Question_Screen.class);

            questionScreen.putExtra("quizMaster", quizMaster);

            v.getContext().startActivity(questionScreen);
        }
    }
}
