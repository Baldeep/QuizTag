package baldeep.quiztagapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import baldeep.quiztagapp.Listeners.AnsConfirmationDialogListener;
import baldeep.quiztagapp.Backend.QuizMaster;

public class AnswerConfirmationDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String title = getArguments().getString("title");
        String message = getArguments().getString("message");

        AlertDialog.Builder confirmDialog = new AlertDialog.Builder(getActivity());

        confirmDialog.setTitle(title);
        confirmDialog.setMessage(message);

        Bundle confirmBundle = new Bundle();
        QuizMaster qm = (QuizMaster) getArguments().getSerializable("quizMaster");
        confirmBundle.putSerializable("quizMaster", qm);
        String answer = getArguments().getString("answer");
        confirmBundle.putString("answer", answer);
        confirmBundle.putString("message", "yes");

        confirmDialog.setPositiveButton("Yes", new AnsConfirmationDialogListener(this.getActivity(), confirmBundle));

        Bundle noBundle = new Bundle();
        noBundle.putString("message", "no");

        confirmDialog.setNegativeButton("No", new AnsConfirmationDialogListener(this.getActivity(), noBundle));

        return confirmDialog.create();
    }
}