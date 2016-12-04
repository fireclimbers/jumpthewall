package com.cecs343.jumpthewall;


import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

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
    private int attackCooldown = 2;
    private int score;



    public Player(int x, int y, int w, int h, Resources r) {
        this.x = x;
        this.y = y;
        height = h;
        width = w;
        dy = 0;

        onGround = false;
        stompTimer = 0;
        attackTimer = 0;
        score = 0;
        //playing = true;

        runAnimation.setFrames(getFrames(R.drawable.walking_strip10,108,114,10,r));
        runAnimation.setDelay(60);

        jumpAnimation.setFrames(getFrames(R.drawable.jump,96,158,1,r),0,-40);
        jumpAnimation.setDelay(60);

        attackAnimation.setFrames(getFrames(R.drawable.attack_strip7,218,126,7,r),-34,-12);
        attackAnimation.setDelayFrames(1);

        dieAnimation.setFrames(getFrames(R.drawable.die_strip13,202,196,8,r),-80,-60);
        dieAnimation.setDelay(60);


    }

    public void update() {
        score++;
        if (stompTimer > -stompCooldown) {stompTimer--;}
        if (attackTimer > -attackCooldown) {attackTimer--;}

        runAnimation.update();
        if (attackTimerIsOn()) {
            attackAnimation.updateFrame();
        }

        if (up) {
            //jump height
            dy = GamePanel.movespeed*2;
        } else {
            //gravity
            dy +=-(double)GamePanel.movespeed*2/10;
        }

        if(dy>28)dy=28;
        if(dy<-28)dy=-28;

        if (up) { setUp(false); }

        y+=dy;
    }

    public void setDeathAni() {
        dieAnimation.reset();
        //
    }

    public void updateDeath() {
        dieAnimation.update();
        if (dieAnimation.playedOnce()) {
            dieAnimation.setFrame(dieAnimation.getTotalFrames()-1);
        }

        dx = GamePanel.movespeed;

        if (dieAnimation.getFrame() == 1) {
            //jump height
            dy = -14;
        } else {
            //gravity
            dy +=1.4;
        }

        if(dy>28)dy=28;
        if(dy<-28)dy=-28;

        if (y > GamePanel.gameWidth+600) {
            dy = 0;
        }

        x+=dx;
        y+=dy;
    }

    public void draw(Canvas canvas) {
        if (!playing && score > 0) {
            canvas.drawBitmap(dieAnimation.getImage(),(int)x+dieAnimation.getOffsetX(),(int)y+dieAnimation.getOffsetY(),null);
        } else if (attackTimerIsOn()) {
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

    public int getScore() { return score; }
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
    public void resetScore() {score = 0;}
}
