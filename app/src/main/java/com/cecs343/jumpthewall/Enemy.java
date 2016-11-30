package com.cecs343.jumpthewall;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by docbot on 11/26/16.
 */
public class Enemy extends GameObject {
    private Random r = new Random();
    private Animation ani = new Animation();
    private Bitmap spritesheet;

    public Enemy(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;

        dx = -GamePanel.movespeed;

        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for(int i=0;i<image.length;i++) {
            image[i] = Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }

        ani.setFrames(image);
        ani.setDelay(120);
    }

    public void update() {
        x -= dx;
        ani.update();
    }

    public void draw(Canvas canvas) {
        try{
            canvas.drawBitmap(ani.getImage(),(int)x,(int)y,null);
        }catch (Exception e){}
    }

    @Override
    public int getWidth() {
        return width-20;
    }

    @Override
    public double getX() { return x+10; }

    @Override
    public int getHeight() { return height-20; }

    @Override
    public double getY() { return y+20; }

}
