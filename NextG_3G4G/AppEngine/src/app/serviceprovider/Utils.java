package app.serviceprovider;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.RandomAccessFile;

import app.PrintLog;
import app.adshandler.Ads;
import app.pnd.adshandler.R;
import app.server.v2.Slave;

public class Utils {
    public static String QUANTUM_URL = "http://quantum4you.com/piqvalue.php?val=3G4G_Icon_Ads";

    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null;
        } catch (Exception e) {
            Ads.printdata("Error in Getting Network Connection " + e);
            return true;
        }
    }

    // public static String moreappURl = "https://play.google.com/store/apps/developer?id=Utility+Top+Apps";
    public static boolean is_samsung = false;
    public static int brihtness_value = 70;
    CountDownTimer timer;
    Toast toast;
    public static String current_package = "com.mtool.fourgswitch";
    public static String current_package_pro = "com.mtool.nearme_pro";
    public static String current_launcher = "app.pnd.boosterforram.AppLauncher";
    public static String stealt_code = "*111#";

    public void initPackage(String packagename) {
        current_package = packagename;
    }

    public void removeAds(Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id="
                        + current_package_pro));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
    }

    public boolean get_ISFIRSTTIME(Context context) {

        SharedPreferences ISFIRSTTIME = context.getSharedPreferences(
                "ISFIRSTTIME", Activity.MODE_PRIVATE);
        return ISFIRSTTIME.getBoolean("ISFIRSTTIME", true);

    }

    public void set_ISFIRSTTIME(Context context, boolean data) {
        SharedPreferences ISFIRSTTIME = context.getSharedPreferences(
                "ISFIRSTTIME", Activity.MODE_PRIVATE);
        Editor editor = ISFIRSTTIME.edit();
        editor.putBoolean("ISFIRSTTIME", data);
        editor.commit();
    }

    public void sendFeedback(Context context) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        // intent.putExtra(Intent.EXTRA_EMAIL, "appinfomagic@gmail.com");
        intent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{Slave.FEEDBACK_email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack");
        intent.putExtra(Intent.EXTRA_TEXT, ""
                + context.getResources().getString(R.string.app_name) + "\n"
                + "Device Brand:   " + Build.BRAND + "\nDevice Model: "
                + Build.MODEL + "\nDevice Version: "
                + Build.VERSION.SDK_INT);
        // intent.putExtra(Intent.EXTRA_TEXT, "Device Model  "+Build.MODEL);
        // intent.putExtra(Intent.EXTRA_TEXT,
        // "Device Version  "+android.os.Build.VERSION.SDK_INT);
        //
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent,
                "Please send your feedback "));
    }


    public void shareUrl(Context context) {
        String link_val = "https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&feature=search_result";
        link_val = Slave.SHARE_URL;
        String body = "<a href=\"" + link_val + "\">" + link_val + "</a>";

        String mailBody = "";
        mailBody += "Hi, Download this cool and fast performance App\n";
        mailBody = Slave.SHARE_TEXT + " ";
//		mailBody += body;
        mailBody += link_val;
//        mailBody += "https://play.google.com/store/apps/details?id=";
//        mailBody += "" + current_package;
//        mailBody += "&feature=search_result";
        mailBody += "";

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);

        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, String.valueOf(Html.fromHtml(mailBody))); // mailBody
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(Intent
                .createChooser(sharingIntent, "Share using"));
    }

    public void rateUs(Context context) {
        try {
            PrintLog.print("Rate App URL is " + Slave.RATE_APP_rateurl);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Slave.RATE_APP_rateurl));

            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(browserIntent);
        } catch (Exception e) {
            PrintLog.print("Rate App URL is exp  " + e.getMessage());
        }
    }

    public void moreApps(Context context) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(Slave.MOREAPP_moreurl));
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(browserIntent);
        } catch (Exception e) {

        }
    }


    public boolean get_Message(Context context) {

        SharedPreferences incomingenable = context.getSharedPreferences(
                "incomingenable", Activity.MODE_PRIVATE);
        return incomingenable.getBoolean("incomingenable", true);

    }


    public long get_installation(Context context) {

        SharedPreferences installation = context.getSharedPreferences(
                "installation", Activity.MODE_PRIVATE);
        return installation.getLong("installation", 0);

    }


    public boolean get_screen(Context context) {

        SharedPreferences screen = context.getSharedPreferences("screen",
                Activity.MODE_PRIVATE);
        return screen.getBoolean("screen", false);

    }


    public void set_count(Context context, int data) {
        SharedPreferences count = context.getSharedPreferences("count",
                Activity.MODE_PRIVATE);
        Editor editor = count.edit();
        editor.putInt("count", data);
        editor.commit();
    }

    public int get_count(Context context) {

        SharedPreferences count = context.getSharedPreferences("count",
                Activity.MODE_PRIVATE);
        return count.getInt("count", 1);

    }


    public void set_App_Launch_Count(Context context, int data) {
        SharedPreferences count = context.getSharedPreferences("lcount",
                Activity.MODE_PRIVATE);
        Editor editor = count.edit();
        editor.putInt("count", data);
        editor.commit();
    }

    public int get_App_Launch_Count(Context context) {

        SharedPreferences count = context.getSharedPreferences("lcount",
                Activity.MODE_PRIVATE);
        return count.getInt("count", 1);

    }

    public boolean get_lollipop(Context context) {

        SharedPreferences lollipop = context.getSharedPreferences("lollipop",
                Activity.MODE_PRIVATE);
        return lollipop.getBoolean("lollipop", true);

    }

    public void set_lollipop(Context context, boolean data) {
        SharedPreferences lollipop = context.getSharedPreferences("lollipop",
                Activity.MODE_PRIVATE);
        Editor editor = lollipop.edit();
        editor.putBoolean("lollipop", data);
        editor.commit();
    }

    public static int get_servicecount(Context context) {

        SharedPreferences servicecount = context.getSharedPreferences(
                "servicecount", Activity.MODE_PRIVATE);
        return servicecount.getInt("servicecount", 1);

    }

    public static void set_servicecount(Context context, int data) {
        SharedPreferences servicecount = context.getSharedPreferences(
                "servicecount", Activity.MODE_PRIVATE);
        Editor editor = servicecount.edit();
        editor.putInt("servicecount", data);
        editor.commit();
    }

    public boolean get_ck_backup1(Context context) {

        SharedPreferences ck_backup1 = context.getSharedPreferences(
                "ck_backup1", Activity.MODE_PRIVATE);
        return ck_backup1.getBoolean("ck_backup1", false);

    }

    public void set_ck_backup1(Context context, boolean data) {
        SharedPreferences ck_backup1 = context.getSharedPreferences(
                "ck_backup1", Activity.MODE_PRIVATE);
        Editor editor = ck_backup1.edit();
        editor.putBoolean("ck_backup1", data);
        editor.commit();
    }

    public boolean get_ck_backup2(Context context) {

        SharedPreferences ck_backup2 = context.getSharedPreferences(
                "ck_backup2", Activity.MODE_PRIVATE);
        return ck_backup2.getBoolean("ck_backup2", true);

    }

    public void set_ck_backup2(Context context, boolean data) {
        SharedPreferences ck_backup2 = context.getSharedPreferences(
                "ck_backup2", Activity.MODE_PRIVATE);
        Editor editor = ck_backup2.edit();
        editor.putBoolean("ck_backup2", data);
        editor.commit();
    }

    public boolean get_ck_backup3(Context context) {

        SharedPreferences ck_backup3 = context.getSharedPreferences(
                "ck_backup3", Activity.MODE_PRIVATE);
        return ck_backup3.getBoolean("ck_backup3", true);

    }

    public void set_ck_backup3(Context context, boolean data) {
        SharedPreferences ck_backup3 = context.getSharedPreferences(
                "ck_backup3", Activity.MODE_PRIVATE);
        Editor editor = ck_backup3.edit();
        editor.putBoolean("ck_backup3", data);
        editor.commit();
    }

    public boolean get_ck_ram1(Context context) {

        SharedPreferences ck_ram1 = context.getSharedPreferences("ck_ram1",
                Activity.MODE_PRIVATE);
        return ck_ram1.getBoolean("ck_ram1", false);

    }

    public void set_ck_ram1(Context context, boolean data) {
        SharedPreferences ck_ram1 = context.getSharedPreferences("ck_ram1",
                Activity.MODE_PRIVATE);
        Editor editor = ck_ram1.edit();
        editor.putBoolean("ck_ram1", data);
        editor.commit();
    }

    public boolean get_ck_ram2(Context context) {

        SharedPreferences ck_ram2 = context.getSharedPreferences("ck_ram2",
                Activity.MODE_PRIVATE);
        return ck_ram2.getBoolean("ck_ram2", true);

    }

    public void set_ck_ram2(Context context, boolean data) {
        SharedPreferences ck_ram2 = context.getSharedPreferences("ck_ram2",
                Activity.MODE_PRIVATE);
        Editor editor = ck_ram2.edit();
        editor.putBoolean("ck_ram2", data);
        editor.commit();
    }

    public boolean get_ck_ram3(Context context) {

        SharedPreferences ck_ram3 = context.getSharedPreferences("ck_ram3",
                Activity.MODE_PRIVATE);
        return ck_ram3.getBoolean("ck_ram3", true);

    }

    public void set_ck_ram3(Context context, boolean data) {
        SharedPreferences ck_ram3 = context.getSharedPreferences("ck_ram3",
                Activity.MODE_PRIVATE);
        Editor editor = ck_ram3.edit();
        editor.putBoolean("ck_ram3", data);
        editor.commit();
    }

    public static String getTotalRAM() {
        RandomAccessFile reader = null;
        String load = "500";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Streams.close(reader);
        }
        PrintLog.print("Total orignal " + load);
        if (load.contains("kB"))
            load = load.replace("kB", "");
        if (load.contains("MemTotal:"))
            load = load.replace("MemTotal:", "");

        PrintLog.print("Total orignal trimed" + load);
        return load.trim();
    }

    public static long getUsed(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static void set_locale2(Context context, String id) {
        SharedPreferences getinstallreceiver = context.getSharedPreferences(
                "locale_id", Activity.MODE_MULTI_PROCESS);
        Editor editor = getinstallreceiver.edit();
        editor.putString("locale_id", id);
        editor.commit();
    }

    public static int getRamPercent(Context context) {
        float total = Integer.parseInt(getTotalRAM()) / 1024;
        PrintLog.print("Total  === " + total);
        float getused = getUsed(context) / (1024 * 1024);
        PrintLog.print("Total  used === " + getused);
        float per = (getused / total) * 100;

        PrintLog.print("Total percent " + per);

        return (int) per;

    }

    public void set_AdsImageURL(Context context, String data) {
        SharedPreferences AdsImageURL = context.getSharedPreferences(
                "AdsImageURL", Activity.MODE_PRIVATE);
        Editor editor = AdsImageURL.edit();
        editor.putString("AdsImageURL", data);
        editor.commit();
    }

    public String get_AdsImageURL(Context context) {

        SharedPreferences AdsImageURL = context.getSharedPreferences(
                "AdsImageURL", Activity.MODE_PRIVATE);
        return AdsImageURL.getString("AdsImageURL", "");

    }

    public static int get_FeatchFromServerCount(Context context) {

        SharedPreferences FeatchFromServerCount = context.getSharedPreferences(
                "FeatchFromServerCount", Activity.MODE_PRIVATE);
        return FeatchFromServerCount.getInt("FeatchFromServerCount", 1);

    }

    public static void set_FeatchFromServerCount(Context context, int data) {
        SharedPreferences FeatchFromServerCount = context.getSharedPreferences(
                "FeatchFromServerCount", Activity.MODE_PRIVATE);
        Editor editor = FeatchFromServerCount.edit();
        editor.putInt("FeatchFromServerCount", data);
        editor.commit();
    }

    public static int get_FulladsshownCount(Context context) {

        SharedPreferences FulladsshownCount = context.getSharedPreferences(
                "FulladsshownCount", Activity.MODE_PRIVATE);
        return FulladsshownCount.getInt("FulladsshownCount", 0);

    }

    public static void set_FulladsshownCount(Context context, int data) {
        SharedPreferences FulladsshownCount = context.getSharedPreferences(
                "FulladsshownCount", Activity.MODE_PRIVATE);
        Editor editor = FulladsshownCount.edit();
        editor.putInt("FulladsshownCount", data);
        editor.commit();
    }

    public static int get_fullservicecount(Context context) {

        SharedPreferences fullservicecount = context.getSharedPreferences(
                "fullservicecount", Activity.MODE_PRIVATE);
        return fullservicecount.getInt("fullservicecount", 0);

    }

    public static void set_fullservicecount(Context context, int data) {
        SharedPreferences fullservicecount = context.getSharedPreferences(
                "fullservicecount", Activity.MODE_PRIVATE);
        Editor editor = fullservicecount.edit();
        editor.putInt("fullservicecount", data);
        editor.commit();
    }


    public static int get_fullservicecount_start(Context context) {

        SharedPreferences fullservicecount = context.getSharedPreferences(
                "fullservicecount_start", Activity.MODE_PRIVATE);
        return fullservicecount.getInt("fullservicecount_start", 0);

    }

    public static void set_fullservicecount_start(Context context, int data) {
        SharedPreferences fullservicecount = context.getSharedPreferences(
                "fullservicecount_start", Activity.MODE_PRIVATE);
        Editor editor = fullservicecount.edit();
        editor.putInt("fullservicecount_start", data);
        editor.commit();
    }


    public static int get_fullservicecount_exit(Context context) {

        SharedPreferences fullservicecount = context.getSharedPreferences(
                "fullservicecount_exit", Activity.MODE_PRIVATE);
        return fullservicecount.getInt("fullservicecount_exit", 0);

    }

    public static void set_fullservicecount_exit(Context context, int data) {
        SharedPreferences fullservicecount = context.getSharedPreferences(
                "fullservicecount_exit", Activity.MODE_PRIVATE);
        Editor editor = fullservicecount.edit();
        editor.putInt("fullservicecount_exit", data);
        editor.commit();
    }

    public void showPrivacyPolicy(final Context mContext, View mLayout, boolean isFirstTime) {
        View mView = mLayout;
        LinearLayout linearLayout = (LinearLayout) mView.findViewById(R.id.bottom);
        TextView tvTerms = (TextView) mView.findViewById(R.id.tvTerms);
        TextView tvPrivacy = (TextView) mView.findViewById(R.id.tvPrivacy);

        tvTerms.setText(Html.fromHtml("<u>Terms of Service</u>"));
        tvPrivacy.setText(Html.fromHtml("<u>Privacy Policy</u>"));

        if (!isFirstTime) {
            linearLayout.setVisibility(View.GONE);
        } else {
            linearLayout.setVisibility(View.VISIBLE);
        }

        tvTerms.setClickable(true);
        tvTerms.setMovementMethod(LinkMovementMethod.getInstance());
        tvTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Slave.ABOUTDETAIL_TERM_AND_COND)));
                } catch (Exception e) {
                }
            }
        });

        tvPrivacy.setClickable(true);
        tvPrivacy.setMovementMethod(LinkMovementMethod.getInstance());

        tvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Slave.ABOUTDETAIL_PRIVACYPOLICY)));
                } catch (Exception e) {
                }
            }
        });
    }


}
