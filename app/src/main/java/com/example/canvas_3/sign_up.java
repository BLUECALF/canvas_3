package com.example.canvas_3;

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
import com.google.firebase.ktx.Firebase;

public class sign_up extends Activity {
    EditText username,game_password,email;
    String _username,_game_password,_email;

    private FirebaseAuth auth;

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
        SQLiteDatabase  db = dbhelper.getWritableDatabase();

        //content values
        ContentValues content = new ContentValues();
        content.put("username",_username);
        content.put("email",_email);
        content.put("game_password",_game_password);

        //put to databse and check if its in.
        if(username.getText().toString().equals("")|email.getText().toString().equals("")||game_password.getText().equals(""))
        {
            Toast.makeText( this, "Please Enter Required data", Toast.LENGTH_SHORT).show();
        }
        else{
            long row_no = db.insert("player",null,content);
            if(row_no==1)
        {
            //saving the data was successfull go to play.
            // we sign up to firebase first

            auth = FirebaseAuth.getInstance();

            auth.createUserWithEmailAndPassword(_email,_game_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                    Toast.makeText(sign_up.this, "Sign up successfull ", Toast.LENGTH_SHORT).show();

                    Intent gui = new Intent(sign_up.this, GUI_activity.class);
                    startActivity(gui);
                    finish();}
                    else {Toast.makeText(sign_up.this, "Sign up Failed ", Toast.LENGTH_SHORT).show();}
                }

            });



        }}




    }

}