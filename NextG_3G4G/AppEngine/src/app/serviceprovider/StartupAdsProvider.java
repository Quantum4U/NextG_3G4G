package app.serviceprovider;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.ads.banner.BannerListener;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;
import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.VideoListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import java.util.ArrayList;

import app.PrintLog;
import app.pnd.adshandler.R;
import app.server.v2.Slave;


public class StartupAdsProvider {

    public StartAppAd startAppAd;
    ImageView imgFreeApp;
    TextView txtFreeApp;
    Context context;
    public static NativeAdDetails nativeAd = null;
    AdMobAds adMobAds;

    public StartupAdsProvider(Activity activity, String appid) {
        appid = appid.trim();
        startAppAd = new StartAppAd(activity);
        StartAppSDK.init(activity, appid, false);


        StartAppAd.disableSplash();
        //StartAppSDK.init(activity, "DeveloperID", "ApplicationID", true);
        PrintLog.print("Init for " + appid);
        adMobAds = new AdMobAds(activity);
    }

    public LinearLayout getBannerAds(final Context context) {
        PrintLog.print("call banner");
        final Banner startAppBanner = new Banner(context);
        RelativeLayout.LayoutParams bannerParameters = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_IN_PARENT | RelativeLayout.CENTER_HORIZONTAL);
//        bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        final LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.addView(startAppBanner, bannerParameters);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        startAppBanner.setBannerListener(new BannerListener() {
            @Override
            public void onReceiveAd(View view) {
                PrintLog.print("StartupAdsProvider.onReceiveAd ");
                startAppBanner.showBanner();
            }

            @Override
            public void onFailedToReceiveAd(View view) {
                if (adMobAds == null)
                    adMobAds = new AdMobAds(context);
                linearLayout.addView(adMobAds.admob_GetBannerAds(context, Slave.ADMOB_BANNER_ID_STATIC));
                PrintLog.print("StartupAdsProvider.onFailedToReceiveAd ");
            }

