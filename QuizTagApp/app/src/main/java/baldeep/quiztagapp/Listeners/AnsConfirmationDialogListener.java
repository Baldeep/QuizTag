package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import baldeep.quiztagapp.Frontend.AnswerConfirmationDialog;
import baldeep.quiztagapp.Frontend.InformationDialog;
import baldeep.quiztagapp.Frontend.Question_Screen;
import baldeep.quiztagapp.backend.QuizMaster;

public class AnsConfirmationDialogListener implements DialogInterface.OnClickListener {

    private Activity activity;
    private Bundle arguments;

    public AnsConfirmationDialogListener(Activity activity, Bundle arguments) {
        this.activity = activity;
        this.arguments = arguments;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String message = arguments.getString("message");
        if(message.equals("yes")){

            /** From here -- **/
            QuizMaster quizMaster = (QuizMaster) arguments.getSerializable("quizMaster");
            String answer = arguments.getString("answer");
            Boolean correct = quizMaster.checkAnswer(answer);

            Bundle checked = new Bundle();
            if(correct){

                System.out.println("************************CORRECT**************");
                checked.putString("title", "Correct!");
                String msg = "You earned " + quizMaster.getPointsPerQuestion() + " points!";
                checked.putString("message", msg);
                DialogFragment df = new InformationDialog();
                df.setArguments(checked);
                df.show(activity.getFragmentManager(), "result");
                quizMaster.setNextQuestion();

                /** --  to here should be in a separate method **/
            } else {
                /** This also should be in a different method --- */
                checked.putString("title", "Incorrect!");
                checked.putString("message", "Try again!");
                DialogFragment df = new InformationDialog();
                df.setArguments(checked);
                df.show(activity.getFragmentManager(), "result");
                quizMaster.wrongAnswer();
                /** -- up to here **/
            }
        }
    }
}
