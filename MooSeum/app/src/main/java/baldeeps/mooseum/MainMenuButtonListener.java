package baldeeps.mooseum;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by skb12156 on 01/02/2016.
 */
public class MainMenuButtonListener implements View.OnClickListener {

    String message;

    public MainMenuButtonListener(String msg){
        this.message = message;
    }
    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT);
    }
}
