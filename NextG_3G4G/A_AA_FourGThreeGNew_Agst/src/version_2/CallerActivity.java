package version_2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.adshandler.AHandler;

import com.appnextg.fourg.R;

import callerinfo.CallUtils;
import callerinfo.GetCallerDetails;
import dmax.dialog.SpotsDialog;

/**
 * Created by black on 4/12/2017.
 */
public class CallerActivity extends AppCompatActivity {

    private Button btn_launch;
    private EditText et_phone;
    private CallUtils utils;
    private TextView number, network, location;
    private LinearLayout parent;
    LinearLayout nativeads;
    private String str_mob, str_country, str_network, str_location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callerfragment);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("Mobile Number Tracker");
        mToolbar.setTitleTextColor(Color.WHITE);

        utils = new CallUtils();
        btn_launch = (Button) findViewById(R.id.btn_launch);
        et_phone = (EditText) findViewById(R.id.et_phone);

        number = (TextView) findViewById(R.id.mobileNumber);
        network = (TextView) findViewById(R.id.network);
        location = (TextView) findViewById(R.id.location);
        parent = (LinearLayout) findViewById(R.id.parent);
        nativeads = (LinearLayout) findViewById(R.id.nativeads);
        nativeads.addView(new AHandler().showNativeLarge(CallerActivity.this));

        btn_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (et_phone.getText().toString().length() > 4) {
                    final AlertDialog ad = new SpotsDialog(CallerActivity.this, R.style.SweetDialog);
                    ad.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            ad.dismiss();


                            parent.setVisibility(View.GONE);
                            number.setText("Mobile No:" + " " + et_phone.getText().toString());
                            str_mob = "Mobile No:" + " " + et_phone.getText().toString();
                            searchNumber(et_phone.getText().toString(), CallerActivity.this, network, location);
//                            searchNumber(et_phone.getText().toString(), CallerActivity.this, false);
//                            searchNumber(et_phone.getText().toString(),ad);

                        }
                    }, 2500);
                } else {
                    Toast.makeText(CallerActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

//    private void searchNumber(String mobNumber, final AlertDialog ad){
//                Calldorado.search(this, new CDOPhoneNumber(mobNumber), new CDOSearchProcessListener() {
//            @Override
//            public void onSearchSent() {
//                ad.dismiss();
//            }
//            @Override
//            public void onSearchSuccess() {
//                ad.dismiss();
//            }
//            @Override
//            public void onSearchFailed(String errorMessage) {
//                ad.dismiss();
//                Toast.makeText(CallerActivity.this,"Something went wrong. Please try again!",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void searchNumber(String number, Context context, TextView tv_network, TextView tv_location) {
        if (number.length() == 11)
            number = number.substring(1, 11);
        if (number.length() == 12)
            number = number.substring(2, 12);
        if (number.length() == 13)
            number = number.substring(3, 13);
        String orignal = number;
        GetCallerDetails getCallerDetails = new GetCallerDetails();
        System.out.println("Caller User Input " + number);
        if (number != null && number.length() > 4) {
            number = number.substring(0, 4);
            System.out.println("Caller User substring " + number);
            String tempnum = number;
            number = tempnum.substring(0, 2) + "," + tempnum.substring(2, 4);
            System.out.println("Caller User replacement " + number);

            String filedata = loadAssetTextAsString(context, "data.txt");

            int index = filedata.indexOf(number);
            //System.out.println("Sub Index "+index);
            String caller_info = filedata.substring(index + 11, index + 100);
            getCallerDetails.getDetails(caller_info);

            if (orignal.length() < 10) {
//                textView.setText("Mobile No: " + orignal + "\n(Unknown Location)");
                tv_location.setText("Location: Unknown Location");
                str_location = "Location: Unknown Location";
            } else {
                String network_name = getCallerDetails.getDetails(caller_info).OPARATOR_NAME;
                String location = getCallerDetails.getDetails(caller_info).LOCATION;

                if (network_name.length() < 4 || location.length() < 4) {
//                    textView.setText("Mobile No: " + orignal + "\n(Unknown Location)   ");
                    tv_location.setText("Location: Unknown Location");
                    str_location = "Location: Unknown Location";
                } else {
//                    textView.setText("Mobile No: " + orignal + "\n Network:" + getCallerDetails.getDetails(caller_info).OPARATOR_NAME
//                            + "    \n" + "Location:" + getCallerDetails.getDetails(caller_info).LOCATION + "      ");
                    tv_location.setText("Location:" + " " + getCallerDetails.getDetails(caller_info).LOCATION);
                    tv_network.setText("Network:" + " " + getCallerDetails.getDetails(caller_info).OPARATOR_NAME);
                    str_location = "Location:" + " " + getCallerDetails.getDetails(caller_info).LOCATION;
                    str_network = "Network:" + " " + getCallerDetails.getDetails(caller_info).OPARATOR_NAME;
                }

            }
        } else {
//            textView.setText("Mobile No " + orignal + "(Unknown Location)");
            tv_location.setText("Location: Unknown Location");
            str_location = "Location: Unknown Location";
        }

        startActivity(new Intent(this, CalerDetailActivity.class).putExtra("mob", str_mob)
                .putExtra("loc", str_location).putExtra("net", str_network));
    }


    private void searchNumber(String number, Context context, boolean isOutGoing) {
        try {
            if (number != null) {
                if (number.length() == 11)
                    number = number.substring(1, 11);
                if (number.length() == 12)
                    number = number.substring(2, 12);
                if (number.length() == 13)
                    number = number.substring(3, 13);
                Intent intent = new Intent(context, CalerDetailActivity.class);
                String orignal = number;
                GetCallerDetails getCallerDetails = new GetCallerDetails();
                System.out.println("Caller User Input " + number);
                if (number != null && number.length() > 4) {


                    String number5digit = number.substring(0, 5);

                    number = number.substring(0, 4);

                    String filedata5digit = loadAssetTextAsString(context, "data5.txt");
                    String filedata4digit = loadAssetTextAsString(context, "data4.txt");

                    int index;
                    String caller_info;

                    index = filedata5digit.indexOf(number5digit);

                    if (index == -1) {
                        index = filedata4digit.indexOf(number);
                        caller_info = filedata4digit.substring(index + 5, index + 100);

                    } else {
                        caller_info = filedata5digit.substring(index + 6, index + 100);
                    }

                    getCallerDetails.getDetails(caller_info);
                    ;


                    System.out.print("caller_info " + caller_info);


                    //System.out.println("Sub Index "+index);

                    if (orignal.length() < 10) {
//                textView.setText("Mobile No: " + orignal + "\n(Unknown Location)");
                        intent.putExtra("loc", "Unknown Location");
                    } else {
                        String network_name = getCallerDetails.getDetails(caller_info).OPARATOR_NAME;
                        String location = getCallerDetails.getDetails(caller_info).LOCATION;

                        if (network_name.length() < 2 || location.length() < 2) {
//                    textView.setText("Mobile No: " + orignal + "\n(Unknown Location)   ");
                            intent.putExtra("loc", "Unknown Location");
                        } else {
//                    textView.setText("Mobile No: " + orignal + "\n Network:" + getCallerDetails.getDetails(caller_info).OPARATOR_NAME
//                            + "    \n" + "Location:" + getCallerDetails.getDetails(caller_info).LOCATION + "      ");
                            intent.putExtra("net", getCallerDetails.getDetails(caller_info).OPARATOR_NAME);
                            intent.putExtra("loc", getCallerDetails.getDetails(caller_info).LOCATION);
                        }

                    }
                } else {
//            textView.setText("Mobile No " + orignal + "(Unknown Location)");
                    intent.putExtra("loc", "Unknown Location");
                }
                intent.putExtra("mob", orignal);
                intent.putExtra("isOutGoing", isOutGoing);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        } catch (NullPointerException e) {

        } catch (Exception e) {

        }

    }

    private String loadAssetTextAsString(Context context, String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            System.out.println("sub ex" + e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("sub ex 2 " + e);
                }
            }
        }

        return null;
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
