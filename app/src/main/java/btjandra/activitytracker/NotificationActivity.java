package btjandra.activitytracker;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by tjandraca on 8/01/2015.
 */
public class NotificationActivity extends Activity {

//    @Override
//    public void onCreate()
//    {
//        // TODO Auto-generated method stub
//        super.onCreate();
//    }
@Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Bradley's log", "Notification starting!");
        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(this);
        //get a notification icon
        mBuilder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Whatcha doing?")
                .setContentText("Time to add a new entry");

        Intent newIntent = new Intent(this, NewEntry.class);

        //stack builder craziness
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NewEntry.class);
        stackBuilder.addNextIntent(newIntent);
        PendingIntent pi = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, mBuilder.build());

//        return 0;
    }

    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
