package baldeep.quiztagapp.Backend;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Baldeep on 06/03/2016.
 */
public class GameSaver {
    public void saveQuiz(Activity activity, Bundle saveGameData){
        PowerUps pu = (PowerUps) saveGameData.getSerializable("powerUps");
        SharedPreferences saveGame = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor saver = saveGame.edit();
        saver.putString("quizName", saveGameData.getString("quizName"));
        saver.putInt("currentQuestionNo", saveGameData.getInt("currentQuestionNo"));
        saver.putInt("skips", pu.getSkips());
        saver.putInt("hints", pu.getHints());
        saver.putInt("points", pu.getPoints());

        Log.i("Saving", "Power ups(points: " + pu.getPoints() + ", hints: " + pu.getHints() +
                ", skips: " + pu.getSkips() + ")");

        saver.commit();
    }

    public void savePowerUps(Activity activity, Bundle saveGameData){
        PowerUps pu = (PowerUps) saveGameData.getSerializable("powerUps");
        SharedPreferences saveGame = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor saver = saveGame.edit();
        saver.putInt("skips", pu.getSkips());
        saver.putInt("hints", pu.getHints());
        saver.putInt("points", pu.getPoints());

        Log.i("Saving", "Power ups(points: " + pu.getPoints() + ", hints: " + pu.getHints() +
                ", skips: " + pu.getSkips() + ")");

        saver.commit();
    }

    public Bundle loadGame(Activity activity){
        SharedPreferences saveGame = activity.getPreferences(Context.MODE_PRIVATE);
        int points = saveGame.getInt("points", 120);
        int hints = saveGame.getInt("hints", 10);
        int skips = saveGame.getInt("skips", 10);
        String quizName = saveGame.getString("quizName", " ");
        int currentQuestionNo = saveGame.getInt("currentQuestionNo", 1);

        Log.i("Loading", "Power ups(points: " + points + ", hints: " + hints +
                ", skips: " + skips + ")");

        PowerUps pu = new PowerUps(points, hints, skips);

        Bundle saveGameData = new Bundle();
        saveGameData.putSerializable("powerUps", pu);
        saveGameData.putString("quizName", quizName);
        saveGameData.putInt("currentQuestionNo", currentQuestionNo);
        return saveGameData;
    }
}
