package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import baldeep.quiztagapp.Frontend.NFC_Tag_Writer;

/**
 * Created by skb12156 on 05/02/2016.
 */
public class ConnectDialogListener implements DialogInterface.OnClickListener {

    private String message;
    private Context origin;

    public ConnectDialogListener(Context context, String message){
        this.origin = context;
        this.message = message.toLowerCase();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(!message.equals("cancel")){
            Toast.makeText(origin, "Connecting to " + message, Toast.LENGTH_SHORT).show();
            //Intent connectActivity = new Intent(origin, NFC_Tag_Writer.class);
            //origin.startActivity(connectActivity);
        }
    }
}
