package app.ads;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appnextg.fourg.R;


public class UtilsApp {
    public static boolean is_samsung = false;
    public static int brihtness_value = 70;
    CountDownTimer timer;
    Toast toast;
    public static String current_package = "app.pnd.fourg";
    public static String current_launcher = "app.pnd.fourg.AppLauncher";
    public static String stealt_code = "*111#";

    public void initPackage(String packagename) {
        current_package = packagename;
    }

    public void set_use_count(Context context, int data) {
        SharedPreferences use_count = context.getSharedPreferences("use_count",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = use_count.edit();
        editor.putInt("use_count", data);
        editor.commit();
    }

    public int get_use_count(Context context) {

        SharedPreferences use_count = context.getSharedPreferences("use_count",
                Activity.MODE_WORLD_WRITEABLE);
        return use_count.getInt("use_count", 0);

    }

    public boolean get_ISFIRSTTIME(Context context) {

        SharedPreferences ISFIRSTTIME = context.getSharedPreferences("ISFIRSTTIME",
                Activity.MODE_WORLD_WRITEABLE);
        return ISFIRSTTIME.getBoolean("ISFIRSTTIME", true);

    }

    public void set_ISFIRSTTIME(Context context, boolean data) {
        SharedPreferences ISFIRSTTIME = context.getSharedPreferences("ISFIRSTTIME",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = ISFIRSTTIME.edit();
        editor.putBoolean("ISFIRSTTIME", data);
        editor.commit();
    }

    public void showToast(Context context, String number) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View layout = inflater.inflate(R.layout.toastlayout, null);
        TextView callerinfo = (TextView) layout.findViewById(R.id.callerinfo);
        number = number + "   ";
        callerinfo.setText(number);

        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        // toast.show();

        timer = new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            public void onFinish() {
                timer.cancel();
            }

        }.start();
    }

    public void sendFeedback(Context context) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        //intent.putExtra(Intent.EXTRA_EMAIL, "appinfomagic@gmail.com");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"appinfomagic@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack");
        intent.putExtra(Intent.EXTRA_TEXT, "" + context.getResources().getString(R.string.pindi_app_name) + "\n" + "Device Brand:   " + Build.BRAND + "\nDevice Model: " + Build.MODEL + "\nDevice Version: " + Build.VERSION.SDK_INT);
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
        mailBody += "" + current_package;
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
        mailBody += "" + current_package;
        mailBody += "&feature=search_result";

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);


        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, mailBody); // mailBody
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public void rateUs(Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + current_package));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }

    public void moreApps(Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Quantum4u"));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
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

    public void set_Message(Context context, boolean data) {
        SharedPreferences incomingenable = context.getSharedPreferences("incomingenable",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = incomingenable.edit();
        editor.putBoolean("incomingenable", data);
        editor.commit();
    }

    public boolean get_Message(Context context) {

        SharedPreferences incomingenable = context.getSharedPreferences("incomingenable",
                Activity.MODE_WORLD_WRITEABLE);
        return incomingenable.getBoolean("incomingenable", true);

    }


    public void set_Mail(Context context, boolean data) {
        SharedPreferences outgoing = context.getSharedPreferences("outgoing",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = outgoing.edit();
        editor.putBoolean("outgoing", data);
        editor.commit();
    }

    public boolean get_Mail(Context context) {

        SharedPreferences outgoing = context.getSharedPreferences("outgoing",
                Activity.MODE_WORLD_WRITEABLE);
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

    public void set_vibration(Context context, boolean data) {
        SharedPreferences vibration = context.getSharedPreferences("vibration",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = vibration.edit();
        editor.putBoolean("vibration", data);
        editor.commit();
    }

    public boolean get_vibration(Context context) {

        SharedPreferences vibration = context.getSharedPreferences("vibration",
                Activity.MODE_WORLD_WRITEABLE);
        return vibration.getBoolean("vibration", false);

    }


    public void set_notification(Context context, boolean data) {
        SharedPreferences notification = context.getSharedPreferences("notification",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = notification.edit();
        editor.putBoolean("notification", data);
        editor.commit();
    }

    public boolean get_notification(Context context) {

        SharedPreferences notification = context.getSharedPreferences("notification",
                Activity.MODE_WORLD_WRITEABLE);
        return notification.getBoolean("notification", true);

    }

    public void set_pattern_vis(Context context, boolean data) {
        SharedPreferences pattern = context.getSharedPreferences("pattern",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = pattern.edit();
        editor.putBoolean("pattern", data);
        editor.commit();
    }


    public boolean get_reeboot(Context context) {

        SharedPreferences reeboot = context.getSharedPreferences("reeboot",
                Activity.MODE_WORLD_WRITEABLE);
        return reeboot.getBoolean("reeboot", true);

    }

    public void set_reeboot(Context context, boolean data) {
        SharedPreferences reeboot = context.getSharedPreferences("reeboot",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = reeboot.edit();
        editor.putBoolean("reeboot", data);
        editor.commit();
    }


    public boolean get_screen(Context context) {

        SharedPreferences screen = context.getSharedPreferences("screen",
                Activity.MODE_WORLD_WRITEABLE);
        return screen.getBoolean("screen", false);

    }

    public void set_screen(Context context, boolean data) {
        SharedPreferences screen = context.getSharedPreferences("screen",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = screen.edit();
        editor.putBoolean("screen", data);
        editor.commit();
    }


    public boolean get_fullscreen(Context context) {

        SharedPreferences fullscreen = context.getSharedPreferences("fullscreen",
                Activity.MODE_WORLD_WRITEABLE);
        return fullscreen.getBoolean("fullscreen", true);

    }

    public void set_fullscreen(Context context, boolean data) {
        SharedPreferences fullscreen = context.getSharedPreferences("fullscreen",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = fullscreen.edit();
        editor.putBoolean("fullscreen", data);
        editor.commit();
    }

    public boolean get_pattern_vis(Context context) {

        SharedPreferences pattern = context.getSharedPreferences("pattern",
                Activity.MODE_WORLD_WRITEABLE);
        return pattern.getBoolean("pattern", false);

    }


    public void set_count(Context context, int data) {
        SharedPreferences count = context.getSharedPreferences("count",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = count.edit();
        editor.putInt("count", data);
        editor.commit();
    }

    public int get_count(Context context) {

        SharedPreferences count = context.getSharedPreferences("count",
                Activity.MODE_WORLD_WRITEABLE);
        return count.getInt("count", 1);

    }

    public boolean get_lollipop(Context context) {

        SharedPreferences lollipop = context.getSharedPreferences("lollipop",
                Activity.MODE_WORLD_WRITEABLE);
        return lollipop.getBoolean("lollipop", false);

    }


    public void set_lollipop(Context context, boolean data) {
        SharedPreferences lollipop = context.getSharedPreferences("lollipop",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = lollipop.edit();
        editor.putBoolean("lollipop", data);
        editor.commit();
    }

    public static void set_locale2(Context context, String id) {
        SharedPreferences getinstallreceiver = context.getSharedPreferences(
                "locale_id", Activity.MODE_MULTI_PROCESS);
        Editor editor = getinstallreceiver.edit();
        editor.putString("locale_id", id);
        editor.commit();
    }

    public static String get_locale2(Context context) {
        SharedPreferences getinstallreceiver = context.getSharedPreferences(
                "locale_id", Activity.MODE_MULTI_PROCESS);
        return getinstallreceiver.getString("locale_id", "en_GB");
    }


}
