package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import baldeep.quiztagapp.Frontend.AnswerConfirmationDialog;
import baldeep.quiztagapp.backend.QuizMaster;

/**
 * Created by skb12156 on 18/02/2016.
 */
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
            QuizMaster quizMaster = (QuizMaster) arguments.getSerializable("quizMaster");
            String answer = arguments.getString("answer");
            Boolean correct = quizMaster.checkAnswer(answer);

            if(correct){
                quizMaster.setNextQuestion();
                Toast.makeText(activity, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Wrong Answer!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