            @Override
            public void onClick(View view) {
                PrintLog.print("StartupAdsProvider.onClick ");

            }
        });

        PrintLog.print("Returing ads as " + linearLayout);
        return linearLayout;

    }

    public void showVideoAds(final Context context) {

        final StartAppAd rewardedVideo = new StartAppAd(context);
        rewardedVideo.setVideoListener(new VideoListener() {

            public void onVideoCompleted() {
//				Toast.makeText(
//						context,
//						"Rewarded video has completed - grant the user his reward",
//						Toast.LENGTH_LONG).show();
            }
        });
        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {

            public void onReceiveAd(Ad arg0) {

                rewardedVideo.showAd();
            }

            public void onFailedToReceiveAd(Ad arg0) {
            }
        });

    }

    public void showfullAds(Activity activity, int Videoadscount) {
        Utils utils = new Utils();

        int currentcount = utils.get_FulladsshownCount(activity);
        utils.set_FulladsshownCount(activity, currentcount + 1);
        PrintLog.print("currentcount " + currentcount + "Videoadscount "
                + Videoadscount);
        PrintLog.print("1736 start app 01");
        if (currentcount % Videoadscount == 0) {
            PrintLog.print("1736 start app 02");
            showVideoAds(activity);
        } else {
            PrintLog.print("1736 start app 03ma");
            PrintLog.print("IS Ready " + startAppAd.isReady());
//            if (startAppAd.isReady() == false)
//                startAppAd.loadAd();
//            startAppAd.showAd();
//            startAppAd.loadAd();


//            if(startAppAd!=null && startAppAd.isReady()){
//                PrintLog.print("1736 start app 04");
//                startAppAd.loadAd();
//            }else{
//                PrintLog.print("1736 start app 05");
//                if(adMobAds==null)
//                    adMobAds = new AdMobAds(context);
//                adMobAds.admob_showFullAds(activity, Slave.ADMOB_FULL_ID_STATIC);
//            }

            if (startAppAd != null) {
                startAppAd.loadAd(new AdEventListener() {
                    @Override
                    public void onReceiveAd(Ad ad) {
                        PrintLog.print("Startapp full ads on ads success");
                        startAppAd.showAd();
                    }

                    @Override
                    public void onFailedToReceiveAd(Ad ad) {
                        PrintLog.print("Startapp full ads on ads failure");
                        if (adMobAds == null)
                            adMobAds = new AdMobAds(context);
                        adMobAds.admob_showFullAds(context, Slave.ADMOB_FULL_ID_STATIC);

                    }
                });


            }


        }

    }

  /*  public AdEventListener nativeAdListener = new AdEventListener() {

        public void onReceiveAd(Ad ad) {

            // Get the native ad
            ArrayList<NativeAdDetails> nativeAdsList = startAppNativeAd
                    .getNativeAds();
            if (nativeAdsList.size() > 0) {
                nativeAd = nativeAdsList.get(0);
            }

            // Verify that an ad was retrieved
            if (nativeAd != null) {

                // When ad is received and displayed - we MUST send impression
                nativeAd.sendImpression(context);

                if (imgFreeApp != null && txtFreeApp != null) {

                    // Set button as enabled
                    imgFreeApp.setEnabled(true);
                    txtFreeApp.setEnabled(true);

                    // Set ad's image
                    imgFreeApp.setImageBitmap(nativeAd.getImageBitmap());

                    // Set ad's title
                    if (nativeAd.getTitle() != null && !nativeAd.getTitle().contains("Error")) {
                        PrintLog.print("onclic Init dsds>>>>>>>>>>>> 00000");
                        txtFreeApp.setText(nativeAd.getTitle());
                        PrintLog.print("onclic Init dsds>>>>>>>>>>>>");
                        imgFreeApp.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                PrintLog.print("onclic >>>>>>>>>>>>");
                                nativeAd.sendClick(context);
                            }
                        });

                    }


                    //nativeAd.get
                }
            }

        }

        public void onFailedToReceiveAd(Ad ad) {

            // Error occurred while loading the native ad
            if (txtFreeApp != null) {
                txtFreeApp.setText("Error while loading Native Ad");
            }
        }
    };*/

  /*  public void provideMicroAds(final Activity ct, ImageView image, TextView text) {
        context = ct;
        imgFreeApp = image;
        txtFreeApp = text;
        startAppNativeAd = new StartAppNativeAd(context);
        startAppNativeAd.loadAd(
                new NativeAdPreferences().setAdsNumber(1)
                        .setAutoBitmapDownload(true)
                        .setImageSize(NativeAdPreferences.NativeAdBitmapSize.SIZE150X150),
                nativeAdListener);
        startAppAd.loadAd();

//
//		image.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				PrintLog.print(" avActivity 00");
//				clickNativeAds(ct);
//
//			}
//		});


    }*/

    public void clickNativeAds(Activity activity) {
        PrintLog.print(" avActivity 22  " + activity);
        if (nativeAd != null)
            nativeAd.sendClick(activity);
    }

    public LinearLayout getBannerAdsNew(Context context) {
        PrintLog.print("JEE Baat");
        StartAppSDK.init(context, "DeveloperID", "ApplicationID", true);
        Banner startAppBanner = new Banner(context);
        RelativeLayout.LayoutParams bannerParameters = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bannerParameters.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.addView(startAppBanner, bannerParameters);
        return linearLayout;

    }

    // private LinearLayout nativeAdContainer;
    //private LinearLayout adView;
    private StartAppNativeAd startAppNativeAd;

    public View showNativeMedium(final Context context, String adsID) {
        PrintLog.print("here is the startapp medium id" + " " + adsID);
        final LinearLayout nativeAdContainer = new LinearLayout(context);
        nativeAdContainer.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        AbsListView.LayoutParams linLayoutParam = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        nativeAdContainer.setLayoutParams(linLayoutParam);


        final StartAppNativeAd startAppNativeAd = new StartAppNativeAd(context);
        startAppNativeAd.loadAd(
                new NativeAdPreferences()
                        .setAdsNumber(1)
                        .setAutoBitmapDownload(true)
                        .setPrimaryImageSize(2),
                new AdEventListener() {
                    @Override
                    public void onReceiveAd(Ad ad) {
                        PrintLog.print("0956 checking 01");
                        LayoutInflater inflater = LayoutInflater.from(context);
                        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.sa_native_medium,
                                null, false);
                        nativeAdContainer.removeAllViews();
                        nativeAdContainer.addView(adView);


                        ArrayList<NativeAdDetails> nativeAdsList = startAppNativeAd.getNativeAds();
                        if (nativeAdsList.size() > 0) {
                            nativeAd = nativeAdsList.get(0);
                        }

                        // Verify that an ad was retrieved
                        if (nativeAd != null) {

                            // When ad is received and displayed - we MUST send impression
                            nativeAd.sendImpression(context);


                            LinearLayout adsParent = (LinearLayout) adView.findViewById(R.id.native_ad_unit);
                            adsParent.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    nativeAd.sendClick(context);
                                }
                            });

                            ImageView contentad_image = (ImageView) adView.findViewById(R.id.native_ad_icon);
                            contentad_image.setImageBitmap(nativeAd.getImageBitmap());

                            TextView contentad_headline = (TextView) adView.findViewById(R.id.native_ad_title);
                            contentad_headline.setText(nativeAd.getTitle());

                            TextView contentad_body = (TextView) adView.findViewById(R.id.native_ad_body);
                            contentad_body.setText(nativeAd.getDescription());

                            Button contentad_logo = (Button) adView.findViewById(R.id.native_ad_call_to_action);
//                contentad_logo.setText(nativeAd.);

//							ImageView ad_choices_container = (ImageView) adView.findViewById(R.id.adsIcon);
//							ad_choices_container.setImageBitmap(nativeAd.getSecondaryImageBitmap());


                        }
                    }

                    @Override
                    public void onFailedToReceiveAd(Ad ad) {
                        PrintLog.print("0956 checking 02");
                        loadAdmobNative(nativeAdContainer, false, context);
                    }
                });


        return nativeAdContainer;
    }

    //LinearLayout nativeAdContainerlarge;


    public View showNativeLarge(final Context context, String appID) {
        PrintLog.print("here is the startapp medium id" + " " + appID);
        final LinearLayout nativeAdContainerlarge = new LinearLayout(context);
        nativeAdContainerlarge.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        AbsListView.LayoutParams linLayoutParam = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.FILL_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        nativeAdContainerlarge.setLayoutParams(linLayoutParam);

        final StartAppNativeAd startAppNativeAd = new StartAppNativeAd(context);
        final NativeAdDetails[] nativeAd = {null};

        startAppNativeAd.loadAd(
                new NativeAdPreferences()
                        .setAdsNumber(1)
                        .setAutoBitmapDownload(true)
                        .setPrimaryImageSize(3),
                new AdEventListener() {
                    @Override
                    public void onReceiveAd(Ad ad) {
                        PrintLog.print("0956 checking 01");
                        LayoutInflater inflater = LayoutInflater.from(context);
                        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.sa_native_large,
                                null, false);

                        nativeAdContainerlarge.removeAllViews();
                        nativeAdContainerlarge.addView(adView);


                        ArrayList<NativeAdDetails> nativeAdsList = startAppNativeAd.getNativeAds();
                        if (nativeAdsList.size() > 0) {
                            nativeAd[0] = nativeAdsList.get(0);
                        }

                        // Verify that an ad was retrieved
                        if (nativeAd[0] != null) {

                            // When ad is received and displayed - we MUST send impression
                            nativeAd[0].sendImpression(context);


                            LinearLayout adsParent = (LinearLayout) adView.findViewById(R.id.native_ad_unit);
                            adsParent.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    nativeAd[0].sendClick(context);
                                }
                            });

                            ImageView contentad_image = (ImageView) adView.findViewById(R.id.native_ad_icon);
                            contentad_image.setImageBitmap(nativeAd[0].getImageBitmap());

                            TextView contentad_headline = (TextView) adView.findViewById(R.id.native_ad_title);
                            contentad_headline.setText(nativeAd[0].getTitle());

                            TextView contentad_body = (TextView) adView.findViewById(R.id.native_ad_body);
                            contentad_body.setText(nativeAd[0].getDescription());

                            PrintLog.print("1134 url" + " " + nativeAd[0].getImageUrl());
                            Button contentad_logo = (Button) adView.findViewById(R.id.native_ad_call_to_action);
//                contentad_logo.setText(nativeAd.);

//							ImageView ad_choices_container = (ImageView) adView.findViewById(R.id.adsIcon);
//							ad_choices_container.setImageBitmap(nativeAd.getSecondaryImageBitmap());


                        }
                    }

                    @Override
                    public void onFailedToReceiveAd(Ad ad) {
                        PrintLog.print("0956 checking 02");
                        loadAdmobNative(nativeAdContainerlarge, true, context);
                    }
                });


        return nativeAdContainerlarge;
    }

    public void loadAdmobNative(LinearLayout linearLayout, boolean isnativelarge, Context ctx) {
        if (linearLayout != null && linearLayout.getChildCount() > 0)
            linearLayout.removeAllViews();
        // adsviewlocal.removeAllViews();
        if (adMobAds == null)
            adMobAds = new AdMobAds(ctx);
        if (isnativelarge)
            linearLayout.addView(new AdmobNativeAdvanced().getNativeAdvancedAds((Activity) ctx, Slave.ADMOB_NATIVE_LARGE_ID_STATIC, true));
        else if (linearLayout != null && linearLayout.getChildCount() > 0)
            linearLayout.removeAllViews();

        linearLayout.addView(new AdmobNativeAdvanced().getNativeAdvancedAds((Activity) ctx, Slave.ADMOB_NATIVE_LARGE_ID_STATIC, false));
        PrintLog.print("FBAds.onError admob loaded now ");
    }
}
