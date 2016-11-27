package com.cecs343.jumpthewall;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by docbot on 11/26/16.
 */
public class Enemy extends GameObject {
    private int score;
    private int speed;
    private Random r = new Random();
    private Animation ani = new Animation();
    private Bitmap spritesheet;

    public Enemy(Bitmap res, int x, int y, int w, int h, int s, int numFrames) {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        score = s;

        speed = 7 + (int) (r.nextDouble()*score/30);

        if (speed >= 40)speed = 40;

        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for(int i=0;i<image.length;i++) {
            image[i] = Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }

        ani.setFrames(image);
        ani.setDelay(100-speed);
    }

    public void update() {
        x -= speed;
        ani.update();
    }

    public void draw(Canvas canvas) {
        try{
            canvas.drawBitmap(ani.getImage(),x,y,null);
        }catch (Exception e){}
    }

    @Override
    public int getWidth() {
        return width-10;
    }

}
