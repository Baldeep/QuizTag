package baldeep.quiztagapp.Listeners;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import baldeep.quiztagapp.backend.PowerUps;
import baldeep.quiztagapp.backend.QuizMaster;
import baldeep.quiztagapp.Frontend.Game_Menu;
import baldeep.quiztagapp.Frontend.Question_Screen;

/**
 * Created by skb12156 on 05/02/2016.
 */
public class GameMenuButtonListener implements View.OnClickListener {

    String message;
    PowerUps pu;
    Game_Menu menu;

    public GameMenuButtonListener(Game_Menu menu, String msg, PowerUps pu){
        this.message = msg;
        this.pu = pu;
        this.menu = menu;
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
        System.out.print("Got as far as making a toast");


        if(message.equals("start")){
            Intent questionScreen = new Intent(menu, Question_Screen.class);
            System.out.print("/n Putting PowerUps class " + pu);
            questionScreen.putExtra("PowerUp", pu);

            v.getContext().startActivity(questionScreen);
        }
    }
}
