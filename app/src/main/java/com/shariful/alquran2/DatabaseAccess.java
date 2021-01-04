package com.shariful.alquran2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static  DatabaseAccess instance;
    Cursor c=null;
    DatabaseAccess(Context context){
        this.openHelper =new DatabaseOpenHelper(context);

    }

    public static DatabaseAccess getInstance(Context context){
        if (instance==null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }
    public void open(){
        this.db = openHelper.getWritableDatabase();
    }
    public void close(){
        if (db!=null){
            this.db.close();
        }
    }

    public List<SuraListModel> getSuraList(){
        SuraListModel suraListModel = null;
        List<SuraListModel> mSuraList = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery("SELECT * FROM qp_sura",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            suraListModel = new SuraListModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9));
            mSuraList.add(suraListModel);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return mSuraList;
    }


// for sura details list
    public List<DetailsModel> getSuraDetails(String arg){
        DetailsModel detailsModel = null;
        List<DetailsModel> suraDetailsList = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery("SELECT * FROM quran_verses WHERE _tag=?",new String[]{arg});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            detailsModel = new DetailsModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
            suraDetailsList.add(detailsModel);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return suraDetailsList;
    }



}
