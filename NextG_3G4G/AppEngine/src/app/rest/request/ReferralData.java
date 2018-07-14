package app.rest.request;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import app.rest.rest_utils.RestUtils;
import app.server.v2.DataHubConstant;

/**
 * Created by rajeev on 21/05/18.
 */

public class ReferralData {

    @SerializedName("app_id")
    public String appID = DataHubConstant.APP_ID;

//    @SerializedName("gcmid")
//    public String gcmID;

    @SerializedName("country")
    public String country;

    @SerializedName("screen")
    public String screen;

    @SerializedName("launchcount")
    public String launchCount;

    @SerializedName("version")
    public String version;

    @SerializedName("osversion")
    public String osVersion;

    @SerializedName("dversion")
    public String deviceVersion;

    @SerializedName("referrer")
    public String referrer;


    public ReferralData(Context context, String refferalID) {
//        gcmID = mGCMId;
        country = RestUtils.getCountryCode(context);
        screen = RestUtils.getScreenDimens(context);
        launchCount = RestUtils.getAppLaunchCount();
        version = RestUtils.getVersion(context);
        osVersion = RestUtils.getOSVersion(context);
        deviceVersion = RestUtils.getDeviceVersion(context);
        referrer = refferalID;
    }
}
