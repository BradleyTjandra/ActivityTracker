package btjandra.activitytracker;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tjandraca on 7/01/2015.
 */
//Done with the help of http://www.mysamplecode.com/2012/07/android-listview-cursoradapter-sqlite.html

public class DisplayData extends ActionBarActivity {

    private EntriesDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        datasource = new EntriesDataSource(this);
        datasource.open();

        String[] from = {"date",
                MySQLiteHelper.COLUMN_ACTION,
                MySQLiteHelper.COLUMN_PRODUCTIVITY,
                MySQLiteHelper.COLUMN_ENERGY
        };
        int[] to = {R.id.date_text_view,
                R.id.action_text_view,
                R.id.productivity_text_view,
                R.id.energy_text_view
        };
        //I want this to be a table, but it seems listView adapters are only really designed to hold
        // a single string value in a textView
        ListView listView = (ListView)findViewById(R.id.display_data_table);
        Cursor cursor = datasource.getCursorForDisplayData();

//        CharSequence text = "Cursor length: " + cursor.getCount();
//        int duration = Toast.LENGTH_SHORT;
//        Toast.makeText(getApplicationContext(), text, duration).show();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.display_item_list_item,
                cursor,
                from,
                to,
                0);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}
