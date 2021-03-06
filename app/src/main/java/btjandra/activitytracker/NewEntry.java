package btjandra.activitytracker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// I found http://sqlfiddle.com/ infinitely useful here for finding the most recent date.
public class NewEntry extends ActionBarActivity {

    private EntriesDataSource datasource;
    private Date before;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);

        datasource = new EntriesDataSource(this);
        datasource.open();

        // writes the time of last entry to GUI
        Intent intent = getIntent();
        TextView prev_time = (TextView)findViewById(R.id.prevtime_text_view);
        before = new Date(datasource.getLastEntry().getTimestamp());
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
        prev_time.setText(df.format(before));

        // TODO: Make the judgement call on whether it's worth updating to API 21 or not
//        TextClock cv = (TextClock)findViewById(R.id.curtime_text_clock);
//        prev_time.setFontFeatureSettings(cv.getFontFeatureSettings());


        // TODO: create a spinner adapter to allow the user to pick a role
        // fills in the list of roles
//        Spinner spinner = (Spinner)findViewById(R.id.role_spinner);
//        Spinner
//        spinner.setAdapter(spinnerAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_adder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSubmit(View view) {
        //get the info from the GUI somehow
        Date now = new Date();
        long timestamp = now.getTime();
        before = now;

        EditText action_editTextId = (EditText)findViewById(R.id.action_edit_text);
        String action = action_editTextId.getText().toString();
        if (action == "") {
            action = "Other";
        }

        RadioGroup productivity_radio_group = (RadioGroup)findViewById(R.id.prod_radio_group);
        int radioButtonId = productivity_radio_group.getCheckedRadioButtonId();
        int productivity=0;
        switch (radioButtonId) {
            case R.id.productivity1:
                productivity = 1;
                break;
            case R.id.productivity2:
                productivity = 2;
                break;
            case R.id.productivity3:
                productivity = 3;
                break;
            case R.id.productivity4:
                productivity = 4;
                break;
            case R.id.productivity5:
                productivity = 5;
                break;
            default:
                Context context = getApplicationContext();
                CharSequence text = "Please select a productivity value";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return;
        }

        RadioGroup energy_radio_group = (RadioGroup)findViewById(R.id.energy_radio_group);
        int energy_radioButtonId = energy_radio_group.getCheckedRadioButtonId();
        int energy = 0;
        switch (energy_radioButtonId) {
            case R.id.energy1:
                energy = 1;
                break;
            case R.id.energy2:
                energy = 2;
                break;
            case R.id.energy3:
                energy = 3;
                break;
            case R.id.energy4:
                energy = 4;
                break;
            case R.id.energy5:
                energy = 5;
                break;
            default:
                Context context = getApplicationContext();
                CharSequence text = "Please select a energy value";
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
                return;
        }

        try {
            datasource.createEntry(timestamp, action, productivity, energy);
        }
        catch (InvalidParameterException exception) {
            Context context = getApplicationContext();
            CharSequence text = "Something's wrong! :S";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();

            return;
        }
        Context context = getApplicationContext();
        CharSequence text = "Submitted entry to records";
        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(context, text, duration).show();

//        if ((new Calendar(now.getTime())).get(Calendar.HOUR_OF_DAY) > 14) {
//            I guess I could remind myself here to drink water??
//        }

        this.finish();
    }
}
