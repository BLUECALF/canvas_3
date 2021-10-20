package com.example.canvas_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class game_lobby extends Activity {
    String username;

    String player_skin_choice;

    game_DB_helper db_helper;

    TextView coins;
    TextView total_metres;

     int _coins;
     int _total_metres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        //sqlite databse helper

        db_helper = new game_DB_helper(this);
        player_skin_choice = "ninja_boy";

        get_player_coins_n_metres_from_db();

        coins = (TextView)findViewById(R.id.coins);
        total_metres =(TextView) findViewById(R.id.total_metres);





    }


    public void run_game(View v)
    {
        Intent gameplay = new Intent(this, com.example.canvas_3.gameplay.class);
        gameplay.putExtra("playerschoice",player_skin_choice);
        startActivity(gameplay);
    }


    // funtion to get coins and metres details from database;
    public void get_player_coins_n_metres_from_db()
    {
        db_helper = new game_DB_helper(this);

        SQLiteDatabase db = db_helper.getReadableDatabase();

        String fields[]={"username"};
        Cursor c = db.query("player",fields,null,null,null,null,null);
        c.moveToFirst();
        username = c.getString(0);
        DatabaseReference ref = FirebaseDatabase.getInstance("https://canvas-3-b2835-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int  _coins =Integer.parseInt(snapshot.child("player").child(username).child("coins").getValue(String.class));
                int    _total_metres = Integer.parseInt(snapshot.child("player").child(username).child("metres").getValue(String.class));
                System.out.println("\n\n the coins in lobby I are"+_coins);
                System.out.println("\n\n the metres in lobby I  are"+_total_metres);

                // set coins & metres to be number of coins in db
                coins.setText("COINS : "+_coins);
                total_metres.setText("TOTAL METRES : "+_total_metres);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // FUNCTIONS TO HANDLE TRANSACTIONS.

    public void buy_skin(String playername,String skin_name,int price)
    {
       //maake firebase database reference
        DatabaseReference ref = FirebaseDatabase.getInstance("https://canvas-3-b2835-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        //1 check if skin is owned,ifnot
        //2 check if money is enough if its enough
        //3 make skin to be owned
        //4 reduce the money for the game.


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String skin_owned = snapshot.child("player").child(playername).child(skin_name).getValue().toString();

                int player_cash =Integer.parseInt(snapshot.child("player").child(playername).child("coins").getValue().toString());

                if(skin_owned.equals("false")&&player_cash>=price)
                {
                    //player has ability to buy the skin and can buy.
                    //buy skin.
                  ref.child("player").child(playername).child(skin_name).setValue("true");

                  int new_player_cash = player_cash - price;

                  ref.child("player").child(playername).child("coins").setValue(new_player_cash);

                    Toast.makeText(game_lobby.this, "Skin purchase Successfull", Toast.LENGTH_SHORT).show();


                }else if(skin_owned.equals("true")){Toast.makeText(game_lobby.this, "Skin purchase : FAILED :\n You Have This Skin", Toast.LENGTH_LONG).show();
                }else{ Toast.makeText(game_lobby.this, "Skin purchase Failed \n Your Coins Are Not Enough ", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void buy_skin_cat(View v)
    {
        String skin ="cat";
        int price = 10;

        //get username from sqlite database.

        SQLiteDatabase db = db_helper.getReadableDatabase();

        String fields[]={"username"};
       Cursor c = db.query("player",fields,null,null,null,null,null);
       c.moveToFirst();
       username = c.getString(0);

       buy_skin(username,skin,price);


    }



}