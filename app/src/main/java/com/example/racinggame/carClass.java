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
        public ArrayList<Bitmap> collisions = null;
        boolean death;
        boolean up;
        float verticalSpeed;
        float animTime=0;
        float totalAnimTime = 1;
        float numFrames;

        public carClass(Bitmap decodeResource, int x, int y, int screenWidth, int screenheight) {
            this.bitmap = decodeResource;
            this.x = x;
            this.y = y;
            death=false;
            screenHeight = screenheight;
            verticalSpeed = 0;
        }

        public void draw(Canvas canvas){
            if (!death) {
                canvas.drawBitmap(bitmap, x - bitmap.getWidth()/2, y - bitmap.getHeight()/2, null);
            }
            else {
                int i = (int) (animTime/totalAnimTime*numFrames);
                if (i<numFrames)
                    canvas.drawBitmap(collisions.get(i), x - bitmap.getWidth()/2, y - bitmap.getHeight()/2, null);
            }
        }

        public void update(float dt){
                verticalSpeed += screenHeight /2 * dt;
                if (up)
                   verticalSpeed -= screenHeight* dt*2;
                 y+= verticalSpeed* dt;
            }

        public boolean collision(Point obstacleTopLeft, Point obstacleTopRight, Point obstacleBottomRight, Point obstacleBottomLeft){
            Point topLeft = new Point();
            Point topRight = new Point();
            Point bottomLeft = new Point();
            Point bottomRight = new Point();

            ArrayList<Point> obstPoints = new ArrayList<Point>();
            obstPoints.add(obstacleTopLeft);
            obstPoints.add(obstacleTopRight);
            obstPoints.add(obstacleBottomLeft);
            obstPoints.add(obstacleBottomRight);


            getPoint(topLeft,topRight,bottomLeft,bottomRight);

            for (int i = 0; i<obstPoints.size(); i++){
                if (bottomRight.x>=obstPoints.get(i).x)
                    if (topLeft.x<=obstPoints.get(i).x)
                        if(obstPoints.get(i).y>=topLeft.y)
                            if(obstPoints.get(i).y<=bottomRight.y)
                                return true;
            }
            return false;
        }

        private void getPoint(Point topLeft, Point topRight, Point bottomLeft, Point bottomRight) {
            topLeft.x = x-bitmap.getWidth() / 2;
            topLeft.y = y - bitmap.getHeight() / 2;

            topRight.x = x+bitmap.getWidth() / 2;
            topRight.y = y - bitmap.getHeight() / 2;

            bottomLeft.x = x-bitmap.getWidth() / 2;
            bottomLeft.y = y + bitmap.getHeight() / 2;

            bottomRight.x = x+bitmap.getWidth() / 2;
            bottomRight.y = y+bitmap.getHeight() / 2;

        }
    }
