package version_2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.adshandler.AHandler;

import com.appnextg.fourg.R;

/**
 * Created by rajeev on 18/04/17.
 */
public class CalerDetailActivity extends AppCompatActivity {

    TextView number, network, location, country;
    LinearLayout nativeads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callerdetail);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("Track Details");
        mToolbar.setTitleTextColor(Color.WHITE);

        Intent intent = getIntent();

        String str_mobile = intent.getStringExtra("mob");
        String str_location = intent.getStringExtra("loc");
        String str_net = intent.getStringExtra("net");


        number = (TextView) findViewById(R.id.mobileNumber);
        network = (TextView) findViewById(R.id.network);
        location = (TextView) findViewById(R.id.location);
        country = (TextView) findViewById(R.id.country);
        nativeads = (LinearLayout) findViewById(R.id.nativeads);
        nativeads.addView(new AHandler().showNativeLarge(this));


        number.setText("Mobile: " + str_mobile);
        network.setText("Network: " + str_net);
        location.setText("Location: " + str_location);
        country.setText("Country: INDIA");


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
