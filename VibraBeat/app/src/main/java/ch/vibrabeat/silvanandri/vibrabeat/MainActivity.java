package ch.vibrabeat.silvanandri.vibrabeat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private String beat = "";
    private Date timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    onTouchDown();
                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    onTouchUp();
                }

                return true;
            }
        });
    }

    private void onTouchDown() {
        Log.d("MAINACTIVITY", "Touch down event detected");
        Date timestamp_new = new Date();

        if (timestamp != null) {
            long interval = getInterval(timestamp, timestamp_new, TimeUnit.MILLISECONDS);

            beat += interval + ";";
        }

        timestamp = timestamp_new;
    }

    private void onTouchUp() {
        Log.d("MAINACTIVITY", "Touch up event detected");
        Date timestamp_new = new Date();
        long interval = getInterval(timestamp, timestamp_new, TimeUnit.MILLISECONDS);

        beat += interval + ";";
        timestamp = timestamp_new;

        Log.d("MAINACTIVITY", "Beat: " + beat);
    }

    private long getInterval(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
