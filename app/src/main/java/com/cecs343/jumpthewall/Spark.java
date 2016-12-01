package com.cecs343.jumpthewall;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by docbot on 11/30/16.
 */

public class Spark extends GameObject {
    private Animation ani = new Animation();
    private Bitmap spritesheet;

    public Spark(int x, int y, Resources r) {
        super.x = x;
        super.y = y;
        width = 0;
        height = 0;

        dx = GamePanel.movespeed;

        ani.setFrames(getFrames(R.drawable.spark_strip7,96,96,7,r),-48,-48);
        ani.setDelay(30);
    }

    public void update() {
        x += dx;
        y += dy;
        ani.update();
    }

    public boolean isPlayedOnce() {
        return ani.playedOnce();
    }

    public void draw(Canvas canvas) {
        try{
            canvas.drawBitmap(ani.getImage(),(int)x+ani.getOffsetX(),(int)y+ani.getOffsetY(),null);
        }catch (Exception e){}
    }
}
