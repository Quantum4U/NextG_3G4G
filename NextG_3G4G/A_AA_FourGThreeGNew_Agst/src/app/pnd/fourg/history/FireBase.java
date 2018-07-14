package app.pnd.fourg.history;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.LinearLayout;

import com.checkspeed.Utility;

import app.adshandler.AHandler;
import app.adshandler.DataHub;
import app.pnd.fourg.AppLauncher;
import app.pnd.fourg.MainActivity;

import app.server.v2.DataHubConstant;
import com.appnextg.fourg.R;
import version_2.MainActivity_V2;


/**
 * Created by Chetan on 7/11/2016.
 */
public class FireBase extends AppCompatActivity {
    public static String TYPE_1 = "type1";
    public static String TYPE_2 = "type2";
    public static String TYPE_3 = "type3";
    public static String TYPE_4 = "type4";
    public static String TYPE_5 = "type5";
    public static String TYPE_6 = "type6";
    public static String TYPE_7 = "type7";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("<<<checking FireBase.onCreate MainActivity start ");

        Intent mainintent = getIntent();
        if (getIntent() != null) {
            if (mainintent.getStringExtra("type1") != null) {
                callMain();
            } else if (mainintent.getStringExtra("type2") != null) {
                try {
                    Intent webintent = new Intent(Intent.ACTION_VIEW);
                    webintent.setData(Uri.parse(mainintent.getStringExtra("type2")));
                    startActivity(webintent);
                    finish();
                } catch (Exception e) {
                    callMain();
                }
            } else if (mainintent.getStringExtra("type3") != null) {
                Intent promptintent = new Intent(this, InAppPrompt.class);
                promptintent.putExtra(Utility.INAPP_Text, mainintent.getStringExtra("type3"));
                startActivity(promptintent);
                finish();
            }
            else if (mainintent.getStringExtra(TYPE_4) != null) {
                new app.serviceprovider.Utils().shareUrl(this);
                finish();
            } else if (mainintent.getStringExtra(TYPE_5) != null) {
                new app.serviceprovider.Utils().rateUs(this);
                finish();
            } else if (mainintent.getStringExtra(TYPE_6) != null) {
                new app.serviceprovider.Utils().moreApps(this);
                finish();
            } else if (mainintent.getStringExtra(TYPE_7) != null) {
                callMain();
            } else {
                System.out.println("please see type 3 excp");
                callMain();
            }
        } else {
            System.out.println("<<<checking FireBase.onCreate MainActivity ");
            callMain();
        }
    }

    private void callMain() {

       // new AHandler().v2CallOnSplash(this, DataHubConstant.APP_ID);
        setContentView(R.layout.splash_firebase);
        LinearLayout adsbanner= (LinearLayout)findViewById(R.id.adsbanner);
        adsbanner.addView(new AHandler().getBannerHeader(this));

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                startActivity(new Intent(FireBase.this, MainActivity_V2.class));
                finish();
            }
        }, 3000);


    }

}
