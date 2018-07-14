package app.ui;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import app.PrintLog;
import app.adshandler.DataHub;
import app.pnd.adshandler.R;
import app.server.v2.DataHubPreference;
import app.server.v2.Slave;
import app.serviceprovider.Utils;


/**
 * Created by quantum4u1 on 15/03/18.
 */

public class ANG_AboutActivity extends AppCompatActivity {
    private CardView rl_website, rl_our_apps, rl_terms_of_service, rl_privacy_policy;
    private ImageView iv_fb, iv_insta, iv_twitter;
    private TextView tv_query, appVersion, appName;

    private int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_nextg);

        ImageView logo = (ImageView) findViewById(R.id.logo);
        try {

            Drawable icon = getPackageManager().getApplicationIcon(getPackageName());
            logo.setImageDrawable(icon);
        } catch (Exception e) {

        }

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i++;
                if (i == 10) {
                    i = 0;
                    Intent myIntent = new Intent(ANG_AboutActivity.this, ANG_PrintActivity.class);
                    startActivity(myIntent);
                }
            }
        });

        tv_query = (TextView) findViewById(R.id.tv_query);
        appVersion = (TextView) findViewById(R.id.appVersion);
        appName = (TextView) findViewById(R.id.appName);

        appName.setText(new DataHubPreference(this).getAppname());

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            appVersion.setText("(Ver. " + version + ")");
        } catch (Exception e) {
            PrintLog.print("exception in checking app version");
        }

        String myString = "if any issues/Query please contact us ";
        int i1 = myString.indexOf("c");
        int i2 = myString.indexOf("us");
        tv_query.setMovementMethod(LinkMovementMethod.getInstance());
        tv_query.setText(myString, TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable) tv_query.getText();
        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                new Utils().sendFeedback(ANG_AboutActivity.this);
            }
        };
        mySpannable.setSpan(myClickableSpan, i1, i2 + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        rl_website = (CardView) findViewById(R.id.rl_website);
        rl_our_apps = (CardView) findViewById(R.id.rl_our_apps);
        rl_terms_of_service = (CardView) findViewById(R.id.rl_terms_of_service);
        rl_privacy_policy = (CardView) findViewById(R.id.rl_privacy_policy);

        iv_fb = (ImageView) findViewById(R.id.iv_fb);
        iv_insta = (ImageView) findViewById(R.id.iv_insta);
        iv_twitter = (ImageView) findViewById(R.id.iv_twitter);

        iv_fb.setOnClickListener(mOnClickListener);
        iv_insta.setOnClickListener(mOnClickListener);
        iv_twitter.setOnClickListener(mOnClickListener);

        rl_website.setOnClickListener(mOnClickListener);
        rl_our_apps.setOnClickListener(mOnClickListener);
        rl_terms_of_service.setOnClickListener(mOnClickListener);
        rl_privacy_policy.setOnClickListener(mOnClickListener);


        if (Slave.ETC_5.equalsIgnoreCase("10")) {
            findViewById(R.id.socialLayout).setVisibility(View.VISIBLE);
        }
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.rl_website) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Slave.ABOUTDETAIL_WEBSITELINK)));
                } catch (Exception e) {
                }
            } else if (view.getId() == R.id.rl_our_apps) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Slave.ABOUTDETAIL_OURAPP)));
                } catch (Exception e) {
                }
            } else if (view.getId() == R.id.rl_terms_of_service) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Slave.ABOUTDETAIL_TERM_AND_COND)));
                } catch (Exception e) {
                }
            } else if (view.getId() == R.id.rl_privacy_policy) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Slave.ABOUTDETAIL_PRIVACYPOLICY)));
                } catch (Exception e) {
                }
            } else if (view.getId() == R.id.iv_fb) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Slave.ABOUTDETAIL_FACEBOOK)));
                } catch (Exception e) {
                }
            } else if (view.getId() == R.id.iv_insta) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Slave.ABOUTDETAIL_INSTA)));
                } catch (Exception e) {
                }
            } else if (view.getId() == R.id.iv_twitter) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Slave.ABOUTDETAIL_TWITTER)));
                } catch (Exception e) {
                }
            }
        }


    };
}