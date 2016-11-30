package com.cecs343.jumpthewall;

import  android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;


/**
 * Created by docbot on 11/26/16.
 */
public class Enemy extends GameObject {
    private Animation ani = new Animation();
    private Bitmap spritesheet;
    private boolean obeyGravity;

    public Enemy(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;

        obeyGravity = true;
        dx = GamePanel.movespeed;

        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for(int i=0;i<image.length;i++) {
            image[i] = Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }

        ani.setFrames(image);
        ani.setDelay(120);
    }

    public void update() {
        if (obeyGravity) {
            dy += 1;
        }
        x += dx;
        y += dy;
        ani.update();
    }

    public void draw(Canvas canvas) {
        try{
            canvas.drawBitmap(ani.getImage(),(int)x,(int)y,null);
        }catch (Exception e){}
    }

    public boolean isObeyGravity() { return obeyGravity; }
    public void setObeyGravity(boolean b) {obeyGravity = b; }

}
