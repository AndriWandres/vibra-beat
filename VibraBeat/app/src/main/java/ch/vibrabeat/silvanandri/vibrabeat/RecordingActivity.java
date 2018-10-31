package ch.vibrabeat.silvanandri.vibrabeat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import ch.vibrabeat.silvanandri.vibrabeat.model.Beat;

public class RecordingActivity extends AppCompatActivity {
    /** Timestamp used for calculating the beat */
    private Date timestamp;

    /** Rhythm in form of milliseconds separated by semicolons */
    private String beatStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        getSupportActionBar().hide();

        findViewById(R.id.recActivity).setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.color.lightBgColor);
                    onTouchDown();
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.color.bgColor);
                    onTouchUp();
                }

                return true;
            }
        });

        findViewById(R.id.stopButton).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                stopRecording();
            }
        });
    }

    /** Stops recording and switches to SaveBeatActivity */
    public void stopRecording() {
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        Beat beat = new Beat(beatStr);
        Log.d("", "Beat: " + beat);

    }

    /** Is fired when finger is placed on the center area */
    private void onTouchDown() {
        Date timestamp_new = new Date();

        if (timestamp != null) {
            long interval = getInterval(timestamp, timestamp_new, TimeUnit.MILLISECONDS);
            beatStr += interval + ";";
        }

        timestamp = timestamp_new;
    }

    /** Is fired when finger is released from the center area */
    private void onTouchUp() {
        Date timestamp_new = new Date();

        long interval = getInterval(timestamp, timestamp_new, TimeUnit.MILLISECONDS);
        beatStr += interval + ";";

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
