package ch.vibrabeat.silvanandri.vibrabeat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
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
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Read pattern from beat string
        String[] patternStr = beatStr.split(";");
        long[] patternLong = new long[patternStr.length];
        int[] amplitudes = new int[patternLong.length];

        for(int i = 0; i < patternStr.length; i++) {
            patternLong[i] = Long.parseLong(patternStr[i]);
            amplitudes[i] = 255;
        }

        // Vibrate depending on Amplitude support
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            vibrator.vibrate(VibrationEffect.createWaveform(patternLong, amplitudes, -1));
        } else{
            vibrator.vibrate(patternLong, -1);
        }

        Beat beat = new Beat(beatStr);
        Log.d("",beat.getBeatLength());
    }

    /**
     * Saves the beat to the database
     * @param view
     */
    public void saveBeat(View view) {
        Beat beat = new Beat(beatStr);


    }
}
