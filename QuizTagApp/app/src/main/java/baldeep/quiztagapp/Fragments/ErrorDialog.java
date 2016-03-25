package baldeep.quiztagapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import baldeep.quiztagapp.Constants.Constants;

/**
 * Created by skb12156 on 25/03/2016.
 */
public class ErrorDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder errorDialog = new AlertDialog.Builder(getActivity());

        errorDialog.setTitle((String) getArguments().get(Constants.TITLE));
        errorDialog.setMessage((String) getArguments().get(Constants.MESSAGE));

        errorDialog.setCancelable(false);

        errorDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        });


        return errorDialog.create();
    }
}
