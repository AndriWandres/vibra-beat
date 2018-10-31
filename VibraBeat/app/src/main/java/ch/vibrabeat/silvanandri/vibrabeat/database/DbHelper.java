package ch.vibrabeat.silvanandri.vibrabeat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = "DBACCESS";

    public static final String DB_NAME = "vibrabeat.db";
    public static final int DB_VERSION = 1;

    public static final String SQL_CREATE_BEAT =
            "CREATE TABLE Beat (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, beatString TEXT NOT NULL);";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(SQL_CREATE_BEAT);
            Log.d(LOG_TAG, "Die Tabelle Beat wird mit SQL-Befehl: " + SQL_CREATE_BEAT + " angelegt.");
        }
        catch (Exception ex) {
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(LOG_TAG, "onUpgrade wurde aufgerufen" );
    }
}
