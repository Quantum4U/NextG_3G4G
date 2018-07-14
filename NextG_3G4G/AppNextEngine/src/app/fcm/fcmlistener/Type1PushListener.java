package app.fcm.fcmlistener;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.Map;
import java.util.Random;

import app.PrintLog;
import app.fcm.MapperUtils;
import app.fcm.NotificationUIResponse;
import app.fcm.imageparser.ImageDownloader;
import app.fcm.imageparser.LoadImage;
import app.pnd.adshandler.R;
import app.server.v2.DataHubConstant;

/**
 * Created by quantum4u1 on 27/04/18.
 */

public class Type1PushListener implements FCMType, ImageDownloader {

    private NotificationUIResponse push;
    private Context context;

    @Override
    public void generatePush(Context c, NotificationUIResponse r) {
        if (r != null) {

            this.push = r;
            this.context = c;

            if (r.icon_src.equalsIgnoreCase("NA") ||
                    r.icon_src.equalsIgnoreCase("")) {
//                createNotification(null, r);
            } else {
                new LoadImage(c, r.icon_src, this).startDownload();
            }
        }
    }

    private void createNotification(Bitmap bitmap, NotificationUIResponse push) {
        int TYPE_1 = getRandomNo();
        PrintLog.print("Before lunch logs 03");

        Intent intent;


        if (push.click_type.equalsIgnoreCase("url")) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(push.click_value));

        } else if (push.click_type.equalsIgnoreCase("deeplink")) {
            intent = new Intent(DataHubConstant.CUSTOM_ACTION);
            intent.addCategory(this.context.getPackageName());
            intent.putExtra(MapperUtils.keyValue, push.click_value);
        } else {
            intent = new Intent(DataHubConstant.CUSTOM_ACTION);
            intent.addCategory(this.context.getPackageName());
        }

        PendingIntent contentIntent = PendingIntent.getActivity(this.context, TYPE_1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews contentView = new RemoteViews(this.context.getPackageName(), R.layout.notification_type1);
        contentView.setTextViewText(R.id.title, push.headertext);
        contentView.setTextColor(R.id.title, Color.parseColor(push.headertextcolor));
        contentView.setTextViewText(R.id.contentTitle, push.footertext);
        contentView.setTextColor(R.id.contentTitle, Color.parseColor(push.footertextcolor));
        contentView.setTextViewText(R.id.button, push.buttontext);
        contentView.setTextColor(R.id.button, Color.parseColor(push.buttontextcolor));
        contentView.setImageViewBitmap(R.id.imageView, bitmap);


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this.context)
                        .setLargeIcon(bitmap)
                        .setContentTitle(push.headertext)
                        .setContentText(push.footertext)
                        .setContent(contentView);

        builder.setContentIntent(contentIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.status_app_icon);

        } else {
            builder.setSmallIcon(R.drawable.app_icon);
        }

        // Add as notification
        NotificationManager manager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        //notification.contentView = contentView;
        notification.contentIntent = contentIntent;

        if (push.cancelable.equalsIgnoreCase("yes")) {
            notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_AUTO_CANCEL;
        } else {
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

        }

        if (push.sound.equalsIgnoreCase("yes")) {
            notification.defaults |= Notification.DEFAULT_SOUND;
        }
        if (push.vibration.equalsIgnoreCase("yes")) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        manager.notify(TYPE_1, notification);
    }

    @Override
    public void onImageDownload(Map<String, Bitmap> mMap) {
        createNotification(mMap.get(this.push.icon_src), this.push);
    }


    private int getRandomNo() {
        Random r = new Random();
        int low = 10;
        int high = 100;
        final int result = r.nextInt(high - low) + low;
        return result;
    }
}
