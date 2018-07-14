package app.server.v2;

import android.app.ProgressDialog;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import app.PrintLog;
import app.pnd.adshandler.R;

/**
 * Created by hp on 9/20/2017.
 */
public class DataHubConstant {

    public static boolean IS_LIVE = true;

    public static int APP_LAUNCH_COUNT = 1;
    public static String APP_ID = "appnext4gswitch";

    public static String CUSTOM_ACTION = APP_ID + ".MY_CUSTOM_ACTION";


    private Context mContext;


    public static String KEY_SUCESS = "success";
    public static String KEY_NA = "NA";

    private static String mPackageName;

    public DataHubConstant(Context c) {
        this.mContext = c;
        mPackageName = c.getPackageName();
    }


    public String readFromAssets(String filename) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(mContext.getAssets().open(filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // do reading, usually loop until end of file reading
        StringBuilder sb = new StringBuilder();
        String mLine = null;
        try {
            mLine = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (mLine != null) {
            sb.append(mLine); // process line
            try {
                mLine = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintLog.print("check for logs 01");
        return sb.toString();
    }

    private ArrayList<String> quantumList = new ArrayList<String>() {{
        add("com.app.autocallrecorder");
        add("pnd.app2.vault5");
        add("app.pnd.fourg");
        add("app.pnd.speedmeter");
        add("com.app.filemanager");
        add("com.app.autocallrecorder_pro");
        add("com.appbackup.security");
        add("com.all.superbackup");
        add("com.quantum.nearbyme");
        add("com.hd.editor");
        add("com.quantam.rail");
        add("com.quantum.cleaner");
        add("com.quantum.mtracker");
        add("app.quantum.supdate");
        add("com.app.pcollage");
        add("com.app.ninja");
        add("com.app.filemanager_pro");
        add("app.pnd.speedmeter_pro");
        add("app.pnd.fourg_pro");
        add("app.quantum.supdate_pro");
        add("com.quantum.mtracker_pro");
        add("com.quantum.nearbyme_pro");
        add("com.appbackup.security_pro");
        add("com.all.superbackup_pro");
        add("pnd.app.vault_pro");
    }};


    public String parseAssetData() {
//        mPackageName = "bhanu.kaushik";

        for (String s : quantumList) {
            if (mPackageName.contains("quantum") || mPackageName.equalsIgnoreCase(s)) {
                return readFromAssets("account_quantum.txt");
            }
        }

        if (mPackageName.contains("mtool") ||
                mPackageName.equalsIgnoreCase("com.appsbackupshare_pro") ||
                mPackageName.equalsIgnoreCase("com.appsbackupshare") ||
                mPackageName.equalsIgnoreCase("fnc.utm.com.flashoncallsmsreader") ||
                mPackageName.equalsIgnoreCase("fnc.utm.com.flashoncallsmsreader_pro")) {
            return readFromAssets("account_mtool.txt");
        }

        if (mPackageName.equalsIgnoreCase("com.pnd.shareall") || mPackageName.equalsIgnoreCase("app.phone2location")
                || mPackageName.equalsIgnoreCase("com.pnd.fourgspeed") || mPackageName.equalsIgnoreCase("com.q4u.qrscanner")) {
            return readFromAssets("account_qu.txt");
        }

        if (mPackageName.contains("appnextg")) {
            return readFromAssets("account_nextg.txt");
        }
        return readFromAssets("account_quantum.txt");
    }

    public int getSplash_poweredIcon() {


        for (String s : quantumList) {
            if (mPackageName.contains("quantum") || mPackageName.equalsIgnoreCase(s)) {
                return R.drawable.about_arrow;
            }
        }
        if (mPackageName.contains("mtool") ||
                mPackageName.equalsIgnoreCase("com.appsbackupshare_pro") ||
                mPackageName.equalsIgnoreCase("com.appsbackupshare") ||
                mPackageName.equalsIgnoreCase("fnc.utm.com.flashoncallsmsreader") ||
                mPackageName.equalsIgnoreCase("fnc.utm.com.flashoncallsmsreader_pro")) {
            return R.drawable.about_arrow;
        }

        if (mPackageName.equalsIgnoreCase("com.pnd.shareall") || mPackageName.equalsIgnoreCase("app.phone2location")
                || mPackageName.equalsIgnoreCase("com.pnd.fourgspeed") || mPackageName.equalsIgnoreCase("com.q4u.qrscanner")) {
            return R.drawable.about_arrow;
        }


        return R.drawable.about_arrow;
    }

    public int getAboutus_Icon() {


        for (String s : quantumList) {
            if (mPackageName.contains("quantum") || mPackageName.equalsIgnoreCase(s)) {
                return R.drawable.about_arrow;
            }
        }
        if (mPackageName.contains("mtool") ||
                mPackageName.equalsIgnoreCase("com.appsbackupshare_pro") ||
                mPackageName.equalsIgnoreCase("com.appsbackupshare") ||
                mPackageName.equalsIgnoreCase("fnc.utm.com.flashoncallsmsreader") ||
                mPackageName.equalsIgnoreCase("fnc.utm.com.flashoncallsmsreader_pro")) {
            return R.drawable.about_arrow;
        }

        if (mPackageName.equalsIgnoreCase("com.pnd.shareall") || mPackageName.equalsIgnoreCase("app.phone2location")
                || mPackageName.equalsIgnoreCase("com.pnd.fourgspeed") || mPackageName.equalsIgnoreCase("com.q4u.qrscanner")) {
            return R.drawable.about_arrow;
        }

        return R.drawable.about_arrow;
    }


}
