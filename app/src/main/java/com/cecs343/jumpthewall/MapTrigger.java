package com.cecs343.jumpthewall;

import android.graphics.Canvas;

/**
 * Created by docbot on 11/28/16.
 */

public class MapTrigger extends GameObject {

    public MapTrigger(int x, int y) {
        this.x = x;
        this.y = y;
        width = 0;
        height = 0;
        this.dx = GamePanel.movespeed;
    }

    public void update() {
        x += dx;
    }

    public void draw(Canvas canvas) {}
}
