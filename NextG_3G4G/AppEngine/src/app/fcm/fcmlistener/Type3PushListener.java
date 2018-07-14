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

import app.fcm.ButtonFirst;
import app.fcm.ButtonSecond;
import app.fcm.MapperUtils;
import app.fcm.NotificationActionReceiver;
import app.fcm.NotificationUIResponse;
import app.fcm.imageparser.ImageDownloader;
import app.fcm.imageparser.LoadImage;
import app.pnd.adshandler.R;
import app.server.v2.DataHubConstant;

/**
 * Created by quantum4u1 on 27/04/18.
 */

public class Type3PushListener implements FCMType, ImageDownloader {

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
        if (push.button1 != null) {
            ButtonFirst buttonValue = new ButtonFirst();
            buttonValue = push.button1;
            if (push.button1 != null || push.button2 != null) {
                ButtonFirst buttonValue1 = new ButtonFirst();
                buttonValue1 = push.button1;
                ButtonSecond buttonValue2 = new ButtonSecond();
                buttonValue2 = push.button2;
                try {
                    if (buttonValue2.status.equalsIgnoreCase("0")) {
                        type4Notification(mBitmap, buttonValue1, buttonValue2);

                    } else if (buttonValue.status.equalsIgnoreCase("0")) {
                        type3Notification(mBitmap, buttonValue1);
                    }

                } catch (Exception e) {

                }

            }

        }

    }

    private void type3Notification(Map<String, Bitmap> mBitmap, ButtonFirst btn) {
        int TYPE_3 = getRandomNo();

        // notificationIntent = new Intent(action);
        // notificationIntent.addCategory(context.getPackageName());
        Intent intent;
        if (this.push.click_type.equalsIgnoreCase("url")) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(this.push.click_value));

        } else if (this.push.click_type.equalsIgnoreCase("deeplink")) {
            intent = new Intent(DataHubConstant.CUSTOM_ACTION);
            intent.addCategory(context.getPackageName());
            intent.putExtra(MapperUtils.keyValue, btn.click_value);
        } else {
            intent = new Intent(DataHubConstant.CUSTOM_ACTION);
            intent.addCategory(context.getPackageName());
        }


        //PendingIntent contentIntent = PendingIntent.getActivity(context, TYPE_3, notificationIntent,
        //      PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pcloseIntent = PendingIntent.getActivity(context, TYPE_3, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_type3_one);
        contentView.setTextViewText(R.id.title, this.push.headertext);
        contentView.setTextColor(R.id.title, Color.parseColor(this.push.headertextcolor));
        contentView.setImageViewBitmap(R.id.icon, mBitmap.get(this.push.icon_src));
        contentView.setImageViewBitmap(R.id.image, mBitmap.get(this.push.banner_src));
        contentView.setTextViewText(R.id.button, btn.buttontext);
        contentView.setTextColor(R.id.button, Color.parseColor(btn.buttontextcolor));
        // contentView.setOnClickPendingIntent(R.id.button,
        //        pcloseIntent);

        RemoteViews contentViewbig = new RemoteViews(context.getPackageName(), R.layout.notification_type3_onebig);
        contentViewbig.setTextViewText(R.id.title, this.push.headertext);
        contentViewbig.setTextColor(R.id.title, Color.parseColor(this.push.headertextcolor));
        contentViewbig.setImageViewBitmap(R.id.icon, mBitmap.get(this.push.icon_src));
        contentViewbig.setImageViewBitmap(R.id.image, mBitmap.get(this.push.banner_src));
        contentViewbig.setTextViewText(R.id.button, btn.buttontext);
        contentViewbig.setTextColor(R.id.button, Color.parseColor(btn.buttontextcolor));
        // contentViewbig.setOnClickPendingIntent(R.id.button,
        //       pcloseIntent);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setCustomContentView(contentView)
                .setCustomBigContentView(contentViewbig);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setSmallIcon(R.drawable.status_app_icon);

        } else {
            mBuilder.setSmallIcon(R.drawable.app_icon);
        }

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = mBuilder.build();
        // notification.contentView = contentView;
        notification.contentIntent = pcloseIntent;

        if (this.push.cancelable.equalsIgnoreCase("yes")) {
            notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_AUTO_CANCEL;
        } else {
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
        }

        if (this.push.sound.equalsIgnoreCase("yes")) {
            notification.defaults |= Notification.DEFAULT_SOUND;
        }
        if (this.push.vibration.equalsIgnoreCase("yes")) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        mNotificationManager.notify(TYPE_3, notification);
    }


    private void type4Notification(Map<String, Bitmap> mBitmap, ButtonFirst btn1, ButtonSecond btn2) {
        int TYPE_4 = getRandomNo();

        Intent intent1, intent2;
        if (btn1.click_type.equalsIgnoreCase("url")) {
            intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(btn1.click_value));

        } else if (btn1.click_type.equalsIgnoreCase("deeplink")) {
            intent1 = new Intent(DataHubConstant.CUSTOM_ACTION);
            intent1.addCategory(this.context.getPackageName());
            intent1.putExtra(MapperUtils.keyValue, btn1.click_value);
        } else {
            intent1 = new Intent(DataHubConstant.CUSTOM_ACTION);
            intent1.addCategory(this.context.getPackageName());
        }
