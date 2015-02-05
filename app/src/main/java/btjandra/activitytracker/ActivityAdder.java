package btjandra.activitytracker;

// huge shout out to http://www.vogella.com/tutorials/AndroidSQLite/article.html for its assistance

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.util.Log;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ActivityAdder extends ListActivity {

    private EntriesDataSource datasource;
    private Date before;
    public static final String EXTRA_PREV_TIME = "btjandra.activitytracker.activityadder.LAST_TIME_UTC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        String values[] = new String[] {"New Entry",
        "Analyze Data",
        "See Data",
        "Change Settings",
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.home_page_list_item,
                values);
        setListAdapter(adapter);

        // we'll change this to actually get the last date from SQL
        datasource = new EntriesDataSource(getApplicationContext());
        datasource.open();

        Entry prev_entry = datasource.getLastEntry();
        before = new Date();
        before.setTime(prev_entry.getTimestamp());

        // Creating a recurring alarm to enter into log
        Context context = getApplicationContext();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        SimpleDateFormat df = new SimpleDateFormat("hh:mm");

        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getService(context,
                0,
                new Intent(context, NewEntryNotificationService.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        long intervalMillis = AlarmManager.INTERVAL_HOUR;
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), intervalMillis, pi);
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

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {

        final int NEW_ENTRY = 0;
        final int ANALYZE_DATA = 1;
        final int SEE_DATA = 2;
        final int SETTINGS = 3;

        Intent intent;
        switch (position) {
            case NEW_ENTRY:
                intent = new Intent(this, NewEntry.class);
                startActivity(intent);
                break;
//            case ANALYZE_DATA:
//                break;
            case SEE_DATA:
                intent = new Intent(this, DisplayData.class);
                startActivity(intent);
//            case SETTINGS:
//                break;
        }
    }
}
