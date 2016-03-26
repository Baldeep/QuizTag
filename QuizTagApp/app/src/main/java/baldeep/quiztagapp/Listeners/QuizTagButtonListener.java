package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import baldeep.quiztagapp.Frontend.Quiz_Tag_Screen;
import baldeep.quiztagapp.backend.FileHandler;
import baldeep.quiztagapp.Constants.Constants;

/**
 * Created by skb12156 on 18/03/2016.
 */
public class QuizTagButtonListener implements View.OnClickListener {

    Bundle arguments;
    Activity activity;

    public QuizTagButtonListener(Activity activity, Bundle arguments){
        this.arguments = arguments;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        FileHandler fh = new FileHandler();
        fh.writeFile(activity, arguments.getString(Constants.MESSAGE));
    }
}
