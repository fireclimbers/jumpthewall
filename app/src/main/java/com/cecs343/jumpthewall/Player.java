package com.cecs343.jumpthewall;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by docbot on 11/26/16.
 */
public class Player extends GameObject {
    private Bitmap spritesheet;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private boolean onGround;
    private int stompTimer;

    public Player(Bitmap res, int x, int y, int w, int h, int numFrames) {
        this.x = x;
        this.y = y;
        dy = 0;
        height = h;
        width = w;
        spritesheet = res;

        onGround = false;
        stompTimer = 0;

        Bitmap[] image = new Bitmap[numFrames];

        for (int i=0;i<image.length;i++) {
            image[i] = Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }

        animation.setFrames(image);
        animation.setDelay(60);
        startTime = System.nanoTime();

    }

    public void setUp(boolean b) {
        up = b;
    }
    public boolean getUp() {return up;}
    public void setStompTimer() {if (stompTimer == 0) stompTimer = 4;}
    public boolean stompTimerIsOn() {if (stompTimer < 1) return false; return true;}

    public void update() {
        if (stompTimer > 0) {stompTimer--;}

        animation.update();

        if (up) {
            System.out.println("up!!");
            //dy -=1;
            dy = -9;
        } else {
            dy +=1;
        }

        if(dy>14)dy=14;
        if(dy<-14)dy=-14;

        if (onGround) {
            dy += 1;
        }

        if (up) { setUp(false); }

        y+=dy*2;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(),(int)x,(int)y,null);
    }

    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void setOnGround(boolean b) {onGround = b;}
    public boolean getOnGround() {return onGround;}
}
