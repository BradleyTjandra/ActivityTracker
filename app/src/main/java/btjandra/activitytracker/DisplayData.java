package btjandra.activitytracker;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Date;
import java.util.List;

/**
 * Created by tjandraca on 7/01/2015.
 */
public class DisplayData extends ActionBarActivity {

    private EntriesDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        datasource = new EntriesDataSource(this);
        datasource.open();

        //I want this to be a table, but it seems listView adapters are only really designed to hold
        // a single string value in a textView
        ListView listView = (ListView)findViewById(R.id.display_data_table);
        List<Entry> values = datasource.getAllEntries();
        ArrayAdapter<Entry> adapter = new ArrayAdapter<>(this,
                R.layout.display_item_list_item,
                values);
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
