package com.example.racinggame;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class gameActivity extends Activity{

        final static int scoreUpdate = 0;
        final static int crashed = 1;
        final static int lost = 2;

        View pauseMenu;
        View winInfo;
        View lostInfo;

        RelativeLayout mainGameLay;
        gameLogic gameWindow;
        TextView txt;
        TextView tx2;
        int get_coins=0;
        int score=0;

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what== scoreUpdate){
                    coinGatheredLogic();
                }
                if (msg.what== crashed){
                    postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Message msg = handler.obtainMessage();
                            msg.what = lost;
                            handler.sendMessage(msg);
                        }
                    }, 3000);
                }
                if (msg.what== lost){
                    loseLogic();
                }
                super.handleMessage(msg);
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.game_layout);
            mainGameLay = findViewById(R.id.gamescreen);
            DisplayMetrics dm = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(dm);

            final int heightS = dm.heightPixels;
            final int widthS = dm.widthPixels;
            gameWindow = new gameLogic(getApplicationContext(),this,widthS, heightS);

            mainGameLay.addView(gameWindow);
            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.setBackgroundResource(R.drawable.btn);
            relativeLayout.setGravity(Gravity.CENTER);
            int scoreWidth = widthS/6;
            int scoreHeight = heightS/6;
            mainGameLay.addView(relativeLayout,scoreWidth, scoreHeight);
            relativeLayout.setX(0);
            txt = new TextView(this);
            txt.setTextColor(Color.WHITE);
            txt.setText(getResources().getString(R.string.score) + score);

            relativeLayout.addView(txt);

            LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);

            pauseMenu = myInflater.inflate(R.layout.onpausemenu_layout, mainGameLay, false);
            mainGameLay.addView(pauseMenu);
            pauseMenu.setVisibility(View.GONE);



            Button continueButton = pauseMenu.findViewById(R.id.imCont);
            continueButton.setOnClickListener(continueClicked);

            Button toMainMenu = pauseMenu.findViewById(R.id.toMain);
            toMainMenu.setOnClickListener(this.toMainMenu);

            winInfo = myInflater.inflate(R.layout.win_layout, mainGameLay, false);
            mainGameLay.addView(winInfo);
            Button gotoMain = winInfo.findViewById(R.id.toMain);
            gotoMain.setOnClickListener(this.toMainMenu);
            winInfo.setVisibility(View.GONE);

            lostInfo = myInflater.inflate(R.layout.lose_layout, mainGameLay, false);
            mainGameLay.addView(lostInfo);
            Button lostContinuation = lostInfo.findViewById(R.id.toMain);
            lostContinuation.setOnClickListener(this.toMainMenu);
            lostInfo.setVisibility(View.GONE);
        }



    OnClickListener toMainMenu = new OnClickListener() {

        @Override
        public void onClick(View v) {
            gameWindow.thread.setRunning(false);
            gameActivity.this.finish();
        }
    };

    OnClickListener continueClicked = new OnClickListener() {
        @Override
        public void onClick(View v) {
            pauseMenu.setVisibility(View.GONE);
            gameWindow.gamePaused =false;
        }
    };

        @Override
        public void onBackPressed() {
            pauseMenu.setVisibility(View.VISIBLE);
            gameWindow.gamePaused =true;
        }

        @Override
        protected void onStop() {
            super.onStop();
        }

        protected void loseLogic() {
            gameWindow.gamePaused =true;
            lostInfo.setVisibility(View.VISIBLE);
            tx2 = lostInfo.findViewById(R.id.textView1);
            tx2.setText("You've scored "+ score + " points");
        }

        protected void coinGatheredLogic() {
            get_coins++;
            score+=10;
            txt.setText("Score: " + score);
            if (get_coins==10){
                winLogic();
            }
        }
        private void winLogic() {
            gameWindow.gamePaused = true;
            winInfo.setVisibility(View.VISIBLE);
        }
    }


