package baldeep.quiztagapp.Listeners;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

import baldeep.quiztagapp.backend.FileHandler;

/**
 * Created by skb12156 on 18/03/2016.
 */
public class QuizTagButtonListener implements View.OnClickListener {

    public QuizTagButtonListener(Activity activity, Bundle arguments){

    }
    @Override
    public void onClick(View v) {
        FileHandler fh = new FileHandler();
        fh.writeFile();
    }
}
