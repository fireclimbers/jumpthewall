package com.cecs343.jumpthewall.enemies;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.cecs343.jumpthewall.Animation;
import com.cecs343.jumpthewall.GamePanel;
import com.cecs343.jumpthewall.enemies.Enemy;

import java.util.Random;

/**
 * Created by docbot on 11/26/16.
 */
public class SwoopEnemy extends Enemy {
    private Animation ani = new Animation();
    private boolean fakeOut;
    private boolean hasJumped;
    Random r;

    public SwoopEnemy(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super(res,x,y,w,h,numFrames);

        dy = 0;
        hasJumped = false;
        setObeyGravity(false);

        Random r = new Random();
        fakeOut = r.nextBoolean();
        //fakeOut = false;
    }

    public void update() {
        super.update();

        if (getX() < GamePanel.gameWidth && !hasJumped) {
            //dy = -36;
            hasJumped = true;
        }

        if (hasJumped) {
            //dy += 1.5;
            //swoopsfx.start();
            if (fakeOut) {
                int topOffset = 96;
                int leftOffset = 64;
                int c = GamePanel.gameHeight-topOffset;
                int xx = (GamePanel.gameWidth-leftOffset)/2;
                double a = c/(Math.pow(xx,2));
                double currentX = x-xx-leftOffset;
                double currentY = -a*Math.pow(currentX,2) + c;
                y = currentY;
            } else {
                dx -= 0.2;
                int topOffset = 112;
                int leftOffset = 0;
                int c = GamePanel.gameHeight-topOffset;
                int xx = (GamePanel.gameWidth-leftOffset);
                double a = c/(Math.pow(xx,2));
                double currentX = x-leftOffset;
                double currentY = -a*Math.pow(currentX,2) + c;
                y = currentY;
            }
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
