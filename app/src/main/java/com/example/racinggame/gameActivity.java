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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class gameActivity extends Activity{

        final static int UPDATE_SCORE = 0;
        final static int DEATH = 1;
        final static int LOSE = 2;

        View pauseButton;
        View pauseMenu;
        View winInfo;
        View lostInfo;

        RelativeLayout mainGameLay;
        gameLogic gameWindow;
        TextView txt;
        int get_coins=0;
        int score=0;

        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==UPDATE_SCORE){

                    coinGatheredLogic();
                }
                if (msg.what==DEATH){
                    postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Message msg = handler.obtainMessage();
                            msg.what = LOSE;
                            handler.sendMessage(msg);

                        }
                    }, 3000);
                }
                if (msg.what==LOSE){
                    loseLogic();
                }

                super.handleMessage(msg);
            }
        };

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.game_layout);
            mainGameLay = findViewById(R.id.main_game_rl);
            DisplayMetrics dm = new DisplayMetrics();
            this.getWindowManager().getDefaultDisplay().getMetrics(dm);

            final int heightS = dm.heightPixels;
            final int widthS = dm.widthPixels;
            gameWindow = new gameLogic(getApplicationContext(),this,widthS, heightS);
            mainGameLay.addView(gameWindow);


            RelativeLayout RR = new RelativeLayout(this);
            RR.setBackgroundResource(R.drawable.btn);
            RR.setGravity(Gravity.CENTER);
            mainGameLay.addView(RR,380,160);
            RR.setX(0);
            txt= new TextView(this);
            txt.setTextColor(Color.WHITE);
            txt.setText("Score: " + score);
            RR.addView(txt);

            LayoutInflater myInflater = (LayoutInflater) getApplicationContext().getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
            pauseButton = myInflater.inflate(R.layout.pause_layout, mainGameLay, false);
            pauseButton.setX(widthS-250);
            pauseButton.setY(0);
            mainGameLay.addView(pauseButton);

            ImageView pauseImage = pauseButton.findViewById(R.id.PauseImage);
            pauseButton.setOnTouchListener(new buttonTouched(pauseImage));
            pauseButton.setOnClickListener(pauseClicked);
            pauseButton.getLayoutParams().height=250;
            pauseButton.getLayoutParams().width=250;

            pauseMenu = myInflater.inflate(R.layout.onpausemenu_layout, mainGameLay, false);
            mainGameLay.addView(pauseMenu);
            pauseMenu.setVisibility(View.GONE);

            ImageView continueButton = pauseMenu.findViewById(R.id.imCont);
            ImageView toMainMenu = pauseMenu.findViewById(R.id.toMain);
            continueButton.setOnTouchListener(new buttonTouched(continueButton));
            continueButton.setOnClickListener(continueClicked);
            toMainMenu.setOnTouchListener(new buttonTouched(toMainMenu));
            toMainMenu.setOnClickListener(this.toMainMenu);

            winInfo = myInflater.inflate(R.layout.win_layout, mainGameLay, false);
            mainGameLay.addView(winInfo);
            ImageView gotoMain = winInfo.findViewById(R.id.toMain);
            gotoMain.setOnTouchListener(new buttonTouched(gotoMain));
            gotoMain.setOnClickListener(this.toMainMenu);
            winInfo.setVisibility(View.GONE);

            lostInfo = myInflater.inflate(R.layout.lose_layout, mainGameLay, false);
            mainGameLay.addView(lostInfo);
            ImageView lostContinuation = lostInfo.findViewById(R.id.toMain);
            lostContinuation.setOnTouchListener(new buttonTouched(lostContinuation));
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
            pauseButton.setVisibility(View.VISIBLE);
            gameWindow.gamePaused =false;
        }
    };
    OnClickListener pauseClicked =new OnClickListener() {

        @Override
        public void onClick(View v) {
            pauseButton.setVisibility(View.GONE);
            pauseMenu.setVisibility(View.VISIBLE);
            gameWindow.gamePaused =true;
        }
    };

        @Override
        public void onBackPressed() {
            pauseButton.setVisibility(View.GONE);
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
            gameWindow.gamePaused =true;
            winInfo.setVisibility(View.VISIBLE);

        }

    }


