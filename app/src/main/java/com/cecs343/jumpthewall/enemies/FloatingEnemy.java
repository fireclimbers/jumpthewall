package com.cecs343.jumpthewall.enemies;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.cecs343.jumpthewall.enemies.Enemy;

/**
 * Created by docbot on 11/29/16.
 */

public class FloatingEnemy extends Enemy {
    public FloatingEnemy(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super(res,x,y,w,h,numFrames);
        setObeyGravity(false);
    }

    public void update() {
        super.update();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public Rect getRect() {
        return new Rect((int)x+6,(int)y+8,(int)x+width-6,(int)y+height);
    }
}
