package baldeep.quiztagapp.backend;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import baldeep.quiztagapp.Frontend.Main_Menu;

public class FileReader {

    /**
     * Parses in JSON file into a QuestionPool to be used for the quiz master
     *
     * GSON tutorial - http://kylewbanks.com/blog/Tutorial-Android-Parsing-JSON-with-GSON
     *
     * File Opening - http://stackoverflow.com/questions/12421814/how-can-i-read-a-text-file-in-android
     *
     * @param context
     */
    public static void readFile(Context context){
        Gson gson = new Gson();

        AssetManager am = context.getAssets();

        try {
            InputStream in = am.open("data.txt");
            InputStreamReader input = new InputStreamReader(in);

            QuestionPool qp = gson.fromJson(input, QuestionPool.class);

            List<Question> questions = qp.getQuestionPool();

            for(Question q : questions) {
                System.out.print(q.getQuestion());
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "IO Exception", Toast.LENGTH_SHORT).show();
        }
    }
}
