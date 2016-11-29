package com.cecs343.jumpthewall;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    public static int movespeed = -8;
    public static int tileSize = 32;
    public static int gameWidth = 832;
    public static int gameHeight = 448;
    public static int mapPart;
    private Background bg;
    private Player player;
    private MapTrigger mapTrigger;
    private ArrayList<Enemy> enemies;
    //private long enemyStartTime;
    private ArrayList<Block> blocks;
    public int timer;


    public GamePanel(Context context) {
        super(context);

        //this class is the view that creates all the graphics

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

        bg = new Background(R.drawable.newbg, getResources());
        enemies = new ArrayList<>();
        blocks = new ArrayList<>();

        mapPart = 0;
        createMapPart(0,0);

        timer = 0;
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //touch controls go here
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            if(!player.getPlaying()) {
                player.setPlaying(true);
            } else if (player.getOnGround()) {
                player.setOnGround(false);
                player.setUp(true);
            } else {
                //start 4 frame timer
                player.setStompTimer();
            }
            return true;
        }/*
        if(event.getAction()==MotionEvent.ACTION_UP) {
            player.setUp(false);
            return true;
        }*/
        return super.onTouchEvent(event);
    }

    public void createMapPart(int startX, int startY) {
        if (mapPart == Level1.map.length) {
            //player.setPlaying(false);
            //return;
            mapPart = 0;
        }
        for(int i=0;i<Level1.map[mapPart].length;i++) {
            if (Level1.map[mapPart][i] != 0) {
                int x = i % (gameWidth/32);
                int y = i / (gameWidth/32);
                if (Level1.map[mapPart][i] == 1) {
                    blocks.add(new Block(BitmapFactory.decodeResource(getResources(),R.drawable.blackblock),startX+x*32,startY+y*32,32,32));
                }
                if (Level1.map[mapPart][i] == 2) {
                    if (player == null)
                    player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.pinky31f),startX+x*32,startY+y*32,90,90,31);
                }
                if (Level1.map[mapPart][i] == 3) {
                    enemies.add(new Enemy(BitmapFactory.decodeResource(getResources(), R.drawable.enemy41f), startX+x*32, startY+y*32, 56, 80, 41));
                }
            }
            if (i == Level1.map[mapPart].length-1) {
                int x = i % (gameWidth/32);
                int y = i / (gameWidth/32);
                if (mapTrigger == null)
                    mapTrigger = new MapTrigger(startX+x*32,startY+y*32);
                else {
                    mapTrigger.setX(startX+x*32);
                    mapTrigger.setY(startY+y*32);
                }

            }
        }
        mapPart++;
    }

    public void update() {

        //updates for all game objects here

        if(player.getPlaying()) {
            timer += 1;

            bg.update();
            player.update();
            mapTrigger.update();

            if (mapTrigger.getX() < gameWidth) {
                createMapPart(mapTrigger.getX()+32,0);
            }

            ArrayList<Integer> toBeRemoved = new ArrayList<>();
            for (int i=0;i<enemies.size();i++) {
                enemies.get(i).update();
                int col = collision(enemies.get(i),player);
                if(col == 0) {
                    player.setPlaying(false);
                    break;
                }
                if (col == 3) {
                    //make a small window (4 frames) where player can jump
                    if (player.stompTimerIsOn()) {
                        toBeRemoved.add(i);
                        player.setUp(true);
                    } else {
                        player.setPlaying(false);
                        break;
                    }
                }
                if(enemies.get(i).getX() < -100) {
                    toBeRemoved.add(i);
                }
            }
            for (int i=toBeRemoved.size()-1;i>=0;i--) {
                enemies.remove((int)toBeRemoved.get(i));
            }

            toBeRemoved = new ArrayList<>();
            for (int i=0;i<blocks.size();i++) {
                blocks.get(i).update();
                //if player is above block
                int col = collision(blocks.get(i),player);
                if(col == 0) {
                    player.setPlaying(false);
                }
                if (col == 3) {
                    player.setOnGround(true);
                    while(collision(blocks.get(i),player) != -1) {
                        player.setY(player.y-1);
                    }
                    player.setDy(0);
                }
                if(blocks.get(i).getX() < -100) {
                    toBeRemoved.add(i);
                }
            }

            for (int i=toBeRemoved.size()-1;i>=0;i--) {
                blocks.remove((int)toBeRemoved.get(i));
            }

        }
    }

    public int collision(GameObject a, GameObject b) {
        int dx=(a.getX()+a.getWidth()/2)-(b.getX()+b.getWidth()/2);
        int dy=(a.getY()+a.getHeight()/2)-(b.getY()+b.getHeight()/2);
        int width=(a.getWidth()+b.getWidth())/2;
        int height=(a.getHeight()+b.getHeight())/2;
        int crossWidth=width*dy;
        int crossHeight=height*dx;
        int collision=-1;
        //
        if(Math.abs(dx)<=width && Math.abs(dy)<=height){
            if(crossWidth>crossHeight){
                collision=(crossWidth>(-crossHeight))?3:2;
            }else{
                collision=(crossWidth>-(crossHeight))?0:1;
            }
        }
        return(collision);
        //return a.getRect().intersect(b.getRect());
    }

    @Override
    public void draw(Canvas canvas) {

        //scales graphics to be size of phone or tablet
        //and then draws all game objects


        final float scaleFactorX = getWidth()/(gameWidth*1.f);
        final float scaleFactorY = getHeight()/(gameHeight*1.f);
        if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);

            bg.draw(canvas);
            player.draw(canvas);

            /*Paint p = new Paint();
            p.setColor(Color.RED);
            canvas.drawRect(player.getRect(), p);
            p.setColor(Color.BLUE);*/

            for(Enemy e : enemies) {
                e.draw(canvas);
                //canvas.drawRect(e.getRect(), p);
            }

            for(Block b : blocks) {
                b.draw(canvas);
                //canvas.drawRect(b.getRect(), p);
            }

            canvas.restoreToCount(savedState);
        }
    }
}
