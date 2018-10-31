package ch.vibrabeat.silvanandri.vibrabeat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DbManager {
    private static final String LOG_TAG = "DBACCESS";

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public DbManager(Context context) {
        Log.d(LOG_TAG, "DBMAnager erzeugt DBHelper.");
        dbHelper = new DbHelper(context);
    }

    public void open() {
        Log.d(LOG_TAG, "Eine Referenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: " + database.getPath());
    }

    public void close() {
        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }
}