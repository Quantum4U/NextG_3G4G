package app.fcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import app.PrintLog;
import app.adshandler.EngineHandler;
import app.rest.request.DataRequest;
import app.server.v2.DataHubHandler;
import app.socket.EngineApiController;
import app.socket.Response;


/**
 * Created by rajeev on 26/02/18.
 */

public class InstallReferrerReceiver extends BroadcastReceiver {
    private String referrerId;
    private GCMPreferences preferences;
    private String sendURL;
    private DataHubHandler mHandler;

    @Override
    public void onReceive(Context context, Intent intent) {
        preferences = new GCMPreferences(context);
        try {
            if ((null != intent) && (intent.getAction().equals("com.android.vending.INSTALL_REFERRER"))) {
                PrintLog.print("Message App is getting installed first time..");
                referrerId = intent.getStringExtra("referrer");
                preferences.setreferrerId(referrerId);
                PrintLog.print("Message App is getting installed first time Referrer is: " + referrerId);

                mHandler = new DataHubHandler();
                doGCMRequest(context);
            }
        } catch (Exception e) {
            preferences.setGCMRegister(false);
        }


    }


    private void doGCMRequest(final Context mContext) {


        DataRequest mRequest = new DataRequest();
        EngineApiController mController = new EngineApiController(mContext, new Response() {
            @Override
            public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
/*                Gson gson = new Gson();
                ServerResponse gcmResponse = gson.fromJson(response.toString(), ServerResponse.class);
                PrintLog.print("response GCM OK app launch" + " " + gcmResponse.status + " " + gcmResponse.status + " " + gcmResponse.reqvalue);
                if (gcmResponse.status.equals("0")) {
                    preferences.setGCMRegister(true);
                    preferences.setVirtualGCMID(gcmResponse.reqvalue);
                    preferences.setGCMID("NA");
                } else {
                    preferences.setGCMRegister(false);
                }*/

                mHandler.parseReferalData(mContext, response.toString());

            }

            @Override
            public void onErrorObtained(String errormsg, int responseType) {
                PrintLog.print("response referal Failed app launch" + " " + errormsg);
                preferences.setGCMRegister(false);
            }
        }, EngineApiController.REFERRAL_ID_CODE);
        mController.getReferralRequest(mRequest);


    }


}
