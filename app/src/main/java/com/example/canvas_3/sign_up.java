package com.jangwarun.canvas_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;

public class sign_up extends Activity {
    EditText username,game_password,email;
    String _username,_game_password,_email;

SQLiteDatabase db;
ContentValues content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    //on click function of submitt button.
    public void submitt_player_data(View v)
    {
        // make database object then send data to db.
       username = findViewById(R.id._username);
       email =  findViewById(R.id._email);
       game_password = findViewById(R.id._game_password);

       _username = username.getText().toString();
       _email = email.getText().toString();
       _game_password = game_password.getText().toString();

       //make db helper 0bj
        game_DB_helper dbhelper = new game_DB_helper(this);

        //sqlite databsae object.
        db = dbhelper.getWritableDatabase();

        //content values
        content = new ContentValues();
        content.put("username",_username);
        content.put("email",_email);
        content.put("game_password",_game_password);

        //put to databse and check if its in.
        if(username.getText().toString().equals("")|email.getText().toString().equals("")||game_password.getText().equals(""))
        {
            Toast.makeText( this, "Please Enter Required data  !!", Toast.LENGTH_SHORT).show();
        }
        else if(!email.getText().toString().trim().contains(".")){
            Toast.makeText( this, "Please Enter Correct Email Address  !!", Toast.LENGTH_SHORT).show();        }
        else{
            // 1check if username & password have been used.
            // 2 enter player deatils in realtime db
            //3 register player as new user.
            // store player data in local storage.


            //1
            store_2_realtime_db(_email,_username,_game_password);



        }




    }
    public int store_2_realtime_db(String email,String username,String password)
    {
        //enter data to realtime database
        DatabaseReference ref_out = FirebaseDatabase.getInstance("https://canvas-3-b2835-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        HashMap<String,Object> map = new HashMap<>();

        map.put("email",_email);
        map.put("username",_username);
        map.put("game_password",_game_password);
        // characters player can use.
        map.put("boy","true");
        map.put("girl","true");
        map.put("cat","false");
        map.put("dog","false");
        map.put("cute_robot","false");
        map.put("ninja_boy","false");
        map.put("ninja_girl","false");
        map.put("jack_o_lantern","false");
        map.put("santa_claus","false");
        map.put("gems","1"); //currencies
        map.put("coins","20");
        map.put("metres","0");


        // first we check if there is username like his in the database.

        ref_out.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String _name_in_db = snapshot.child("player").child(username).child("username").getValue(String.class);
                String _email_insame_name = snapshot.child("player").child(username).child("email").getValue(String.class);
                String Refined_email = email.replace(".","_");
                String _email_in_db = snapshot.child("player").child(Refined_email).child("email").getValue(String.class);


                // check if username and email is in database

                if(_name_in_db==null&&_email_in_db==null&&_email_insame_name==null)
                {
                    //save data of the user
                  DatabaseReference ref_in=  FirebaseDatabase.getInstance("https://canvas-3-b2835-default-rtdb.europe-west1.firebasedatabase.app").getReference();
                    ref_in.child("player").child(username).updateChildren(map);
                    ref_in.child("player").child(Refined_email).updateChildren(map);

                    //create player as user
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password);

                    //save to sqlite
                    long rows = db.insert("player",null,content);

                    if(rows==1)
                    {
                        Toast.makeText(sign_up.this, "Registration successful  :) ", Toast.LENGTH_SHORT).show();
                        Intent gui = new Intent(sign_up.this,GUI_activity.class);
                        startActivity(gui);
                    }

                }else {
                    Toast.makeText(sign_up.this, "Username or email has been used :( \n Or too short password", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return 0;


    }

}