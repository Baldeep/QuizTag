package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.content.DialogInterface;

import baldeep.quiztagapp.Frontend.AnswerConfirmationDialog;

/**
 * Created by skb12156 on 18/02/2016.
 */
public class AnsConfirmationDialogListener implements DialogInterface.OnClickListener {

    String message;
    Activity activity;

    public AnsConfirmationDialogListener(Activity activity, String message) {
        this.activity = activity;
        this.message = message;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(message.equals("yes")){

        }
    }
}
