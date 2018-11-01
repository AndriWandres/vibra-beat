package ch.vibrabeat.silvanandri.vibrabeat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import ch.vibrabeat.silvanandri.vibrabeat.model.Beat;

public class SaveActivity extends AppCompatActivity {
    private String beatStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Intent intent = getIntent();
        beatStr = intent.getStringExtra("beatStr");
    }

    /**
     * Plays the recorded beat
     * @param view
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
        Beat beat = new Beat(beatStr);


    }
}
