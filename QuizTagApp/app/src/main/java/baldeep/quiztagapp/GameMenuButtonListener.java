package baldeep.quiztagapp;

import android.content.Intent;
import android.view.View;

import baldeep.quiztagapp.backend.QuizMaster;

/**
 * Created by skb12156 on 05/02/2016.
 */
public class GameMenuButtonListener implements View.OnClickListener {

    String message;
    QuizMaster quizMaster;

    public GameMenuButtonListener(String msg, QuizMaster quizMaster){
        this.message = msg;
        this.quizMaster = quizMaster;
    }


    @Override
    public void onClick(View v) {
        //Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();

        if(message.equals("start")){
            Intent guestionScreen = new Intent(v.getContext(), Question_Screen.class);
            guestionScreen.putExtra("quizMaster", quizMaster);

            v.getContext().startActivity(guestionScreen);
        }
    }
}
