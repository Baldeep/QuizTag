package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

public class QuitDialogListener implements DialogInterface.OnClickListener {

    String message;
    Activity origin;

    public QuitDialogListener(Activity context, String message) {
        this.origin = context;
        this.message = message;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(message.equals("yes")){
            origin.finish();
        }
    }
}
