package app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import app.pnd.adshandler.R;
import app.server.v2.DataHubConstant;
import app.server.v2.DataHubPreference;
import app.server.v2.Slave;

/**
 * Created by quantum4u1 on 05/04/18.
 */

public class ANG_PrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print);


        TextView tv = (TextView) findViewById(R.id.textViewPrint);

        String str = new DataHubPreference(this).getJSON();
        try {
            tv.setText(/*new DataHubPreference(this).getJSON()*/new JSONObject(str).toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }


/*
        tv.setText("App Launch Count:" + " " + DataHubConstant.APP_LAUNCH_COUNT + " \n"
                + "TOP Banner ID:" + " " + Slave.TOP_BANNER_ad_id + " :PROVIDER ID" + " " + Slave.TOP_BANNER_provider_id + " \n"
                + "Bottom Banner ID:" + " " + Slave.BOTTOM_BANNER_ad_id + " :PROVIDER ID" + " " + Slave.BOTTOM_BANNER_provider_id + " \n"
                + "Full Ads ID:" + " " + Slave.FULL_ADS_ad_id + " :PROVIDER ID" + " " + Slave.FULL_ADS_provider_id + " \n"
                + "LAUNCH FULL ADS ID:" + " " + Slave.LAUNCH_FULL_ADS_ad_id + " :PROVIDER ID" + " " + Slave.LAUNCH_FULL_ADS_provider_id + " \n"
                + "EXIT FULL ADS ID:" + " " + Slave.EXIT_FULL_ADS_ad_id + " :PROVIDER ID" + " " + Slave.EXIT_FULL_ADS_provider_id + " \n"
                + "NATIVE MEDIUM ID:" + " " + Slave.NATIVE_LARGE_ad_id + " :PROVIDER ID" + " " + Slave.NATIVE_MEDIUM_provider_id + " \n"
                + "NATIVE LARGE ID:" + " " + Slave.NATIVE_LARGE_ad_id + " :PROVIDER ID" + " " + Slave.NATIVE_LARGE_provider_id + " \n"
                + "SHARE:" + " " + Slave.SHARE_TEXT + " \n"
                + "RATE:" + " " + Slave.RATE_APP_HEADER_TEXT + " \n"
                + "AdMOB STATIC BANNER ID:" + " " + Slave.ADMOB_BANNER_ID_STATIC + " \n"
                + "AdMOB STATIC FULL ID:" + " " + Slave.ADMOB_FULL_ID_STATIC + " \n"
                + "ADMOB STATIC NATIVE MEDIUM:" + " " + Slave.ADMOB_NATIVE_MEDIUM_ID_STATIC + " \n"
                + "ADMOB STATIC NATIVE LARGE:" + " " + Slave.ADMOB_NATIVE_LARGE_ID_STATIC + " \n"
                + "CP_FULL_PAGE:" + " " + Slave.CP_cpname + " :IS LAUNCH ENABLED" + " " + Slave.CP_is_start + " :IS EXIT ENABLED" + " " + Slave.CP_is_exit + " :START DAY" + " " + Slave.CP_startday + " :NAVIGATION" + " " + Slave.CP_navigation_count);
*/


    }
}
