package baldeep.quiztagapp.Frontend;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Fragments.AnswerConfirmationDialog;
import baldeep.quiztagapp.Fragments.ConnectDialog;
import baldeep.quiztagapp.Fragments.ErrorDialog;
import baldeep.quiztagapp.Fragments.InformationDialog;
import baldeep.quiztagapp.Fragments.NFCInfoDialog;
import baldeep.quiztagapp.Fragments.QuitDialog;
import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.R;

/**
 * This class creates wrapper methods to create pop up dialogs
 */
public class DialogCreator {

    public void failedBuyDialog(Activity activity, Bundle arguments){
        String type = arguments.getString(Constants.MESSAGE);
        DialogFragment noPoint = new InformationDialog();
        Bundle noPointBundle = new Bundle();
        noPointBundle.putString(Constants.TITLE, "Not enough points");
        noPointBundle.putString(Constants.MESSAGE, "You don't have enough points to buy more " + type +
                "\nPlay the game to earn more points!");
        noPoint.setArguments(noPointBundle);
        noPoint.show(activity.getFragmentManager(), "No Point");
    }

    public void confirmAnswerDialog(Activity activity, Bundle arguments){
        QuizMaster quizMaster = (QuizMaster) arguments.getSerializable(Constants.QUIZMASTER);
        String answer = arguments.getString(Constants.ANSWER);
        Log.d("DialogCreator", answer);
        String title = activity.getResources().getString(R.string.question_prenumber_text) + " " +
                quizMaster.getCurrentQuestionNumber();

        String text = activity.getResources().getString(R.string.confirm_answer1) + answer
                + activity.getResources().getString(R.string.confirm_answer2);


        Bundle confirmBundle = new Bundle();
        confirmBundle.putSerializable(Constants.QUIZMASTER, quizMaster);
        confirmBundle.putString(Constants.ANSWER, answer);
        confirmBundle.putString(Constants.TITLE, title);
        confirmBundle.putString(Constants.MESSAGE, text);
        DialogFragment df = new AnswerConfirmationDialog();
        df.setArguments(confirmBundle);
        df.show(activity.getFragmentManager(), Constants.ANSWER);
    }

    public void quitConfirmationDialog(FragmentManager fragmentManager, Bundle arguments){
        DialogFragment df = new QuitDialog();
        df.show(fragmentManager, "Quit Dialog");
    }

    public void nfcDisabledDialog(FragmentManager fragmentManager, Bundle arguments){
        DialogFragment df = new NFCInfoDialog();
        Bundle nfcBundle = new Bundle();
        nfcBundle.putString("title", "NFC Hardware");
        nfcBundle.putString("message", "Check NFC is enabled");
        nfcBundle.putString("type", "nfcOff");
        df.setArguments(nfcBundle);
        df.show(fragmentManager, "NFC Info");
    }

    public void smartCaseConnectionDialog(FragmentManager fragmentManager, Bundle arguments){
        DialogFragment df = new ConnectDialog();
        Bundle connectArgs = new Bundle();
        connectArgs.putString("title", "Connect to SmartCase");
        connectArgs.putString("message",
                "Connecting to SmartCase, please place the phone on the case and press OK");
        connectArgs.putString("type", "SmartCase");
        df.setArguments(connectArgs);
        df.show(fragmentManager, "Connect Dialog");
    }


    public void quizFinishedDialog(FragmentManager fragmentManager, Bundle arguments) {
        DialogFragment df = new ConnectDialog();
        Bundle connectArgs = new Bundle();
        connectArgs.putString("title", "Quiz Complete");
        connectArgs.putString("message",
                "Congratulations! You have finished the quiz!");
        df.setArguments(connectArgs);
        df.show(fragmentManager, "Connect Dialog");
    }

    public void errorDialog(FragmentManager fragmentManager, Bundle arguments) {
        DialogFragment df = new ErrorDialog();
        Bundle connectArgs = new Bundle();
        connectArgs.putString(Constants.TITLE, arguments.getString(Constants.TITLE));
        connectArgs.putString(Constants.MESSAGE, arguments.getString(Constants.MESSAGE));
        df.setArguments(connectArgs);
        df.show(fragmentManager, Constants.TITLE);
    }
}
