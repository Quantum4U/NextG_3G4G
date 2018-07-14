package app.socket;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Cache.Entry;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import app.PrintLog;
import app.ecrypt.MCrypt;
import app.fcm.GCMPreferences;
import app.rest.request.DataRequest;
import app.rest.request.GCMIDData;
import app.rest.request.MasterData;
import app.rest.request.NotificationIDData;
import app.rest.request.ReferralData;
import app.rest.request.VersionData;

public class EngineClient {

    private int responseType = 0;
    private WeakReference<Context> weakReference;
    private Response resp;
    private GCMPreferences gcmPreferences;
    private String fcmToken, _notificatioID;
    private boolean fromFCMService = false;

    public EngineClient(Context context, Response res) {
        weakReference = new WeakReference<Context>(context);
        gcmPreferences = new GCMPreferences(context);
        this.resp = res;
    }

    public void setFCMTokens(String mToken) {
        this.fcmToken = mToken;
        fromFCMService = true;
    }

    public void setNotificatioID(String _id) {
        this._notificatioID = _id;
    }

    public void Communicate(String url, Object value, int responseType) {
        PrintLog.print("json check request :" + " url: " + url + " value: " + value.toString());
        this.responseType = responseType;
        try {
            RequestQueue queue = Volley.newRequestQueue(weakReference.get());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                    getJsonObject(value), createResponseListener(),
                    createErrorListener());

            if (isCacheRequestApplicable(responseType)) {
                String cache = getCachedValue(queue, jsonObjectRequest);
                PrintLog.print("65656 cache status is here " + cache
                        + " and " + responseType);
                PrintLog.print("7869 response obtained id " + cache);
                if (cache != null && cache.length() > 1)
                    resp.onResponseObtained(cache, responseType, false);
                else
                    queue.add(jsonObjectRequest);
            } else {
                queue.add(jsonObjectRequest);
                PrintLog.print("json obtained is here " + url + " a,d "
                        + jsonObjectRequest + " and ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Listener<JSONObject> createResponseListener() {

        return new Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PrintLog.print("7869 response obtained id " + response);
                resp.onResponseObtained(response, responseType, false);

            }
        };
    }


    private ErrorListener createErrorListener() {
        return new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String jsonString = "";
                PrintLog.print("response is here " + error);

                resp.onErrorObtained(jsonString, responseType);

            }
        };
    }


    public JSONObject getJsonObject(Object obj) throws JSONException {
        Gson gson = new Gson();

        if (responseType == EngineApiController.VERSION_ID_CODE) {
            VersionData vData = new VersionData(weakReference.get());
            String jsonStr = gson.toJson(vData);
            String encryptData = getEncrypterString(jsonStr);
            PrintLog.print("printing version EncryptData" + " " + jsonStr);

            ((DataRequest) obj).data = encryptData;

            String jsonObjectStr = gson.toJson(obj);
            PrintLog.print("printing version EncryptData 1" + " " + jsonObjectStr);

            return new JSONObject(jsonObjectStr);
        } else if (responseType == EngineApiController.MASTER_SERVICE_CODE) {
            MasterData mData = new MasterData(weakReference.get());
            String jsonStr = gson.toJson(mData);

            String encryptData = getEncrypterString(jsonStr);

            PrintLog.print("printing master EncryptData" + " " + jsonStr);

            ((DataRequest) obj).data = encryptData;

            String jsonObjectStr = gson.toJson(obj);
            PrintLog.print("printing master EncryptData 1" + " " + jsonObjectStr);

            return new JSONObject(jsonObjectStr);
        } else if (responseType == EngineApiController.GCM_SERVICE_CODE) {

//            if (fromFCMService) {
            GCMIDData gData = new GCMIDData(weakReference.get(), fcmToken);
            String jsonStr = gson.toJson(gData);
            String encryptData = getEncrypterString(jsonStr);
            PrintLog.print("printing gcm EncryptData from service" + " " + jsonStr);

            ((DataRequest) obj).data = encryptData;

            String jsonObjectStr = gson.toJson(obj);
            PrintLog.print("printing gcm EncryptData from service 1" + " " + jsonObjectStr);

            return new JSONObject(jsonObjectStr);

           /* } else {
                GCMIDData gData = new GCMIDData(weakReference.get(), gcmPreferences.getGCMID(), gcmPreferences.getreferrerId());
                String jsonStr = gson.toJson(gData);
                String encryptData = getEncrypterString(jsonStr);
                PrintLog.print("printing gcm EncryptData from else" + " " + jsonStr);

                ((DataRequest) obj).data = encryptData;

                String jsonObjectStr = gson.toJson(obj);
                PrintLog.print("printing gcm EncryptData from else 1 " + " " + jsonObjectStr);

                return new JSONObject(jsonObjectStr);

            }*/
        } else if (responseType == EngineApiController.NOTIFICATION_ID_CODE) {

            NotificationIDData notiData = new NotificationIDData(_notificatioID);
            String jsonStr = gson.toJson(notiData);
            String encryptData = getEncrypterString(jsonStr);
            PrintLog.print("printing notification EncryptData " + " " + jsonStr);

            ((DataRequest) obj).data = encryptData;

            String jsonObjectStr = gson.toJson(obj);
            PrintLog.print("printing notification Encryption 1" + " " + jsonObjectStr);

            return new JSONObject(jsonObjectStr);
        } else if (responseType == EngineApiController.REFERRAL_ID_CODE) {
            ReferralData rData = new ReferralData(weakReference.get(), gcmPreferences.getreferrerId());
            String jsonStr = gson.toJson(rData);
            String encryptData = getEncrypterString(jsonStr);
            PrintLog.print("printing referal EncryptData " + " " + jsonStr);

            ((DataRequest) obj).data = encryptData;

            String jsonObjectStr = gson.toJson(obj);
            PrintLog.print("printing referal from 1" + " " + jsonObjectStr);
            return new JSONObject(jsonObjectStr);
        }

        return null;
    }

    private String getEncrypterString(String jsonStr) {
        String value = "";
        MCrypt mcrypt = new MCrypt();
        try {
            value = MCrypt.bytesToHex(mcrypt.encrypt(jsonStr));
        } catch (Exception e) {
            PrintLog.print("exception encryption" + " " + e);
            e.printStackTrace();
        }
        return value;
    }

    public static String loadJSONFromAsset(Context context, String pathName) {
        PrintLog.print("45845 json retun is here" + pathName);
        String json = null;
        try {
            PrintLog.print("json retun is here" + pathName);
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

    public String getCachedValue(RequestQueue queue, JsonObjectRequest request) {
        Entry entry = queue.getCache().get(request.getCacheKey());
        PrintLog.print("78989 status of cache entry is here "
                + request.getCacheKey());

        if (entry != null && entry.data != null)
            return new String(entry.data);
        else
            return null;

    }

    public boolean isCacheRequestApplicable(int responeType) {
        // if (responeType == EngineApiController.ROOTPAGEDETAIL
        // || responseType == EngineApiController.NEXTPAGEDETAIL)
        // return true;
        // else
        return false;

    }
}
