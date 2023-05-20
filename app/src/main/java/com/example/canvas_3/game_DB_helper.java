package com.jangwarun.canvas_3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class game_DB_helper extends SQLiteOpenHelper {


    public game_DB_helper(Context context) {
        super(context,"jangwa.db", null,1);//jangwa.db is database name
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //create a table.
        db.execSQL("create table player (username text,email text,game_password)");//fields of this table are. usename,email,game_password

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists player");
        onCreate(db);
    }
}
