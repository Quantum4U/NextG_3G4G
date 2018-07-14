package app.pnd.fourg;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.util.Iterator;

import app.fcm.FCMController;
import app.fcm.NotificationUIResponse;
import app.rest.request.DataRequest;
import app.server.v2.DataHubHandler;
import app.socket.EngineApiController;
import app.socket.Response;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";


    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        System.out.println("152 get message");
        try {

            if (remoteMessage.getData() != null) {
                Iterator myVeryOwnIterator = remoteMessage.getData().keySet().iterator();
                while (myVeryOwnIterator.hasNext()) {
                    String key = (String) myVeryOwnIterator.next();
                    String value = (String) remoteMessage.getData().get(key);
                    System.out.println("MyFirebaseMessagingService.onMessageReceived " + key + " " + value);


                    DataRequest request = new DataRequest();
                    EngineApiController mApiController = new EngineApiController(getApplicationContext(),
                            new Response() {
                                @Override
                                public void onResponseObtained(Object response, int responseType, boolean isCachedData) {
                                    new DataHubHandler().parseNotificationData(response.toString(), new DataHubHandler.NotificationListener() {
                                        @Override
                                        public void pushFCMNotification(String json) {
                                            System.out.println("json on push notification received" + " " + json);
                                            showFCMNotification(json);
                                        }
                                    });
                                }

                                @Override
                                public void onErrorObtained(String errormsg, int responseType) {
                                    System.out.println("response on notification ERROR" + " " + errormsg);
                                }
                            }, EngineApiController.NOTIFICATION_ID_CODE);
                    mApiController.setNotificatioID(value);
                    mApiController.getNotificationIDRequest(request);

                }
            }

        } catch (Exception e) {
            System.out.println("exception 152 get here is the notification exception" + " " + e);
        }
    }

    private void showFCMNotification(String response) {
        Gson gson = new Gson();
        NotificationUIResponse notiResponse = gson.fromJson(response.toString(), NotificationUIResponse.class);

        if (notiResponse.status.equalsIgnoreCase("0")) {
            new FCMController(getApplicationContext(), notiResponse);
        }
    }

}
