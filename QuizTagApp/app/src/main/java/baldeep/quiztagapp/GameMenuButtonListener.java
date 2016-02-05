package baldeep.quiztagapp;

import android.content.Intent;
import android.view.View;

/**
 * Created by skb12156 on 05/02/2016.
 */
public class GameMenuButtonListener implements View.OnClickListener {

    String message;

    public GameMenuButtonListener(String msg){
        this.message = msg;
    }


    @Override
    public void onClick(View v) {
        //Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();

        if(message.equals("start")){
            Intent gameScreen = new Intent(v.getContext(), Game_Menu.class);
            gameScreen.putExtra("playMessage", "play was pressed");

            v.getContext().startActivity(gameScreen);
        }
    }
}
