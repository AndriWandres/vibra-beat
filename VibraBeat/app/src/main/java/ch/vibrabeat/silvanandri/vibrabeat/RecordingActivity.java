package ch.vibrabeat.silvanandri.vibrabeat;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class RecordingActivity extends AppCompatActivity {
    private boolean vibrating = false;

    private Vibrator vib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        getSupportActionBar().hide();

        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        findViewById(R.id.recActivity).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setBackgroundResource(R.color.lightBgColor);

                    vibrating = true;

                    Thread t = new Thread() {
                        @Override
                         public void run() {
                            while (vibrating) {
                                vib.vibrate(100);
                            }
                        }
                    };
                    t.start();
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    v.setBackgroundResource(R.color.bgColor);

                    vibrating = false;
                }

                return true;
            }
        });

        findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }
        });
    }

    public void test() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
