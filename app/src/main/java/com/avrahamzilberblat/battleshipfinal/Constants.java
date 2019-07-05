package com.avrahamzilberblat.battleshipfinal;

public final class Constants {

    private Constants() {
        // restrict instantiation
    }

    public static final String TAG = "DatabaseHelper";
    public static final String TABLE_NAME_Easy = "Easy_Table";
    public static final String TABLE_NAME_Normal = "Normal_Table";
    public static final String TABLE_NAME_Hard = "Hard_Table";
    public static final int DELAY_MILLS = 3000;
    public static final int TOP_TEN_HIGH_RATIO = 10;
    public static final String COL1 = "ID";
    public static final String COL2 = "name";
    public static final String COL3 = "scoreRatio";
    public static final String COL4 = "lat";
    public static final String COL5 = "long";
    public static final int PERMISSIONS_REQUEST_ENABLE_GPS=9002;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=9003;
    public static final int ERROR_DIALOG_REQUEST = 9001;
    public static final int MISSED = 2;
    public static final int HIT = 1;

}