package callerinfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appnextg.fourg.R;

public class CallUtils {
    CountDownTimer timer;
//    Toast toast;

    public static final String put_Location = "call_location";
    public static final String put_Network = "call_network";
    public static final String put_Number = "call_number";

    public void sendFeedback(Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        //intent.putExtra(Intent.EXTRA_EMAIL, "appinfomagic@gmail.com");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"appinfomagic@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack");
        intent.putExtra(Intent.EXTRA_TEXT, "Device Brand:   " + Build.BRAND + "\nDevice Model: " + Build.MODEL + "\nDevice Version: " + Build.VERSION.SDK_INT);
//		intent.putExtra(Intent.EXTRA_TEXT, "Device Model  "+Build.MODEL);
//		intent.putExtra(Intent.EXTRA_TEXT, "Device Version  "+android.os.Build.VERSION.SDK_INT);
//
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "Send Email"));
    }


    public void shareOnWhatsApp(Context context) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String mailBody = "";
        mailBody += "Hi,\n";
        mailBody += "Download this cool app \n";

        mailBody += "https://play.google.com/store/apps/details?id=";
        mailBody += "app.caller.model1";
        mailBody += "&feature=search_result";

        whatsappIntent.putExtra(Intent.EXTRA_TEXT, mailBody);
        try {
            context.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            //ToastHelper.MakeShortText("Whatsapp have not been installed.");
            Toast.makeText(context, "Whats not intalled", Toast.LENGTH_LONG).show();
        }
    }

    public void shareUrl(Context context) {
        String mailBody = "";
        mailBody += "Hi,\n";
        mailBody += "Download this cool app \n";

        mailBody += "https://play.google.com/store/apps/details?id=";
        mailBody += "app.caller.model1";
        mailBody += "&feature=search_result";

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);


        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, mailBody); // mailBody
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public void rateUs(Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=app.caller.model1"));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }

    public void showToast(Context context, String number, boolean isOutGoing) {

        /*
        * uncomment this line for call details*/
        searchNumber(number, context, isOutGoing);


        //        LayoutInflater inflater = LayoutInflater.from(context);
//       View layout = inflater.inflate(R.layout.toastlayout, null);
//        TextView callerinfo = (TextView) layout.findViewById(R.id.callerinfo);
//        searchNumber(number, context, isOutGoing);
//        toast = new Toast(context);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
        // toast.show();

//        timer = new CountDownTimer(4000, 1000) {
//            public void onTick(long millisUntilFinished) {
//                toast.show();
//            }
//
//            public void onFinish() {
//                timer.cancel();
//            }
//
//        }.start();
    }

    private void searchNumber1(String number, Context context, boolean isOutGoing) {
        try {
            if (number != null) {
                if (number.length() == 11)
                    number = number.substring(1, 11);
                if (number.length() == 12)
                    number = number.substring(2, 12);
                if (number.length() == 13)
                    number = number.substring(3, 13);
                Intent intent = new Intent(context, CallInfoActivity.class);
                String orignal = number;
                GetCallerDetails getCallerDetails = new GetCallerDetails();
                System.out.println("Caller User Input " + number);
                if (number != null && number.length() > 4) {
                    number = number.substring(0, 4);
                    System.out.println("Caller User substring " + number);
                    String tempnum = number;
                    number = tempnum.substring(0, 2) + "," + tempnum.substring(2, 4);

                    String filedata = loadAssetTextAsString(context, "data.txt");
                    int index = filedata.indexOf(number);

                    String caller_info = filedata.substring(index + 11, index + 100);
                    getCallerDetails.getDetails(caller_info);


                    //System.out.println("Sub Index "+index);

                    if (orignal.length() < 10) {
//                textView.setText("Mobile No: " + orignal + "\n(Unknown Location)");
                        intent.putExtra(put_Location, "Unknown Location");
                    } else {
                        String network_name = getCallerDetails.getDetails(caller_info).OPARATOR_NAME;
                        String location = getCallerDetails.getDetails(caller_info).LOCATION;

                        if (network_name.length() < 4 || location.length() < 4) {
//                    textView.setText("Mobile No: " + orignal + "\n(Unknown Location)   ");
                            intent.putExtra(put_Location, "Unknown Location");
                        } else {
//                    textView.setText("Mobile No: " + orignal + "\n Network:" + getCallerDetails.getDetails(caller_info).OPARATOR_NAME
//                            + "    \n" + "Location:" + getCallerDetails.getDetails(caller_info).LOCATION + "      ");
                            intent.putExtra(put_Network, getCallerDetails.getDetails(caller_info).OPARATOR_NAME);
                            intent.putExtra(put_Location, getCallerDetails.getDetails(caller_info).LOCATION);
                        }

                    }
                } else {
//            textView.setText("Mobile No " + orignal + "(Unknown Location)");
                    intent.putExtra(put_Location, "Unknown Location");
                }
                intent.putExtra(put_Number, orignal);
                intent.putExtra("isOutGoing", isOutGoing);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        } catch (NullPointerException e) {

        } catch (Exception e) {

        }
    }

    private void searchNumber(String number, Context context, boolean isOutGoing) {
        try {
            if (number != null) {
                if (number.length() == 11)
                    number = number.substring(1, 11);
                if (number.length() == 12)
                    number = number.substring(2, 12);
                if (number.length() == 13)
                    number = number.substring(3, 13);
                Intent intent = new Intent(context, CallInfoActivity.class);
                String orignal = number;
                GetCallerDetails getCallerDetails = new GetCallerDetails();
                System.out.println("Caller User Input " + number);
                if (number != null && number.length() > 4) {


                    String number5digit = number.substring(0, 5);

                    number = number.substring(0, 4);

                    String filedata5digit = loadAssetTextAsString(context, "data5.txt");
                    String filedata4digit = loadAssetTextAsString(context, "data4.txt");

                    int index;
                    String caller_info;

                    index = filedata5digit.indexOf(number5digit);

                    if (index == -1) {
                        index = filedata4digit.indexOf(number);
                        caller_info = filedata4digit.substring(index + 5, index + 100);

                    } else {
                        caller_info = filedata5digit.substring(index + 6, index + 100);
                    }

                    getCallerDetails.getDetails(caller_info);
                    ;


                    System.out.print("caller_info " + caller_info);


                    //System.out.println("Sub Index "+index);

                    if (orignal.length() < 10) {
//                textView.setText("Mobile No: " + orignal + "\n(Unknown Location)");
                        intent.putExtra(put_Location, "Unknown Location");
                    } else {
                        String network_name = getCallerDetails.getDetails(caller_info).OPARATOR_NAME;
                        String location = getCallerDetails.getDetails(caller_info).LOCATION;

                        if (network_name.length() < 2 || location.length() < 2) {
//                    textView.setText("Mobile No: " + orignal + "\n(Unknown Location)   ");
                            intent.putExtra(put_Location, "Unknown Location");
                        } else {
//                    textView.setText("Mobile No: " + orignal + "\n Network:" + getCallerDetails.getDetails(caller_info).OPARATOR_NAME
//                            + "    \n" + "Location:" + getCallerDetails.getDetails(caller_info).LOCATION + "      ");
                            intent.putExtra(put_Network, getCallerDetails.getDetails(caller_info).OPARATOR_NAME);
                            intent.putExtra(put_Location, getCallerDetails.getDetails(caller_info).LOCATION);
                        }

                    }
                } else {
//            textView.setText("Mobile No " + orignal + "(Unknown Location)");
                    intent.putExtra(put_Location, "Unknown Location");
                }
                intent.putExtra(put_Number, orignal);
                intent.putExtra("isOutGoing", isOutGoing);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        } catch (NullPointerException e) {

        } catch (Exception e) {

        }
    }

    private String loadAssetTextAsString(Context context, String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            System.out.println("sub ex" + e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("sub ex 2 " + e);
                }
            }
        }

        return null;
    }

    public void set_Incomng(Context context, boolean data) {
        SharedPreferences incomingenable = context.getSharedPreferences("incomingenable",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = incomingenable.edit();
        editor.putBoolean("incomingenable", data);
        editor.commit();
    }

    public boolean get_Incomng(Context context) {
        SharedPreferences incomingenable = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences incomingenable = context.getSharedPreferences("incomingenable",
//                Activity.MODE_PRIVATE);
        return incomingenable.getBoolean("incomingenable", true);

    }


    public void set_outgoing(Context context, boolean data) {
        SharedPreferences outgoing = context.getSharedPreferences("outgoing",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = outgoing.edit();
        editor.putBoolean("outgoing", data);
        editor.commit();
    }

    public boolean get_outgoing(Context context) {
        SharedPreferences outgoing = PreferenceManager.getDefaultSharedPreferences(context);

//        SharedPreferences outgoing = context.getSharedPreferences("outgoing",
//                Activity.MODE_PRIVATE);
        return outgoing.getBoolean("outgoing", true);

    }


    public void set_installation(Context context, long data) {
        if (get_installation(context) == 0) {
            SharedPreferences installation = context.getSharedPreferences("installation",
                    Activity.MODE_WORLD_WRITEABLE);
            Editor editor = installation.edit();
            editor.putLong("installation", data);
            editor.commit();
        }
    }

    public long get_installation(Context context) {

        SharedPreferences installation = context.getSharedPreferences("installation",
                Activity.MODE_WORLD_WRITEABLE);
        return installation.getLong("installation", 0);

    }


}
