package app.pnd.fourg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import app.adshandler.AHandler;
import app.campaign.CampaignHandler;
import app.fcm.GCMPreferences;
import app.serviceprovider.Utils;

import com.appnextg.fourg.R;

import version_2.MainActivity_V2;

/**
 * Created by rajeev on 25/05/18.
 */
public class SplashActivityV3 extends AppCompatActivity {
    private GCMPreferences mPreference;
    private Animation animation;
    private ImageView imageView;
    RelativeLayout layoutStart;
    private Handler h;
    private boolean appLaunch = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_firebase);

        appLaunch = false;

        imageView = (ImageView) findViewById(R.id.imageView);
        new AHandler().vANG_CallOnSplash(this, new AHandler.OnCacheFullAdLoaded() {
            @Override
            public void onFullAdLoaded() {
                if (!mPreference.isFirsttime()) {
                    launchApp();
                }

            }

            @Override
            public void onFullAdFailed() {
                if (!mPreference.isFirsttime()) {
                    launchApp();

                    try {
                        if (h != null)
                            h.removeCallbacks(r);
                    } catch (Exception e) {
                        System.out.println("exception splash 1" + " " + e);
                    }
                }
            }
        });
        CampaignHandler.getInstance().initCampaign(this, null);
        mPreference = new GCMPreferences(this);
        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in_splash);
        layoutStart = (RelativeLayout) findViewById(R.id.layoutStart);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mPreference.isFirsttime()) {
                    layoutStart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        layoutStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchApp();
                mPreference.setFirstTime(false);
            }
        });

        if (mPreference.isFirsttime()) {
            //layoutStart.setVisibility(View.VISIBLE);
            imageView.setAnimation(animation);
        } else {
            findViewById(R.id.imageView).setVisibility(View.GONE);
            ImageView logo = (ImageView) findViewById(R.id.Logo);
            logo.setVisibility(View.VISIBLE);
            logo.setAnimation(animation);

            layoutStart.setVisibility(View.GONE);

            h = new Handler();
            h.postDelayed(r, 6000);
        }


        LinearLayout layout_tnc = (LinearLayout) findViewById(R.id.layout_tnc);
        new Utils().showPrivacyPolicy(this, layout_tnc, mPreference.isFirsttime());

        LinearLayout adsbanner = (LinearLayout) findViewById(R.id.adsbanner);
        adsbanner.addView(new AHandler().getBannerHeader(this));

    }


    private Runnable r = new Runnable() {
        @Override
        public void run() {
            launchApp();
        }
    };

    private void launchApp() {
        System.out.println("1241 app launch logs" + " " + appLaunch);
        if (appLaunch == false) {
            appLaunch = true;
            startActivity(new Intent(SplashActivityV3.this, MainActivity_V2.class));
            finish();
        }
    }

}
