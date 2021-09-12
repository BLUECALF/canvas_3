package com.example.canvas_3;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

public class obstacleView {
    //this is the class that will make obstacles to be seen.

    //it needs an array list of obstacle images.
    ArrayList<Bitmap> imageArray;

    int screen_width;
    int screen_height;

    //it will randomly chose which obstacle is made .
    boolean obstacle_1_created;
    Bitmap obstacle_1_image;
    int obstacle_1_x;
    int obstacle_1_y;

    //obstacle 2 data
    boolean obstacle_2_created;
    Bitmap obstacle_2_image;
    int obstacle_2_x;
    int obstacle_2_y;
    Random random = new Random();



    //it will draw the obstacle to canvas.


    // CONSTRUCTOR HERE


    public obstacleView(ArrayList<Bitmap> obstacleArray,int width_of_screen,int height)
    {
        imageArray = new ArrayList<>(obstacleArray);
        obstacle_1_created =  false;
        obstacle_2_created = false;
        screen_width = width_of_screen;
        screen_height = height;
    }
      //function to update obstacle possition and values and draw them.

    public void obstacle_update_and_draw(Canvas canvas)
    {
        if(obstacle_1_created == false)
        {
            //we make the obstacle.
            obstacle_1_created = true;
            obstacle_1_x = screen_width;
            obstacle_1_y = screen_height - 200;

            //allocate an image.

            obstacle_1_image = imageArray.get(random.nextInt(9));


        }else if(obstacle_1_created == true)
        {
            obstacle_1_x = obstacle_1_x - 20;

            //check if it has passed. then its shut down.
            canvas.drawBitmap(obstacle_1_image,obstacle_1_x,obstacle_1_y,null);
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

            //allocate an image.

            obstacle_2_image = imageArray.get(random.nextInt(9));


        }else if(obstacle_2_created == true)
        {
            obstacle_2_x = obstacle_2_x - 20;

            //check if it has passed. then its shut down.
            canvas.drawBitmap(obstacle_2_image,obstacle_2_x,obstacle_2_y,null);
            if(obstacle_2_x < -100)
            {
                //shut down //
                obstacle_2_created = false;
            }


        }



    }

}
