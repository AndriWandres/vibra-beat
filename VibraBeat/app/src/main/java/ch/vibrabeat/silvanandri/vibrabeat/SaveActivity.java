package ch.vibrabeat.silvanandri.vibrabeat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import ch.vibrabeat.silvanandri.vibrabeat.model.Beat;

/**
 * Displays activity where it is possible to save your previous recorded beat.
 */
public class SaveActivity extends AppCompatActivity {
    /** Rhythm pattern of the beat. */
    private String beatStr;

    /** The view of the last beat list item that was selected */
    private ImageView imageView;

    /** Timer instance for scheduling tasks */
    private Timer timer;

    /** Handler for using callbacks */
    private Handler handler;

    /**
     * Is fired on creation of the activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        setTitle("Save Beat");

        // Prepare Handler and Timer
        timer = new Timer();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                imageView.setImageResource(R.drawable.play_button);

                imageView = null;

                return true;
            }
        });

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
        if(imageView == null) {
            imageView = (ImageView) findViewById(R.id.save_play_button);
            imageView.setImageResource(R.drawable.stop_button);

            Beat beat = new Beat(beatStr);

            beat.runBeatString((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));

            String[] strings = beatStr.split(";");
            long time = 0;
            for (String s : strings) {
                time += Integer.parseInt(s);
            }

            // Starts the timer which runs the handler handleMessage by sending an empty message after it waited time milliseconds
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, time);
        } else {
            imageView.setImageResource(R.drawable.play_button);

            imageView = null;

            ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).cancel();
            timer.cancel();
            timer = new Timer();
        }
    }

    /**
     * Saves the beat to the database
     * @param view User interface component
     */
    public void saveBeat(View view) {
        EditText editText = findViewById(R.id.name_input);
        String name = editText.getText().toString();

        Log.d("", beatStr);

        // Save beat to database
        if (name != null && !name.trim().equals("")) {
            Beat beat = new Beat(name, beatStr);
            beat.save();

            // Navigate to MyBeatsActivity
            Intent intent = new Intent(this, MyBeatsActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Navigate to main menu
     * @param view User interface component
     */
    public void cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
