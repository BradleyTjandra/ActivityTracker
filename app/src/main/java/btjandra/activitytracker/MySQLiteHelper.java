/**
 * Created by tjandraca on 5/01/2015.
 *
 * A lot of this thanks to http://www.vogella.com/tutorials/AndroidSQLite/article.html
 * */
package btjandra.activitytracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{
    public static final String TABLE_ENTRIES = "entries";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_ACTION = "action";
    public static final String COLUMN_PRODUCTIVITY = "productivity";
    public static final String COLUMN_ENERGY = "energy";

    private static final String DATABASE_NAME = "entries.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table " +
            TABLE_ENTRIES + "(" + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TIMESTAMP + " integer, " + COLUMN_ACTION + " text, " +
            COLUMN_PRODUCTIVITY + " integer, " + COLUMN_ENERGY + " integer);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data -BT");
        db.execSQL("DROP TABLES IF EXISTS " + TABLE_ENTRIES);
        onCreate(db);
    }

    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }


}
