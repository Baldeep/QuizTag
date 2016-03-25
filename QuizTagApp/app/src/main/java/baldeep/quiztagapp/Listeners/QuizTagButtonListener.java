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
public class QuizTagButtonListener extends Quiz_Tag_Screen implements View.OnClickListener {

    Bundle arguments;
    public QuizTagButtonListener(Bundle arguments){
        this.arguments = arguments;
    }

    @Override
    public void onClick(View v) {
        FileHandler fh = new FileHandler();
        fh.writeFile(this, arguments.getString(Constants.MESSAGE));
    }
}
