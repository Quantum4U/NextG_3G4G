/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.pnd.fourg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import app.ads.StringPickerDialog;

import app.adshandler.AHandler;
import app.adshandler.MasterData;
import app.adshandler.ANG_PromptHander;
import app.pnd.fourg.history.AppSharedPreferences;
import app.serviceprovider.Utils;

import com.astuetz.PagerSlidingTabStrip;
import com.checkspeed.SpeedFragment;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import callerinfo.CallerFragment;

import com.appnextg.fourg.R;
import version_2.MainActivity_V2;
import version_2.fragment_v2.HomeFragment;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, StringPickerDialog.OnClickListener {

    public static boolean issamung = false;
    private final Handler handler = new Handler();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;

    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;

    Button chatapps, settings;
    int cuurent_counter = 0;
    // For this example, only two pages
    static final int NUM_ITEMS = 4;
    DataBaseHandler dataBaseHandler;
    private ActionBar actionBar;
    private AHandler aHandler;
    AppSharedPreferences preferences;

    fourg fg;
    RootFragment first;
    StaticFragment hisf;
    SpeedFragment spd;
    CallerFragment callerFragment;

    //	 LinearLayout adslayout1,adslayout2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aHandler = new AHandler();
        preferences = new AppSharedPreferences(MainActivity.this);

        // Toast.makeText(getApplicationContext(), currentColor,
        // Toast.LENGTH_LONG).show();

        dataBaseHandler = new DataBaseHandler(getApplicationContext());

        // getActionBar().setBackgroundDrawable(
        // new ColorDrawable(Color.parseColor("#0066CC")));
        // actionBar =getSupportActionBar();
        // actionBar.setDisplayHomeAsUpEnabled(false);
        // // actionBar.setHomeButtonEnabled(true);
        // actionBar.setLogo(R.drawable.version_ic_icon);
        // actionBar.setDisplayUseLogoEnabled(true);
        // actionBar.setTitle("   3G To 4G & Sim Detail");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.version_ic_icon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
                | ActionBar.DISPLAY_SHOW_HOME);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color
                .parseColor("#0068BE")));
        actionBar.setTitle("    "+getResources().getString(R.string.pindi_app_name));

        adslayout1 = (LinearLayout) findViewById(R.id.adsbanner);

        if (new DataBaseHandler(MainActivity.this)
                .get_stealth(MainActivity.this))
            stealthActive(MainActivity.this);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new MyPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        if (issamung)
            pager.setOffscreenPageLimit(4);
        else
            pager.setOffscreenPageLimit(4);
        final int pageMargin;
        if (issamung) {
            pageMargin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 2, getResources()
                            .getDisplayMetrics());
        } else {
            pageMargin = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 3, getResources()
                            .getDisplayMetrics());
        }
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

        changeColor(currentColor);

        // getActionBar().setDisplayShowTitleEnabled(true);
        // setUses();
      // doEngineWork();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // if (Utils.is_samsung)
        // return false;
        // else {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
        // }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.moreapp_menu:
                aHandler.showFullAds(MainActivity.this, false);
                new Utils().moreApps(MainActivity.this);
                break;

            case R.id.rate_menu:
                aHandler.showFullAds(MainActivity.this, false);
                new ANG_PromptHander().ratee(MainActivity.this, getResources()
                        .getDrawable(R.drawable.version_ic_icon),false);
                break;

            case R.id.share_menu:
                aHandler.showFullAds(MainActivity.this, false);
                new Utils().shareUrl(MainActivity.this);
                break;

            case R.id.feedback_menu:
                aHandler.showFullAds(MainActivity.this, false);
                new Utils().sendFeedback(MainActivity.this);
                break;

            case R.id.exit_menu:
                aHandler.showFullAds(MainActivity.this, false);
                finish();
                break;



            case R.id.setting:
                aHandler.showFullAds(MainActivity.this, false);
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeColor(int newColor) {
        newColor = R.color.deep_white;
        tabs.setIndicatorColor(getResources().getColor(R.color.deep_white));
        tabs.setBackgroundColor(getResources().getColor(
                R.color.version_header_bg));
        tabs.setIndicatorHeight(5);
        tabs.setTextSize(25);
        tabs.setTextColor(getResources().getColor(R.color.white));
        // tabs.setBackgroundColor(color)
        // change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(getResources().getColor(newColor));
            Drawable bottomDrawable = getResources().getDrawable(
                    R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[]{
                    colorDrawable, bottomDrawable});

            if (oldBackground == null) {

                // if (Build.VERSION.SDK_INT <
                // Build.VERSION_CODES.JELLY_BEAN_MR1) {
                // ld.setCallback(drawableCallback);
                // } else {
                // //getActionBar().setBackgroundDrawable(ld);
                // }

            } else {

                TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                        oldBackground, ld});

                // workaround for broken ActionBarContainer drawable handling on
                // pre-API 17 builds
                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    // td.setCallback(drawableCallback);
                } else {
                    // getActionBar().setBackgroundDrawable(td);
                }

                td.startTransition(200);

            }

            oldBackground = ld;

            // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
            // actionBar.setDisplayShowTitleEnabled(false);
            // actionBar.setDisplayShowTitleEnabled(true);

        }

        currentColor = newColor;

    }

    public void onColorClicked(View v) {

        int color = Color.parseColor(v.getTag().toString());
        changeColor(color);

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("get current count" + " " + pager.getCurrentItem());
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                System.out.println("get current count" + " " + position);
                if (position == 2) {
                    System.out.println("get current count not null");
                    if (preferences.getRootCounter() >= 3) {
                        preferences.setRootCounter(0);
                    } else {
                        preferences.setRootCounter(preferences.getRootCounter() + 1);

                    }

                    Fragment root = getSupportFragmentManager().
                            findFragmentByTag("android:switcher:" + R.id.pager + ":" + pager.getCurrentItem());
                    System.out.println("finally root" + " " + pager.getCurrentItem() + " " + root);
                    if (pager.getCurrentItem() == 0 && root != null) {
                        System.out.println("finally root success");
                        ((StaticFragment) root).refreshStatic();
                    }

                } else if (position == 1) {
                    System.out.println("get current count not null");
                    if (preferences.getSimCounter() >= 3) {
                        preferences.setSimCounter(0);
                    } else {
                        preferences.setSimCounter(preferences.getSimCounter() + 1);
                    }
                    Fragment staticFrag = getSupportFragmentManager().
                            findFragmentByTag("android:switcher:" + R.id.pager + ":" + pager.getCurrentItem());

                    if (pager.getCurrentItem() == 1 && staticFrag != null) {
                        System.out.println("finally staticFrag success");
                        ((RootFragment) staticFrag).refreshRoot();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
        // changeColor(currentColor);
    }

    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };

    public class MyPagerAdapter extends FragmentPagerAdapter {

//        private final String[] TITLES = {
//                "" + getResources().getString(R.string.pindi_phonedetails),
//                "  " + getResources().getString(R.string.pindi_simdetails),
//                " " + getResources().getString(R.string.pindi_speedcheck),
//                " " + getResources().getString(R.string.pindi_fourgconveter)};

        private final String[] TITLES = {
                "" + getResources().getString(R.string.pindi_speedcheck),
                "  " + getResources().getString(R.string.pindi_simdetails),
                " " + getResources().getString(R.string.pindi_phonedetails), "MOBILE LOCATOR",
                " " + getResources().getString(R.string.pindi_fourgconveter)};


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        public Fragment getItem(int position) {
            // return SuperAwesomeCardFragment.newInstance(position);
            aHandler.showFullAds(MainActivity.this, false);
            System.out.println("position position = " + position);

            if (position == 0) {
                if (spd == null)
                    return new SpeedFragment();
                else
                    return spd;
            } else if (position == 1) {
                if (first == null)
                    return new RootFragment();
                else
                    return first;


            } else if (position == 2) {
                if (hisf == null)
                    return new StaticFragment();
                else
                    return hisf;
            } else if (position == 3) {
                if (callerFragment == null)
                    callerFragment = new CallerFragment();
                return callerFragment;
            } else {
                if (fg == null)
                    return new fourg();
                else
                    return first;
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void savedata() {
        //
        // ArrayList<String> data = AppListAdaptor.getLockPackage();
        //
        // String pkg = "";
        // for (int i = 0; i < data.size(); i++) {
        // pkg = pkg + data.get(i) + "#";
        // }
        //
        // dataBaseHandler.setAllPkges(pkg);
        //
        // new PasswordService().updateArray2(data);
    }

    // public boolean onKeyDown(int keyCode, KeyEvent event) {
    //
    // if (keyCode == KeyEvent.KEYCODE_BACK) {
    // System.out.println("Exit now ");
    // //if(DIFF_IN_DAYS>2)
    // showFullAds(true,MainActivity.this);
    //
    // finish();
    // return false;
    // }
    //
    // return super.onKeyDown(keyCode, event);
    // }
    // @Override

    public void stealthActive(Context context) {
        ComponentName componentToDisable =
                // mig.app.contact
                new ComponentName(Utils.current_package, Utils.current_launcher);
        // new ComponentName("mig.app.contact","android.engine.MainActivity");

        context.getPackageManager().setComponentEnabledSetting(
                componentToDisable,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        new DataBaseHandler(context).set_stealth(context, true);
    }

    public void showPrompt(String msg) {
        AlertDialog prompt = new AlertDialog.Builder(this).create();
        prompt.setTitle("" + getResources().getString(R.string.pindi_app_name));
        prompt.setMessage(msg);

        prompt.setButton("More Apps",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dia, int arg1) {
                        new Utils().moreApps(MainActivity.this);
                        dia.dismiss();
                    }
                });

        prompt.setButton2("" + getResources().getString(R.string.pindi_Yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dia, int arg1) {
                        dia.dismiss();

                        finish();
                    }
                });

        prompt.show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showPrompt("" + getResources().getString(R.string.pindi_doouwantto));
//            aHandler.showFullAds(MainActivity.this, false);
            aHandler.showFullAds(MainActivity.this, false);
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    savedata();

                }
            });

            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    PopupWindow menuoption;

    public void openFirstmenu() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.first_layout, null);
        menuoption = new PopupWindow(MainActivity.this);
        menuoption.setContentView(layout);

        layout.findViewById(R.id.ll_remove_add).setVisibility(View.VISIBLE);

        layout.findViewById(R.id.ll_gift_box).setVisibility(View.VISIBLE);

        layout.findViewById(R.id.ll_update).setVisibility(View.VISIBLE);

        menuoption.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        menuoption.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        menuoption.setFocusable(true);

        // menuoption.setAnimationStyle(R.style.slidAnimation);
        menuoption.setBackgroundDrawable(new BitmapDrawable());

        // menuoption.showAsDropDown(v);
        System.out.println(" RootFragment.DEVICE_H " + RootFragment.DEVICE_H
                + " 222 " + (15 * RootFragment.DEVICE_H) / 100);
        menuoption.showAtLocation(layout, Gravity.RIGHT | Gravity.TOP, 0,
                (14 * RootFragment.DEVICE_H) / 100);
    }

    public void likeapp(View v) {
        aHandler.showFullAds(MainActivity.this, false);
        new ANG_PromptHander().ratee(MainActivity.this,
                getResources().getDrawable(R.drawable.version_ic_icon),false);
    }

    public void shareapp(View v) {
        aHandler.showFullAds(MainActivity.this, false);
        new Utils().shareUrl(MainActivity.this);
    }

    public void sharemore(View v) {
        aHandler.showFullAds(MainActivity.this, false);
        new Utils().moreApps(MainActivity.this);
    }

    public void sharefeedback(View v) {
        aHandler.showFullAds(MainActivity.this, false);
        new Utils().sendFeedback(MainActivity.this);
    }

    public void changeLang(View v) {
        aHandler.showFullAds(MainActivity.this, false);
        showPicker();
    }

    public void exitapp(View v) {
        finish();
    }

    public void showPicker() {
        final String TAG = StringPickerDialog.class.getSimpleName();

        Bundle bundle = new Bundle();
        bundle.putStringArray(
                getString(R.string.pindi_string_picker_dialog_title),
                getStringArray());
        StringPickerDialog dialog = new StringPickerDialog();
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(), TAG);
    }

    private String[] getStringArray() {
        return new String[]{"System Language", "English US", "English UK",
                "français", "português", "espanhol", "japonés", "русский",
                "한국어", "Deutsche", "العربية", "हिंदी"};
        // system,engus,enguk,frence,portigures,spanish,japanies,rus,kor,german,Arebic,Hindi
    }

    public void onClick(String value) {
        // Toast.makeText(getApplicationContext(), ""+value,
        // Toast.LENGTH_LONG);

        showPromptLocale(value);
    }


    public void setLocale(String value) {
        // Toast.makeText(getApplicationContext(), value,
        // Toast.LENGTH_LONG).show();
        if (value == getStringArray()[0]) {
            changeLang("system");
        } else if (value == getStringArray()[1]) {
            changeLang("en");
        } else if (value == getStringArray()[2]) {
            changeLang("en");
        } else if (value == getStringArray()[3]) {
            changeLang("fr");
        } else if (value == getStringArray()[4]) {
            changeLang("pt");
        } else if (value == getStringArray()[5]) {
            changeLang("es");
        } else if (value == getStringArray()[6]) {
            changeLang("ja");
        } else if (value == getStringArray()[7]) {
            changeLang("ru");
        } else if (value == getStringArray()[8]) {
            changeLang("ko");

        } else if (value == getStringArray()[9]) {
            changeLang("de");
        } else if (value == getStringArray()[10]) {
            changeLang("ar");
        } else if (value == getStringArray()[11]) {
            changeLang("hi");
        } else {
            changeLang("system");
        }

        // Toast.makeText(getApplicationContext(), ""+value,
        // Toast.LENGTH_LONG).show();

    }

    public void changeLang(String lcode) {
        if (!lcode.equals("system")) {
            Locale locale = new Locale(lcode);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
    }

    @Override
    public void onClick(View v) {
    }

    public void showPromptLocale(final String language) {
        AlertDialog prompt = new AlertDialog.Builder(this).create();
        prompt.setTitle("" + getResources().getString(R.string.pindi_app_name));
        prompt.setMessage(getResources().getString(
                R.string.pindi_text_on_lang_change));

        prompt.setButton(
                ""
                        + getResources().getString(
                        R.string.pindi_string_picker_dialog_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dia, int arg1) {
                        new Utils().set_locale2(getApplicationContext(),
                                language);
                        finish();
                        Toast.makeText(getApplicationContext(),
                                "Set" + language, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),
                                AppLauncher.class);
                        startActivity(intent);

                        dia.dismiss();
                    }
                });

        prompt.setButton2(
                ""
                        + getResources().getString(
                        R.string.pindi_string_picker_dialog_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dia, int arg1) {
                        dia.dismiss();

                    }
                });
        prompt.show();
    }

    ANG_PromptHander promptHander;
    //	AHandler aHandler;
    LinearLayout adslayout1;
   // public static String App_PID = "02_New";//ShareAll
   // public static String App_PID = "4G_CON_2017";

    // public static String PID_CP="3333";
