package app.adshandler;

import android.content.Context;
import android.os.AsyncTask;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.io.IOException;

import app.PrintLog;
import app.fcm.GCMPreferences;
import app.fcm.ServerResponse;
import app.pnd.adshandler.R;
import app.rest.request.DataRequest;
import app.server.v2.DataHubConstant;
import app.server.v2.DataHubHandler;
import app.server.v2.DataHubPreference;
import app.socket.EngineApiController;
import app.socket.Response;

/**
 * Created by quantum4u1 on 25/04/18.
 */

public class EngineHandler {

    private DataHubPreference preference;
    private DataHubHandler mHandler;
    private DataHubConstant mConstant;
    private GCMPreferences gcmPreference;

    private Context mContext;

    public EngineHandler(Context context) {
        preference = new DataHubPreference(context);
        mHandler = new DataHubHandler();
        mConstant = new DataHubConstant(context);
        gcmPreference = new GCMPreferences(context);
        this.mContext = context;
    }


    public void initServices(boolean fetchFromServer) {
        if (fetchFromServer) {
            doVersionRequest();
        } else {
            PrintLog.print("get pref data" + " " + new DataHubPreference(mContext).getAdsResponse());
            new DataHubHandler().parseMasterData(mContext, new DataHubPreference(mContext).getAdsResponse());
        }
    }

    private void doVersionRequest() {
        DataRequest dataRequest = new DataRequest();

        EngineApiController mApiController = new EngineApiController(mContext, new Response() {
            @Override
            public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
                PrintLog.print("response version OK" + " " + response);

                mHandler.parseVersionData(mContext, response.toString(), new DataHubHandler.MasterRequestListener() {
                    @Override
                    public void callMasterService() {
                        PrintLog.print("checking version flow domasterRequest");
                        doMasterRequest();
                    }
                });


            }

            @Override
            public void onErrorObtained(String errormsg, int responseType) {
                PrintLog.print("response version ERROR" + " " + errormsg);
                if (!preference.getAdsResponse().equalsIgnoreCase(DataHubConstant.KEY_NA)) {
                    mHandler.parseMasterData(mContext, preference.getAdsResponse());
                } else {
                    mHandler.parseMasterData(mContext, mConstant.parseAssetData());
                }
            }
        }, EngineApiController.VERSION_ID_CODE);
        mApiController.getVersionRequest(dataRequest);

        doGCMRequest();
    }


    private void doMasterRequest() {
        DataRequest mMasterRequest = new DataRequest();

        EngineApiController apiController = new EngineApiController(mContext, new Response() {
            @Override
            public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
                PrintLog.print("response master OK" + " " + response.toString() + " :" + responseType);

                mHandler.parseMasterData(mContext, response.toString());

            }

            @Override
            public void onErrorObtained(String errormsg, int responseType) {
                PrintLog.print("response master Failed" + " " + errormsg + " :type" + " " + responseType);

                if (!preference.getAdsResponse().equalsIgnoreCase(DataHubConstant.KEY_NA)) {
                    mHandler.parseMasterData(mContext, preference.getAdsResponse());
                } else {
                    mHandler.parseMasterData(mContext, mConstant.parseAssetData());
                }
            }
        }, EngineApiController.MASTER_SERVICE_CODE);
        apiController.getMasterData(mMasterRequest);


    }


