package version_2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import app.ads.StringPickerDialog;
import app.adshandler.AHandler;

import com.appnextg.fourg.R;

import dmax.dialog.SpotsDialog;

/**
 * Created by black on 4/12/2017.
 */
public class FourGActivity extends AppCompatActivity implements
        View.OnClickListener, StringPickerDialog.OnClickListener {

    AHandler aHandler;

    LinearLayout adslayout1;
    Button buttonfloating;
    //    com.gc.materialdesign.views.ButtonFloat more;
    TextView click_text;
    static int a = 0;
    boolean is4g = false;
    ImageView imageView;
    LinearLayout click;
    private LinearLayout nativeads;
    String networkType;
    Boolean settingPage = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version_fourg);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("Switch 3G To 4G");
        mToolbar.setTitleTextColor(Color.WHITE);

        nativeads = (LinearLayout) findViewById(R.id.nativeads);
        nativeads.addView(new AHandler().showNativeMedium(this));


//        adsbanner.addView(new AHandler().getBannerHeader(getActivity()));

        imageView = (ImageView) findViewById(R.id.image);
//        more = (com.gc.materialdesign.views.ButtonFloat) view.findViewById(R.id.buttonFloat);
        click_text = (TextView) findViewById(R.id.click_text);
        click = (LinearLayout) findViewById(R.id.click_version);
        aHandler = new AHandler();
//        more.setVisibility(View.GONE);
//        more.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                if (a % 2 == 0) {
//                    try {
//                        Toast.makeText(getActivity(), " " + getResources().getString(R.string.pindi_pleaserate), Toast.LENGTH_LONG).show();
//                        new UtilsApp().rateUs(getActivity());
//                    } catch (Exception e) {
//
//                    }
//                } else {
//                    try {
//                        new UtilsApp().moreApps(getActivity());
//                    } catch (Exception e) {
//                    }
//                }
//                a++;
//
        //showPicker();


//            }
//        });

        click.setBackground(getResources().getDrawable(R.drawable.convert_text));
        click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                settingPage = false;
                Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                startActivity(intent);
                //finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!settingPage) {
            try {
                new AsyncData().execute();
            } catch (Exception e) {

            }
        }

//        if (!settingPage) {
//            if (networkType.equalsIgnoreCase("3g")) {
//                is4g = true;
//                try {
//                    new AsyncData().execute();
//                } catch (Exception e) {
//                }
//
//            } else if (networkType.equalsIgnoreCase("4g")) {
//                System.out.println("FourGActivity.onResume 00");
//                is4g = false;
//                try {
//                    new AsyncData().execute();
//                } catch (Exception e) {
//                }
//            } else if (networkType.equalsIgnoreCase("2g")) {
//                Toast.makeText(this, ("Your Network in 2G"), Toast.LENGTH_SHORT).show();
//
//
//            } else if (networkType.equalsIgnoreCase("Notfound")) {
//               // finish();
//
//            }
//        }
    }

    AlertDialog pd;

    class AsyncData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new SpotsDialog(FourGActivity.this, R.style.SweetDialog);
            pd.show();
            pd.setCancelable(true);

        }


        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            networkType = CommonUtils.getNetworkType(FourGActivity.this);
            System.out.println("FourGActivity.onResume " + networkType);
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // if (!settingPage) {
            if (networkType.equalsIgnoreCase("3g")) {
                is4g = true;


            } else if (networkType.equalsIgnoreCase("4g")) {
                is4g = false;


            } else if (networkType.equalsIgnoreCase("2g")) {
                settingPage = false;
                Toast.makeText(FourGActivity.this, ("Your Network in 2G"), Toast.LENGTH_SHORT).show();


            } else if (networkType.equalsIgnoreCase("Notfound")) {
                settingPage = false;
                Toast.makeText(FourGActivity.this, ("Something went wrong, please try again"), Toast.LENGTH_SHORT).show();
                // finish();

            }

            System.out.println("FourGActivity.onPost " + networkType);

            try {
                if (is4g) {
                    //Toast.makeText(FourGActivity.this, "" + getResources().getString(R.string.pindi_converto3g), Toast.LENGTH_LONG).show();
                    click.setBackground(getResources().getDrawable(R.drawable.convert_text));
                    click_text.setText(" " + getResources().getString(R.string.pindi_converto4g));
                    //is4g = false;
                    imageView.setBackgroundResource(R.drawable.version_3g);
                } else {
                    //is4g = true;
                    click.setBackground(getResources().getDrawable(R.drawable.convert_text_3g));
                    imageView.setBackgroundResource(R.drawable.version_4g);
                    //Toast.makeText(FourGActivity.this, "" + getResources().getString(R.string.pindi_converto4g), Toast.LENGTH_LONG).show();
                    click_text.setText("" + getResources().getString(R.string.pindi_converto3g));
                }
                pd.cancel();

            } catch (Exception e) {
            }
            // }
            settingPage = true;
            aHandler.showFullAds(FourGActivity.this, false);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            finish();
        }
    }


    public void showPicker() {
        final String TAG = StringPickerDialog.class.getSimpleName();

        Bundle bundle = new Bundle();
        bundle.putStringArray(getString(R.string.pindi_string_picker_dialog_title), getStringArray());
        StringPickerDialog dialog = new StringPickerDialog();
        dialog.setArguments(bundle);
        dialog.show(FourGActivity.this.getSupportFragmentManager(), TAG);
    }

    @Override
    public void onClick(String value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    private String[] getStringArray() {
        return new String[]{"System Language", "English UK", "English US", "French", "German", "Arebic", "US Eng", "Nepali"};
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