//
//        if (clickType2.equalsIgnoreCase("url")) {
//            intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(clickValue2));
//
//        } else if (clickType2.equalsIgnoreCase("deeplink")) {
//            intent2 = new Intent(action);
//            intent2.addCategory(ctx.getPackageName());
//            intent2.putExtra(MapperUtils.keyValue, clickValue2);
//
//        } else {
//            intent2 = new Intent(action);
//            intent2.addCategory(ctx.getPackageName());
//        }

        intent2 = new Intent(this.context, NotificationActionReceiver.class);
        intent2.setAction("sec_btn");
        intent2.putExtra("sec_btn_type", btn2.click_type);
        intent2.putExtra("sec_btn_value", btn2.click_value);
        intent2.putExtra("TYPE_4", TYPE_4);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //PendingIntent contentIntent = PendingIntent.getActivity(ctx, TYPE_4, notificationIntent,
        //       PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent pendingSwitchIntent = PendingIntent.getActivity(this.context, TYPE_4, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);

        PendingIntent contentIntent2 = PendingIntent.getBroadcast(this.context, TYPE_4, intent2,
                PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews contentView = new RemoteViews(this.context.getPackageName(), R.layout.notification_type3_two);
        contentView.setImageViewBitmap(R.id.image, mBitmap.get(this.push.banner_src));
        contentView.setTextViewText(R.id.title, this.push.headertext);
        contentView.setTextColor(R.id.title, Color.parseColor(this.push.headertextcolor));
        contentView.setImageViewBitmap(R.id.icon, mBitmap.get(this.push.icon_src));
        contentView.setTextViewText(R.id.button, btn1.buttontext);
        contentView.setTextColor(R.id.button, Color.parseColor(btn1.buttontextcolor));
        contentView.setTextViewText(R.id.button2, btn2.buttontext);
        contentView.setTextColor(R.id.button2, Color.parseColor(btn2.buttontextcolor));

//        contentView.setOnClickPendingIntent(R.id.button,
//                pendingSwitchIntent);

        contentView.setOnClickPendingIntent(R.id.button2,
                contentIntent2);

        RemoteViews contentViewbig = new RemoteViews(this.context.getPackageName(), R.layout.notification_type3_twobig);
        contentViewbig.setImageViewBitmap(R.id.image, mBitmap.get(this.push.banner_src));
        contentViewbig.setTextViewText(R.id.title, this.push.headertext);
        contentViewbig.setTextColor(R.id.title, Color.parseColor(this.push.headertextcolor));
        contentViewbig.setImageViewBitmap(R.id.icon, mBitmap.get(this.push.icon_src));
        contentViewbig.setTextViewText(R.id.button, btn1.buttontext);
        contentViewbig.setTextColor(R.id.button, Color.parseColor(btn1.buttontextcolor));
        contentViewbig.setTextViewText(R.id.button2, btn2.buttontext);
        contentViewbig.setTextColor(R.id.button2, Color.parseColor(btn2.buttontextcolor));

//        contentViewbig.setOnClickPendingIntent(R.id.button,
//                pendingSwitchIntent);

        contentViewbig.setOnClickPendingIntent(R.id.button2,
                contentIntent2);


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this.context)
                        .setContentTitle(this.push.headertext)
                        .setCustomContentView(contentView)
                        .setCustomBigContentView(contentViewbig);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setSmallIcon(R.drawable.status_app_icon);

        } else {
            builder.setSmallIcon(R.drawable.app_icon);
        }
        // Add as notification
        NotificationManager mNotificationManager = (NotificationManager) this.context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = builder.build();

        //notification.contentView = contentView;
        notification.contentIntent = pendingSwitchIntent;


        if (this.push.cancelable.equalsIgnoreCase("yes")) {
            notification.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_AUTO_CANCEL;
        } else {
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
        }

        if (this.push.sound.equalsIgnoreCase("yes")) {
            notification.defaults |= Notification.DEFAULT_SOUND;
        }
        if (this.push.vibration.equalsIgnoreCase("yes")) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        mNotificationManager.notify(TYPE_4, notification);
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
        if (push.type.equalsIgnoreCase("type3") && (!push.banner_src.equalsIgnoreCase("NA")
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
