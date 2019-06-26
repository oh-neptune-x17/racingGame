package com.example.racinggame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;


public class mainMenu extends Activity {

    Button buttonStart;
    Button buttonExit;
    LinearLayout hub;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hub = findViewById(R.id.hub);
        buttonStart = hub.findViewById(R.id.startBtn);
        buttonExit = hub.findViewById(R.id.exitBtn);

        buttonExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        buttonStart.setOnClickListener(new OnClickListener() {
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