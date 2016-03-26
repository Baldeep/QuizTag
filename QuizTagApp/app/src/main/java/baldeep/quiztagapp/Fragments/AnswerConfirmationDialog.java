package baldeep.quiztagapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Listeners.AnsConfirmationDialogListener;
import baldeep.quiztagapp.backend.QuizMaster;

public class AnswerConfirmationDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = getArguments().getString(Constants.TITLE);
        String message = getArguments().getString(Constants.MESSAGE);

        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(getActivity());

        confirmDialog.setTitle(title);
        confirmDialog.setMessage(message);

        Bundle confirmBundle = new Bundle();
        QuizMaster qm = (QuizMaster) getArguments().getSerializable(Constants.QUIZMASTER);
        confirmBundle.putSerializable(Constants.QUIZMASTER, qm);
        String answer = getArguments().getString(Constants.ANSWER);
        Log.d("AnsConfDialog", answer);
        confirmBundle.putString(Constants.ANSWER, answer);
        confirmBundle.putString(Constants.MESSAGE, Constants.YES);

        confirmDialog.setPositiveButton(Constants.YES, new AnsConfirmationDialogListener(this.getActivity(), confirmBundle));

        Bundle noBundle = new Bundle();
        noBundle.putString(Constants.MESSAGE, Constants.NO);

        confirmDialog.setNegativeButton(Constants.NO, new AnsConfirmationDialogListener(this.getActivity(), noBundle));

        return confirmDialog.create();
    }
}