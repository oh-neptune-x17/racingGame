package com.example.racinggame;
import java.util.ArrayList;
import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
public class bonusCoins {
        private Bitmap bitmap;
        private int x;
        private int y;
        obstaclesManager obstaclesManager;

        public bonusCoins(Bitmap decodeResource, int x, int y) {
            this.bitmap = decodeResource;
            this.x = x;
            this.y = y;
        }

         public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
         }

         public void update(float dt){
        if (x <-obstaclesManager.game_panel.screenWidth /4)
        {
            x= obstaclesManager.game_panel.screenWidth +bitmap.getWidth();
            Random rand = new Random();
            y=rand.nextInt(obstaclesManager.sH)+ obstaclesManager.halfS - obstaclesManager.sH/2;
        }

        x -= obstaclesManager.game_panel.carSpeed * dt;
        }

        public ArrayList<Point> getPointsArray() {
            Point bottomLeft = new Point();
            Point bottomRight = new Point();
            Point topLeft = new Point();
            Point topRight = new Point();
            bottomLeft.x = x - bitmap.getWidth() / 2;
            bottomLeft.y = y + bitmap.getHeight() / 2;
            topLeft.x = x - bitmap.getWidth() / 2;
            topLeft.y = y - bitmap.getHeight() / 2;
            bottomRight.x = x + bitmap.getWidth() / 2;
            bottomRight.y = y + bitmap.getHeight() / 2;
            topRight.x = x + bitmap.getWidth() / 2;
            topRight.y = y - bitmap.getHeight() / 2;

            ArrayList<Point> temp = new ArrayList<Point>();
            temp.add(topLeft);
            temp.add(topRight);
            temp.add(bottomRight);
            temp.add(bottomLeft);
            return temp;
        }

    public void setObstaclesManager(obstaclesManager mgr){
        obstaclesManager = mgr;
    }
        public void setX(int x) {
            this.x=x;
        }
        public void setY(int y) {
            this.y=y;
        }
    }

