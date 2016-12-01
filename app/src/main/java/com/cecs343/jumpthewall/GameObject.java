package com.cecs343.jumpthewall;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by docbot on 11/26/16.
 */
public abstract class GameObject {
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    protected int width;
    protected int height;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rect getRect() {
        return new Rect((int)x,(int)y,(int)x+width,(int)y+height);
    }

    public Bitmap[] getFrames(int drawable, int width, int height, int numFrames, Resources r) {
        Bitmap sprite = BitmapFactory.decodeResource(r,drawable);
        Bitmap[] image = new Bitmap[numFrames];
        for (int i=0;i<image.length;i++) {
            image[i] = Bitmap.createBitmap(sprite,i*width,0,width,height);
        }
        return image;
    }

    abstract public void update();

    abstract public void draw(Canvas canvas);
}
