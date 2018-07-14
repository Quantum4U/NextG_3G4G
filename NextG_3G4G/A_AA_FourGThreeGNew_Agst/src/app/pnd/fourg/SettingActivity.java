package app.pnd.fourg;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;


import com.appnextg.fourg.R;

import app.adshandler.AHandler;




public class SettingActivity extends AppCompatActivity {
    private Switch image, video, audio, apk, incoming, outgoing;
    private MediaPreferences sharedPreferences;
    private LinearLayout adsbanner;
    private RelativeLayout layoutCallID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_setting);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("Setting");
        mToolbar.setTitleTextColor(Color.WHITE);


        layoutCallID = (RelativeLayout) findViewById(R.id.layoutCallID);
        layoutCallID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AHandler().showFullAds(SettingActivity.this, false);
//                Calldorado.createCalldoradoSettingsActivity(SettingActivity.this);
            }
        });

        adsbanner = (LinearLayout) findViewById(R.id.adsbanner);

        adsbanner.addView(new AHandler().showNativeLarge(SettingActivity.this));

        sharedPreferences = new MediaPreferences(this);

        image = (Switch) findViewById(R.id.switchPhoto);
        video = (Switch) findViewById(R.id.switchVideo);
        audio = (Switch) findViewById(R.id.switchMusic);
        apk = (Switch) findViewById(R.id.switchAPK);
        incoming = (Switch) findViewById(R.id.switchIncoming);
        outgoing = (Switch) findViewById(R.id.switchOutGoing);

        image.setChecked(sharedPreferences.getSettingImage());
        video.setChecked(sharedPreferences.getSettingVideo());
        audio.setChecked(sharedPreferences.getSettingAudio());
        apk.setChecked(sharedPreferences.getSettingAPK());

        incoming.setChecked(sharedPreferences.getIncomingCaller());
        outgoing.setChecked(sharedPreferences.getOutgoingCaller());

        incoming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sharedPreferences.setIncomingCaller(true);
                } else {
                    sharedPreferences.setIncomingCaller(false);
                }
            }
        });


        outgoing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    sharedPreferences.setOutgoingCaller(true);
                } else {
                    sharedPreferences.setOutgoingCaller(false);
                }
            }
        });


        image.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                System.out.println("if switch is checked" + " " + b);
                if (b) {
                    sharedPreferences.setSettingImage(true);
                } else {
                    sharedPreferences.setSettingImage(false);
                }
            }
        });

        video.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    sharedPreferences.setSettingVideo(true);
                else
                    sharedPreferences.setSettingVideo(false);
            }
        });

        audio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    sharedPreferences.setSettingAudio(true);
                else
                    sharedPreferences.setSettingAudio(false);
            }
        });

        apk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    sharedPreferences.setSettingAPK(true);
                else
                    sharedPreferences.setSettingAPK(false);
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
