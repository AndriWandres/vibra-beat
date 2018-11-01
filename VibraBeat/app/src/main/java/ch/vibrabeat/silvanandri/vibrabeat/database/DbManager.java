package ch.vibrabeat.silvanandri.vibrabeat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbManager {
    private static final String LOG_TAG = "DB_ACCESS";

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbManager(Context context) {
        Log.d(LOG_TAG, "DbManager creates DbHelper.");
        dbHelper = new DbHelper(context);
    }


    public void open() {
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Database reference received. Path to database: " + database.getPath());
    }

    /** Closes the database connection */
    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Database closed with the help of DbHelper.");
    }
}
