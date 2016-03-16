package baldeep.quiztagapp.Listeners;

import android.os.Bundle;
import android.view.View;

import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.Frontend.Question_Screen;

public class QuestionScreenButtonListener implements View.OnClickListener {

    private Question_Screen screen;
    private Bundle arguments;

    public QuestionScreenButtonListener(Question_Screen screen, Bundle arguments){
        this.arguments = arguments;
        this.screen = screen;
    }


    @Override
    public void onClick(View v) {
        String message = arguments.getString("message");
        QuizMaster quizMaster = (QuizMaster) arguments.getSerializable("quizMaster");

        if(message.equals("hint")){
            quizMaster.revealHints();
        }
        if(message.equals("skip")){
            quizMaster.skipQuestion();
        }

    }


}