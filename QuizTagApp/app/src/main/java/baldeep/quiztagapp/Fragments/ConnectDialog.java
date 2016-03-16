package baldeep.quiztagapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import baldeep.quiztagapp.Listeners.ConnectDialogListener;

public class ConnectDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder connectDialog = new AlertDialog.Builder(getActivity());

        connectDialog.setTitle((String) getArguments().get("title"));
        connectDialog.setMessage((String) getArguments().get("message"));

        String type = getArguments().getString("type");

        connectDialog.setPositiveButton("OK", new ConnectDialogListener(this.getActivity(), type));

        connectDialog.setNegativeButton("CANCEL",
                new ConnectDialogListener(this.getActivity(), "cancel"));


        return connectDialog.create();
    }
}
