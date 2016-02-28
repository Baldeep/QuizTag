package baldeep.quiztagapp.Frontend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import baldeep.quiztagapp.Listeners.ConnectDialogListener;

/**
 * Created by Baldeep on 28/02/2016.
 */
public class ShowAnswerDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder answerDialog = new AlertDialog.Builder(getActivity());

        answerDialog.setTitle((String) getArguments().get("title"));
        answerDialog.setMessage((String) getArguments().get("message"));

        //answerDialog.setCancelable(false);

        answerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, just dissapear
            }
        });


        return answerDialog.create();
    }
}
