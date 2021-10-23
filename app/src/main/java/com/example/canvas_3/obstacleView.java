package com.example.canvas_3;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class obstacleView {
    Paint paint = new Paint();
    GameThread t;
    Context con_text;
    //this is the class that will make obstacles to be seen.

    //it needs an array list of obstacle images.
    ArrayList<Bitmap> imageArray;
    ArrayList<Bitmap> bird_image;

    int screen_width;
    int screen_height;

    int hitbox_x;
    int hitbox_y;

    //it will randomly chose which obstacle is made .
    boolean obstacle_1_created;
    Bitmap obstacle_1_image;
    int obstacle_1_x;
    int obstacle_1_y;
    String obstacle_1_type;
    int obstacle_1_slide_no;
    boolean obstacle_1_player_sheilded;

    //obstacle 2 data
    boolean obstacle_2_created;
    Bitmap obstacle_2_image;
    int obstacle_2_x;
    int obstacle_2_y;
    String obstacle_2_type;
    int obstacle_2_slide_no;
    boolean obstacle_2_player_sheilded;

    Random random = new Random();



    //it will draw the obstacle to canvas.


    // CONSTRUCTOR HERE


    public obstacleView(ArrayList<Bitmap> obstacleArray,ArrayList<Bitmap> bird_image_array,GameThread thread,Context context)
    {
        t = thread;
        imageArray = new ArrayList<>(obstacleArray);
        bird_image = new ArrayList<>(bird_image_array);
        con_text=context;
        obstacle_1_created =  false;
        obstacle_2_created = false;
        obstacle_1_slide_no = 0;
        obstacle_2_slide_no = 0;

    }
      //function to update obstacle position and values and draw them.

    public void obstacle_update_and_draw(Canvas canvas)
    {

        if(obstacle_1_created == false)
        {
            //we make the obstacle.
            obstacle_1_created = true;
            obstacle_1_x = screen_width;
            obstacle_1_y = screen_height - 200;
            obstacle_1_player_sheilded=false;

            //give the obstacle a type
            if(random.nextInt(4) != 0){
                //allocate an image if obstacle_type == jump
                obstacle_1_image = imageArray.get(random.nextInt(9));
                obstacle_1_type ="jump";}
            else{obstacle_1_type="slide"; obstacle_1_y = obstacle_1_y - 50;}





        }else if(obstacle_1_created == true&& obstacle_1_type.equals("jump"))
        {
            obstacle_1_x = obstacle_1_x - 20;

            //check if it has passed. then its shut down.
            canvas.drawBitmap(obstacle_1_image,obstacle_1_x,obstacle_1_y,null);
            if(obstacle_1_x < -100)
            {
                //shut down //
                obstacle_1_created = false;
            }
        }//if the obstacle_1_type == slide for example a bird.
        else if(obstacle_1_created == true && obstacle_1_type.equals("slide"))
        {
            obstacle_1_x = obstacle_1_x - 20;
            //find out which slide should be drawn then draw it.
            canvas.drawBitmap(bird_image.get(obstacle_1_slide_no),obstacle_1_x,obstacle_1_y,null);
            obstacle_1_slide_no ++;

            if(obstacle_1_slide_no>5){obstacle_1_slide_no = 0;}
            //if obstacle is not seen it should be shut down- destroyed
            if(obstacle_1_x < -100)
            {
                //shut down //
                obstacle_1_created = false;
            }

        }

        //obststacle two creation and draw  instructions

        if(obstacle_2_created == false)
        {
            //we make the obstacle two .
            obstacle_2_created = true;
            obstacle_2_x = obstacle_1_x  +  ((random.nextInt(4) + 5)*100);
            obstacle_2_y = screen_height - 200;
            obstacle_2_player_sheilded = false;

            //give obstacle two a type and image.
            if(random.nextInt(4) != 0)
            {obstacle_2_image = imageArray.get(random.nextInt(9));
            obstacle_2_type = "jump";
            }else{obstacle_2_type = "slide"; obstacle_2_y = obstacle_2_y - 50;}




        }else if(obstacle_2_created == true && obstacle_2_type.equals("jump"))
        {
            obstacle_2_x = obstacle_2_x - 20;

            //check if it has passed. then its shut down.
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.RED);
            canvas.drawBitmap(obstacle_2_image,obstacle_2_x,obstacle_2_y,null);
            //canvas.drawRect(obstacle_2_x,obstacle_2_y,obstacle_2_x+100,obstacle_2_y+100,paint);
            if(obstacle_2_x < -100)
            {
                //shut down //
                obstacle_2_created = false;
            }


        }else if(obstacle_2_created == true && obstacle_2_type.equals("slide"))
        {
            //update x.
            obstacle_2_x = obstacle_2_x- 20;

            //draw image.
            canvas.drawBitmap(bird_image.get(obstacle_2_slide_no),obstacle_2_x,obstacle_2_y,null);
            obstacle_2_slide_no++;
            if(obstacle_2_slide_no>5){obstacle_2_slide_no = 0;}

            // if obstacle is far from seen.
            if(obstacle_2_x < -100){obstacle_2_created = false;}


        }




    }
    public int report_collission(Canvas canvas,int player_jump_x,int player_jump_y)
    {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(50);

        //in colision we have to calculate the hitbox
        //in our case it will have its own x and y and width.
         hitbox_x = player_jump_x + 20;
         hitbox_y  = player_jump_y + 20;
        int hitbox_width =60;
        int hitbox_height = 60;
        int obstacle_1_hitbox_x= obstacle_1_x + 30;
        int obstacle_1_hitbox_y = obstacle_1_y + 30;
        int obstacle_2_hitbox_x = obstacle_2_x + 30;
        int obstacle_2_hitbox_y = obstacle_2_y + 30;
        int obstacle_width = 40;
        int obstacle_height = 60;

        if(     hitbox_x < obstacle_1_hitbox_x + obstacle_width &&
                hitbox_x + hitbox_width > obstacle_1_hitbox_x &&

                hitbox_y < obstacle_1_hitbox_y + obstacle_height &&
                hitbox_y + hitbox_height > obstacle_1_hitbox_y
        ){ return 1;// rerurn 1 meaning collision happened
            }

        if(    hitbox_x < obstacle_2_hitbox_x + obstacle_width &&
                hitbox_x + hitbox_width > obstacle_2_hitbox_x &&

                hitbox_y < obstacle_2_hitbox_y + obstacle_height &&
                hitbox_y + hitbox_height > obstacle_2_hitbox_y
        ){return 2; // return 1 meaning collission happened
            }
        return 0 ; //all went well no collision
    }


}
