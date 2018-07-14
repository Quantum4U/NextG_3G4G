package version_2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;

import app.adshandler.AHandler;
import app.adshandler.ANG_PromptHander;
import app.fcm.MapperUtils;
import app.pnd.fourg.SettingActivity;
import app.pnd.fourg.SplashActivityV3;
import app.serviceprovider.Utils;

import com.appnextg.fourg.R;

/**
 * Created by rajeev on 25/05/18.
 */

public class MapperActivity extends Activity {

    private String value = " ";
    private AHandler aHandler;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //  yourAllactivity = new ArrayList<>();
        // yourAllactivity = addActivity(yourAllactivity);

        aHandler = new AHandler();
        aHandler.vANG_CallOnBGLaunch(this);


        intent = getIntent();
        value = intent.getStringExtra(MapperUtils.keyValue);
        System.out.println("0643 key value" + " " + value);

        try {
            if (value != null) {
                if (value.equalsIgnoreCase(MapperUtils.LAUNCH_SPLASH)) {
                    this.finish();
                    //Remember to add here your LauncherClass.
                    startActivity(new Intent(this, SplashActivityV3.class));


                } else if (value.equalsIgnoreCase(MapperUtils.gcmAppLaunch)) {
                    this.finish();
                    //Remember to add here your LauncherClass.
                    startActivity(new Intent(this, MainActivity_V2.class));


                } else if (value.equalsIgnoreCase(MapperUtils.gcmRemoveAds)) {
                    this.finish();
                    //Remember to add here your RemoveAdClass.
                    aHandler.showRemoveAdsPrompt(this);

                } else if (value.equalsIgnoreCase(MapperUtils.gcmFeedbackApp)) {
                    this.finish();
                    //Remember to add here your FeedbackClass.
                    new Utils().sendFeedback(this);

                } else if (value.equalsIgnoreCase(MapperUtils.gcmMoreApp)) {
                    this.finish();
                    //Remember to add here your MoreAppClass.
                    new Utils().moreApps(this);

                } else if (value.equalsIgnoreCase(MapperUtils.gcmRateApp)) {

                    //Remember to add here your RareAppClass.
                    new ANG_PromptHander().ratee(this, getResources()
                            .getDrawable(R.drawable.version_ic_icon), true);

                } else if (value.equalsIgnoreCase(MapperUtils.gcmShareApp)) {
                    this.finish();
                    //Remember to add here your ShareAppClass.
                    new Utils().shareUrl(this);

                } else if (value.equalsIgnoreCase(MapperUtils.PhoneDetails)) {
                    this.finish();
                    startActivity(new Intent(this, SIMDetailsActivity.class));


                } else if (value.equalsIgnoreCase(MapperUtils.CallerAct)) {
                    this.finish();
                    startActivity(new Intent(this, CallerActivity.class));


                } else if (value.equalsIgnoreCase(MapperUtils.SimDetails)) {
                    this.finish();
                    startActivity(new Intent(this, PhoneDetailActivity.class));


                } else if (value.equalsIgnoreCase(MapperUtils.ThreeTOFour)) {
                    this.finish();
                    startActivity(new Intent(this, FourGActivity.class));


                } else if (value.equalsIgnoreCase(MapperUtils.Setting)) {
                    this.finish();
                    startActivity(new Intent(this, SettingActivity.class));
                } else {
                    this.finish();
                    //Remember to add here your LauncherClass.
                    startActivity(new Intent(this, MainActivity_V2.class));
                }
            } else {
                this.finish();
            }
        } catch (Exception e) {
            this.finish();
            startActivity(new Intent(this, MainActivity_V2.class));
        }


    }

}
