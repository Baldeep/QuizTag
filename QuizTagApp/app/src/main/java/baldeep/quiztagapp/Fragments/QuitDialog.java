package baldeep.quiztagapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import baldeep.quiztagapp.Listeners.ConnectDialogListener;
import baldeep.quiztagapp.Listeners.QuitDialogListener;

public class QuitDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder connectDialog = new AlertDialog.Builder(getActivity());

        connectDialog.setTitle("Quit");
        connectDialog.setMessage("Are you sure you want to exit?");

        connectDialog.setPositiveButton("Yes", new QuitDialogListener(this.getActivity(), "yes"));

        connectDialog.setNegativeButton("No", new QuitDialogListener(this.getActivity(), "no"));

        return connectDialog.create();
    }
}