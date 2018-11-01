package ch.vibrabeat.silvanandri.vibrabeat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.orm.SugarContext;

import ch.vibrabeat.silvanandri.vibrabeat.database.DbManager;

public class MainActivity extends AppCompatActivity {
    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        SugarContext.init(this);
        dbManager = new DbManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbManager.close();
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
