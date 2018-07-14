package version_2.fragment_v2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import app.adshandler.AHandler;
import app.adshandler.DataHub;
import app.adshandler.MasterData;
import app.adshandler.ANG_PromptHander;
import app.pnd.fourg.MediaPreferences;
import app.serviceprovider.Utils;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import version_2.CallerActivity;
import version_2.CommonUtils;
import version_2.FourGActivity;
import version_2.MoreActivity;
import version_2.PhoneDetailActivity;
import version_2.SIMDetailsActivity;
import version_2.SpeedCheckActivity;

import com.appnextg.fourg.R;

/**
 * Created by black on 4/13/2017.
 */
public class HomeFragment extends Fragment {
    private ListView listView;
    private HomeAdapter adapter;
    private static final int REQUEST_PHONE_STATE = 1;
    private MediaPreferences mediaPreferences;
//    private AutoScrollViewPager homePager;
//    private HomePagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.v2_main, container, false);
        listView = (ListView) view.findViewById(R.id.listView);

//        homePager = (AutoScrollViewPager) view.findViewById(R.id.homePager);
//        pagerAdapter = new HomePagerAdapter(getActivity());

        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.version_ic_icon);
        mToolbar.setTitle("3G To 4g Switch");
        mToolbar.setTitleTextColor(Color.WHITE);


        mediaPreferences = new MediaPreferences(getActivity());
        adapter = new HomeAdapter(getActivity());
        listView.setAdapter(adapter);


        ((LinearLayout) view.findViewById(R.id.adsholder)).addView(new AHandler().getBannerHeader(getActivity()));


//        view.findViewById(R.id.dashboard).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), SpeedCheckActivity.class));
//            }
//        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                aHandler.showFullAds(getActivity(), false);
                                                switch (position) {

                                                    case 0:
//                                                        homePager.setCurrentItem(0);
//                                                        pagerAdapter.animateTrackView();
                                                        startActivity(new Intent(getActivity(), SpeedCheckActivity.class));
                                                        break;

                                                    case 1:
                                                        startActivity(new Intent(getActivity(), CallerActivity.class));
                                                        break;

                                                    case 2:
                                                        //Sim Details Page
                                                        if (checkPermissionNew()) {
                                                            startActivity(new Intent(getActivity(), PhoneDetailActivity.class));
                                                        } else
                                                            checkForPhoneStatePermission();
                                                        break;

                                                    case 3:
                                                        //Phone Details Page
                                                        if (checkPermissionNew()) {
                                                            startActivity(new Intent(getActivity(), SIMDetailsActivity.class));
                                                        } else
                                                            checkForPhoneStatePermission();

                                                        break;

                                                    case 4:
                                                        startActivity(new Intent(getActivity(), FourGActivity.class));
                                                        break;

                                                    case 5:
                                                        startActivity(new Intent(getActivity(), MoreActivity.class));
                                                        break;

                                                }
                                            }
                                        }

        );

//        homePager.setAdapter(pagerAdapter);

//        if (CommonUtils.isNetworkConnected(getActivity())) {
//            try {
//                System.out.println("here is the time 0" + " " + DataHub.ETC_2);
//                if (Integer.parseInt(DataHub.ETC_2) >= 5000) {
//                    homePager.setInterval(Integer.parseInt(DataHub.ETC_2));
//                    System.out.println("here is the time 1" + " " + DataHub.ETC_2);
//                } else {
//                    System.out.println("here is the time 2" + " " + DataHub.ETC_2);
//                    homePager.setInterval(10000);
//                }
//            } catch (Exception e) {
//                System.out.println("here is the time 3" + " " + DataHub.ETC_2);
//                homePager.setInterval(10000);
//            }
//            homePager.setDirection(AutoScrollViewPager.RIGHT);
//            homePager.startAutoScroll();
//            homePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    if (position != 0) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                            homePager.setBackground(null);
//                        }
//                    }
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
//        }

        doEngineWork(view);
        return view;
    }


    private AHandler aHandler;


    public static String App_PID = "MTOOL_4G_Converter";
    public static String PID_CP = "MTOOL_CP";
    // public static String PID_CP="3333";
//
    Utils utils;
    //

    LinearLayout adslayout1;
    ANG_PromptHander promptHander;

    public void doEngineWork(View v) {
        aHandler = new AHandler();
        adslayout1 = (LinearLayout) v.findViewById(R.id.adsbanner);
        promptHander = new ANG_PromptHander();
        utils = new app.serviceprovider.Utils();

//        adslayout1.addView(aHandler.getBannerHeader(getActivity()));
//        new MasterData(getActivity()).getMasterData(getActivity(), App_PID,
//                adslayout1, PID_CP, null, true);
        // new GPID().execute();
        //  new QuantamAds().execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PHONE_STATE:
                boolean accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                mediaPreferences.setPermissionAllowed(accepted);
                if (accepted) {

                    // .. Can now obtain the UUID
                    startActivity(new Intent(getActivity(), SIMDetailsActivity.class));
                } else {
                    Toast.makeText(getActivity(), "Unable to continue without granting permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }


    private boolean checkPermissionNew() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE);


        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void checkForPhoneStatePermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_PHONE_STATE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                    showPermissionMessage();

                } else {

                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            REQUEST_PHONE_STATE);
                }
            }
        } else {
            //... Permission has already been granted, obtain the UUID
            startActivity(new Intent(getActivity(), SIMDetailsActivity.class));
        }

    }


    private void showPermissionMessage() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Read phone state")
                .setMessage("This app requires the permission to read phone state to continue")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_PHONE_STATE},
                                REQUEST_PHONE_STATE);
                    }
                }).create().show();
    }

}
