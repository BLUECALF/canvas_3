package com.example.canvas_3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class GameThread  extends Thread{

    //global objects and variables

    boolean running;
    GameView gv;
    obstacleView ov;
    PlayerView pv;
    SurfaceHolder holder;
    Canvas canvas;


    //time variables to determine fps;
    int frames;
    int fps;

    long starttime;
    long currenttime;
    long passedtime;




    //constructor
    public GameThread(obstacleView obstaclev,GameView gameview,PlayerView player,SurfaceHolder sh)
    {
        running = false;
        gv = gameview;
        pv = player;
        ov = obstaclev;
        holder = sh;
        frames = 0;


    }

    //game loop main in run method

    public void run()
    {
        starttime = System.currentTimeMillis();
        while(running)
        {
            //check surface validity
            if(holder.getSurface().isValid())
            {
                //draw and render the  game

                //lock canvas
                canvas = holder.lockCanvas();
                gv.draw(canvas);
                ov.obstacle_update_and_draw(canvas);
                pv.draw(canvas);
                frames = frames+1;

                fps();

                //unlock and post canvas drawings

                holder.unlockCanvasAndPost(canvas);
                if(pv.action.equals("run"))
                {try {
                    sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}
                if(pv.action.equals("jump")) //sleep at more miliseconds if player is jumping.
                {
                    try {
                        sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                if(pv.action.equals("slide"))
                {
                    try {
                        sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }



            }
            else{continue;}


        }

    }

    //Frame per second counter ;
    public void fps()
    {

        currenttime = System.currentTimeMillis();
        passedtime = currenttime - starttime;

        if(passedtime>1000)
        {
             fps = (int)(frames);
            frames = 0;
          starttime = System.currentTimeMillis();
        }

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(50);

        canvas.drawText(" FPS :  "+fps,10,50,paint);
    }
}

