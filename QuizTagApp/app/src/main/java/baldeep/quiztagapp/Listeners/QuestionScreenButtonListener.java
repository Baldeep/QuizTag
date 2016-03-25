package baldeep.quiztagapp.Listeners;

import android.os.Bundle;
import android.view.View;

import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Frontend.Question_Screen;

public class QuestionScreenButtonListener extends Question_Screen implements View.OnClickListener {

    private Bundle arguments;

    public QuestionScreenButtonListener(Bundle arguments){
        this.arguments = arguments;
    }


    @Override
    public void onClick(View v) {
        String message = arguments.getString(Constants.MESSAGE);
        QuizMaster quizMaster = (QuizMaster) arguments.getSerializable(Constants.QUIZMASTER);

        // When hints is pressed, reveal hints
        if(message.equals(Constants.HINTS)){
            quizMaster.revealHints();
        }

        // When skips is pressed, skip the question
        if(message.equals(Constants.SKIPS)){
            quizMaster.skipQuestion();
        }

    }


}