package baldeep.quiztagapp.Frontend;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import baldeep.quiztagapp.Listeners.AnsConfirmationDialogListener;


public class AnswerConfirmationDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String answer = getArguments().getString("answer");

        AlertDialog.Builder connectDialog = new AlertDialog.Builder(getActivity());

        connectDialog.setTitle("Confirm");
        connectDialog.setMessage("You have selected: \n" + answer + "\nIs this your final answer?");

        connectDialog.setPositiveButton("Yes", new AnsConfirmationDialogListener(this.getActivity(), "yes"));

        connectDialog.setNegativeButton("No", new AnsConfirmationDialogListener(this.getActivity(), "no"));

        return connectDialog.create();
    }
}