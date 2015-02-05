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

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tjandraca on 8/01/2015.
 */
public class NewEntryNotificationService extends Service {

    EntriesDataSource database;

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {

        database = new EntriesDataSource(getApplicationContext());
        database.open();
        Calendar before = Calendar.getInstance();
        before.setTimeInMillis(database.getLastEntry().getTimestamp());

        Calendar notification_interval = Calendar.getInstance();
        notification_interval.set(Calendar.HOUR_OF_DAY, notification_interval.get(Calendar.HOUR_OF_DAY)-1);
        notification_interval.set(Calendar.MINUTE, 30);

        if (notification_interval.after(before)) {

            Log.d("Bradley's log", "Notification starting!");
            NotificationCompat.Builder mBuilder;
            mBuilder = new NotificationCompat.Builder(this);
            //get a notification icon
            mBuilder.setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("Whatcha doing?")
                    .setContentText("Time to add a new entry")
                    .setAutoCancel(true);

            Intent newIntent = new Intent(this, NewEntry.class);

            //stack builder craziness
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(NewEntry.class);
            stackBuilder.addNextIntent(newIntent);
            PendingIntent pi = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pi);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, mBuilder.build());

            return START_STICKY;
        } else {
            return START_NOT_STICKY;
        }
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//        Log.d("Bradley's log", "Notification starting!");
//        NotificationCompat.Builder mBuilder;
//        mBuilder = new NotificationCompat.Builder(this);
//        //TODO: get a notification icon
//        mBuilder.setSmallIcon(R.drawable.ic_launcher)
//                .setContentTitle("Whatcha doing?")
//                .setContentText("Time to add a new entry");
//
//        Intent newIntent = new Intent(this, NewEntry.class);
//
//        //stack builder craziness
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(NewEntry.class);
//        stackBuilder.addNextIntent(newIntent);
//        PendingIntent pi = stackBuilder.getPendingIntent(0,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pi);
//
//        NotificationManager notificationManager =
//                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0, mBuilder.build());
//    }

    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
