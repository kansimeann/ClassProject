package com.example.kansimeannet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelperClass extends SQLiteOpenHelper {

    public SqliteHelperClass(Context context){
        super(context,"sqlite_db",null,1);
    }
    public void onCreate(SQLiteDatabase db){
db.execSQL("CREATE table users(id integer primary key autoincrement not null, NAME varchar(100) not null,EMAIL varchar(100) not null,PASSWORD varchar(100) not null)");
    }
    public void onUpgrade(SQLiteDatabase db,int newVersion,int oldVersion){
        db.execSQL("DROP table if  exists users");
        onCreate(db);
    }
    public boolean sendData(String message){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Message",message);
    long send=db.insert("daily_messages",null,contentValues);

    if (send==-1)
        return false;
    else
        return true;

    }

}