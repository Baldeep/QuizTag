package baldeep.quiztagapp;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import baldeep.quiztagapp.backend.QuizMaster;

/**
 * Created by Baldeep on 11/02/2016.
 */
public class QuestionScreenButtonListener implements View.OnClickListener {

    String message;
    QuizMaster quizMaster;
    Question_Screen screen;

    public QuestionScreenButtonListener(Question_Screen screen, String msg, QuizMaster qm){
        this.message = msg;
        this.quizMaster = qm;
        this.screen = screen;
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();

        if(message.equals("hint")){
            screen.displayHints();
        }
        if(message.equals("skip")){
            screen.skipQuestion();
        }

    }
}