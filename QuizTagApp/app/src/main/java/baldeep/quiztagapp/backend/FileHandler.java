package baldeep.quiztagapp.backend;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.util.List;

import baldeep.quiztagapp.Frontend.Main_Menu;

public class FileHandler implements Serializable{

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

    /**
     * http://www.androidinterview.com/android-internal-storage-read-and-write-text-file-example/
     * @param context
     * @param text
     */
    public void writeFile(Context context, String text){

        try {
            FileOutputStream fileout = context.openFileOutput("quiz.txt", Context.MODE_PRIVATE);

            Log.d("WRITE ADDRESS", context.getFilesDir().getAbsolutePath());
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(text);
            outputWriter.close();

            //display file saved message
            Toast.makeText(context, "File saved successfully!",
                    Toast.LENGTH_SHORT).show();
            Log.d("Writing", "Completed.....................");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readFileTest(Context context){
        try {
            FileInputStream fileIn= context.openFileInput("quiz.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);

            char[] inputBuffer= new char[100];
            String s="";
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                s +=readstring;
            }
            InputRead.close();
            Log.d("READING", s);
            Toast.makeText(context, s,Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
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

        QuestionPool questionPool = new QuestionPool(qp.getQuizName(), qp.getQuestionPool(), qp.isRandom());
        return questionPool;
    }
}
