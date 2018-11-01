package ch.vibrabeat.silvanandri.vibrabeat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import ch.vibrabeat.silvanandri.vibrabeat.model.Beat;

public class SaveActivity extends AppCompatActivity {
    private String beatStr;

    /**
     * Is fired on creation of the activity.
     * Retrieves the beatstring provided by the RecordingActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Intent intent = getIntent();
        beatStr = intent.getStringExtra("beatStr");
    }

    /**
     * Plays the recorded beat
     * @param view User interface component
     */
    public void playRecording(View view) {
        Beat beat = new Beat("0;" + beatStr);
        Log.d("",beat.getBeatLength());

        beat.runBeatString((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));
    }

    /**
     * Saves the beat to the database
     * @param view
     */
    public void saveBeat(View view) {
        EditText editText = findViewById(R.id.name_input);
        String name = editText.getText().toString();

        // Save beat to database
        if (name != null && !name.trim().equals("")) {
            Beat beat = new Beat(name, "0;" + beatStr);
            beat.save();
        }
    }

    /**
     * Navigate to main menu
     * @param view
     */
    public void dismiss(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
