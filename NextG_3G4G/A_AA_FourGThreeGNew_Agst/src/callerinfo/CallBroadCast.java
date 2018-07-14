package callerinfo;

import java.util.ArrayList;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import app.adshandler.AHandler;
import app.pnd.fourg.MainActivity;
import app.pnd.fourg.MediaPreferences;

import com.appnextg.fourg.R;


public class CallBroadCast extends BroadcastReceiver {
    Context ctx;
    CallUtils utils;
    Intent intent_;
    String numberis = "";
    String temp_number = "test";
    TelephonyManager telephony;
    private MediaPreferences mediaPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        ctx = context;
        utils = new CallUtils();
        intent_ = intent;

        mediaPreferences = new MediaPreferences(context);

        numberis = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        System.out.println("bhanu query here is my number " + " " + numberis);


        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            System.out.println("bhanu query here inside outgoing ");
            if (utils.get_outgoing(ctx)) {

                if (mediaPreferences.getOutgoingCaller()) {
                    System.out.println("if contact exists" + " " + contactExists(context, numberis));
                    if (contactExists(context, numberis)) {

                    } else {
                        utils.showToast(context, numberis, true);
                    }
                }
            }
        } else {
            System.out.println("bhanu query here inside else");
            if (utils.get_Incomng(ctx)) {
                System.out.println("bhanu query here inside getIncoming");
                if (mediaPreferences.getIncomingCaller()) {
                    System.out.println("if contact exists else" + " " + contactExists(context, numberis));
                    if (contactExists(context, numberis)) {

                    } else {
                        MyPhoneStateListener phoneListener = new MyPhoneStateListener();
                        telephony = (TelephonyManager)
                                context.getSystemService(Context.TELEPHONY_SERVICE);
                        telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
                    }
                }

            }
            //}
        }


    }

    public class MyPhoneStateListener extends PhoneStateListener {
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    System.out.println("bhanu query here inside Call_State_idle");
                    telephony.listen(this, PhoneStateListener.LISTEN_NONE);

                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("bhanu query here inside offhook");
                    Bundle bundle = intent_.getExtras();
                    String phoneNr = bundle.getString("incoming_number");
                    if (phoneNr != null)
                        utils.showToast(ctx, phoneNr, false);
                    temp_number = phoneNr;
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("bhanu query here inside call state ringing");
//                    Bundle bundle = intent_.getExtras();
//                    String phoneNr = bundle.getString("incoming_number");
//                    if (phoneNr != null)
//                        utils.showToast(ctx, phoneNr, false);
//                    temp_number = phoneNr;
                    break;
            }
        }
    }

    private boolean checkNotifications(Context context, String contact) {

        ArrayList<String> conArrayList = new ArrayList<String>();
        boolean is_unknow = true;
//		Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
//		while (phones.moveToNext())
//		{
//		 // String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//		  String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//		System.out.println("PN ------- "+phoneNumber +" contact "+contact);
//		  if(phoneNumber!=null && phoneNumber.contains(contact))
//			is_unknow=false;
//		  //conArrayList.add(phoneNumber);
//		
//		}
        return is_unknow;


    }

    private void shownotification(Context context, String notificationMessage) {
        Notification notification = new Notification(R.drawable.version_ic_icon, "1 New Unknown call found", 1);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("NotificationMessage", notificationMessage);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        notification.setLatestEventInfo(context, "Tap here to see details ", notificationMessage, pendingNotificationIntent);
//	notification.
    }

    public boolean contactExists(Context context, String number) {
/// number is the phone number
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }
}
