package baldeep.quiztagapp.backend;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

import baldeep.quiztagapp.Frontend.Main_Menu;

public class FileReader implements Serializable{

    private QuestionPool qp;

    /**
     * Parses in JSON file into a QuestionPool to be used for the quiz master
     *
     * GSON tutorial - http://kylewbanks.com/blog/Tutorial-Android-Parsing-JSON-with-GSON
     *
     * File Opening - http://stackoverflow.com/questions/12421814/how-can-i-read-a-text-file-in-android
     *
     * @param context
     */
    private void readFile(Context context){
        Gson gson = new Gson();

        AssetManager am = context.getAssets();

        try {
            InputStream in = am.open("data.txt");
            InputStreamReader input = new InputStreamReader(in);

            qp = gson.fromJson(input, QuestionPool.class);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "IO Exception", Toast.LENGTH_SHORT).show();
        }
    }

    public QuestionPool getQuestionPoolFromFile(Context context){
        // Need to set the hints as GSON doesn't call the constructor

        readFile(context);

        for(Question q : qp.getQuestionPool())
            q.resetHints();

        System.out.println("Printing questions **********************************************");
        System.out.println(qp.getQuizName());
        for(Question q : qp.getQuestionPool()) {
            System.out.println(q.getQuestion() + ", " + q.getAnswer() + "\n");

            for(String s : q.getHints()){
                System.out.println(s);
            }
            System.out.println("*************************************");
        }

        QuestionPool questionPool = new QuestionPool(qp.getQuizName(), qp.getQuestionPool());
        return questionPool;
    }
}
