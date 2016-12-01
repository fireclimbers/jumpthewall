package com.cecs343.jumpthewall;

import android.graphics.Bitmap;

/**
 * Created by docbot on 11/26/16.
 */
public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private int offsetX, offsetY;
    private long startTime;
    private long delay;
    private int frameTimer;
    private int delayFrames;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames) {
        offsetX = 0;
        offsetY = 0;
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        frameTimer = 0;
    }

    public void setFrames(Bitmap[] frames, int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
        frameTimer = 0;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setDelay(long d) {
        //in milliseconds
        delay = d;
    }

    public void setDelayFrames(int d) {
        delayFrames = d;
    }

    public void setFrame(int i) {
        currentFrame = i;
    }

    public int getTotalFrames() { return frames.length; }

    public void update() {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if (elapsed > delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length) {
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public void updateFrame() {
        if (frameTimer >= delayFrames-1) {
            currentFrame++;
            frameTimer = 0;
            startTime = System.nanoTime();
            if(currentFrame == frames.length) {
                currentFrame = 0;
                playedOnce = true;
            }
        } else {
            frameTimer++;
        }
    }

    public Bitmap getImage() {
        return frames[currentFrame];
    }

    public int getFrame() {
        return currentFrame;
    }

    public boolean playedOnce() {
        return playedOnce;
    }

    public void reset() {
        setFrame(0);
        playedOnce = false;
    }
}
