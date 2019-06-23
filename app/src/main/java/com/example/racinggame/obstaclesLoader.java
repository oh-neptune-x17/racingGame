package com.example.racinggame;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class obstaclesLoader {
        private Bitmap bitmap;
        private int x;
        private int y;
        obstaclesManager obstManager;
        boolean loaded;

        public obstaclesLoader(Bitmap resourceLoaded, int x, int y) {
            bitmap=resourceLoaded;
            this.x=x;
            this.y=y;
        }

        public void setManager(obstaclesManager obstaclesmanager) {
            obstManager = obstaclesmanager;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setY(int y) {
            this.y=y;
        }

        public void draw(Canvas canvas) {
            canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
        }
        public void update(float dt, boolean stillRunning) {

            if (x<-bitmap.getWidth()){
                if (stillRunning)
                {
                    if (Math.abs(obstManager.whenBounce - obstManager.halfS)<50)
                        loaded = true;
                    if ((obstManager.whenBounce ==-1)|| loaded){
                        obstManager.whenBounce = new Random().nextInt(obstManager.screenH - obstManager.sH /2)+ obstManager.sH /4;
                    }
                    if (obstManager.halfS < obstManager.whenBounce){
                        obstManager.halfS = obstManager.halfS + new Random().nextInt(14);}
                    else{
                        obstManager.halfS = obstManager.halfS - new Random().nextInt(14);}
                    y = obstManager.halfS - obstManager.sH /2  - bitmap.getHeight()/2;
                }
                else
                {
                    y = obstManager.halfS + obstManager.sH /2 + bitmap.getHeight()/2;
                }
                x += bitmap.getWidth()*(obstManager.topwall.size());
            }
            x = (int) (x - obstManager.game_panel.carSpeed *dt);
        }

        public ArrayList<Point> getPointsArray() {
            Point topLeft = new Point();
            Point topRight = new Point();
            Point bottomLeft = new Point();
            Point bottomRight = new Point();

            topLeft.x = x - bitmap.getWidth() / 2;
            topLeft.y = y - bitmap.getHeight() / 2;
            bottomLeft.x = x - bitmap.getWidth() / 2;
            bottomLeft.y = y + bitmap.getHeight() / 2;
            topRight.x = x + bitmap.getWidth() / 2;
            topRight.y = y - bitmap.getHeight() / 2;
            bottomRight.x = x+bitmap.getWidth() / 2;
            bottomRight.y = y+bitmap.getHeight() / 2;

            ArrayList<Point> temp = new ArrayList<>();
            temp.add(topLeft);
            temp.add(topRight);
            temp.add(bottomRight);
            temp.add(bottomLeft);
            return temp;
        }
    }


