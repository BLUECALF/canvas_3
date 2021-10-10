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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class game_lobby extends Activity {
    Button equip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
    }

    public void character_available(View v)

    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.gameover_save_me);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

       game_DB_helper db_helper = new game_DB_helper(this);
       SQLiteDatabase db = db_helper.getReadableDatabase();
       String field[]= {"username"};
       Cursor c = db.query("player",field,null,null,null,null,null);
       c.moveToFirst();
       String username = c.getString(0);
       System.out.println("username is "+ username);

        DatabaseReference out = FirebaseDatabase.getInstance("https://canvas-3-b2835-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        out.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String available = snapshot.child("player").child(username).child("boy").getValue(String.class);
                System.out.println("boy character is \n\n"+ available+"\n\n\n");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void run_game(View v)
    {
        Intent gameplay = new Intent(this, com.example.canvas_3.gameplay.class);
        startActivity(gameplay);
    }


}