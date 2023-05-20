package com.jangwarun.canvas_3;



import android.graphics.Bitmap;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

public class CoinView {
    int coin_x;
    int coin_y;
    boolean coin_created;
    boolean screen_passed;
    int screen_determiner;
    int screen_width;
    int screen_height;
    int coin_slide_number;
    ArrayList<Bitmap> coin_image_array;
    Random random;

    public CoinView(ArrayList<Bitmap> coin_image)
    {
        // coins should be copper.
        coin_image_array = new ArrayList<>(coin_image);
        coin_slide_number = 0;
        random = new Random();
        screen_passed = true;
        screen_determiner = 0;

    }
    public void draw_coin(Canvas canvas)
    {
        //ask if screen has passed ,if true then determine if coin is spawned
        //if not addscreen determiner.


        // choose randomly if a coin can be spawned
        // spawn the coin
        //draw the coin
        // update coin coordinates
        // if its not seen then end it
        // if its collided end it also
        if(screen_passed){
            //if screen has passed we choose coin spawn.
            if(coin_created==false) {
                // if coin has not been made

                int choice = random.nextInt(4);
                int jump_variation = random.nextInt(11)*10;


                if (choice == 0) // coin has been chosen to spawn
                {
                    // spawn the coin
                    coin_created = true;
                    coin_x = screen_width + 1000;
                    coin_y = screen_height - 200 -jump_variation;
                }
                screen_passed = false;
        }


        }else {
            //we  add the screen determiner to know if screen has passed.
            screen_determiner +=20;
            if(screen_determiner >= screen_width){screen_passed = true; screen_determiner = 0; }
        }
        if(coin_created == true) // if coin has been created
        {
            canvas.drawBitmap(coin_image_array.get(coin_slide_number),coin_x,coin_y,null);

            coin_x = coin_x - 20;
            coin_slide_number++;

            if(coin_slide_number == 8)  // repeat cycles
            {
                coin_slide_number = 0;
            }
            if(coin_x<0) //if its not seen it is destroyed
            {coin_created=false; coin_x = 0;coin_y = 0;}
        }



    }
    public int  report_coin_collision(int player_hitbox_x,int player_hitbox_y)
    {
        int hitbox_x = coin_x;
        int hitbox_y = coin_y;
        int coin_widht_height = 25;
        int player_w_h = 80 ;

        if(hitbox_x < player_hitbox_x + player_w_h &&
                hitbox_x + coin_widht_height > player_hitbox_x &&

                hitbox_y < player_hitbox_y + player_w_h &&
                hitbox_y + coin_widht_height > player_hitbox_y)
        {
            // what happens if collision has happened
            return 1;
        }

        return 0;
    }

}
