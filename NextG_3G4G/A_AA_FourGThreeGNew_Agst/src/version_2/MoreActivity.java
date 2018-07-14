package version_2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appnextg.fourg.R;

import java.util.Locale;

import app.adshandler.AHandler;
import app.adshandler.ANG_PromptHander;
import app.pnd.fourg.SettingActivity;
import app.serviceprovider.Utils;

/**
 * Created by rajeev on 28/05/18.
 */

public class MoreActivity extends AppCompatActivity {
    private RelativeLayout layoutAds, layoutSetting, layoutAboutus, layoutRate, layoutFreeAp, layoutShare, layoutFeedback;
    //private Preference preference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_activity);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("More");
        mToolbar.setTitleTextColor(Color.WHITE);

//        layoutAds = (RelativeLayout) findViewById(R.id.layoutRemoveAds);
        layoutSetting = (RelativeLayout) findViewById(R.id.layoutSetting);
        layoutAboutus = (RelativeLayout) findViewById(R.id.layoutAboutUs);
        layoutRate = (RelativeLayout) findViewById(R.id.layoutRate);
        layoutFreeAp = (RelativeLayout) findViewById(R.id.layoutFreeApp);
        layoutShare = (RelativeLayout) findViewById(R.id.layoutshare);
        layoutFeedback = (RelativeLayout) findViewById(R.id.layoutFeedback);


        ((LinearLayout) findViewById(R.id.adsbanner)).addView(new AHandler().getBannerHeader((this)));


//        layoutAds.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AHandler().showFullAds((MoreActivity.this), false);
//                //startActivity(new Intent(getActivity(), UpgradeActivity.class));
//                if (CommonUtils.isNetworkConnected(MoreActivity.this)) {
//                    new AHandler().openBillingPage((MoreActivity.this));
//                } else {
//                    Toast.makeText(MoreActivity.this, getResources().getString(R.string.internetConnetion), Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        layoutAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AHandler().showFullAds((MoreActivity.this), false);
                new AHandler().showAboutUs((MoreActivity.this));
            }
        });


        layoutRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AHandler().showFullAds((MoreActivity.this), false);
                new ANG_PromptHander().ratee((MoreActivity.this), getResources()
                        .getDrawable(R.drawable.version_ic_icon), false);
            }
        });


        layoutFreeAp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AHandler().showFullAds((MoreActivity.this), false);
                new Utils().moreApps((MoreActivity.this));
            }
        });


        layoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AHandler().showFullAds((MoreActivity.this), false);
                new Utils().shareUrl((MoreActivity.this));
            }
        });


        layoutFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AHandler().showFullAds((MoreActivity.this), false);
                new Utils().sendFeedback((MoreActivity.this));
            }
        });

        layoutSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AHandler().showFullAds((MoreActivity.this), false);
                startActivity(new Intent(MoreActivity.this, SettingActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
