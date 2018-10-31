package ch.vibrabeat.silvanandri.vibrabeat;

import ch.vibrabeat.silvanandri.vibrabeat.model.Beat;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyBeatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_beats);

        Beat[] beats = {
                new Beat("Test Beat 1", ""),
                new Beat("Test Beat 2", ""),
                new Beat("Test Beat 3", ""),
                new Beat("Test Beat 4", ""),
                new Beat("Test Beat 5", ""),
        };

        BeatArrayAdapter adapter = new BeatArrayAdapter(this, beats);

        ListView lv = findViewById(R.id.beatsView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Click ListItem Number " + position, Toast.LENGTH_LONG) .show();
            }
        });
    }

    private class BeatArrayAdapter extends ArrayAdapter<Beat> {
        private Context context;
        private Beat[] beats;

        public BeatArrayAdapter(Context context, Beat[] beats) {
            super(context, -1, beats);

            this.context = context;
            this.beats = beats;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View beatItem = inflater.inflate(R.layout.beat_item, parent, false);
            TextView beatItemName = beatItem.findViewById(R.id.beatItemName);
            beatItemName.setText("Test");
            TextView beatItemTime = beatItem.findViewById(R.id.beatItemTime);
            beatItemTime.setText("10:10");

            return beatItem;
        }
    }
}
