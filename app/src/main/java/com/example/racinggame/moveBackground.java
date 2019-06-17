package com.example.racinggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class moveBackground {

        Bitmap bgTexture;
        int x,y;
        int ScreenWidth;
        int bgcnter;
        gameLogic gamePanel;

        public moveBackground(Bitmap bitmap , int Screen_w, gameLogic Game_panel) {
            this.bgTexture = bitmap;
            this.x=0;
            this.y=0;
            this.ScreenWidth=Screen_w;
            bgcnter = ScreenWidth/ bgTexture.getWidth()+1;
            gamePanel = Game_panel;
        }

        public void draw(Canvas canvas){
            for (int i = 0; i< bgcnter +1; i++){
                if (canvas!=null)
                    canvas.drawBitmap(bgTexture, bgTexture.getWidth()*i+x, y, null);
            }
            if (Math.abs(x)> bgTexture.getWidth())
            {
                x = x + bgTexture.getWidth();
            }

        }

        public void update(float dt){
            x = (int) (x - gamePanel.carSpeed *dt);
        }

    }




