package baldeep.quiztagapp.Listeners;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import baldeep.quiztagapp.Fragments.InformationDialog;
import baldeep.quiztagapp.Frontend.Shop_Menu;
import baldeep.quiztagapp.backend.PowerUps;

/**
 * Created by Baldeep on 23/02/2016.
 */
public class ShopButtonListener implements View.OnClickListener {

    Bundle arguments;
    Shop_Menu menu;

    public ShopButtonListener(Shop_Menu shop_menu, Bundle hintsBundle) {
        this.menu = shop_menu;
        this.arguments = hintsBundle;
    }

    @Override
    public void onClick(View v) {
        String message = arguments.getString("message");
        PowerUps pu = (PowerUps) arguments.getSerializable("powerUps");

        Intent update = new Intent();
        update.putExtra("powerUps", pu);

        if(message.equals("hints")){
            if(pu.getPoints() > pu.getHintsCost()){
                pu.setPoints(pu.getPoints() - pu.getHintsCost());
                pu.setHints(pu.getHints() + 1);
                menu.update();
            } else {
                DialogFragment noPoint = new InformationDialog();
                Bundle noPointBundle = new Bundle();
                noPointBundle.putString("title", "Not enough points");
                noPointBundle.putString("message", "You don't have enough points to buy more hints" +
                        "\nPlay the game to earn more points!");
                noPoint.setArguments(noPointBundle);
                noPoint.show(menu.getFragmentManager(), "No Point");
            }
        } else if(message.equals("skips")){
            if(pu.getPoints() > pu.getSkipsCost()){
                pu.setPoints(pu.getPoints()-pu.getSkipsCost());
                pu.setSkips(pu.getSkips() + 1);
                menu.update();
            } else {
                DialogFragment noPoint = new InformationDialog();
                Bundle noPointBundle = new Bundle();
                noPointBundle.putString("title", "Not enough points");
                noPointBundle.putString("message", "You don't have enough points to buy more skips" +
                        "\nPlay the game to earn more points!");
                noPoint.setArguments(noPointBundle);
                noPoint.show(menu.getFragmentManager(), "No Point");
            }
        }
    }
}
