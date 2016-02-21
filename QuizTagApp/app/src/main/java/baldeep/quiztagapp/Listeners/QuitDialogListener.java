package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class QuitDialogListener implements DialogInterface.OnClickListener {

    String message;
    Activity activity;

    public QuitDialogListener(Activity activity, String message) {
        this.activity = activity;
        this.message = message;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(message.equals("yes")){
            activity.finish();
        }
    }
}
