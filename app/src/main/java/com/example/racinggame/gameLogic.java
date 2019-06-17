package com.example.racinggame;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class gameLogic extends SurfaceView implements SurfaceHolder.Callback {

    public mainThread thread;
    public boolean gamePaused;
    private moveBackground background;
    private carClass carObject;
    private obstaclesManager barrelsManager;
    private bonusCoins coin;
    public float carSpeed;
    public int screenWidth;
    public int screenHeight;
    public gameActivity game;

    public gameLogic(Context context, gameActivity game, int screenWidth, int screenHeight) {
        super(context);

        getHolder().addCallback(this);
        this.game = game;
        thread = new mainThread(getHolder(), this);
        background = new moveBackground(BitmapFactory.decodeResource(getResources(), R.drawable.gamebackground), screenWidth, this);
        barrelsManager = new obstaclesManager(BitmapFactory.decodeResource(getResources(), R.drawable.obstaclesmall), this);
        barrelsManager.setScreen(screenWidth, screenHeight);
        carObject = new carClass(BitmapFactory.decodeResource(getResources(), R.drawable.carsmall), 100, screenHeight/3, screenWidth, screenHeight);
        coin = new bonusCoins(BitmapFactory.decodeResource(getResources(), R.drawable.coing), -200, -200);

        coin.setObstaclesManager(barrelsManager);
        setFocusable(true);
        carSpeed = screenWidth / 2.f;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            carObject.up = true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            carObject.up = false;
        }
        return true;
    }

    void drawGame(Canvas canvas) {
        if (!gamePaused)
            if (canvas != null) {
                canvas.drawColor(Color.BLACK);
                background.draw(canvas);
                coin.draw(canvas);
                carObject.draw(canvas);
                barrelsManager.draw(canvas);
            }
    }

    void updateGame(float dt) {
        carObject.update(dt);
        if (!carObject.death) {
            background.update(dt);
            coin.update(dt);
            barrelsManager.update(dt);
            ArrayList<Point> coin_point = new ArrayList<Point>(coin.getPointsArray());
            if (carObject.collision(coin_point.get(0), coin_point.get(1), coin_point.get(2), coin_point.get(3))) {
                coin.setX(-200);
                coin.setY(-200);
                Message msg = barrelsManager.game_panel.game.handler.obtainMessage();
                //  msg.what = 0;
                barrelsManager.game_panel.game.handler.sendMessage(msg);
            }

            for (int i = 0; i < barrelsManager.topwall.size(); i++) {
                ArrayList<Point> temp = new ArrayList<Point>(barrelsManager.topwall.get(i).getPointsArray());
                ArrayList<Point> temp2 = new ArrayList<Point>(barrelsManager.bottomwall.get(i).getPointsArray());
                if ((carObject.collision(temp.get(0), temp.get(1), temp.get(2), temp.get(3))) || (carObject.collision(temp2.get(0), temp2.get(1), temp2.get(2), temp2.get(3)))) {
                    carObject.death = true;
                    Message msg = barrelsManager.game_panel.game.handler.obtainMessage();
                    msg.what = 1;
                    barrelsManager.game_panel.game.handler.sendMessage(msg);
                }
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }
}