//
    Utils utils;
//
    public void doEngineWork() {
        aHandler = new AHandler();
        adslayout1 = (LinearLayout) findViewById(R.id.adsbanner);
        promptHander = new ANG_PromptHander();
        utils = new app.serviceprovider.Utils();
//        new MasterData(this).getMasterData(MainActivity.this, HomeFragment.App_PID,
//                adslayout1, HomeFragment.PID_CP, null, true);
        // new GPID().execute();


      //  new QuantamAds().execute();

    }
//
//
//    private class QuantamAds extends AsyncTask<Void, Void, String> {
//        private String data;
////        private ProgressDialog bar;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
////            bar = new ProgressDialog(MainActivity.this);
////            bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////            bar.setCancelable(false);
////            bar.show();
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            try {
//                data = getData(Utils.QUANTUM_URL);
//                System.out.println("1225 here is doInBackGround" + " " + data);
//            } catch (Exception e) {
//                System.out.println("exception in doinbackground main activity" + e);
//            }
//            return data;
//        }
//
//        @Override
//        protected void onPostExecute(String json) {
//            super.onPostExecute(json);
//            System.out.println("1225 here is json" + " " + json);
//            try {
//                parseData(json);
//            } catch (Exception e) {
//                System.out.println("exception in onPostExecute main activity" + e);
//            }
////            if (bar.isShowing()) {
////                bar.cancel();
////            }
//
//        }
//    }
//
//
//    public void parseData(String data) {
//        try {
//            if (data != null && !data.equals("")) {
//                System.out.println("bhanu check 1");
//                String src = data.substring(data.indexOf("<src>") + 5, data.indexOf("</src>"));
//
//                System.out.println("bhanu check 2 source" + " " + src);
//                if (src != null) {
//                    System.out.println("bhanu check 3");
//                    String icon[] = src.split("#");
//                    System.out.println("bhanu check 4");
//                    if (icon != null && icon.length > 0) {
//                        System.out.println("bhanu check 5");
//                        for (int i = 0; i < icon.length; i++) {
//                            preferences.setQUANTUM_SRC(icon[i], i);
//                            System.out.println("bhanu check 6");
//                        }
//                    }
//                }
//
//
//                String click = data.substring(data.indexOf("<click>") + 7, data.indexOf("</click>"));
//                System.out.println("bhanu check 2 click" + " " + click);
//                if (click != null) {
//                    String deeplink[] = click.split("#");
//                    if (deeplink != null && deeplink.length > 0) {
//
//
//                        for (int i = 0; i < deeplink.length; i++) {
//                            preferences.setQUANTUM_CLICK(deeplink[i], i);
//                            System.out.println("bhanu check 6");
//                        }
//                    }
//                }
//
//
//                String pkg = data.substring(data.indexOf("<pkg>") + 5, data.indexOf("</pkg>"));
//                System.out.println("bhanu check 2 pkg" + " " + pkg);
//                if (pkg != null) {
//                    String checkPackage[] = pkg.split("#");
//                    if (checkPackage != null && checkPackage.length > 0) {
//
//                        for (int i = 0; i < checkPackage.length; i++) {
//                            preferences.setQUANTUM_PKG(checkPackage[i], i);
//                            System.out.println("bhanu check 6");
//
//                        }
//                    }
//                }
//
//
//                String appname = data.substring(data.indexOf("<appname>") + 9, data.indexOf("</appname>"));
//                System.out.println("bhanu check 2 appname" + " " + appname);
//                if (appname != null) {
//                    String checkPackage[] = appname.split("#");
//                    if (checkPackage != null && checkPackage.length > 0) {
//
//                        for (int i = 0; i < checkPackage.length; i++) {
//                            preferences.setQUANTUM_AppName(checkPackage[i], i);
//                            System.out.println("bhanu check 6");
//
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            Ads.printdata("from pid PID_VAL receive parsing excp  " + e);
//        }
//
//    }
//
//    public String getData(String url) {
//        try {
//            HttpGet httpGet = new HttpGet(url);
//            HttpParams httpParameters = new BasicHttpParams();
//            HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
//            HttpConnectionParams.setSoTimeout(httpParameters, 15000);
//            HttpClient httpclient = new DefaultHttpClient();
//            httpGet.setParams(httpParameters);
//
//            HttpEntity entity = httpclient.execute(httpGet).getEntity();
//            BufferedReader br = null;
//            if (entity != null) {
//                Log.i("read", "nube");
//                br = new BufferedReader(new InputStreamReader(
//                        entity.getContent()));
//            } else {
//                Log.i("read", "local");
//                System.out.println("Unable to read:...");
//            }
//            String texto = "";
//            while (true) {
//                String line = br.readLine();
//                if (line != null) {
//                    texto = texto + line;
//                } else {
//                    System.out.println("new data : Grand " + texto);
//                    return texto;
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("new data error liink Grand " + url);
//            System.out.println("new data error: Grand " + e);
//            e.printStackTrace();
//            return null;
//        }
//    }

    private boolean ifPackageExist(String pkg) {

        return false;
    }

   /* public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }*/
}
