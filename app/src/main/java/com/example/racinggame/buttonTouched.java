package com.example.racinggame;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class buttonTouched{// implements View.OnTouchListener {
    ImageView buttonImg;

    public buttonTouched(ImageView imageButton) {
        buttonImg = imageButton;
    }
/*   // animations not fixed
    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                buttonImg.setImageResource(R.drawable.button2);
                break;
            case MotionEvent.ACTION_UP:
                buttonImg.setImageResource(R.drawable.btn);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                buttonImg.setImageResource(R.drawable.btn);
                break;
            default:
                break;
        }
        return false;
    }
*/
}