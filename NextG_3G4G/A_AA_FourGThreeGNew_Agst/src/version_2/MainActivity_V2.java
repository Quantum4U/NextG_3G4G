package version_2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import app.adshandler.AHandler;
import app.adshandler.ANG_PromptHander;
import app.adshandler.MasterData;
import app.pnd.fourg.HistoryActivity;
import app.pnd.fourg.MediaPreferences;
import app.pnd.fourg.SettingActivity;

import com.appnextg.fourg.R;

import app.serviceprovider.Utils;
import callerinfo.CallerFragment;
import version_2.fragment_v2.HomeFragment;

/**
 * Created by black on 4/12/2017.
 */
public class MainActivity_V2 extends AppCompatActivity {

    String url = "https://docs.google.com/document/d/1OFKSfjK7r4vD1JyV4sYRlQrEF1IbUaJpjz-pF4d5OTE/pub";
    public boolean fromCallrado = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        setNavigation(savedInstanceState);
        askPermission();

        try {
            fromCallrado = getIntent().getBooleanExtra("key", false);
        } catch (Exception e) {

        }
        if (!fromCallrado) {
            aHandler = new AHandler();
            aHandler.vANG_CallonAppLaunch(this);
        }

//        Calldorado.startCalldorado(this);

    }


    private void askPermission() {
        if (checkPermissionNew()) {

//             "Permission already granted."

        } else {
//        "Please request permission."

            requestPermissionNew();
        }
    }


    private boolean checkPermissionNew() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionNew() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS}, REQUEST_PHONE_STATE_MAIN);

    }


    private static final int REQUEST_PHONE_STATE_MAIN = 91;
    private MediaPreferences mediaPreferences;
    private DrawerLayout mDrawerLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    private void setNavigation(Bundle savedInstanceState) {
        aHandler = new AHandler();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_appbarlayout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        displayView();

    }

    private Fragment fragment;

    public void displayView() {
        // update the main content by replacing fragments
        fragment = null;
        fragment = new HomeFragment();
        if (fragment != null) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    AHandler aHandler;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.removeAds:
                aHandler.showFullAds(MainActivity_V2.this, false);
                if (CommonUtils.isNetworkConnected(this)) {
                    new AHandler().openBillingPage(MainActivity_V2.this);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.internetConnetion), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.moreapp_menu:
                if (!fromCallrado)
                    aHandler.showFullAds(MainActivity_V2.this, false);
                new Utils().moreApps(MainActivity_V2.this);
                break;

            case R.id.rate_menu:
                if (!fromCallrado)
                    aHandler.showFullAds(MainActivity_V2.this, false);
                new ANG_PromptHander().ratee(MainActivity_V2.this, getResources()
                        .getDrawable(R.drawable.version_ic_icon), false);
                break;

            case R.id.share_menu:
                if (!fromCallrado)
                    aHandler.showFullAds(MainActivity_V2.this, false);
                new Utils().shareUrl(MainActivity_V2.this);
                break;

            case R.id.feedback_menu:
                if (!fromCallrado)
                    aHandler.showFullAds(MainActivity_V2.this, false);
                new Utils().sendFeedback(MainActivity_V2.this);
                break;

            case R.id.exit_menu:
                if (!fromCallrado)
                    aHandler.showFullAds(MainActivity_V2.this, false);
                finish();
                break;


            case R.id.setting:
                if (!fromCallrado)
                    aHandler.showFullAds(MainActivity_V2.this, false);
                startActivity(new Intent(MainActivity_V2.this, SettingActivity.class));
                break;

            case R.id.privacy:
                if (!fromCallrado)
                    aHandler.showFullAds(MainActivity_V2.this, false);
                new AHandler().showAboutUs(MainActivity_V2.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PHONE_STATE_MAIN:
                if (grantResults.length > 0) {
                    boolean phoneStateAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean readContactsAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (phoneStateAccepted && readContactsAccepted) {
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,
                                                                    Manifest.permission.READ_CONTACTS},
                                                            REQUEST_PHONE_STATE_MAIN);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity_V2.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            } else {
                // showPrompt("Love using AppLock? \nPlease Rate on Play Store");
                showPrompt();
                if (!fromCallrado)
                    new AHandler().vANG_ManageAppExit(this);

                return false;
            }

        }

        return super.onKeyDown(keyCode, event);
    }

    public void showPrompt() {
        AlertDialog.Builder dia = new AlertDialog.Builder(this).setTitle(getApplicationContext()
                .getResources().getString(R.string.pindi_app_name))
                .setMessage(getApplicationContext().getResources().getString(R.string.forAlert))
                .setPositiveButton(getApplicationContext().getResources().getString(R.string.forOption), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //aHandler.showFullAds(MainActivity.this,false);
                        MainActivity_V2.this.finish();
                    }
                })
                .setNegativeButton(getApplicationContext().getResources().getString(R.string.forMoreOption), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new app.serviceprovider.Utils().moreApps(MainActivity_V2.this);
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.version_ic_icon).setCancelable(false);
        dia.setCancelable(true);
        dia.show();
    }


}
