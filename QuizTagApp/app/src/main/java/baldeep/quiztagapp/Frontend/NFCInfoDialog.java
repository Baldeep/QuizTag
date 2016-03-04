package baldeep.quiztagapp.Frontend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Created by skb12156 on 04/03/2016.
 */
public class NFCInfoDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder infoDialog = new AlertDialog.Builder(getActivity());

        infoDialog.setTitle((String) getArguments().get("title"));
        infoDialog.setMessage((String) getArguments().get("message"));

        infoDialog.setCancelable(false);

        infoDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(getArguments().getString("type").equals("nfcOFF")){
                    // Open NFC Settings
                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                } else if (getArguments().getString("type").equals("noNFC")){
                    // Close the application
                    getActivity().finish();
                }
            }
        });


        return infoDialog.create();
    }
}
