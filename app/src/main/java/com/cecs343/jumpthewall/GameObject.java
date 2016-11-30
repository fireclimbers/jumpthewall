package com.cecs343.jumpthewall;

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
        return new Rect((int)getX(),(int)getY(),(int)getX()+getWidth(),(int)getY()+getHeight());
    }

    abstract public void update();

    abstract public void draw(Canvas canvas);
}
