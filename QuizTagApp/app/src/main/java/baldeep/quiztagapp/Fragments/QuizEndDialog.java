package baldeep.quiztagapp.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import baldeep.quiztagapp.backend.PowerUps;

/**
 * Created by Baldeep on 19/03/2016.
 */
public class QuizEndDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder quizEndDialog = new AlertDialog.Builder(getActivity());

        quizEndDialog.setTitle("Quiz Finished");
        quizEndDialog.setMessage("Congratulations! You have finished the quiz!" +
                "\nFind another quiz tag to play more!");

        quizEndDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PowerUps pu = (PowerUps) getArguments().getSerializable("powerUps");
                Intent onResult = new Intent();
                onResult.putExtra("powerUps", pu);
                getActivity().setResult(Activity.RESULT_OK, onResult);
                getActivity().finish();
            }
        });

        quizEndDialog.setCancelable(false);

        return quizEndDialog.create();
    }
}
