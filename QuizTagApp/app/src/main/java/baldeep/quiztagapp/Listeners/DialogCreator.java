package baldeep.quiztagapp.Listeners;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;

import baldeep.quiztagapp.Fragments.AnswerConfirmationDialog;
import baldeep.quiztagapp.Fragments.ConnectDialog;
import baldeep.quiztagapp.Fragments.InformationDialog;
import baldeep.quiztagapp.Fragments.NFCInfoDialog;
import baldeep.quiztagapp.Fragments.QuitDialog;
import baldeep.quiztagapp.backend.QuizMaster;

/**
 * This class creates wrapper methods to create pop up dialogs
 */
public class DialogCreator {

    public void failedBuyDialog(FragmentManager fragmentManager, Bundle arguments){
        String type = arguments.getString("message");
        DialogFragment noPoint = new InformationDialog();
        Bundle noPointBundle = new Bundle();
        noPointBundle.putString("title", "Not enough points");
        noPointBundle.putString("message", "You don't have enough points to buy more " + type +
                "\nPlay the game to earn more points!");
        noPoint.setArguments(noPointBundle);
        noPoint.show(fragmentManager, "No Point");
    }

    public void confirmAnswerDialog(FragmentManager fragmentManager, Bundle arguments){
        QuizMaster quizMaster = (QuizMaster) arguments.getSerializable("quizMaster");
        String answer = arguments.getString("answer");

        Bundle confirmBundle = new Bundle();
        confirmBundle.putSerializable("quizMaster", quizMaster);
        confirmBundle.putString("answer", answer);

        String title = "Question " + quizMaster.getCurrentQuestionNumber();
        confirmBundle.putString("title", title);

        String text = "You have selected:\n\n\t\t\t" + answer
                + "\n\nIs this your final answer?";
        confirmBundle.putString("message", text);

        DialogFragment df = new AnswerConfirmationDialog();
        df.setArguments(confirmBundle);
        df.show(fragmentManager, "Confirm Dialog");
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
}
