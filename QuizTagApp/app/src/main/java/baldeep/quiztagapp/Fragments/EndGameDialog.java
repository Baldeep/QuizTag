package baldeep.quiztagapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Listeners.EndGameDialogListener;
import baldeep.quiztagapp.R;

public class EndGameDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder endGameDialog = new AlertDialog.Builder(getActivity());

        setCancelable(false);

        endGameDialog.setTitle(getArguments().getString(Constants.TITLE));
        endGameDialog.setMessage(getArguments().getString(Constants.MESSAGE));

        Bundle quitBundle = new Bundle();
        quitBundle.putSerializable(Constants.POWERUPS,
                getArguments().getSerializable(Constants.POWERUPS));
        quitBundle.putString(Constants.QUIZNAME, getArguments().getString(Constants.QUIZNAME));
        quitBundle.putInt(Constants.CURRENTQUESTIONNO,
                getArguments().getInt(Constants.CURRENTQUESTIONNO));
        quitBundle.putString(Constants.MESSAGE, Constants.QUIT);
        endGameDialog.setPositiveButton(getActivity().getResources().getString(R.string.quit),
                new EndGameDialogListener(this.getActivity(), quitBundle));

        return endGameDialog.create();
    }
}

