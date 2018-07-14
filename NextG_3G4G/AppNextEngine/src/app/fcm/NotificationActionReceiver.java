package app.fcm;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import app.PrintLog;
import app.adshandler.DataHub;
import app.server.v2.DataHubConstant;

/**
 * Created by rajeev on 10/04/18.
 */

public class NotificationActionReceiver extends BroadcastReceiver {
    Intent intent1, intent2;
    int TYPE_4;

    @Override
    public void onReceive(Context context, Intent intent) {
        PrintLog.print("NotificationActionReceiver.onReceive ");

            if (intent.getAction().equalsIgnoreCase("sec_btn")) {
            TYPE_4 = intent.getIntExtra("TYPE_4", 0);
            String clickType2 = intent.getStringExtra("sec_btn_type");
            String clickValue2 = intent.getStringExtra("sec_btn_value");

            PrintLog.print("NotificationActionReceiver.onReceive 01 " + clickType2 + " " + clickValue2);
            if (clickType2.equalsIgnoreCase("url")) {
                intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(clickValue2));
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);

            } else if (clickType2.equalsIgnoreCase("deeplink")) {
                intent2 = new Intent(DataHubConstant.CUSTOM_ACTION);
                intent2.addCategory(context.getPackageName());
                intent2.putExtra(MapperUtils.keyValue, clickValue2);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);

            } else {
                intent2 = new Intent(DataHubConstant.CUSTOM_ACTION);
                intent2.addCategory(context.getPackageName());
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            }
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(TYPE_4);
        }
    }
}
