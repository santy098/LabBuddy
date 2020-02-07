package com.example.labbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseConnSqlite extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Lab.db";
    public static final String TABLE_NAME = "Labud";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "PHONENO";
    public static final String COL_5 = "FEEDBACK";
    public static final String COL_6 = "CURRENTVERSION";
    public static final String COL_7 = "NEWVERSOIN";
    public static final String COL_8 = "UID";
    public static final String COL_9 = "UIDTYPE";
    public static final String COL_10 = "ROLE";

    public DatabaseConnSqlite(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +" (ID TEXT,USERNAME TEXT,PASSWORD TEXT,PHONENO TEXT,FEEDBACK TEXT,CURRENTVERSION TEXT,NEWVERSOIN TEXT,UID TEXT,UIDTYPE TEXT,ROLE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        //onCreate(db);
    }

    public boolean insertData(String id,String uname,String pass,String phone,String feedback,String currentversion,String newversion,String uid,String uidtype,String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,uname);
        contentValues.put(COL_3,pass);
        contentValues.put(COL_4,phone);
        contentValues.put(COL_5,feedback);
        contentValues.put(COL_6,currentversion);
        contentValues.put(COL_7,newversion);
        contentValues.put(COL_8,uid);
        contentValues.put(COL_9,uidtype);
        contentValues.put(COL_10,role);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public void updatedata(String feedback){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_5,feedback);
        db.update(TABLE_NAME,contentValues,null,null);
    }

    public void updatedata1(String currentversion){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6,currentversion);
        db.update(TABLE_NAME,contentValues,null,null);
    }

    public void updatedata2(String newversion){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_7,newversion);
        db.update(TABLE_NAME,contentValues,null,null);
    }

    public Integer deleteData () {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID=1",null);
    }
    
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * from Labud", null );
        return res;
    }
   /* public boolean checkValidity() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        c.moveToFirst();
        if(c == null){
            return true;
        }
        else{
            return false;
        }
    }*/
}
