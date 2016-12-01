package com.cecs343.jumpthewall.enemies;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.cecs343.jumpthewall.enemies.Enemy;

/**
 * Created by docbot on 11/29/16.
 */

public class StationaryEnemy extends Enemy {
    public StationaryEnemy(Bitmap res, int x, int y, int w, int h, int numFrames) {
        super(res,x,y,w,h,numFrames);
    }

    public void update() {
        super.update();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    public Rect getRect() {
        return new Rect((int)x,(int)y+12,(int)x+width-10,(int)y+height-12);
    }
}
