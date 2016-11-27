package com.cecs343.jumpthewall;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    public static int movespeed = -5;
    public static int gameWidth;
    public static int gameHeight;
    private long enemyStartTime;
    private Background bg;
    private Player player;
    private ArrayList<Enemy> enemies;

    public GamePanel(Context context) {
        super(context);

        //this class is the view that creates all the graphics

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.youchan, options);
        gameWidth = options.outWidth;
        gameHeight = options.outHeight;

        getHolder().addCallback(this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter < 1000) {
            try{
                counter++;
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;
            } catch(InterruptedException e){e.printStackTrace();}
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        //where all the graphics are created for the first time

        bg = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.youchan), -5);
        player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.pinky31f),90,90,31);
        enemies = new ArrayList<>();
        enemyStartTime = System.nanoTime();

        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //touch controls go here
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            System.out.println("DOWN");
            if(!player.getPlaying()) {
                player.setPlaying(true);
            } else {
                player.setUp(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP) {
            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update() {

        //updates for all game objects here

        if(player.getPlaying()) {
            bg.update();
            player.update();

            //as score goes up, enemies will be created at a higher rate
            long enemiesElapsed = (System.nanoTime()-enemyStartTime)/1000000;
            if(enemiesElapsed > (2000-player.getScore()/4)) {
                enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy41f),
                        gameWidth + 10,
                        gameHeight-32-80,
                        56,
                        80,
                        player.getScore(),
                        41));

                enemyStartTime = System.nanoTime();
            }

            for (int i=0;i<enemies.size();i++) {
                enemies.get(i).update();
                if(collision(enemies.get(i),player)) {
                    enemies.remove(i);
                    player.setPlaying(false);
                    break;
                }
                if(enemies.get(i).getX() < -100) {
                    enemies.remove(i);
                    break;
                }
            }

        }
    }

    public boolean collision(GameObject a, GameObject b) {
        return a.getRect().intersect(b.getRect());
    }

    @Override
    public void draw(Canvas canvas) {

        //scales graphics to be size of phone
        //and then draws all game objects

        final float scaleFactorX = getWidth()/(gameWidth*1.f);
        final float scaleFactorY = getHeight()/(gameHeight*1.f);
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);

            bg.draw(canvas);
            player.draw(canvas);
            /*
            Paint p = new Paint();
            p.setColor(Color.RED);
            canvas.drawRect(player.getRect(), p);
            p.setColor(Color.BLUE);*/

            for(Enemy e : enemies) {
                e.draw(canvas);
                //canvas.drawRect(e.getRect(), p);
            }

            canvas.restoreToCount(savedState);
        }
    }
}
