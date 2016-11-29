package com.cecs343.jumpthewall;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by docbot on 11/28/16.
 */

public class Block extends GameObject {
    private Bitmap image;

    public Block(Bitmap res, int x, int y, int w, int h) {
        image = res;
        this.dx = GamePanel.movespeed;
        this.x = x;
        this.y = y;
        width = w;
        height = h;
    }

    public void update() {
        x += dx;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,x,y,null);
    }
}
