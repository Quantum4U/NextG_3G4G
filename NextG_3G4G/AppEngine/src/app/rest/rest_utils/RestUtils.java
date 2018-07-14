package app.rest.rest_utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import app.server.v2.DataHubConstant;

/**
 * Created by quantum4u1 on 23/04/18.
 */

public class RestUtils {


    public static String getCountryCode(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        return tm.getSimCountryIso();
//        return "FR";

//        String locale;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            locale = context.getResources().getConfiguration().getLocales().get(0).getCountry();
//        } else {
//            locale = context.getResources().getConfiguration().locale.getCountry();
//        }
//        return locale;
    }

    public static String getScreenDimens(Context context) {
        String Screen = "hdpi";
        try {
            float den = context.getResources().getDisplayMetrics().density;
            if (den == .75)
                Screen = "LDPI";

            else if (den == 1.0)
                Screen = "MDPI";

            else if (den == 1.5)
                Screen = "HDPI";

            else if (den == 2.0)
                Screen = "XHDPI";

            else if (den == 3.0)
                Screen = "XXHDPI";

            else if (den == 4.0)
                Screen = "XXXHDPI";


        } catch (Exception e) {
            return Screen;
        }
        return Screen;
    }

    public static String getAppLaunchCount() {
        return Integer.toString(DataHubConstant.APP_LAUNCH_COUNT);
    }

    public static String getVersion(Context context) {
        String appversion = "1";
        try {
            appversion = "" + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {

        }
        return appversion;
    }

    public static String getOSVersion(Context context) {
        return "" + Build.VERSION.SDK_INT;
    }

    public static String getDeviceVersion(Context context) {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;

        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer);
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


}
