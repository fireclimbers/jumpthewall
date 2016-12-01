package com.cecs343.jumpthewall;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by docbot on 11/26/16.
 */
public class Player extends GameObject {
    private boolean up;
    private boolean playing;
    private Animation runAnimation = new Animation();
    private Animation jumpAnimation = new Animation();
    private Animation attackAnimation = new Animation();
    private Animation dieAnimation = new Animation();
    private boolean onGround;
    private int stompTimer;
    private int attackTimer;
    private int stompWindow = 4;
    private int stompCooldown = 8;
    private int attackWindow = 8; //# of frames attack is + 1
    private int attackCooldown = 4;

    public Player(int x, int y, int w, int h, Resources r) {
        this.x = x;
        this.y = y;
        height = h;
        width = w;
        dy = 0;

        onGround = false;
        stompTimer = 0;
        attackTimer = 0;

        runAnimation.setFrames(getFrames(R.drawable.walking_strip10,108,114,10,r));
        runAnimation.setDelay(60);

        jumpAnimation.setFrames(getFrames(R.drawable.jump,96,158,1,r),0,-40);
        jumpAnimation.setDelay(60);

        attackAnimation.setFrames(getFrames(R.drawable.attack_strip7,218,126,7,r),-34,-12);
        attackAnimation.setDelayFrames(1);

    }

    public Bitmap[] getFrames(int drawable, int width, int height, int numFrames, Resources r) {
        Bitmap sprite = BitmapFactory.decodeResource(r,drawable);
        Bitmap[] image = new Bitmap[numFrames];
        for (int i=0;i<image.length;i++) {
            image[i] = Bitmap.createBitmap(sprite,i*width,0,width,height);
        }
        return image;
    }

    public void update() {
        if (stompTimer > -stompCooldown) {stompTimer--;}
        if (attackTimer > -attackCooldown) {attackTimer--;}

        runAnimation.update();
        if (attackTimerIsOn()) {
            attackAnimation.updateFrame();
        }

        if (up) {
            //jump height
            dy = -14;
        } else {
            //gravity
            dy +=1.4;
        }

        if(dy>28)dy=28;
        if(dy<-28)dy=-28;

        if (up) { setUp(false); }

        y+=dy;
    }

    public void draw(Canvas canvas) {
        if (attackTimerIsOn()) {
            canvas.drawBitmap(attackAnimation.getImage(),(int)x+attackAnimation.getOffsetX(),(int)y+attackAnimation.getOffsetY(),null);
        } else if (onGround) {
            canvas.drawBitmap(runAnimation.getImage(),(int)x+runAnimation.getOffsetX(),(int)y+runAnimation.getOffsetY(),null);
        } else {
            canvas.drawBitmap(jumpAnimation.getImage(),(int)x+jumpAnimation.getOffsetX(),(int)y+jumpAnimation.getOffsetY(),null);
        }

    }

    public Rect getAttackHitbox() {
        if (attackTimer <= 5 && attackTimer >= 3) {
            return new Rect((int)x+width,(int)y+32,(int)x+width+106,(int)y+32+56);
        }
        return new Rect(0,0,0,0);
    }

    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void setOnGround(boolean b) {onGround = b;}
    public boolean getOnGround() {return onGround;}
    public void setUp(boolean b) {
        up = b;
    }
    public boolean getUp() {return up;}
    public void setStompTimer() {if (stompTimer == -stompCooldown) stompTimer = stompWindow;}
    public boolean stompTimerIsOn() {return (stompTimer > 0);}
    public void setAttackTimer() {
        if (attackTimer == -attackCooldown) {
            attackTimer = attackWindow;
            attackAnimation.setFrame(-1);
        }
    }
    public boolean attackTimerIsOn() {return (attackTimer > 0);}
}
