package baldeep.quiztagapp.backend;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Exceptions.NullObjectException;

public class FileHandler implements Serializable {

    /**
     * http://www.androidinterview.com/android-internal-storage-read-and-write-text-file-example/
     *
     * @param context
     * @param text
     */
    public void writeFile(Context context, String fileName, String text) {

        try {

            FileOutputStream fileout = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(text);
            outputWriter.close();

            //display file saved message
            Log.d("Writing", "Completed.....................");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String readFile(Context context, String fileName) throws FileNotFoundException {
        String fileAsString = "";

        FileInputStream fileIn = context.openFileInput(fileName);
        InputStreamReader InputRead = new InputStreamReader(fileIn);
        BufferedReader br = new BufferedReader(InputRead);

        try {

            String line = "";
            while ((line = br.readLine()) != null) {
                fileAsString += line;
            }
                InputRead.close();
                br.close();
                System.out.println("READING FILE: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "I/O Exception", Toast.LENGTH_SHORT).show();
        }

        return fileAsString;
    }

    /**
     * Parses in JSON file into a QuestionPool to be used for the quiz master
     * <p>
     * GSON tutorial - http://kylewbanks.com/blog/Tutorial-Android-Parsing-JSON-with-GSON
     * <p>
     * File Opening - http://stackoverflow.com/questions/12421814/how-can-i-read-a-text-file-in-android
     *
     * @param context
     */
    public QuestionPool readQuestionPoolFromFile(Context context) throws NullObjectException {
        String fileName = Constants.QUIZFILE;

        Gson gson = new Gson();
        QuestionPool qp = null;

        try {
            // try to read the file
            String qpString = readFile(context, fileName);

            System.out.println("FILE FOUND: " + qpString);

            // if it's there, load up the questionPool
            qp = gson.fromJson(qpString, QuestionPool.class);
        } catch(FileNotFoundException e){
            // If the filr is missing, then write the default quiz from the assets on the file
            qp = readQuestionPoolfromAssets(context);
            writeFile(context, fileName, gson.toJson(qp));
        }
        //
        if (qp != null) {
            if(qp.getQuestionPool() == null){
                qp = readQuestionPoolfromAssets(context);
            }
            QuestionPool questionPool = new QuestionPool(qp.getQuizName(), qp.getQuestionPool(), qp.isRandom());
            Log.d("READING DATA.TXT", "quiz isn't null");
            Log.d("QP", questionPool.getQuizName());
            Log.d("QP", questionPool.getQuestionPoolSize() + "");
            Log.d("QP", questionPool.isRandom() + "");

            for (Question q : qp.getQuestionPool()) {
                q.setHintsArraySize(Constants.HINTSARRAYSIZE);
                q.resetHints();
            }

            return questionPool = new QuestionPool(qp.getQuizName(), qp.getQuestionPool(), qp.isRandom());
        } else {
            qp = readQuestionPoolfromAssets(context);
            throw new NullObjectException("Reading QuestionPool from File");
        }
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

            if (qp != null) {
                questionPool = new QuestionPool(qp.getQuizName(), qp.getQuestionPool(), qp.isRandom());
                Log.d("READING DATA.TXT", "quiz isn't null");
                Log.d("QP", questionPool.getQuizName());
                Log.d("QP", questionPool.getQuestionPoolSize() + "");
                Log.d("QP", questionPool.isRandom() + "");
                for (Question q : qp.getQuestionPool()) {
                    q.setHintsArraySize(Constants.HINTSARRAYSIZE);
                    q.resetHints();
                }

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

        if (questionPool != null) {
            return questionPool;
        } else {
            Log.d("READING DATA.TXT", "quiz is null, returning empty quiz");
            throw new NullObjectException("Couldn't load from assets");
        }
    }

}