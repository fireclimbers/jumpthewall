package com.cecs343.jumpthewall;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Intent;
import android.app.Activity;


import com.cecs343.jumpthewall.enemies.Enemy;
import com.cecs343.jumpthewall.enemies.FloatingEnemy;
import com.cecs343.jumpthewall.enemies.PitEnemy;
import com.cecs343.jumpthewall.enemies.ShieldedEnemy;
import com.cecs343.jumpthewall.enemies.StationaryEnemy;
import com.cecs343.jumpthewall.enemies.SwoopEnemy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    public static int initMovespeed = -16;
    public static int movespeed = -16;
    public static int tileSize = 64;
    public static int gameWidth = 832;
    public static int gameHeight = 448;
    public static int mapPart;
    private Background bg;
    private Player player;
    private MapTrigger mapTrigger;
    private ArrayList<Enemy> enemies;
    private ArrayList<Block> blocks;
    private ArrayList<Spark> sparks;
    private int screenCount;
    private int phaseCount;
    private int phaseLength = 19;
    private int[] bgRotation = {R.drawable.newbg3,R.drawable.newbg4,R.drawable.newbg1,R.drawable.newbg2};
    //public int timer;

    MediaPlayer jumpSfx;
    MediaPlayer swingSfx;
    MediaPlayer landSfx;
    MediaPlayer hitSfx;
    MediaPlayer deathSfx;
    MediaPlayer pitEnemySfx;
    MediaPlayer swoopEnemySfx;

    Game c;

    Random rand = new Random();

    //choose random background, and random block type
    int randbg = rand.nextInt(3);
    int randblock = rand.nextInt(2);
    int randsong = rand.nextInt(2);

    public GamePanel(Context context) {
        super(context);

        c = (Game)context;

        //this class is the view that creates all the graphics

        deathSfx = MediaPlayer.create(context, R.raw.deathscream);
        jumpSfx = MediaPlayer.create(context,R.raw.playerjump);
        swingSfx = MediaPlayer.create(context,R.raw.playerswing);
        landSfx = MediaPlayer.create(context,R.raw.playerland);
        hitSfx = MediaPlayer.create(context,R.raw.hammerimpact);
        pitEnemySfx = MediaPlayer.create(context,R.raw.pitenemyjump);
        swoopEnemySfx = MediaPlayer.create(context,R.raw.swoopenemy);


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

        bg = new Background(R.drawable.newbg3, getResources());

        //where all the graphics are created for the first time
        player = new Player(64,256,64,112,getResources());
        enemies = new ArrayList<>();
        blocks = new ArrayList<>();
        sparks = new ArrayList<>();
        phaseCount = 0;
        screenCount = 0;

        mapPart = 0;
        createMapPart(0,0);

        //timer = 0;
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //touch controls go here
        //jump on right
        //attack on left
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
//            if (!player.getPlaying() && player.getScore() > 0) {
//                //player.setPlaying(true);
//                newGame();
//            } else
            if (!player.getPlaying() && player.getScore() == 0) {
                player.setPlaying(true);
            } else if (player.getPlaying()) {
                if (event.getX() > getWidth()/2) {
                    if (player.getOnGround()) {
                        player.setOnGround(false);
                        player.setUp(true);
                        jumpSfx.start();
                    } else {
                        //start 4 frame timer
                        player.setStompTimer();
                    }
                } else {
                    player.setAttackTimer();
                    swingSfx.start();
                }
            } else if (!player.getPlaying()) {
                if(event.getX() > getWidth()/2) {
                    c.closeActivity();
                    //Intent intent = new Intent().setClass(getContext(), Menu.class);
                    //((Activity) getContext()).startActivity(intent);
                    //player.setPlaying(false);
                } else {
                    newGame();
                }
            }

            return true;
        }
        return super.onTouchEvent(event);
    }



    public void newGame() {
        //player.resetScore();
        bg = new Background(R.drawable.newbg3, getResources());
        enemies.clear();
        blocks.clear();
        sparks.clear();

        movespeed = initMovespeed;
        mapPart = 0;
        screenCount = 0;
        phaseCount = 0;
        createMapPart(0,0);
        player = new Player(64,256,64,112,getResources());
        //player.setPlaying(true);
    }

    public void createMapPart(int startX, int startY) {
        mapPart = rand.nextInt(10);

        int[] map = AllMaps.getMapLevel(phaseCount,screenCount,mapPart, rand.nextInt(10));
        int numScreens = map.length/((gameWidth/tileSize)*(gameHeight/tileSize));
        for(int i = 0; i< map.length; i++) {
            if (map[i] != 0) {
                int x = i % (gameWidth*numScreens/tileSize);
                int y = i / (gameWidth*numScreens/tileSize);
                if (map[i] == 1) {
                    int blockSprite;
                    if (randblock == 0) {
                        blockSprite = R.drawable.brick;
                    } else if (randblock == 1) {
                        blockSprite = R.drawable.brick1;
                    } else {
                        blockSprite = R.drawable.brick2;
                    }
                    blocks.add(new Block(BitmapFactory.decodeResource(getResources(), blockSprite), startX + x * tileSize, startY + y * tileSize, 32, 32));
                    blocks.add(new Block(BitmapFactory.decodeResource(getResources(), blockSprite), (startX + x * tileSize) + (tileSize/2), startY + y * tileSize, 32, 32));
                    blocks.add(new Block(BitmapFactory.decodeResource(getResources(), blockSprite), startX + x * tileSize, (startY + y * tileSize) + (tileSize/2), 32, 32));
                    blocks.add(new Block(BitmapFactory.decodeResource(getResources(), blockSprite), (startX + x * tileSize) + (tileSize/2), (startY + y * tileSize) + (tileSize/2), 32, 32));
                }
                if (map[i] == 2) {
                    enemies.add(new StationaryEnemy(BitmapFactory.decodeResource(getResources(), R.drawable.chomper8f), startX+x*tileSize, startY+y*tileSize, 52, 56, 8));
                }
                if (map[i] == 3) {
                    enemies.add(new FloatingEnemy(BitmapFactory.decodeResource(getResources(), R.drawable.floating_strip5), startX+x*tileSize, startY+y*tileSize, 80, 44, 5));
                }
                if (map[i] == 4) {
                    enemies.add(new ShieldedEnemy(BitmapFactory.decodeResource(getResources(), R.drawable.shielded_strip8), startX+x*tileSize, startY+y*tileSize, 60, 56, 8));
                }
                if (map[i] == 5) {
                    enemies.add(new PitEnemy(BitmapFactory.decodeResource(getResources(), R.drawable.eye16f), startX+x*tileSize, gameHeight, 40, 78, 16));
                }
                if (map[i] == 6) {
                    enemies.add(new SwoopEnemy(BitmapFactory.decodeResource(getResources(), R.drawable.swoop_strip8), startX+x*tileSize, 0, 54, 50, 8));
                }
            }
            if (i == map.length-1) {
                int x = i % (gameWidth*numScreens/tileSize);
                int y = i / (gameWidth*numScreens/tileSize);
                //if (mapTrigger == null)
                    mapTrigger = new MapTrigger(startX+x*tileSize,startY+y*tileSize);
                //else {
                //    mapTrigger.setX(startX+x*32);
                //    mapTrigger.setY(startY+y*32);
                //}

            }
        }
        if (screenCount == phaseLength) {
            screenCount = 0;
            phaseCount++;
            movespeed -= 1;
            bg.changeBackground(bgRotation[phaseCount%4],getResources());
        } else {
            screenCount++;
        }

        if (map.length == 0) {
            createMapPart(startX,startY);
        }
    }

    public void update() {

        //updates for all game objects here

        if(player.getPlaying()) {
            bg.update();
            player.update();
            mapTrigger.update();

            if (mapTrigger.getX() < gameWidth) {
                createMapPart((int)mapTrigger.getX()+tileSize,0);
            }

            ArrayList<Integer> toBeRemoved = new ArrayList<>();
            for (int i=0;i<enemies.size();i++) {
                enemies.get(i).update();
                int col = collision(enemies.get(i),player);
                int attackCol = collision(enemies.get(i).getRect(),player.getAttackHitbox());
                if (attackCol != -1) {
                    //if player is attacking it
                    hitSfx.start();
                    if (!(enemies.get(i) instanceof ShieldedEnemy)) {
                        toBeRemoved.add(i);
                    } //might need to add an else here
                } else if(col == 0) {
                    //if player runs into it
                    player.setPlaying(false);
                    break;
                } else if (col == 3) {
                    //if player is stoming on it
                    landSfx.start();
                    if (player.stompTimerIsOn()) {
                        if (!(enemies.get(i) instanceof PitEnemy)) {
                            toBeRemoved.add(i);
                            player.setUp(true);
                        } else {
                            player.setPlaying(false);
                            break;
                        }
                    } else {
                        //if player is not stomping on it
                        player.setPlaying(false);
                        break;
                    }
                }
                if(enemies.get(i).getX() < -200 || enemies.get(i).getY() > gameHeight+200) {
                    toBeRemoved.add(i);
                }
            }

            Iterator<Spark> sparksIter = sparks.iterator();
            while (sparksIter.hasNext()) {
                Spark s = sparksIter.next();
                s.update();
                if(s.getX() < -100 || s.getY() > gameHeight+200 || s.isPlayedOnce()) {
                    sparksIter.remove();
                }
            }

            for (int i=toBeRemoved.size()-1;i>=0;i--) {
                int eX = enemies.get((int)toBeRemoved.get(i)).getRect().centerX();
                int eY = enemies.get((int)toBeRemoved.get(i)).getRect().centerY();
                sparks.add(new Spark(eX, eY,getResources()));
                enemies.remove((int)toBeRemoved.get(i));
            }

            toBeRemoved = new ArrayList<>();
            player.setOnGround(false);
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
                        player.setY(player.getY()-1);
                    }
                    player.setDy(0);
                }
                for (Enemy e : enemies) {
                    if (e.isObeyGravity()) {
                        int eCol = collision(blocks.get(i),e);
                        if (eCol == 3) {
                            while(collision(blocks.get(i),e) != -1) {
                                e.setY(e.getY()-1);
                            }
                            e.setDy(0);
                        }
                    }
                }

                if(blocks.get(i).getX() < -100 || blocks.get(i).getY() > gameHeight+200) {
                    toBeRemoved.add(i);
                }
            }

            for (int i=toBeRemoved.size()-1;i>=0;i--) {
                blocks.remove((int)toBeRemoved.get(i));
            }

            if (player.getY() > gameHeight+40) {
                player.setPlaying(false);
            }

            if (!player.getPlaying() && player.getScore() > 0) {
                bg = new Background(R.drawable.optionmenu, getResources());
                deathSfx.start();
                player.setDeathAni();
            }
        } else if (player.getScore() > 0) {
            //do death animation, restart after animation ends
            player.updateDeath();

            for (int i=0;i<enemies.size();i++) {
                enemies.get(i).updateOnlyAnimation();
            }
        }
    }




    public int collision(GameObject aa, GameObject bb) {
        Rect aRect = aa.getRect();
        Rect bRect = bb.getRect();

        int dx=((int)aRect.left+aRect.width()/2)-((int)bRect.left+bRect.width()/2);
        int dy=((int)aRect.top+aRect.height()/2)-((int)bRect.top+bRect.height()/2);
        int width=(aRect.width()+bRect.width())/2;
        int height=(aRect.height()+bRect.height())/2;
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

    public int collision(Rect aRect, Rect bRect) {
        int dx=((int)aRect.left+aRect.width()/2)-((int)bRect.left+bRect.width()/2);
        int dy=((int)aRect.top+aRect.height()/2)-((int)bRect.top+bRect.height()/2);
        int width=(aRect.width()+bRect.width())/2;
        int height=(aRect.height()+bRect.height())/2;
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

            Paint p = new Paint();
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(1);

            bg.draw(canvas);
            for(Block b : blocks) {
                b.draw(canvas);
                //canvas.drawRect(b.getRect(), p);
            }

            player.draw(canvas);




            if (player.attackTimerIsOn()) {
                p.setColor(Color.RED);
            } else {
                p.setColor(Color.GREEN);
            }


            //canvas.drawRect(player.getRect(), p);
            //canvas.drawRect(player.getAttackHitbox(), p);
            p.setColor(Color.BLUE);

            for(Enemy e : enemies) {
                e.draw(canvas);
                //canvas.drawRect(e.getRect(), p);
            }

            for(Spark s : sparks) {
                s.draw(canvas);
            }

            canvas.restoreToCount(savedState);
        }
    }
}
