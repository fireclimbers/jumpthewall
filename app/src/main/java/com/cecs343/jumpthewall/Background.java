package com.cecs343.jumpthewall;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by docbot on 11/25/16.
 */
public class Background extends GameObject {
    private Bitmap image;

    public Background(int res, Resources r) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        image = BitmapFactory.decodeResource(r,res,o);
        width = o.outWidth;
        height = o.outHeight;
        this.dx = GamePanel.movespeed;
    }

    public void update() {
        x += dx;
        if (x<-width) {
            x = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,(int)x,(int)y,null);
        if (x<-width+GamePanel.gameWidth) {
            canvas.drawBitmap(image,(int)x+width,(int)y,null);
        }
    }

}
