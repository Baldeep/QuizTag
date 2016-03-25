package baldeep.quiztagapp.backend;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;

import baldeep.quiztagapp.Exceptions.NullObjectException;

public class FileHandler implements Serializable{

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

    private String readFile(Context context){
        String fileAsString = "";
        try {
            FileInputStream fileIn= context.openFileInput("quiz.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            BufferedReader br = new BufferedReader(InputRead);

            String line = "";
            while((line = br.readLine()) != null){
                fileAsString += line;
            }

            InputRead.close();
            br.close();
            Log.d("READING", fileAsString);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileAsString;
    }

    /**
     * Parses in JSON file into a QuestionPool to be used for the quiz master
     *
     * GSON tutorial - http://kylewbanks.com/blog/Tutorial-Android-Parsing-JSON-with-GSON
     *
     * File Opening - http://stackoverflow.com/questions/12421814/how-can-i-read-a-text-file-in-android
     *
     * @param context
     */
    public QuestionPool getQuestionPoolFromFile(Context context){
        Gson gson = new Gson();
        QuestionPool qp = null;

        String qpAsString = readFile(context);

        // if qpAsString is an empty strng gson will create a non null empty class object.
        try {
            qp = gson.fromJson(qpAsString, QuestionPool.class);

            if (qp != null) {
                Log.d("READING QUIZ.TXT", "quiz isn't null");
                // Gson doesn't use the constructor
                for (Question q : qp.getQuestionPool())
                    q.resetHints();

                System.out.println("Printing questions **********************************************");
                System.out.println(qp.getQuizName());
                for (Question q : qp.getQuestionPool()) {
                    System.out.println(q.getQuestion() + ", " + q.getAnswer() + "\n");
            /*for(String s : q.getHints()){
                System.out.println(s);
            }*/
                    System.out.println("*************************************");
                }

                QuestionPool questionPool = new QuestionPool(qp.getQuizName(), qp.getQuestionPool(), qp.isRandom());
                return questionPool;
            }
        } catch(JsonParseException e){
            Toast.makeText(context, "File JSon format error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return qp;
    }

    /**
     * A sample quiz is stored in the assets, this will be read and produced if there is no saved
     * quiz file.
     */
    public QuestionPool readQuestionPoolfromAssets(Context context) throws NullObjectException {
        Gson gson = new Gson();

        QuestionPool questionPool = null;

        AssetManager am = context.getAssets();

        try {
            InputStream in = am.open("data.txt");
            InputStreamReader input = new InputStreamReader(in);

            QuestionPool qp = gson.fromJson(input, QuestionPool.class);

            if(qp!= null) {
                questionPool = new QuestionPool(qp.getQuizName(), qp.getQuestionPool(), qp.isRandom());
                Log.d("READING DATA.TXT", "quiz isn't null");
                Log.d("QP", questionPool.getQuizName());
                Log.d("QP", questionPool.getQuestionPoolSize() + "");
                Log.d("QP", questionPool.isRandom() + "");
                for (Question q : qp.getQuestionPool())
                    q.resetHints();

                questionPool = new QuestionPool(qp.getQuizName(), qp.getQuestionPool(), qp.isRandom());
            }

            System.out.println("Printing questions **********************************************");
            System.out.println(qp.getQuizName());
            for (Question q : qp.getQuestionPool()) {
                System.out.println(q.getQuestion() + ", " + q.getAnswer() + "\n");
            /*for(String s : q.getHints()){
                System.out.println(s);
            }*/
                System.out.println("*************************************");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "IO Exception", Toast.LENGTH_SHORT).show();
        }

        if(questionPool!= null){
            return questionPool;
        } else {
            Log.d("READING DATA.TXT", "quiz is null, returning empty quiz");
            throw new NullObjectException("Couldn't load from assets");
        }
    }

    /**
     * A sample quiz is stored in the assets, this will be read and produced if there is no saved
     * quiz file.
     */
    public QuestionPool readQuestionPoolfromAssetss(Context context) throws NullObjectException {
        Gson gson = new Gson();

        QuestionPool qp = null;

        AssetManager am = context.getAssets();

        try {
            InputStream in = am.open("data.txt");
            InputStreamReader input = new InputStreamReader(in);

            qp = gson.fromJson(input, QuestionPool.class);

            System.out.println("Printing questions **********************************************");
            System.out.println(qp.getQuizName());
            for (Question q : qp.getQuestionPool()) {
                System.out.println(q.getQuestion() + ", " + q.getAnswer() + "\n");
            /*for(String s : q.getHints()){
                System.out.println(s);
            }*/
                System.out.println("*************************************");
            }
         } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "IO Exception", Toast.LENGTH_SHORT).show();
        }

        if(qp!= null){
            Log.d("READING DATA.TXT", "quiz isn't null");
            Log.d("QP", qp.getQuizName());
            Log.d("QP", qp.getQuestionPoolSize() + "");
            Log.d("QP", qp.isRandom() + "");
            for (Question q : qp.getQuestionPool())
                q.resetHints();

            QuestionPool questionPool = new QuestionPool(qp.getQuizName(), qp.getQuestionPool(), qp.isRandom());
            return questionPool;
        } else {
            Log.d("READING DATA.TXT", "quiz is null, returning empty quiz");
            throw new NullObjectException("Couldn't load from assets");
        }
    }

}
