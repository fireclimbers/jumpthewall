package com.cecs343.jumpthewall;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by docbot on 11/26/16.
 */
public class Player extends GameObject {
    private Bitmap spritesheet;
    private int score;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private boolean onGround;

    public Player(Bitmap res, int x, int y, int w, int h, int numFrames) {
        this.x = x;
        this.y = y;
        dy = 0;
        score = 0;
        height = h;
        width = w;
        spritesheet = res;

        onGround = false;

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

    public void update() {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if (up) {
            //dy -=1;
            dy = -15;
        } else {
            dy +=1;
        }

        if(dy>14)dy=14;
        if(dy<-14)dy=-14;

        /*if ((y+height+14 > GamePanel.gameHeight-32) && (!up)) {
            y = GamePanel.gameHeight-height-32;
            dy = 0;
        }*/

        if (onGround) {
            dy += 1;
        }

        if (up) { setUp(false); }

        y+=dy*2;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }

    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void setOnGround(boolean b) {onGround = b;}
    public void resetDY(){dy = 0;}
    public void resetScore(){score = 0;}
}
