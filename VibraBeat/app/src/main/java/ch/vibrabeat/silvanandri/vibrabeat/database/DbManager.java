package ch.vibrabeat.silvanandri.vibrabeat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Manages the database connection
 */
public class DbManager {
    /** Tag for writing debug logs */
    private static final String LOG_TAG = "DB_ACCESS";

    /** SQLiteDatabase instance */
    private SQLiteDatabase database;

    /** DbHelper instance */
    private DbHelper dbHelper;

    /**
     * Creates a new DbManager
     * @param context Information about application environment
     */
    public DbManager(Context context) {
        Log.d(LOG_TAG, "DbManager creates DbHelper.");
        dbHelper = new DbHelper(context);
    }

    /** Opens a database connection */
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
