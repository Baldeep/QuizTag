package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import baldeep.quiztagapp.Constants.Constants;

public class EndGameDialogListener implements DialogInterface.OnClickListener {

    private Bundle arguments;
    private Activity activity;

    public EndGameDialogListener(Activity activity, Bundle arguments) {
        this.activity = activity;
        this.arguments = arguments;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String message = arguments.getString(Constants.MESSAGE);
        if (message.equals(Constants.QUIT)) {
            Intent goingBack = new Intent();
            goingBack.putExtra(Constants.POWERUPS, arguments.getSerializable(Constants.POWERUPS));
            goingBack.putExtra(Constants.QUIZNAME, arguments.getString(Constants.QUIZNAME));
            goingBack.putExtra(Constants.CURRENTQUESTIONNO,
                    arguments.getInt(Constants.CURRENTQUESTIONNO));
            activity.setResult(Activity.RESULT_OK, goingBack);
            activity.finish();
        }
    }
}
