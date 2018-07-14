package app.pnd.fourg;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import app.fcm.GCMPreferences;
import app.rest.request.DataRequest;
import app.server.v2.DataHubHandler;
import app.socket.EngineApiController;
import app.socket.Response;

/**
 * Created by black on 6/25/2016.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private GCMPreferences preferences;
    private DataHubHandler mHandler;

    @Override
    public void onTokenRefresh() {
        preferences = new GCMPreferences(this);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("my token refreshed" + " " + refreshedToken);
        preferences.setGCMID(refreshedToken);
        mHandler = new DataHubHandler();

//        requestGCM(refreshedToken);


    }


    private void requestGCM(String mToken) {

        DataRequest mRequest = new DataRequest();
        EngineApiController mApiController = new EngineApiController(getApplicationContext(), new Response() {
            @Override
            public void onResponseObtained(Object response, int responseType, boolean isCachedData) {

                mHandler.parseFCMData(getApplicationContext(),response.toString());

            }

            @Override
            public void onErrorObtained(String errormsg, int responseType) {
                System.out.println("response GCM Failed receiver" + " " + errormsg);
                preferences.setGCMRegister(false);
            }
        }, EngineApiController.GCM_SERVICE_CODE);
        mApiController.setFCMTokens(mToken);
        mApiController.getGCMIDRequest(mRequest);
    }


  /*  private void sendRegistrationToServer(String token) {
        preferences.setTokenName(token);
        // Add custom implementation, as needed.
        //function in the activity that corresponds to the layout button

        HashMap<String, String> postDataParams = new HashMap<String, String>();
        postDataParams.put("gcmid", token);
        postDataParams.put("osversion", preferences.getDeviceName());
        postDataParams.put("version", preferences.getAndroidVersion());
        postDataParams.put("country", preferences.getCountry());

        sendURL = GCMUtils.sendDataURL + token + "&app_id=" + preferences.getregisterYourApp() +
                "&country=" + preferences.getCountry() + "&version=" + preferences.getAppVersion() +
                "&osversion=" + preferences.getAndroidVersion() + "&dversion=" + preferences.getDeviceName() + "&reffid=" + preferences.getreferrerId();
        System.out.println("1417 sendURL" + " " + sendURL);
        new myTask().execute();
    }

    private ServerResponse myResponse;

    private class myTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(sendURL);
            Gson gson = new Gson();
            myResponse = gson.fromJson(jsonStr, ServerResponse.class);
            return jsonStr;

        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            //System.out.println("1417 sendURL "+sendURL);
            //System.out.println("1417 response "+response);
            //System.out.println("1417 server resp"+" "+myResponse.status+" "+myResponse.message+" "+myResponse.reqvalue);
            if (myResponse != null) {
                if (myResponse.message.equalsIgnoreCase("Success") && myResponse.status.equalsIgnoreCase("0")) {

                }
            }

        }
    }*/
}
