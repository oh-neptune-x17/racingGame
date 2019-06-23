package com.example.racinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ArrayList;

public class obstaclesManager {
        private Bitmap obstacleTexture;
        private int obstperScreen;
        int screenH;
        int sH;
        int whenBounce = -1;
        int halfS;
        public gameLogic game_panel;

        ArrayList<obstaclesLoader> topwall = null;
        ArrayList<obstaclesLoader> bottomwall = null;

        public obstaclesManager(Bitmap loadedTexture, gameLogic game_panel) {
            obstacleTexture = loadedTexture;
            this.game_panel = game_panel;
        }

        public void setScreen(int width, int height) {
            screenH = height;
            obstperScreen = ((width)/ obstacleTexture.getWidth())+2;
            topwall = new ArrayList<>();
            bottomwall = new ArrayList<>();
            for (int i = 0; i< obstperScreen +1; i++){
                obstaclesLoader obstacleloader = new obstaclesLoader(obstacleTexture, width+200+ obstacleTexture.getWidth()*i, 0);
                obstacleloader.setManager (this);  // manager for top line of obstacles
                topwall.add(obstacleloader);
                obstaclesLoader anotherobstacleloader = new obstaclesLoader(obstacleTexture, width+200+ obstacleTexture.getWidth()*i, 0);
                anotherobstacleloader.setManager (this); // manager for bottom line of obstacles
                bottomwall.add(anotherobstacleloader);
            }
            generateObstacles();
        }

        private void generateObstacles() {
            int h;
            sH = screenH;
            halfS =screenH/2;
            int newsH = screenH * 2/5;
            int gap =  (sH - newsH) / obstperScreen;
            for (int i = 0; i< obstperScreen +1; i++){
                sH -= gap;
                h = topwall.get(i).getBitmap().getHeight()/2;
                topwall.get(i).setY(halfS - sH /2-h);
                bottomwall.get(i).setY(halfS + sH /2+h);
            }
        }
        public void draw(Canvas canvas){
            for (int i = 0; i< obstperScreen +1; i++){
                topwall.get(i).draw(canvas);
                bottomwall.get(i).draw(canvas);
            }
        }
        public void update(float dt){
            for (int i = 0; i< obstperScreen +1; i++){
                topwall.get(i).update(dt, true);
                bottomwall.get(i).update(dt, false);
            }
        }
    }