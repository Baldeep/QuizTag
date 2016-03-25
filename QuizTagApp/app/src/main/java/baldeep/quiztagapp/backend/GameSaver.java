package baldeep.quiztagapp.backend;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import baldeep.quiztagapp.Constants.Constants;

/**
 * Created by Baldeep on 06/03/2016.
 */
public class GameSaver {
    public void saveQuiz(Activity activity, Bundle saveGameData){
        PowerUps pu = (PowerUps) saveGameData.getSerializable(Constants.POWERUPS);
        SharedPreferences saveGame = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor saver = saveGame.edit();
        saver.putString(Constants.QUIZNAME, saveGameData.getString(Constants.QUIZNAME));
        saver.putInt(Constants.CURRENTQUESTIONNO, saveGameData.getInt(Constants.CURRENTQUESTIONNO));
        saver.putInt(Constants.SKIPS, pu.getSkips());
        saver.putInt(Constants.HINTS, pu.getHints());
        saver.putInt(Constants.POINTS, pu.getPoints());

        Log.i("Saving", "Power ups(points: " + pu.getPoints() + ", hints: " + pu.getHints() +
                ", skips: " + pu.getSkips() + ")");

        saver.commit();
    }

    public void savePowerUps(Activity activity, Bundle saveGameData){
        PowerUps pu = (PowerUps) saveGameData.getSerializable(Constants.POWERUPS);
        SharedPreferences saveGame = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor saver = saveGame.edit();
        saver.putInt(Constants.SKIPS, pu.getSkips());
        saver.putInt(Constants.HINTS, pu.getHints());
        saver.putInt(Constants.POINTS, pu.getPoints());

        Log.i("Saving", "Power ups(points: " + pu.getPoints() + ", hints: " + pu.getHints() +
                ", skips: " + pu.getSkips() + ")");

        saver.commit();
    }

    public Bundle loadGame(Activity activity){
        SharedPreferences saveGame = activity.getPreferences(Context.MODE_PRIVATE);
        int points = saveGame.getInt(Constants.POINTS, 120);
        int hints = saveGame.getInt(Constants.HINTS, 10);
        int skips = saveGame.getInt(Constants.SKIPS, 10);
        String quizName = saveGame.getString(Constants.QUIZNAME, " ");
        int currentQuestionNo = saveGame.getInt(Constants.CURRENTQUESTIONNO, 1);

        Log.i("Loading", "Power ups(points: " + points + ", hints: " + hints +
                ", skips: " + skips + ")");

        PowerUps pu = new PowerUps(points, hints, skips);

        Bundle saveGameData = new Bundle();
        saveGameData.putSerializable(Constants.POWERUPS, pu);
        saveGameData.putString(Constants.QUIZNAME, quizName);
        saveGameData.putInt(Constants.CURRENTQUESTIONNO, currentQuestionNo);
        return saveGameData;
    }
}
