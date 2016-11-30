package com.cecs343.jumpthewall;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by docbot on 11/26/16.
 */
public class PitEnemy extends Enemy {
    private Animation ani = new Animation();
    private Bitmap spritesheet;
    private boolean hasJumped;

    public PitEnemy(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super(res,x,y,w,h,numFrames);

        dy = 0;
        hasJumped = false;
        setObeyGravity(false);
    }

    public void update() {
        if (getX() < GamePanel.gameWidth && !hasJumped) {
            dy = -36;
            hasJumped = true;
        }

        if (hasJumped) {
            dy += 1.5;
        }
        super.update();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
