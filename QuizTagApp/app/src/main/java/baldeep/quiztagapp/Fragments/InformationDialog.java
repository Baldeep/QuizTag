package baldeep.quiztagapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import baldeep.quiztagapp.Enums.Constants;
import baldeep.quiztagapp.Listeners.ConnectDialogListener;

/**
 * Created by Baldeep on 28/02/2016.
 */

public class InformationDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder infoDialog = new AlertDialog.Builder(getActivity());

        infoDialog.setTitle((String) getArguments().get(Constants.TITLE));
        infoDialog.setMessage((String) getArguments().get(Constants.MESSAGE));

        //answerDialog.setCancelable(false);

        infoDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, just dissapear
            }
        });


        return infoDialog.create();
    }
}

