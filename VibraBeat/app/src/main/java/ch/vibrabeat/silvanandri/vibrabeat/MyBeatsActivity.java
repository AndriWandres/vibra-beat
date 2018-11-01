package ch.vibrabeat.silvanandri.vibrabeat;

import ch.vibrabeat.silvanandri.vibrabeat.model.Beat;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This activity displays all beats that have been saved to the database.
 */
public class MyBeatsActivity extends AppCompatActivity {
    private View viewItem;

    private Timer timer;

    private Handler handler;

    /**
     * Is fired on creation of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_beats);

        setTitle("My Beats");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Prepare Handler and Timer
        timer = new Timer();
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                viewItem.findViewById(R.id.relativeLayout).setBackgroundResource(R.color.bgColor);

                return true;
            }
        });

        // Load Data
        final List<Beat> beats =  Beat.listAll(Beat.class);

        // Prepare ListView Adapter
        BeatArrayAdapter adapter = new BeatArrayAdapter(this, beats);

        // Set List View Adapter and onItemClick
        ListView lv = findViewById(R.id.beatsView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewItem = view;
                view.findViewById(R.id.relativeLayout).setBackgroundResource(R.color.lightBgColor);

                beats.get(position).runBeatString((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));

                String[] strings = beats.get(position).getBeatString().split(";");
                long time = 0;
                for(String s : strings) {
                    time += Integer.parseInt(s);
                }

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(0);
                    }
                }, time);
            }
        });
    }

    private class BeatArrayAdapter extends ArrayAdapter<Beat> {
        private Context context;
        private List<Beat> beats;

        /**
         * Creates a new BeatArrayAdapter
         * @param context Activity context
         * @param beats List of beats
         */
        public BeatArrayAdapter(Context context, List<Beat> beats) {
            super(context, -1, beats);

            this.context = context;
            this.beats = beats;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View beatItem = inflater.inflate(R.layout.beat_item, parent, false);

            TextView beatItemName = beatItem.findViewById(R.id.beatItemName);
            beatItemName.setText(beats.get(position).getName());

            TextView beatItemTime = beatItem.findViewById(R.id.beatItemTime);
            beatItemTime.setText(beats.get(position).getBeatLength());

            beatItem.findViewById(R.id.deleteText).setTag(position);

            beatItem.findViewById(R.id.deleteText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    beats.get((int) v.getTag()).delete();
                    beats.remove(beats.get((int) v.getTag()));

                    notifyDataSetChanged();
                }
            });

            return beatItem;
        }
    }
}
