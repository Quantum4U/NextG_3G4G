package callerinfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import app.adshandler.AHandler;
import app.adshandler.DataHub;

import com.appnextg.fourg.R;

import app.serviceprovider.Utils;
import version_2.MainActivity_V2;

/**
 * Created by black on 11/24/2016.
 */
public class CallInfoActivity extends Activity {
    private TextView tv_phoneNumber, tv_userName, tv_city, callType;
    private LinearLayout adsbanner;
    private LinearLayout iv_call, iv_message, iv_info, freeapp;
    private String name, number, network, location;
    private ImageView cancel;
    private boolean isOutGoing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new AHandler().vANG_CallOnBGLaunch(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callerinfoactivity);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        adsbanner = (LinearLayout) findViewById(R.id.adsbanner);
//new AHandler().initEngine(this);
        adsbanner.addView(new AHandler().getBannerHeader(CallInfoActivity.this));

        Intent intent
                = getIntent();

        name = intent.getStringExtra("call_number");
        number = intent.getStringExtra("call_number");
        network = intent.getStringExtra("call_network");
        location = intent.getStringExtra("call_location");
        isOutGoing = intent.getBooleanExtra("isOutGoing", false);

        iv_call = (LinearLayout) findViewById(R.id.call);
        iv_message = (LinearLayout) findViewById(R.id.message);
        iv_info = (LinearLayout) findViewById(R.id.info);
        cancel = (ImageView) findViewById(R.id.cancel);
        freeapp = (LinearLayout) findViewById(R.id.freeapps);
        callType = (TextView) findViewById(R.id.callType);
        cancel = (ImageView) findViewById(R.id.cancel);

        tv_userName = (TextView) findViewById(R.id.userName);
        if (isOutGoing)
            callType.setText("New unknown call");
        else
            callType.setText("New unknown call");

        tv_userName.setText(number);


        tv_phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        tv_city = (TextView) findViewById(R.id.city);


        // TODO: 12/3/2016  network == null, hide && if location.contains UNKNOWN; hide all

        if (network == null)
            tv_phoneNumber.setText(name + " ");
        else
            tv_phoneNumber.setText(name + "\n" + network);

        if (location.contains("Unknown")) {
            tv_phoneNumber.setText("No Detail Found");
            tv_city.setText("");
        } else {
            tv_city.setText(location + ", " + "INDIA");
        }

        iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);
                finish();
            }
        });

        iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number));
                startActivity(intent);
                finish();
            }
        });

        iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, number);
                startActivity(intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        freeapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils utilsApp = new Utils();
                utilsApp.moreApps(CallInfoActivity.this);
                finish();
            }
        });

        findViewById(R.id.btnview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CallInfoActivity.this, MainActivity_V2.class));
            }
        });

    }

    private String getCustomString(String restString, String boldString) {
        String htmlString = restString + " \n" + "<b>" + boldString + "</b>";
        return htmlString;
    }
}
