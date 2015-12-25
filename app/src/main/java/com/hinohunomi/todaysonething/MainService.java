package com.hinohunomi.todaysonething;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

public class MainService extends Service {
    private final static String TAG = "MainService";
    private final static String EXTRA_ALARM = "alarm";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        setSchedule(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        if (intent.getStringExtra(EXTRA_ALARM) != null) {
            Log.d(TAG, "alarm receive");
            doNotify();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public static void start(Context context) {
        Intent serviceIntent = new Intent(context, MainService.class);
        context.startService(serviceIntent);
    }

    private static void setSchedule(Context context) {
        Log.d(TAG, "setSchedule");
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (!isSetPending(context)) {
            Log.d(TAG, "set Alarm");
            Intent intent = new Intent(context, MainService.class);
            intent.putExtra(EXTRA_ALARM, "notice");
            PendingIntent pi = PendingIntent.getService(
                    context,
                    -1,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            //TODO every 1 day
            long time = System.currentTimeMillis();
            long delay = 60 * 1000; //test 1 min
            am.setRepeating(AlarmManager.RTC, time, delay, pi);
            Log.d(TAG, "set Alarm");
        }
    }

    private static boolean isSetPending(Context context) {
        Intent intent = new Intent(context, MainService.class);
        PendingIntent pi = PendingIntent.getService(
                context,
                -1,
                intent,
                PendingIntent.FLAG_NO_CREATE);
        return (pi != null);
    }

    private static void cancelSchedule(Context context) {
        Log.d(TAG, "cancelSchedule");
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, MainService.class);
        PendingIntent pi = PendingIntent.getService(
                context,
                -1,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        am.cancel(pi);
        pi.cancel();
    }

    private void doNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
        builder.setSmallIcon(android.R.drawable.sym_def_app_icon);

        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setTicker("Ticker");
        builder.setContentTitle("Today's One Thing");
        builder.setContentText(Globals.thingManager.GetMessage(this));
        builder.setWhen(System.currentTimeMillis());

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(contentIntent);

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(0, builder.build());
    }
}
