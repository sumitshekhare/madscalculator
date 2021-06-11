package com.androidcodr.mvvmcalculator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidcodr.mvvmcalculator.model.LoginModel;
import com.androidcodr.mvvmcalculator.model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDBhelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "calculationmaster.db";

    private static final String TABLE_TRAN = "TransactionMaster";

    private static final String TABLE_ACC = "AccountMaster";


    private String A_accid = "AccId";
    private String A_accname = "AccName";
    private String A_password = "Password";


    private String T_tranid = "TranId";
    private String T_tranaccid = "AccId";
    private String T_trcalculation = "Calculation";


    public SQLiteDBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public boolean addtransaction(HistoryModel site) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T_tranaccid, site.getUser());
        values.put(T_trcalculation, site.getCalculation());
        db.insert(TABLE_TRAN, null, values);
        return true;
    }

    public boolean isAccExists(String user, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println(" checking " + user);
        Cursor cursor = db.rawQuery("select 1 FROM " + TABLE_ACC + " where " + A_accname + "=? and " + A_password + "=?", new String[]{user, pass});
        boolean exists = (cursor.getCount() > 0);
        System.out.println(" exist " + exists);
        return exists;
    }


    public ArrayList<HistoryModel> getAllDateTransData(String user) {
        ArrayList<HistoryModel> siteList1 = new ArrayList<HistoryModel>();
        // Select All Query

        //String selectQuery = "select AccId, Calculation from TransactionMaster where AccId == '" + user + "' ";
        String selectQuery = "select * from TransactionMaster where AccId == '" + user + "' order by ID DESC" ;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HistoryModel site1 = new HistoryModel("" + cursor.getString(1),
                        "" + cursor.getString(2));
                // Adding contact to list
                siteList1.add(site1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return contact list
        return siteList1;
    }


}
