package com.example.racinggame;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class carClass {
        private Bitmap bitmap;
        private int x;
        private int y;
        private int screenHeight;
        boolean death;
        boolean screenTouched;
        float verticalStep;


        public carClass(Bitmap decodeResource, int x, int y, int screenHeight) {
            this.bitmap = decodeResource;
            this.x = x;
            this.y = y;
            death=false;
            this.screenHeight = screenHeight;
            verticalStep = 0;
        }

        public void draw(Canvas canvas){
            if (!death) {
                canvas.drawBitmap(bitmap, x - bitmap.getWidth()/2, y - bitmap.getHeight()/2, null);
            }

        }

        public void update(float dt){
                verticalStep += screenHeight /2 * dt;
                if (screenTouched)
                   verticalStep -= screenHeight * dt * 2;
                y+= verticalStep * dt;
            }

        public boolean collision(Point obstacleTopLeft, Point obstacleTopRight, Point obstacleBottomRight, Point obstacleBottomLeft){
            Point topLeft = new Point(x - bitmap.getWidth() / 2, y - bitmap.getHeight() / 2);
            Point bottomRight = new Point(x+bitmap.getWidth() / 2, y+bitmap.getHeight() / 2);

            ArrayList<Point> obstPoints = new ArrayList<>();
            obstPoints.add(obstacleTopLeft);
            obstPoints.add(obstacleTopRight);
            obstPoints.add(obstacleBottomLeft);
            obstPoints.add(obstacleBottomRight);

            for (int i = 0; i<obstPoints.size(); i++){
                if (bottomRight.x>=obstPoints.get(i).x)
                    if (topLeft.x<=obstPoints.get(i).x)
                        if(obstPoints.get(i).y>=topLeft.y)
                            if(obstPoints.get(i).y<=bottomRight.y)
                                return true;
            }
            return false;
        }
    }
