package com.example.canvas_3;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class GameThread  extends Thread{

    //global objects and variables

    boolean running;
    GameView gv;
    obstacleView ov;
    PlayerView pv;
    SurfaceHolder holder;
    Canvas canvas;
    int collision;


    //time variables to determine fps;
    int frames;
    int fps;
    int game_delay;

    long starttime;
    long currenttime;
    long passedtime;




    //constructor
    public GameThread(obstacleView obstaclev,GameView gameview,PlayerView player,SurfaceHolder sh)
    {
        game_delay = 25;
        running = false;
        gv = gameview;
        pv = player;
        ov = obstaclev;
        holder = sh;
        frames = 0;
        collision = 0;


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
                collision = ov.report_collission(canvas,pv.player_jumping_x,pv.player_jumping_y);
                // check if collision happened
                if(collision == 1)
                {
                    //collission happened with obstacle 1
                    //show dialog box

                    //check if player is sheilded from obstacle
                    if(!ov.obstacle_1_player_sheilded){
                   gv.gp.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                          gv.gp.show_game_over();
                       }

                   });
                    //slow down time by 5 sec
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }


                    if(ov.obstacle_1_player_sheilded==true)
                    {
                        // player clicked watch an advert

                    }else{gv.gp.finish();}

                }
                if(collision == 2)
                {
                    //collission happened with obstacle 2
                    //show dialog box


                    // check if obstacle 2 is not sheilded from player
                    if(!ov.obstacle_2_player_sheilded){
                    gv.gp.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gv.gp.show_game_over();
                        }

                    });
                    //slow down time for 5 sec
                        try {
                            sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }


                    if(ov.obstacle_2_player_sheilded ==true)
                    {
                        // player clicked watch an advert

                    }else{gv.gp.finish();}

                }



                frames = frames+1;

                fps();

                //unlock and post canvas drawings

                holder.unlockCanvasAndPost(canvas);
                if(pv.action.equals("run"))
                {try {
                    sleep(game_delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }}
                if(pv.action.equals("jump")) //sleep at more miliseconds if player is jumping.
                {
                    try {
                        sleep(game_delay + 5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                if(pv.action.equals("slide"))
                {
                    try {
                        sleep(game_delay + 5);
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

