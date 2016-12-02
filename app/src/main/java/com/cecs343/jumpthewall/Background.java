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
    private int drawable;
    private Bitmap nextImage;
    private int nextDrawable;
    private int nextWidth;
    private int nextHeight;
    private boolean transition;

    public Background(int res, Resources r) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        image = BitmapFactory.decodeResource(r,res,o);
        drawable = res;
        width = o.outWidth;
        height = o.outHeight;
        nextDrawable = res;
        transition = false;

        //changeBackground(R.drawable.newbg1,r);
    }

    public void changeBackground(int res, Resources r) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        nextImage = BitmapFactory.decodeResource(r,res,o);
        nextDrawable = res;
        nextWidth = o.outWidth;
        nextHeight = o.outHeight;
        System.out.println("changed");
    }

    public void update() {
        dx = GamePanel.movespeed;
        x += dx;
        if (x<-width) {
            x = 0;
            if (drawable != nextDrawable && transition) {
                drawable = nextDrawable;
                image = nextImage;
                width = nextWidth;
                height = nextHeight;
                transition = false;
            }
        }
        System.out.println(drawable+" "+nextDrawable);
        System.out.println(x+" "+(-width+GamePanel.gameWidth));
        if ((x>=-width+GamePanel.gameWidth) && (drawable != nextDrawable)) {
            System.out.println("geting!!!");
            transition = true;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image,(int)x,(int)y,null);
        if (x<-width+GamePanel.gameWidth) {
            if (!transition) {
                canvas.drawBitmap(image,(int)x+width,(int)y,null);
            } else {
                System.out.println("????");
                canvas.drawBitmap(nextImage,(int)x+width,(int)y,null);
            }

        }
    }

}
