package baldeep.quiztagapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import baldeep.quiztagapp.Listeners.QuitDialogListener;


public class LoadingDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder loadingDialog = new AlertDialog.Builder(getActivity());

        loadingDialog.setCancelable(false);
        loadingDialog.setTitle("Reading Tag");
        loadingDialog.setMessage("Loading, plase wait");

        return loadingDialog.create();
    }
}