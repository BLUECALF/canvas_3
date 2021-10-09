package com.example.canvas_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {


    Toast toast;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);




    }
    public void start_gui(View v)
    {
        // code that runs when screen is touched
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // database reference

        game_DB_helper dbhelper = new game_DB_helper(this);

        //check wether player has ever logged in.
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        //check table for any data
        String fields[] = {"username","email","game_password"};
        // Cursor c = db.query("player",fields,null,null,null,null,null);

        c = db.rawQuery("SELECT * FROM  player",null);
        if(!c.moveToFirst())
        {
            //if there is no data in table.
            Intent start_sign_up = new Intent(this,sign_up.class);
            startActivity(start_sign_up);
            System.out.println("player has no data , has to signup");
        }
        else {
            Intent gui = new Intent(this, GUI_activity.class);
            startActivity(gui);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toast = Toast.makeText(getApplicationContext()," act resumed",Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        toast = Toast.makeText(getApplicationContext(),"act paused",Toast.LENGTH_SHORT);
        toast.show();
    }


}