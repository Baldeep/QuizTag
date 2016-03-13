package baldeep.quiztagwriter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Baldeep on 13/03/2016.
 */
public class InformationDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder infoDialog = new AlertDialog.Builder(getActivity());

        infoDialog.setTitle((String) getArguments().get("title"));
        infoDialog.setMessage((String) getArguments().get("message"));

        //answerDialog.setCancelable(false);

        infoDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing, just dissapear
            }
        });


        return infoDialog.create();
    }
}