//    private void doGCMRequest() {
//        System.out.println("here is the update fcm check" + " " + gcmPreference.getGCMRegister() + " " + gcmPreference.getGCMID());
//        System.out.println("check check check 01");
//       /* if (!gcmPreference.getGCMRegister() && (gcmPreference.getGCMID().equalsIgnoreCase("NA")
//                || gcmPreference.getGCMID().equalsIgnoreCase(""))) {
//            System.out.println("check check check 02");
//            new Async().execute();
//        } else */
//        if (!gcmPreference.getGCMRegister() && (!gcmPreference.getGCMID().equalsIgnoreCase("NA"))) {
//            System.out.println("check check check 03");
//            PrintLog.print("check gcm hit on launch" + " " + gcmPreference.getGCMRegister() + " " + gcmPreference.getGCMID());
//
//
//            DataRequest mRequest = new DataRequest();
//            EngineApiController mController = new EngineApiController(mContext, new Response() {
//                @Override
//                public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
//                    Gson gson = new Gson();
//                    ServerResponse gcmResponse = gson.fromJson(response.toString(), ServerResponse.class);
//                    PrintLog.print("response GCM OK app launch" + " " + " " + gcmResponse.status + " " + gcmResponse.reqvalue);
//                    if (gcmResponse.status != null && gcmResponse.reqvalue != null) {
//                        if (gcmResponse.status.equals("0")) {
//                            gcmPreference.setGCMRegister(true);
//                            gcmPreference.setVirtualGCMID(gcmResponse.reqvalue);
//                            gcmPreference.setGCMID("NA");
//                        } else {
//                            gcmPreference.setGCMRegister(false);
//                        }
//                    }
//                }
//
//                @Override
//                public void onErrorObtained(String errormsg, int responseType) {
//                    PrintLog.print("response GCM Failed app launch" + " " + errormsg);
//                    gcmPreference.setGCMRegister(false);
//                }
//            }, EngineApiController.GCM_SERVICE_CODE);
//            mController.getGCMIDRequest(mRequest);
//        }
//    }

    private void doGCMRequest() {
        if (!gcmPreference.getGCMRegister()) {
            if (!gcmPreference.getGCMID().equalsIgnoreCase("NA")) {
                DataRequest mRequest = new DataRequest();
                EngineApiController mApiController = new EngineApiController(mContext, new Response() {
                    @Override
                    public void onResponseObtained(Object response, int responseType, boolean isCachedData) {

                        mHandler.parseFCMData(mContext, response.toString());

                    }

                    @Override
                    public void onErrorObtained(String errormsg, int responseType) {
                        System.out.println("response GCM Failed receiver" + " " + errormsg);
                        gcmPreference.setGCMRegister(false);
                    }
                }, EngineApiController.GCM_SERVICE_CODE);
                mApiController.setFCMTokens(gcmPreference.getGCMID());
                mApiController.getGCMIDRequest(mRequest);
            } else {
                new Async().execute();
            }
        }


        if (!gcmPreference.getReferalRegister() && !gcmPreference.getreferrerId().equalsIgnoreCase("NA")) {
            DataRequest mRequest = new DataRequest();
            EngineApiController mController = new EngineApiController(mContext, new Response() {
                @Override
                public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
                    PrintLog.print("response referal success" + " ");
                    mHandler.parseReferalData(mContext, response.toString());
                }

                @Override
                public void onErrorObtained(String errormsg, int responseType) {
                    PrintLog.print("response referal Failed app launch 1" + " " + errormsg);
                    gcmPreference.setGCMRegister(false);
                }
            }, EngineApiController.REFERRAL_ID_CODE);
            mController.getGCMIDRequest(mRequest);
        }
    }


    class Async extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            System.out.println("ppppppppp here get ding check");

            String token = null;
            try {
//                token = FirebaseInstanceId.getInstance().getToken("652718777091", "FCM");
                token = FirebaseInstanceId.getInstance().getToken(mContext.getResources().getString(R.string.fcm_defaultSenderId), "FCM");
                System.out.println("ppppppppp here get token 1 " + " " + token);
            } catch (IOException e) {
                System.out.println("ppppppppp here get token e" + " " + e);
                e.printStackTrace();
            }


            DataRequest mRequest = new DataRequest();
            final EngineApiController mController = new EngineApiController(mContext, new Response() {
                @Override
                public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
                  /*  Gson gson = new Gson();
                    ServerResponse gcmResponse = gson.fromJson(response.toString(), ServerResponse.class);
                    PrintLog.print("response GCM OK app launch" + " " + " " + gcmResponse.status + " " + gcmResponse.reqvalue);
                    if (gcmResponse.status != null && gcmResponse.reqvalue != null) {
                        if (gcmResponse.status.equals("0")) {
                            gcmPreference.setGCMRegister(true);
                            gcmPreference.setVirtualGCMID(gcmResponse.reqvalue);
                            gcmPreference.setGCMID("NA");
                        } else {
                            gcmPreference.setGCMRegister(false);
                        }
                    }*/

                    if (mHandler != null)
                        mHandler = new DataHubHandler();
                    mHandler.parseFCMData(mContext, response.toString());

                }

                @Override
                public void onErrorObtained(String errormsg, int responseType) {
                    PrintLog.print("response GCM Failed app launch" + " " + errormsg);
                    gcmPreference.setGCMRegister(false);
                }
            }, EngineApiController.GCM_SERVICE_CODE);
            mController.setFCMTokens(token);
            mController.getGCMIDRequest(mRequest);

            return null;
        }
    }

}
