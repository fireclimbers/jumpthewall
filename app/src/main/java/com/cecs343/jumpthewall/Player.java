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
    private int attackTimer;

    public Player(Bitmap res, int x, int y, int w, int h, int numFrames) {
        this.x = x;
        this.y = y;
        dy = 0;
        height = h;
        width = w;
        spritesheet = res;

        onGround = false;
        stompTimer = 0;
        attackTimer = 0;

        Bitmap[] image = new Bitmap[numFrames];

        for (int i=0;i<image.length;i++) {
            image[i] = Bitmap.createBitmap(spritesheet,i*width,0,width,height);
        }

        animation.setFrames(image);
        animation.setDelay(60);
        startTime = System.nanoTime();

    }

    public void update() {
        if (stompTimer > 0) {stompTimer--;}
        if (attackTimer > -16) {attackTimer--;}

        animation.update();

        if (up) {
            //jump height
            dy = -14;
        } else {
            //gravity
            dy +=1.4;
        }

        //
        if(dy>28)dy=28;
        if(dy<-28)dy=-28;
        /*
        if (onGround) {
            dy += 1;
        }*/

        if (up) { setUp(false); }

        y+=dy;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(),(int)x,(int)y,null);
    }

    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void setOnGround(boolean b) {onGround = b;}
    public boolean getOnGround() {return onGround;}
    public void setUp(boolean b) {
        up = b;
    }
    public boolean getUp() {return up;}
    public void setStompTimer() {if (stompTimer == 0) stompTimer = 4;}
    public boolean stompTimerIsOn() {return (stompTimer > 0);}
    public void setAttackTimer() {if (attackTimer == -16) attackTimer = 3;}
    public boolean attackTimerIsOn() {return (attackTimer > 0);}
}
