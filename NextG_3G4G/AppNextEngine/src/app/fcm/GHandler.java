package app.fcm;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by rajeev on 16/03/18.
 */

public class GHandler {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static void registerGCM(Activity ctx){
        if (checkPlayServices(ctx)) {
            // Start IntentService to register this application with GCM.
//            Intent intent = new Intent(ctx, RegistrationIntentService.class);
//            ctx.startService(intent);
        }
    }


    private static boolean checkPlayServices(Activity ctx) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(ctx);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(ctx, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Toast.makeText(ctx, "This device is not supported.", Toast.LENGTH_LONG).show();
                //finish();
            }
            return false;
        }
        return true;
    }


    public static void phoneInformation(Activity ctx) {
//        GCMPreferences preferences= new GCMPreferences(ctx);
//        if (preferences.getTokenValue() == false) {
//            preferences.setregisterYourApp(MapperUtils.APP_ID);
//            preferences.setDeviceName(new GCMUtils().getDeviceName());
//            preferences.setAndroidVersion(new GCMUtils().getAndroidVersion());
//            preferences.setCountry(new GCMUtils().getUserCountry(ctx));
//            preferences.setAppVersion(new GCMUtils().getAppVersionCode(ctx));
//            //preferences.setDeviceId(new GCMUtils().getDeviceId(MainActivity.this));
//        }
//
//        if (new GCMUtils().getAppVersionCode(ctx) != preferences.getAppVersion()
//                || new GCMUtils().getAndroidVersion() != preferences.getAndroidVersion()
//                || new GCMUtils().getDeviceName() != preferences.getDeviceName()
//                || new GCMUtils().getUserCountry(ctx) != preferences.getCountry()
//                || MapperUtils.APP_ID != preferences.getregisterYourApp()) {
//
//            preferences.setregisterYourApp(MapperUtils.APP_ID);
//            preferences.setDeviceName(new GCMUtils().getDeviceName());
//            preferences.setAndroidVersion(new GCMUtils().getAndroidVersion());
//            preferences.setCountry(new GCMUtils().getUserCountry(ctx));
//            preferences.setAppVersion(new GCMUtils().getAppVersionCode(ctx));
//
//        }
    }
}
