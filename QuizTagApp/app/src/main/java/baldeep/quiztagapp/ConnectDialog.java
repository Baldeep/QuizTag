package baldeep.quiztagapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class ConnectDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder connectDialog = new AlertDialog.Builder(getActivity());

        connectDialog.setTitle("Connecting to SmartCase");
        connectDialog.setMessage("Connecting to SmartCase, place phone on case and press OK");

        connectDialog.setPositiveButton("OK", new ConnectDialogListener(this.getActivity(), "ok"));

        connectDialog.setNegativeButton("CANCEL",
                new ConnectDialogListener(this.getActivity(), "cancel"));

            System.out.println("making dialog");

        return connectDialog.create();
    }
}
