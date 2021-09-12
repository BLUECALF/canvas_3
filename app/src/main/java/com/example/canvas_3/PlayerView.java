package com.example.canvas_3;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class PlayerView {

    //global variables
    Paint paint;
    //player details.
    int screenHeight;
    int screenWidth;

    int slide_no;
    int jump_slide_no;
    int slide_slide_no;

    int player_x;
    int player_y;

    int player_position_in_map = 0;

    String action;

    //player images;
    //put in a  arraylist of bitmap
    ArrayList<Bitmap> playerimages;



    //constructor

    public PlayerView(ArrayList<Bitmap> images)
    {
        //array of bitmaps  player images to aquire values;

        playerimages = new ArrayList<>(images);


        player_x = 0; //player is at 0 first.

        player_y = 0; //player is at 0 first

        //initialisation of running images

        //initialization of values

        slide_no = 0;
        jump_slide_no = 0;
        slide_slide_no = 0;

        action = "run";

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(50);

    }

    //player draw method
    public void draw(Canvas canvas){


        if(action.equals("run"))
        {

            player_x = screenWidth/4;
            player_y = screenHeight-200;

            //check slide number then draw';

            if(slide_no == 0)
            {canvas.drawBitmap(playerimages.get(0),player_x,player_y,null);canvas.drawText(""+screenWidth,100,100,paint);}
            else if(slide_no == 1)
            {canvas.drawBitmap(playerimages.get(1),player_x,player_y,null);}
            else if(slide_no == 2)
            {canvas.drawBitmap(playerimages.get(2),player_x,player_y,null);}
            else if(slide_no == 3)
            {canvas.drawBitmap(playerimages.get(3),player_x,player_y,null);}
            else if(slide_no == 4)
            {canvas.drawBitmap(playerimages.get(4),player_x,player_y,null);}
            else if(slide_no == 5)
            {canvas.drawBitmap(playerimages.get(5),player_x,player_y,null);}
            else if(slide_no == 6)
            {canvas.drawBitmap(playerimages.get(6),player_x,player_y,null);}
            else if(slide_no == 7)
            {canvas.drawBitmap(playerimages.get(7),player_x,player_y,null);}
            else if(slide_no == 8)
            {canvas.drawBitmap(playerimages.get(8),player_x,player_y,null);}
            if(slide_no == 9)
            {canvas.drawBitmap(playerimages.get(9),player_x,player_y,null);}
            else

            slide_no++;

            if(slide_no == 9){slide_no = 0;}

        }

        //on jumping player.
        if(action.equals("jump"))
        {
            //what happens when player jumps
            player_x = screenWidth/4;
            player_y = screenHeight-200;

            //check slide number then draw';

            if(jump_slide_no == 0)
            {canvas.drawBitmap(playerimages.get(10),player_x,player_y-30,null);canvas.drawText(""+screenWidth,100,100,paint);}
            else if(slide_no == 1)
            {canvas.drawBitmap(playerimages.get(11),player_x,player_y-50,null);}
            else if(jump_slide_no == 2)
            {canvas.drawBitmap(playerimages.get(12),player_x,player_y-80,null);}
            else if(jump_slide_no == 3)
            {canvas.drawBitmap(playerimages.get(13),player_x,player_y-110,null);}
            else if(jump_slide_no == 4)
            {canvas.drawBitmap(playerimages.get(14),player_x,player_y-110,null);}
            else if(jump_slide_no == 5)
            {canvas.drawBitmap(playerimages.get(15),player_x,player_y-130,null);}
            else if(jump_slide_no == 6)
            {canvas.drawBitmap(playerimages.get(16),player_x,player_y-130,null);}
            else if(jump_slide_no == 7)
            {canvas.drawBitmap(playerimages.get(17),player_x,player_y-120,null);}
            else if(jump_slide_no == 8)
            {canvas.drawBitmap(playerimages.get(18),player_x,player_y-80,null);}
            if(jump_slide_no == 9)
            {canvas.drawBitmap(playerimages.get(19),player_x,player_y-50,null);}
            else

                jump_slide_no++;

            if(jump_slide_no == 9){jump_slide_no = 0;action="run";}

        }
         // when the player slides towards obstacles.
        if(action.equals("slide"))
        {
            //what happens when player jumps
            player_x = screenWidth/4;
            player_y = screenHeight-200;

            //check slide number then draw';

            if(slide_slide_no == 0)
            {canvas.drawBitmap(playerimages.get(20),player_x+20,player_y,null);canvas.drawText(""+screenWidth,100,100,paint);}
            else if(slide_no == 1)
            {canvas.drawBitmap(playerimages.get(21),player_x+20,player_y,null);}
            else if(slide_slide_no == 2)
            {canvas.drawBitmap(playerimages.get(22),player_x+25,player_y,null);}
            else if(slide_slide_no == 3)
            {canvas.drawBitmap(playerimages.get(23),player_x+25,player_y,null);}
            else if(slide_slide_no == 4)
            {canvas.drawBitmap(playerimages.get(24),player_x+40,player_y,null);}
            else if(slide_slide_no == 5)
            {canvas.drawBitmap(playerimages.get(25),player_x+40,player_y,null);}
            else if(slide_slide_no == 6)
            {canvas.drawBitmap(playerimages.get(26),player_x+40,player_y,null);}
            else if(slide_slide_no == 7)
            {canvas.drawBitmap(playerimages.get(27),player_x+45,player_y,null);}
            else if(slide_slide_no == 8)
            {canvas.drawBitmap(playerimages.get(28),player_x+30,player_y,null);}
            if(slide_slide_no == 9)
            {canvas.drawBitmap(playerimages.get(29),player_x+25,player_y,null);}
            else

                slide_slide_no++;

            if(slide_slide_no == 9){slide_slide_no = 0;action="run";}

        }



    }



}
