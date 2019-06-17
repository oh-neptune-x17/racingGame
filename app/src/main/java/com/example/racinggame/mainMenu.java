package com.example.racinggame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class mainMenu extends Activity {
    RelativeLayout startButton;
    RelativeLayout exitButton;
    ImageView startImgButton;
    ImageView exitImgButton;
    TextView txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start_button);
        exitButton = findViewById(R.id.exit_button);
        startImgButton = findViewById(R.id.img_button2);
        exitImgButton =  findViewById(R.id.img_button2);
        txt = findViewById(R.id.start_gameText);

        startButton.setOnTouchListener(new buttonTouched(startImgButton));
        exitButton.setOnTouchListener(new buttonTouched(exitImgButton));

        exitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(mainMenu.this, gameActivity.class);
                startActivity(myIntent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}