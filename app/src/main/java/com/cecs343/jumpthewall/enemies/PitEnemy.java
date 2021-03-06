package com.cecs343.jumpthewall.enemies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;

import com.cecs343.jumpthewall.Animation;
import com.cecs343.jumpthewall.GameObject;
import com.cecs343.jumpthewall.GamePanel;
import com.cecs343.jumpthewall.R;
import com.cecs343.jumpthewall.enemies.Enemy;

/**
 * Created by docbot on 11/26/16.
 */
public class PitEnemy extends Enemy {
    private Animation ani = new Animation();
    private Bitmap spritesheet;
    private boolean hasJumped;

    public PitEnemy(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super(res,x,y,w,h,numFrames);

        dy = 0;
        hasJumped = false;
        setObeyGravity(false);


    }

    public void update() {
        super.update();

        if (getX() < GamePanel.gameWidth && !hasJumped) {
            //dy = -36;
            hasJumped = true;
        }

        if (hasJumped) {
            //dy += 1.5;
            int topOffset = 64;
            int leftOffset = 64;
            int c = GamePanel.gameHeight-topOffset;
            int xx = (GamePanel.gameWidth-leftOffset)/2;
            double a = c/(Math.pow(xx,2));
            double currentX = x-xx-leftOffset;
            double currentY = -a*Math.pow(currentX,2) + c;
            y = GamePanel.gameHeight-currentY;

        }

        //parabola
        //-ax^2 + c = 0
        //a = c/x^2
        //x = distance/2
        //c = max height
        //use -ax^2 + c = y to find y



    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
