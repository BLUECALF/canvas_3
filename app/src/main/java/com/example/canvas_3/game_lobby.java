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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
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
    TextView gems;

    MediaPlayer lobby_music_sound;

    // ADVERTISEMENT VARRIABLES
    private RewardedAd mRewardedAd;
    private final String TAG = "game_lobby_activity";

    boolean watched_ad = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);

        //load advert
        load_advert();

        //initialize and run the lobby music
        lobby_music_sound = MediaPlayer.create(game_lobby.this, R.raw.lobby_music);
        lobby_music_sound.setLooping(true);
        lobby_music_sound.start();

        //sqlite databse helper

        db_helper = new game_DB_helper(this);
        player_skin_choice = "boy";

        get_player_coins_n_metres_from_db();

        coins = (TextView)findViewById(R.id.coins);
        total_metres =(TextView) findViewById(R.id.total_metres);
        gems =(TextView) findViewById(R.id.gems);
        get_username_from_sqlite();


    }
    public void get_username_from_sqlite()
    {

        //get username from sqlite database.

        SQLiteDatabase db = db_helper.getReadableDatabase();

        String fields[]={"username"};
        Cursor c = db.query("player",fields,null,null,null,null,null);
        c.moveToFirst();
        username = c.getString(0);


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
        DatabaseReference ref = FirebaseDatabase.getInstance("https://canvas-3-b2835-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int  _coins =Integer.parseInt(snapshot.child("player").child(username).child("coins").getValue(String.class));
                int _gems = Integer.parseInt(snapshot.child("player").child(username).child("gems").getValue(String.class));
                int    _total_metres = Integer.parseInt(snapshot.child("player").child(username).child("metres").getValue(String.class));
                System.out.println("\n\n the coins in lobby I are"+_coins);
                System.out.println("\n\n the metres in lobby I  are"+_total_metres);

                // set coins & metres to be number of coins in db
                coins.setText(" Coins : "+_coins);
                total_metres.setText(" Total metres: "+_total_metres);
                gems.setText(" Gems: "+_gems);



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

                int player_gems = Integer.parseInt(snapshot.child("player").child(playername).child("gems").getValue().toString());


                if(skin_owned.equals("false")&&(player_cash>=price||player_gems>=(price/10)))
                {
                    //player has ability to buy the skin and can buy.
                    //buy skin.
                  if(player_cash>=price)
                  { ref.child("player").child(playername).child(skin_name).setValue("true");

                      int new_player_cash = player_cash - price;

                      ref.child("player").child(playername).child("coins").setValue(Integer.toString(new_player_cash));

                      Toast.makeText(game_lobby.this, "Skin purchase of "+ skin_name +" using coins Successfull", Toast.LENGTH_SHORT).show();
                      coins.setText(" Coins : "+new_player_cash);
                  }else{
                      //this means he will use gems to buy skins.
                      ref.child("player").child(playername).child(skin_name).setValue("true");

                      int new_player_gems = player_gems - (price/10);

                      ref.child("player").child(playername).child("gems").setValue(Integer.toString(new_player_gems));

                      Toast.makeText(game_lobby.this, "Skin purchase of "+ skin_name +" using gems Successfull", Toast.LENGTH_SHORT).show();
                      gems.setText(" Gems : "+new_player_gems);

                  }


                }else if(skin_owned.equals("true")){Toast.makeText(game_lobby.this, "Skin purchase : FAILED :\n You Have "+ skin_name +" Skin", Toast.LENGTH_LONG).show();
                }else{ Toast.makeText(game_lobby.this, "Skin purchase "+skin_name+" Failed \n Your Coins Are Not Enough ", Toast.LENGTH_SHORT).show();
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
        int price = 50;
       buy_skin(username,skin,price);
    }
    public void buy_skin_dog(View v)
    {
        String skin ="dog";
        int price = 50;
        buy_skin(username,skin,price);
    }
    public void buy_skin_ninja_boy(View v)
    {
        String skin ="ninja_boy";
        int price = 100;
        buy_skin(username,skin,price);
    }
    public void buy_skin_ninja_girl(View v)
    {
        String skin ="ninja_girl";
        int price = 100;
        buy_skin(username,skin,price);
    }
    public void buy_skin_santa_claus(View v)
    {
        String skin ="santa_claus";
        int price = 300;
        buy_skin(username,skin,price);
    }
    public void buy_skin_cute_robot(View v)
    {
        String skin ="cute_robot";
        int price = 200;
        buy_skin(username,skin,price);
    }
    public void buy_skin_jack_o_lantern(View v)
    {
        String skin ="jack_o_lantern";
        int price = 500;
        buy_skin(username,skin,price);
    }

    
    // CODE FOR EQUIPING THE SKINS .
    public void equip_skin(String playername,String skin_name)
    {
        //maake firebase database reference
        DatabaseReference ref = FirebaseDatabase.getInstance("https://canvas-3-b2835-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        //1 check if skin is owned,if NOT DONT ALLOW EQUIP
       //2 MAKE SKIN TO BE EQUIPED 


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String skin_owned = snapshot.child("player").child(playername).child(skin_name).getValue().toString();

               

                if(skin_owned.equals("true"))
                {
                    //player owns the skin and now can equip the skin.
                  player_skin_choice = skin_name;
                    Toast.makeText(game_lobby.this, "You are using "+ skin_name +" skin now !", Toast.LENGTH_SHORT).show();


                }else if(skin_owned.equals("false")){Toast.makeText(game_lobby.this, "You don't have "+ skin_name +" Skin !!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }public void equip_skin_boy(View v)
    {
        String skin = "boy";
        equip_skin(username,skin);
    }
    public void equip_skin_girl(View v)
    {
        String skin ="girl";
        equip_skin(username,skin);
    }
    public void equip_skin_cat(View v)
    {
        String skin = "cat";
        equip_skin(username,skin);
    }
    public void equip_skin_dog(View v)
    {
        String skin = "dog";
        equip_skin(username,skin);
    }
    public void equip_skin_ninja_boy(View v)
    {
        String skin = "ninja_boy";
        equip_skin(username,skin);
    }
    public void equip_skin_ninja_girl(View v)
    {
        String skin = "ninja_girl";
        equip_skin(username,skin);
    }
    public void equip_skin_santa_claus(View v)
    {
        String skin = "santa_claus";
        equip_skin(username,skin);
    }
    public void equip_skin_cute_robot(View v)
    {
        String skin = "cute_robot";
        equip_skin(username,skin);
    }
    public void equip_skin_jack_o_lantern(View v)
    {
        String skin = "jack_o_lantern";
        equip_skin(username,skin);
    }

    // watching adverts for gems.
    public void watch_advert_for_gems(View v)
    {
        // code to WATCHING ADS FOR THE GEMS
        Toast.makeText(this, "Advert loading (1 ad = 1 gem)", Toast.LENGTH_SHORT).show();

        show_advert_to_user();
    }

    private void load_advert() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Toast.makeText(game_lobby.this, "Advert FAILED to load !", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                        show_full_screen();
                    }
                });

    }
    public void show_full_screen() // funcon to show the advert in full screen
    {mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
        @Override
        public void onAdShowedFullScreenContent() {
            // Called when ad is shown.
            Log.d(TAG, "Ad was shown.");
        }

        @Override
        public void onAdFailedToShowFullScreenContent(AdError adError) {
            // Called when ad fails to show.
            Log.d(TAG, "Ad failed to show.");
        }

        @Override
        public void onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            // Set the ad reference to null so you don't show the ad a second time.
            Log.d(TAG, "Ad was dismissed.");
            Toast.makeText(game_lobby.this, " You have been rewarded 1 gem ", Toast.LENGTH_SHORT).show();

            mRewardedAd = null;
            watched_ad = false;
            load_advert();
        }
    });}

    public void show_advert_to_user()
    {
        if (mRewardedAd != null) {

            mRewardedAd.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    Log.d(TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                    add_player_gem(username);
                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }

    public void add_player_gem(String username)
    {
        //maake firebase database reference
        DatabaseReference ref = FirebaseDatabase.getInstance("https://canvas-3-b2835-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        //1 get gem number
        //increment it by 1
        //save it in realtime db
        //edit in gem edittex

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int gem_amt = Integer.parseInt(snapshot.child("player").child(username).child("gems").getValue(String.class));
                int gem_new_amt = gem_amt + 1;
                String new_gem_string = Integer.toString(gem_new_amt);

               gems.setText(" Gems: "+ gem_new_amt);

               if(!watched_ad){ ref.child("player").child(username).child("gems").setValue(new_gem_string);watched_ad = true;}


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    // overide pause & resume in activity
    @Override
    protected void onPause() {
        super.onPause();
       lobby_music_sound.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lobby_music_sound.stop();
        lobby_music_sound.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lobby_music_sound.start();
    }


}