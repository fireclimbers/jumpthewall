package com.cecs343.jumpthewall;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by docbot on 11/25/16.
 */
public class GUI extends GameObject {
    private Bitmap controls;
    private Bitmap retryMenu;
    private Bitmap start;
    private int offset = 100;

    public GUI(Resources r) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        Bitmap wholeScreen = BitmapFactory.decodeResource(r,R.drawable.gui,o);
        width = o.outWidth;
        height = o.outHeight;
        controls = Bitmap.createBitmap(wholeScreen,0,0,width,offset);
        retryMenu = Bitmap.createBitmap(wholeScreen,0,offset,width,offset);
        start = Bitmap.createBitmap(wholeScreen,0,offset+offset,width,offset);

        //changeBackground(R.drawable.newbg1,r);
    }

    public void update() {

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(controls, (int) x, (int) y,null);
    }

    public void drawStart(Canvas canvas) {
        Paint p = new Paint();
        p.setAlpha(127);
        canvas.drawBitmap(controls, (int) x, (int) y, p);
        canvas.drawBitmap(start, (int) x, offset+offset, null);
    }

    public void drawRetry(Canvas canvas) {
        Paint p = new Paint();
        p.setAlpha(127);
        canvas.drawBitmap(controls, (int) x, (int) y, p);
        canvas.drawBitmap(retryMenu, (int) x, offset, null);
    }

}
