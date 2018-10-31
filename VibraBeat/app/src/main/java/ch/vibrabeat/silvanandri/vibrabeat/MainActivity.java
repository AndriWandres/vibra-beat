package ch.vibrabeat.silvanandri.vibrabeat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
    }

    public void record(View view) {
        Intent intent = new Intent(this, RecordingActivity.class);
        startActivity(intent);
    }

    public void mybeats(View view) {
        Intent intent = new Intent(this, MyBeatsActivity.class);
        startActivity(intent);
    }
}
