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

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import app.adshandler.DataHub;
import app.fcm.MapperUtils;
import app.fcm.NotificationUIResponse;
import app.fcm.imageparser.ImageDownloader;
import app.fcm.imageparser.LoadImage;
import app.pnd.adshandler.R;
import app.server.v2.DataHubConstant;

/**
 * Created by quantum4u1 on 27/04/18.
 */

public class Type2PushListener implements FCMType, ImageDownloader {

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
                new LoadImage(c, getAllUrlstobeDOwnloadedFromPush(r), this).startDownload();
            }
        }
    }

    private void createNotification(Map<String, Bitmap> mBitmap, NotificationUIResponse push) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int TYPE_2 = getRandomNo();

            Intent notificationIntent;

            if (push.click_type.equalsIgnoreCase("url")) {
                notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(push.click_value));

            } else if (push.click_type.equalsIgnoreCase("deeplink")) {
                notificationIntent = new Intent(DataHubConstant.CUSTOM_ACTION);
                notificationIntent.addCategory(context.getPackageName());
                notificationIntent.putExtra(MapperUtils.keyValue, push.click_value);


            } else {
                notificationIntent = new Intent(DataHubConstant.CUSTOM_ACTION);
                notificationIntent.addCategory(context.getPackageName());
            }

            PendingIntent contentIntent = PendingIntent.getActivity(context, TYPE_2, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_type2);
            contentView.setTextViewText(R.id.title, push.headertext);
            contentView.setTextColor(R.id.title, Color.parseColor(push.headertextcolor));
            contentView.setTextViewText(R.id.contentTitle, push.footertext);
            contentView.setTextColor(R.id.contentTitle, Color.parseColor(push.footertextcolor));
            contentView.setImageViewBitmap(R.id.icon, mBitmap.get(push.icon_src));

            RemoteViews contentViewBig = new RemoteViews(context.getPackageName(), R.layout.notification_type2_big);
            contentViewBig.setTextViewText(R.id.title, push.headertext);
            contentViewBig.setTextColor(R.id.title, Color.parseColor(push.headertextcolor));
            contentViewBig.setTextViewText(R.id.contentTitle, push.footertext);
            contentViewBig.setTextColor(R.id.contentTitle, Color.parseColor(push.footertextcolor));
            contentViewBig.setImageViewBitmap(R.id.icon, mBitmap.get(push.icon_src));
            contentViewBig.setImageViewBitmap(R.id.image, mBitmap.get(push.banner_src));

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle(push.headertext)
                    .setCustomContentView(contentView)
                    .setCustomBigContentView(contentViewBig);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBuilder.setSmallIcon(R.drawable.status_app_icon);

            } else {
                mBuilder.setSmallIcon(R.drawable.app_icon);
            }

            NotificationManager mNotificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Notification notification = mBuilder.build();
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

            mNotificationManager.notify(TYPE_2, notification);

        } else {
            new Type1PushListener().generatePush(this.context, this.push);
        }
    }

    @Override
    public void onImageDownload(Map<String, Bitmap> mMap) {
        createNotification(mMap, this.push);
    }

    private ArrayList<String> getAllUrlstobeDOwnloadedFromPush(NotificationUIResponse push) {
        ArrayList<String> arrayList = new ArrayList();
        if (!push.icon_src.equalsIgnoreCase("NA") ||
                !push.icon_src.equalsIgnoreCase("")) {
            arrayList.add(push.icon_src);
        }
        if (push.type.equalsIgnoreCase("type2") && (!push.banner_src.equalsIgnoreCase("NA")
                || !push.banner_src.equalsIgnoreCase(""))) {
            arrayList.add(push.banner_src);
        }
        return arrayList;
    }

    private int getRandomNo() {
        Random r = new Random();
        int low = 10;
        int high = 100;
        final int result = r.nextInt(high - low) + low;
        return result;
    }
}
