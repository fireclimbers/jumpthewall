package com.cecs343.jumpthewall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by docbot on 11/25/16.
 */
public class Background {
    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res, int dx) {
        image = res;
        this.dx = dx;
    }

    public void update() {
        x += dx;
        if (x<-GamePanel.gameWidth) {
            x = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,x,y,null);
        if (x<0) {
            canvas.drawBitmap(image,x+GamePanel.gameWidth,y,null);
        }
    }

}
