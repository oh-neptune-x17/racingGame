package com.example.racinggame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class mainThread extends Thread {
    private float dt;
    private SurfaceHolder surfaceHolder;
    private gameLogic gamePanel;
    private boolean running;

    public mainThread(SurfaceHolder holder, gameLogic gamePanel) {
        this.surfaceHolder = holder;
        this.gamePanel = gamePanel;
        dt=0;
    }

    void setRunning(boolean running){
        this.running = running;

    }

    @Override
    public void run() {
        Canvas canvas;
        while (running) {
            if (!gamePanel.gamePaused){
                long startTime = System.currentTimeMillis();
                canvas = null;
                try{
                    canvas =this.surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        gamePanel.updateGame(dt);
                        gamePanel.drawGame(canvas);
                    }
                }
                finally{
                    if (canvas!=null)
                        surfaceHolder.unlockCanvasAndPost(canvas);
                }
                long endTime = System.currentTimeMillis();
                dt = (endTime-startTime)/1000.f;
            }
        }
    }
}
