package com.cecs343.jumpthewall;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    public static int FPS = 5;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();

        //this is the thread that redraws graphics every frame

        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;

    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        //# of milliseconds per frame
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch(Exception e){}
            finally {
                if (canvas != null) {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch(Exception e){};
                }
            }

            //amount of time updating took in milliseconds
            timeMillis = (System.nanoTime()-startTime)/1000000;
            //leftover time if updating was quick
            waitTime = targetTime-timeMillis;

            try {
                //sleep for leftover time
                this.sleep(waitTime);
            } catch (Exception e){}

            //this part is just used to calculate and print out average fps
            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS) {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }

    public void setRunning(boolean b) {
        running = b;
    }
}
