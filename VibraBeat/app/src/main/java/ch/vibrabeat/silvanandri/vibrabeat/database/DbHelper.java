package ch.vibrabeat.silvanandri.vibrabeat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Utility class for creating the database.
 */
public class DbHelper extends SQLiteOpenHelper {
    /** Tag for writing debug logs */
    private static final String LOG_TAG = "DB_ACCESS";

    /** Constant string with the database name */
    public static final String DB_NAME = "vibrabeat2.db";

    /** Constant string with the beats table name */
    public static final String TABLE_BEAT = "BEAT";

    /** Constant string with the name of the primary key column "ID" */
    public static final String COLUMN_ID = "ID";

    /** Constant string with the name of the "Name" column */
    public static final String COLUMN_NAME = "NAME";

    /** Constant string with the name of the "BeatString" column */
    public static final String COLUMN_BEAT_STRING = "BEAT_STRING";

    /** Constant integer with the database version */
    public static final int DB_VERSION = 1;

    /** Constant string with the sql command that creates the database */
    public static final String SQL_CREATE_BEAT =
            "CREATE TABLE " + TABLE_BEAT + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_BEAT_STRING + " TEXT NOT NULL);";

    /**
     * Creates a new DbHelper
     * @param context Information about application environment
     */
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper created database: " + getDatabaseName());
    }

    /**
     * Is fired once when the database has not yet been created.
     * Executes the database create script.
     * @param db SQLite database instance to be created
     */
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

    /**
     * Is fired when a new patch is sent to the database.
     * e.g a new table is registered.
     * @param db SQLite Database instance
     * @param oldVersion old database version number
     * @param newVersion new database version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(LOG_TAG, "onUpgrade was called" );
    }
}
