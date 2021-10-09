package com.example.canvas_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class gameplay extends Activity {
    GameView gv;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gv = new GameView(this,this);
        setContentView(gv);




    }
    public void show_game_over()
    {
        // make game over dialogue.

        dialog = new Dialog(this);

        dialog.setContentView(R.layout.gameover_save_me);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }
    public void give_up(View v)
    {
        // code that runs if player gives up
        dialog.dismiss();
        finish();
    }
    public void pay_1_coin(View v)
    {
       // player must be sheilded from the obstacle he/she hit

        if(gv.thread.collision == 1)
        {
            //sheild player from obstacle 1
            gv.obstacle.obstacle_1_player_sheilded = true;
        }

        if(gv.thread.collision == 2 )
        {
            //shield player from obstacle 2
            gv.obstacle.obstacle_2_player_sheilded = true;
        }


        dialog.dismiss();
        Toast.makeText(getApplicationContext(), "you have paid 1 coin!!", Toast.LENGTH_SHORT).show();

    }
}