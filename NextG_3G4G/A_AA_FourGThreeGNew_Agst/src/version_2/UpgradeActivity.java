package version_2;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import app.adshandler.AHandler;
import app.serviceprovider.Utils;
import com.appnextg.fourg.R;


/**
 * Created by sony on 15-06-2017.
 */
public class UpgradeActivity extends AppCompatActivity implements View.OnClickListener {
    Button upgradeButton;
    Toolbar toolbar;
    LinearLayout adsbanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upgrade_activity);
        adsbanner = (LinearLayout) findViewById(R.id.adsbanner);
        adsbanner.addView(new AHandler().showNativeLarge(this));
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        upgradeButton = (Button) findViewById(R.id.upgrade_button);
        upgradeButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        new Utils().removeAds(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onDestroy() {
        new AHandler().showFullAds(this, true);
        super.onDestroy();
    }
}
