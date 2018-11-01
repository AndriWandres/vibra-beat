package ch.vibrabeat.silvanandri.vibrabeat;

import ch.vibrabeat.silvanandri.vibrabeat.model.Beat;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
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

        // Sets the ActionBar Title
        setTitle("My Beats");

        // Displays the actionbar back button to return to the parent which was specified in the manifest
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

        // Prepare ListView Adapter to create the items which will be displayed
        BeatArrayAdapter adapter = new BeatArrayAdapter(this, beats);

        // Set List View Adapter and onItemClick
        ListView lv = findViewById(R.id.beatsView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Sets the clicked Item in view to be able to use it in the Handler
                viewItem = view;
                view.findViewById(R.id.relativeLayout).setBackgroundResource(R.color.lightBgColor);

                // Runs the beatstring
                beats.get(position).runBeatString((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));

                // Gets the beatstring and calculates it's length
                String[] strings = beats.get(position).getBeatString().split(";");
                long time = 0;
                for(String s : strings) {
                    time += Integer.parseInt(s);
                }

                // Starts the timer which runs the handler handleMessage by sending an empty message after it waited time milliseconds
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

        /**
         * Creates a BeatItem view and returns it
         * @param position
         * @param convertView
         * @param parent
         * @return BeatItem
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Gets the inflater and creates a BeatItem View with the inflater
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View beatItem = inflater.inflate(R.layout.beat_item, parent, false);

            // Sets the text view which displays the beat name
            TextView beatItemName = beatItem.findViewById(R.id.beatItemName);
            beatItemName.setText(beats.get(position).getName());

            // Sets the text view which displays the time
            TextView beatItemTime = beatItem.findViewById(R.id.beatItemTime);
            beatItemTime.setText(beats.get(position).getBeatLength());

            // Sets the tag of the deleteText of the BeatItem to the position in the list
            beatItem.findViewById(R.id.deleteText).setTag(position);

            // Sets an onClickListener on the deleteText TextView
            beatItem.findViewById(R.id.deleteText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openConfirmDialog(v);
                }
            });

            return beatItem;
        }

        /**
         * Opens a confirm dialog before a beat is deleted
         * @param view View in where the dialog is placed
         */
        private void openConfirmDialog(final View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MyBeatsActivity.this)
                .setCancelable(true)
                .setTitle(R.string.confirm_dialog_title)
                .setMessage(R.string.confirm_dialog_message)
                .setIcon(android.R.drawable.ic_dialog_alert)

                // Callback for when the "confirm" option is clicked
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Removes the item from the database and the list and updates the view
                        beats.get((int) view.getTag()).delete();
                        beats.remove(beats.get((int) view.getTag()));

                        notifyDataSetChanged();
                    }
                })

                // Callback for when the "cancel" option is clicked
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
