package baldeep.quiztagapp.Listeners;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import baldeep.quiztagapp.Frontend.AnswerConfirmationDialog;
import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.Frontend.Question_Screen;

public class QuestionScreenButtonListener implements View.OnClickListener {

    Question_Screen screen;
    Bundle arguments;

    public QuestionScreenButtonListener(Question_Screen screen, Bundle arguments){
        this.arguments = arguments;
        this.screen = screen;
    }


    @Override
    public void onClick(View v) {
        String message = arguments.getString("message");
        QuizMaster quizMaster = (QuizMaster) arguments.getSerializable("quizMaster");

        if(message.equals("hint")){
            Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
            quizMaster.revealHints();
        }
        if(message.equals("skip")){
            Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
            quizMaster.skipQuestion();
        }

        if(message.equals("answer")){
            String answer = arguments.getString("text");
            Toast.makeText(v.getContext(), answer, Toast.LENGTH_SHORT).show();

            Bundle confirmBundle = new Bundle();
            confirmBundle.putSerializable("quizMaster", quizMaster);
            confirmBundle.putString("answer", answer);
            String title = "Question " + arguments.getString("questionNo");
            confirmBundle.putString("title", title);
            String text = "You have selected:\n" + answer
                    + "\nIs this your final answer?";
            confirmBundle.putString("message", text);

            DialogFragment df = new AnswerConfirmationDialog();
            df.setArguments(confirmBundle);
            df.show(screen.getFragmentManager(), "Confirm Dialog");
        }


    }


}