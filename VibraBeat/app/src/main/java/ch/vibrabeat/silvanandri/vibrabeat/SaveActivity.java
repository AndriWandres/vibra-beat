package ch.vibrabeat.silvanandri.vibrabeat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ch.vibrabeat.silvanandri.vibrabeat.model.Beat;

public class SaveActivity extends AppCompatActivity {
    private String beatStr;

    /**
     * Is fired on creation of the activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        // Retrieves beatstring from RecordingActivity
        Intent intent = getIntent();
        beatStr = intent.getStringExtra("beatStr");

        // Set the length of the recording
        String length = new Beat(beatStr).getBeatLength();
        TextView textview = findViewById(R.id.recording_length);
        textview.setText(length);
    }

    /**
     * Plays the recorded beat
     * @param view User interface component
     */
    public void playRecording(View view) {
        Beat beat = new Beat(beatStr);

        beat.runBeatString((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));
    }

    /**
     * Saves the beat to the database
     * @param view
     */
    public void saveBeat(View view) {
        EditText editText = findViewById(R.id.name_input);
        String name = editText.getText().toString();

        Log.d("", beatStr);

        // Save beat to database
        if (name != null && !name.trim().equals("")) {
            Beat beat = new Beat(name, "0;" + beatStr);
            beat.save();
        }

        // Navigate to MyBeatsActivity
        Intent intent = new Intent(this, MyBeatsActivity.class);
        startActivity(intent);
    }

    /**
     * Navigate to main menu
     * @param view
     */
    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
