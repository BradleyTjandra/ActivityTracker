package btjandra.activitytracker; /**
 * Created by tjandraca on 5/01/2015.
 *
 * Needs methods to get types of data e.g. group by month, by hour, etc.
 */

import java.security.InvalidParameterException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EntriesDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TIMESTAMP,
            MySQLiteHelper.COLUMN_ACTION,
            MySQLiteHelper.COLUMN_PRODUCTIVITY,
            MySQLiteHelper.COLUMN_ENERGY};

    public EntriesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        // if it's writable is it also readable?
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Entry createEntry(long timestamp, String action, int productivity, int energy) {

        // put the values of the entry into a ContentValues object
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, timestamp);
        values.put(MySQLiteHelper.COLUMN_ACTION, action);
        values.put(MySQLiteHelper.COLUMN_PRODUCTIVITY, productivity);
        values.put(MySQLiteHelper.COLUMN_ENERGY, energy);

        // insert this data a new row in the SQL table, storing the location of this entry
        long insertId = database.insert(MySQLiteHelper.TABLE_ENTRIES, null, values);

        // we now query the database to find the row we just added, so we have the correct ID
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ENTRIES,
                allColumns,
                MySQLiteHelper.COLUMN_ID + " = " + insertId,
                null,null, null, null);
        cursor.moveToFirst();

        // this creates the new comment
        Entry newEntry = cursorToEntry(cursor);
        cursor.close();
        return newEntry;
    }

    public void deleteEntry(Entry entry) {
        long id = entry.getID();
        System.out.println("btjandra.activitytracker.Entry deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_ENTRIES, MySQLiteHelper.COLUMN_ID
            + " = " + id, null);
    }

    public List<Entry> getAllEntries() {
        List<Entry> entries = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_ENTRIES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Entry entry = cursorToEntry(cursor);
            entries.add(entry);
            cursor.moveToNext();
        }

        cursor.close();
        return entries;
    }

    public Cursor getCursorForDisplayData() {

        String[] columns = {
                MySQLiteHelper.COLUMN_ID,
                "strftime('%H:%M %d/%m/%Y', " + MySQLiteHelper.COLUMN_TIMESTAMP + "/1000, 'unixepoch') AS date",
                MySQLiteHelper.COLUMN_ACTION,
                MySQLiteHelper.COLUMN_PRODUCTIVITY,
                MySQLiteHelper.COLUMN_ENERGY};
        String sortBy = MySQLiteHelper.COLUMN_TIMESTAMP + " asc";

        return database.query(MySQLiteHelper.TABLE_ENTRIES,
                columns, null, null, null, null, sortBy);
    }

    //TODO: Complete this
//    public Entry getLastEntry() {
//        Cursor cursor = database.query(MySQLiteHelper.TABLE_ENTRIES,
//                allColumns, "")
//    }

    private Entry cursorToEntry(Cursor cursor) throws InvalidParameterException {
        Entry entry = new Entry();
        entry.setID(cursor.getLong(0));
        entry.setTimestamp(cursor.getLong(1));
        entry.setAction(cursor.getString(2));
        entry.setProductivity(cursor.getInt(3));
        entry.setEnergy(cursor.getInt(4));
        return entry;

    }
}
