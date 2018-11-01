package ch.vibrabeat.silvanandri.vibrabeat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orm.SugarContext;

import ch.vibrabeat.silvanandri.vibrabeat.database.DbManager;

/**
 * Main activity of Vibra Beat.
 * Displays a play button to start recording.
 */
public class MainActivity extends AppCompatActivity {
    /** DbManager instance */
    private DbManager dbManager;

    /**
     * Is fired on creation of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        SugarContext.init(this);
        dbManager = new DbManager(this);
    }

    /**
     * Is fired when the activity enters the state in where it can interact with the user
     * e.g after the activity has been paused by onPause() and then resumed again.
     * Opens the database connection.
     */
    @Override
    protected void onResume() {
        super.onResume();
        dbManager.open();
    }

    /**
     * Is fired when the activity enters the paused state.
     * Closes the database connection.
     */
    @Override
    protected void onPause() {
        super.onPause();
        dbManager.close();
    }

    /**
     * Starts a recording and navigates to RecordingActivity.
     * @param view User interface component
     */
    public void record(View view) {
        Intent intent = new Intent(this, RecordingActivity.class);
        startActivity(intent);
    }

    /**
     * Navigates to the MyBeatsActivity.
     * @param view User interface component
     */
    public void mybeats(View view) {
        Intent intent = new Intent(this, MyBeatsActivity.class);
        startActivity(intent);
    }
}
