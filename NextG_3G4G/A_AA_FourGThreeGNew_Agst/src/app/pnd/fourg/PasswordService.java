package app.pnd.fourg;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.appnextg.fourg.R;

public class PasswordService extends Service {
    //public static int  times=0;
    public static boolean fulladsenable = false;
    public static boolean screen_is_on = true;
    public static ArrayList<String> pacArrayList = new ArrayList<String>();
    DataBaseHandler dataBaseHandler;
    Timer timer;

    public String current_package = "abpk";

    public static String running_package, running_package_class;
    public static PasswordService listservic;
    public static boolean password_enable = true;

    SharedPreferences app_list;
    static Context ctx;


    public IBinder onBind(Intent intent) {
        return null;
    }

    public static PasswordService data() {
        if (listservic == null) {
            listservic = new PasswordService();
        }
        return listservic;
    }

    public void onCreate() {

        ctx = getApplicationContext();

        registerOnGoingNotificationBroadcastReceiver();


        try {

        } catch (Exception e) {
        }

        super.onCreate();
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        dataBaseHandler = new DataBaseHandler(ctx);


        start_Service();

        return START_STICKY;
    }

    public void start_Service() {
//		if(new Utils().get_notification(ctx))
//		createNotification(true,ctx);
//		else
//			createNotification(false,ctx);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                createNotification2(ctx, true);

            }
        }, 2 * 60 * 60 * 1000, 8 * 60 * 60 * 1000);
    }

    //10800000
    public void onDestroy() {
        // Toast.makeText(getApplicationContext(), "servcie is stoped",
        // Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    ;


    public void createNotification2(Context ct, boolean status) {
        //if(new Utils().get_notification(ctx)){
        if (status) {
            final NotificationManager mgr =
                    (NotificationManager) ct.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification note = new Notification(R.drawable.version_ic_icon,
                    "Cache Cleaner Alert",
                    System.currentTimeMillis());
            PendingIntent i = PendingIntent.getActivity(ct, 0,
                    new Intent(ct, AppLauncher.class),
                    0);

//	        note.setLatestEventInfo(ct, "Cache Cleaner Alert",
//	                                "Tap to Clean Cache", i);

            startForeground(1, note);

        } else
            stopForeground(true);


    }

    private void registerOnGoingNotificationBroadcastReceiver() {
        IntentFilter intentFilter = new IntentFilter("ON_GOING_NOTIFICATION");
        getBaseContext().registerReceiver(onGoingNotification, intentFilter);
    }

    BroadcastReceiver onGoingNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("ON_GOING_NOTIFICATION")) {

                createNotification2(context, intent.getBooleanExtra("bool", true));
            }
        }
    };


    public void createNotification(Context context, boolean status) {
        if (status) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
            //System.out.println("NOti Changed "+btry_prct);
            Intent intent = new Intent(context, AppLauncher.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            Intent notificationIntent = new Intent(context, AppLauncher.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent intent_switching = PendingIntent.getActivity(context, 0, notificationIntent, 0);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification(R.drawable.version_ic_icon, null, System.currentTimeMillis());
//		 Notification note=new Notification(R.drawable.ic_launcher,
//                 "Cache Cleaner Alert",
//                 System.currentTimeMillis());

            remoteViews.setTextViewText(R.id.txtViewForNotificationName, "Cache Cleaner Alert");
            remoteViews.setTextViewText(R.id.txtViewForNotificationDescription, "Clean Cache Now");
            remoteViews.setOnClickPendingIntent(R.id.txtViewForNotificationName, intent_switching);
            notification.contentView = remoteViews;
            notification.contentIntent = pendingIntent;
            notification.flags |= Notification.FLAG_NO_CLEAR;
            //return notification;
//		notificationManager.notify(id, notification);
            startForeground(1, notification);
            System.out.println("callig now");
        } else
            stopForeground(true);
    }

  /*  private int getResId(String drawableName) {

        try {
            Class<drawable> res = R.drawable.class;
            Field field = res.getField(drawableName);
            int drawableId = field.getInt(null);
            return drawableId;
        } catch (Exception e) {
            Log.e("COUNTRYPICKER", "Failure to get drawable id.", e);
        }
        return -1;
    }*/


}