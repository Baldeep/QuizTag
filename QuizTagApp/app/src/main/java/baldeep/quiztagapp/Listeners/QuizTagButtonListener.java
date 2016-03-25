package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import baldeep.quiztagapp.Backend.FileHandler;

/**
 * Created by skb12156 on 18/03/2016.
 */
public class QuizTagButtonListener implements View.OnClickListener {

    Activity activity;
    Bundle arguments;
    public QuizTagButtonListener(Activity activity, Bundle arguments){
        this.activity = activity;
        this.arguments = arguments;
    }

    @Override
    public void onClick(View v) {
        FileHandler fh = new FileHandler();
        fh.writeFile(activity, arguments.getString("string"));
    }
}
