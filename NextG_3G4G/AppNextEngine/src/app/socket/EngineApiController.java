package app.socket;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import app.PrintLog;
import app.server.v2.DataHubConstant;

public class EngineApiController implements Response {
    private WeakReference<Context> contxt;
    private Response response;
    private EngineClient client;


    public static String BASE_URL_MAIN = "http://quantum4you.com/engine/";
    public static String TEST_URL_MAIN = "http://qsoftmobile.com/test/";

    public static String BASE_URL_NEXTG = "http://appnextg.com/engine/";

    public static String ENGINE_VERSION = "3";


    private String MASTER_SERVICE_URL;
    private String GCM_ID_SERVICE_URL;
    private String NOTIFICATION_ID_SERVICE_URL;
    private String VERSION_SERVICE_URl;
    private String REFERRAL_URL;

    public static final int MASTER_SERVICE_CODE = 1;
    public static final int GCM_SERVICE_CODE = 2;
    public static final int NOTIFICATION_ID_CODE = 3;
    public static final int VERSION_ID_CODE = 4;
    public static final int REFERRAL_ID_CODE = 5;

    private int responseType;
    private ProgressDialog dialog;
    private boolean isProgressShow = false;
//    private boolean isDataOnline = true;

    public EngineApiController(Context context, Response response,
                               int responseType, boolean isProgressShow) {
        this.contxt = new WeakReference<Context>(context);
        this.response = response;
        this.responseType = responseType;
        this.isProgressShow = isProgressShow;
        client = new EngineClient(contxt.get(), this);

        if (!context.getPackageName().contains("appnextg")) {

            if (DataHubConstant.IS_LIVE) {
                MASTER_SERVICE_URL = BASE_URL_MAIN + "adservice/adsresponse?engv=" + ENGINE_VERSION;
                GCM_ID_SERVICE_URL = BASE_URL_MAIN + "gcm/requestgcm?engv=" + ENGINE_VERSION;
                NOTIFICATION_ID_SERVICE_URL = BASE_URL_MAIN + "gcm/requestnotification?engv=" + ENGINE_VERSION;
                VERSION_SERVICE_URl = BASE_URL_MAIN + "adservice/checkappstatus?engv=" + ENGINE_VERSION;
                REFERRAL_URL = BASE_URL_MAIN + "gcm/requestreff?engv=" + ENGINE_VERSION;
            } else {
                MASTER_SERVICE_URL = TEST_URL_MAIN + "adservice/adsresponse?engv=" + ENGINE_VERSION;
                GCM_ID_SERVICE_URL = TEST_URL_MAIN + "gcm/requestgcm?engv=" + ENGINE_VERSION;
                NOTIFICATION_ID_SERVICE_URL = TEST_URL_MAIN + "gcm/requestnotification?engv=" + ENGINE_VERSION;
                VERSION_SERVICE_URl = TEST_URL_MAIN + "adservice/checkappstatus?engv=" + ENGINE_VERSION;
                REFERRAL_URL = TEST_URL_MAIN + "gcm/requestreff?engv=" + ENGINE_VERSION;
            }
        } else {
            MASTER_SERVICE_URL = BASE_URL_NEXTG + "adservice/adsresponse?engv=" + ENGINE_VERSION;
            GCM_ID_SERVICE_URL = BASE_URL_NEXTG + "gcm/requestgcm?engv=" + ENGINE_VERSION;
            NOTIFICATION_ID_SERVICE_URL = BASE_URL_NEXTG + "gcm/requestnotification?engv=" + ENGINE_VERSION;
            VERSION_SERVICE_URl = BASE_URL_NEXTG + "adservice/checkappstatus?engv=" + ENGINE_VERSION;
            REFERRAL_URL = BASE_URL_NEXTG + "gcm/requestreff?engv=" + ENGINE_VERSION;
        }

    }

    public EngineApiController(Context context, Response response,
                               int responseType) {
        this(context, response, responseType, true);
    }

    public void setFCMTokens(String mToken) {
        client.setFCMTokens(mToken);
    }

    public void setNotificatioID(String _id) {
        client.setNotificatioID(_id);
    }

    @Override
    public void onResponseObtained(Object response, int responseType,
                                   boolean isCachedData) {
        this.response.onResponseObtained(response, responseType, isCachedData);
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }


    public void getMasterData(Object mMasterRequest) {
        if (checkConnection())
            client.Communicate(MASTER_SERVICE_URL, mMasterRequest, responseType);
        //else
//        response.onResponseObtained(
//                loadjsonfromAsset(responseType, mMasterRequest),
//                MASTER_SERVICE_CODE, true);
    }


    public void getGCMIDRequest(Object mGCMIdRequest) {
        if (checkConnection())
            client.Communicate(GCM_ID_SERVICE_URL, mGCMIdRequest, responseType);
    }

    public void getNotificationIDRequest(Object mNotificationRequest) {
        if (checkConnection())
            client.Communicate(NOTIFICATION_ID_SERVICE_URL, mNotificationRequest, responseType);
    }


    public void getVersionRequest(Object mRequest) {
        if (checkConnection())
            client.Communicate(VERSION_SERVICE_URl, mRequest, responseType);
    }

    public void getCampaignRequest(Object obj) {
//        if(isDataOnline)
//            client.Communicate();
    }

    public void getReferralRequest(Object obj) {
        if (checkConnection()) {
            client.Communicate(REFERRAL_URL, obj, responseType);
        }
    }

    @Override
    public void onErrorObtained(String errormsg, int responseType) {
        this.response.onErrorObtained(errormsg, responseType);
        if (dialog != null) {
            dialog.cancel();
            dialog = null;
        }
    }

    public void showProgressDialog() {
        if (isProgressShow)
            dialog = ProgressDialog.show(contxt.get(), "", "");
    }

    public String loadjsonfromAsset(int responseTYpe, Object object) {
//        if (responseTYpe == EngineApiController.CAMPAIGN_DETAIL)
//            return loadJSONFromAsset(contxt.get(), "local_new");
        return null;
    }

    public String loadJSONFromAsset(Context context, String pathName) {
        PrintLog.print("json return is here" + pathName);
        String json = null;
        try {
            InputStream is = context.getAssets().open(pathName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            PrintLog.print("json retun is here" + json.length());
        } catch (IOException ex) {
            ex.printStackTrace();
            PrintLog.print("json retun is here" + ex);
            return null;
        }
        return json;
    }

    public boolean checkConnection() {
        final ConnectivityManager connMgr = (ConnectivityManager) contxt.get().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            //Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

}