package ch.vibrabeat.silvanandri.vibrabeat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Displays mask where a rhythm is recorded via the users input.
 */
public class RecordingActivity extends AppCompatActivity {
    /** Timestamp used for calculating the beat */
    private Date timestamp;

    /** Rhythm in form of milliseconds separated by semicolons */
    private String beatStr = "0";

    /** Indicator whether the phone is vibrating */
    private boolean vibrating = false;

    /** Service to control hardware vibration */
    private Vibrator vibrator;

    /**
     * Is fired on creation of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        getSupportActionBar().hide();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Add touch listener on whole activity
        findViewById(R.id.recActivity).setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.color.lightBgColor);

                    vibrating = true;

                    // Vibrate while the screen is being touched
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            while (vibrating) {
                                vibrator.vibrate(100);
                            }
                        }
                    };
                    t.start();

                    onTouchDown();
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.color.bgColor);

                    // Stop vibrating when finger is released from the screen
                    vibrating = false;

                    onTouchUp();
                }

                return true;
            }
        });

        // Add click listener on stop button
        findViewById(R.id.stopButton).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                stopRecording();
            }
        });
    }

    /** Stops recording and navigates to SaveActivity */
    public void stopRecording() {
        Intent intent = new Intent(this, SaveActivity.class);
        intent.putExtra("beatStr", beatStr);

        startActivity(intent);
    }

    /** Is fired when finger is placed on the center area */
    private void onTouchDown() {
        Date timestamp_new = new Date();

        if (timestamp != null) {
            long interval = getInterval(timestamp, timestamp_new, TimeUnit.MILLISECONDS);
            beatStr += ";" + interval;
        }

        timestamp = timestamp_new;
    }

    /** Is fired when finger is released from the center area */
    private void onTouchUp() {
        Date timestamp_new = new Date();

        long interval = getInterval(timestamp, timestamp_new, TimeUnit.MILLISECONDS);
        beatStr += ";" + interval;

        timestamp = timestamp_new;
    }

    /**
     * Get the interval between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the interval
     * @return the interval, in the provided unit
     */
    private long getInterval(Date date1, Date date2, TimeUnit timeUnit) {
        long intervalMilliseconds = date2.getTime() - date1.getTime();
        return timeUnit.convert(intervalMilliseconds, TimeUnit.MILLISECONDS);
    }
}
