package baldeeps.mooseum;

import android.view.View;
import android.widget.TextView;

/**
 * Created by skb12156 on 01/02/2016.
 */
public class PlayButtonListener implements View.OnClickListener {

    TextView textbox;

    public PlayButtonListener(TextView textbox){
        this.textbox = textbox;
    }
    @Override
    public void onClick(View v) {
        textbox.setText("You pressed play!");
    }
}
