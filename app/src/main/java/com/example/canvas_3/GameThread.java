package com.example.canvas_3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    int target_distance;




    //constructor
    public GameThread(obstacleView obstaclev,GameView gameview,PlayerView player,SurfaceHolder sh)
    {
        game_delay = 30;
        target_distance = 100;
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
                gv.coin.draw_coin(canvas);
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

                    }else{gv.save_player_coins_in_db(); gv.gp.finish();}

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

                    }else{
                        gv.save_player_coins_in_db();
                        gv.gp.finish(); //save player coins before finishing game.

                    }

                }



                frames = frames+1;

                fps();
                int coin_collide = gv.coin.report_coin_collision(pv.player_jumping_x+10,pv.player_jumping_y+10);

                if(coin_collide==1)// coin collision happened
                {
                    gv.coin.coin_created = false; // coin is collected and dissapears
                    gv.player.player_number_of_coins ++;
                }

                //unlock and post canvas drawings

                holder.unlockCanvasAndPost(canvas);

                ///INCREASE GAME SPEED IF PLAYER IS PAST 100 METRES.
                if(pv.player_distance_covered >= target_distance)
                {
                    //player past target
                    target_distance = target_distance + 100;
                    game_delay = game_delay - 3;
                    gv.gp.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(gv.getContext(), "(っ◔◡◔)っ    SPEED INCREASED ", Toast.LENGTH_SHORT).show();
                            // explain to player speed is increased.delay reduced
                        }
                    });

                }

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
          //update player distance covered

          gv.player.player_distance_covered += 5;
        }

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(50);

        canvas.drawText(" FPS :  "+fps,10,50,paint);
    }

}

