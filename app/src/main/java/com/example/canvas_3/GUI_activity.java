package com.example.canvas_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class GUI_activity extends Activity {
    TextView username;
    private static final String TAG = "MainActivity";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gui);

        // load and show banner advert
        show_ad();
        username = findViewById(R.id.username);

        //find username from sql database.

        game_DB_helper dbhelper = new game_DB_helper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        String[] data_needed = {"username","email"};

        //get data from db
       Cursor c= db.query("player",data_needed,null,null,null,null,null);
       c.moveToFirst();
       username.setText("UserName : "+c.getString(0));

    }

    private void show_ad() {


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void open_lobby(View v)
    {
        Intent lobby = new Intent(this,game_lobby.class);
        startActivity(lobby);
    }

}