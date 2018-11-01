package ch.vibrabeat.silvanandri.vibrabeat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = "DB_ACCESS";

    public static final String DB_NAME = "vibrabeat2.db";
    public static final String TABLE_BEAT = "BEAT";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_BEAT_STRING = "BEAT_STRING";

    public static final int DB_VERSION = 1;

    public static final String SQL_CREATE_BEAT =
            "CREATE TABLE " + TABLE_BEAT + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT NOT NULL, " + COLUMN_BEAT_STRING + " TEXT NOT NULL);";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper created database: " + getDatabaseName());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
        db.execSQL(SQL_CREATE_BEAT);
        Log.d(LOG_TAG, "Table " + TABLE_BEAT + " was created: " + SQL_CREATE_BEAT);
    }
        catch (Exception ex) {
        Log.e(LOG_TAG, "Error while creating table: " + ex.getMessage());
    }
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(LOG_TAG, "onUpgrade was called" );
    }
}
