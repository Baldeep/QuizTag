package baldeep.quiztagapp.Listeners;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import baldeep.quiztagapp.Constants.Constants;
import baldeep.quiztagapp.Fragments.InformationDialog;
import baldeep.quiztagapp.Frontend.Shop_Menu;
import baldeep.quiztagapp.Backend.PowerUps;
import baldeep.quiztagapp.R;


public class ShopButtonListener extends Shop_Menu implements View.OnClickListener {

    Bundle arguments;

    public ShopButtonListener(Bundle hintsBundle) {
        this.arguments = hintsBundle;
    }

    @Override
    public void onClick(View v) {
        String message = arguments.getString(Constants.MESSAGE);
        PowerUps pu = (PowerUps) arguments.getSerializable(Constants.POWERUPS);

        Intent update = new Intent();
        update.putExtra(Constants.POWERUPS, pu);

        if(message.equals(Constants.HINTS)){
            // If it's a hints button
            if(pu.getPoints() > pu.getHintsCost()){
                //
                pu.setPoints(pu.getPoints() - pu.getHintsCost());
                pu.setHints(pu.getHints() + 1);
                update();
            } else {
                DialogFragment noPoint = new InformationDialog();
                Bundle noPointBundle = new Bundle();
                noPointBundle.putString(Constants.TITLE, String.valueOf(R.string.not_enough_points));
                noPointBundle.putString(Constants.MESSAGE, String.valueOf(R.string.play_more_earn_points));
                noPoint.setArguments(noPointBundle);
                noPoint.show(getFragmentManager(), Constants.HINTS);
            }
        } else if(message.equals(Constants.SKIPS)){
            if(pu.getPoints() > pu.getSkipsCost()){
                pu.setPoints(pu.getPoints()-pu.getSkipsCost());
                pu.setSkips(pu.getSkips() + 1);
                update();
            } else {
                DialogFragment noPoint = new InformationDialog();
                Bundle noPointBundle = new Bundle();
                noPointBundle.putString(Constants.TITLE, String.valueOf(R.string.not_enough_points));
                noPointBundle.putString(Constants.MESSAGE, String.valueOf(R.string.play_more_earn_points));
                noPoint.setArguments(noPointBundle);
                noPoint.show(getFragmentManager(), Constants.SKIPS);
            }
        }
    }
}
