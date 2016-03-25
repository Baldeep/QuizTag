package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Fragments.AnswerConfirmationDialog;
import baldeep.quiztagapp.Fragments.InformationDialog;
import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.R;

public class AnsConfirmationDialogListener implements DialogInterface.OnClickListener {

    private Activity activity;
    private Bundle arguments;

    public AnsConfirmationDialogListener(Activity activity, Bundle arguments) {
        this.activity = activity;
        this.arguments = arguments;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String message = arguments.getString(Constants.MESSAGE);
        if(message.equals(Constants.YES)){

            /** From here in Question Screen -- **/
            QuizMaster quizMaster = (QuizMaster) arguments.getSerializable(Constants.QUIZMASTER);
            String answer = arguments.getString(Constants.ANSWER);
            Boolean correct = quizMaster.checkAnswer(answer);

            Bundle checked = new Bundle();
            if(correct){
                Log.d("AnswerConfirmation", "correct");
                checked.putString(Constants.TITLE, activity.getResources().getString((R.string.correct)));
                String msg = activity.getResources().getString((R.string.points_earned1)) + " " +
                        quizMaster.getPointsPerQuestion() + " " +
                        activity.getResources().getString((R.string.points_earned2));
                checked.putString(Constants.MESSAGE, msg);
                DialogFragment df = new InformationDialog();
                df.setArguments(checked);
                df.show(activity.getFragmentManager(), "result");
                quizMaster.setNextQuestion();

                /** --  to here should be in a separate method **/
            } else {
                /** This also should be in a different method in Question Screen --- */
                checked.putString(Constants.TITLE, activity.getResources().getString((R.string.incorrect)));
                checked.putString(Constants.MESSAGE, activity.getResources().getString((R.string.try_again)));
                DialogFragment df = new InformationDialog();
                df.setArguments(checked);
                df.show(activity.getFragmentManager(), "result");
                /** -- up to here **/
            }
        }
    }
}
