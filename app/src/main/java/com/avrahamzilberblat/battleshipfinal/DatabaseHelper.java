package com.avrahamzilberblat.battleshipfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import static com.avrahamzilberblat.battleshipfinal.Constants.COL1;
import static com.avrahamzilberblat.battleshipfinal.Constants.COL2;
import static com.avrahamzilberblat.battleshipfinal.Constants.COL3;
import static com.avrahamzilberblat.battleshipfinal.Constants.COL4;
import static com.avrahamzilberblat.battleshipfinal.Constants.COL5;
import static com.avrahamzilberblat.battleshipfinal.Constants.TABLE_NAME_Easy;
import static com.avrahamzilberblat.battleshipfinal.Constants.TABLE_NAME_Hard;
import static com.avrahamzilberblat.battleshipfinal.Constants.TABLE_NAME_Normal;
import static com.avrahamzilberblat.battleshipfinal.Constants.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Constructor
     * @param context
     * @param tableName
     */
    public DatabaseHelper(Context context,String tableName) {

        super(context, tableName, null, 10);

        SQLiteDatabase dbEasy=this.getWritableDatabase();
        SQLiteDatabase dbNormal=this.getWritableDatabase();
        SQLiteDatabase dbHard=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME_Easy + " (ID INTEGER PRIMARY KEY, " +
                COL2 +" TEXT,"+COL3+" REAL, "+COL4+" REAL,"+COL5+" REAL)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_Normal + " (ID INTEGER PRIMARY KEY, " +
                COL2 +" TEXT,"+COL3+" REAL, "+COL4+" REAL,"+COL5+" REAL)");
        db.execSQL("CREATE TABLE " + TABLE_NAME_Hard + " (ID INTEGER PRIMARY KEY, " +
                COL2 +" TEXT,"+COL3+" REAL, "+COL4+" REAL,"+COL5+" REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME_Easy);
        onCreate(db);
    }

    /**
     * adds the params to the DataBase based on Table's name
     * @param tableName
     * @param playerDetails
     * @return
     */
    public boolean addData(String tableName,PlayerDetails playerDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2,playerDetails.getWinnerName());
        contentValues.put(COL3,playerDetails.getRatio());
        contentValues.put(COL4,playerDetails.getLat());
        contentValues.put(COL5,playerDetails.getLongitude());

        long result = db.insert(tableName, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database
     * @return
     */

    public Cursor getData(String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + tableName;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */
    public Cursor getItemID(String name,Double scoreRatio){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME_Easy +
                " WHERE " + COL2 + " = '" + name + "'" + " AND " +COL3 + " = '" + scoreRatio+"'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_Easy + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME_Easy + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }

    /**
     * Deletes all the info in the DB
     * @param TABLE_NAME
     */
    public void clearDatabase(String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        db.execSQL(clearDBQuery);
    }

}
