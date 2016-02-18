package baldeep.quiztagapp.Listeners;

import android.view.View;
import android.widget.Toast;

import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.Frontend.Question_Screen;

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
            quizMaster.revealHints();
        }
        if(message.equals("skip")){
            quizMaster.skipQuestion();
        }

        if(message.equals("hint")){
            String id = v.getId() + "";
            System.out.print("id" + id + "***********************************");
        }


    }
}