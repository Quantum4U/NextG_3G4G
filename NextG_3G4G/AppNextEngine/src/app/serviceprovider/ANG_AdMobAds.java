package app.serviceprovider;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;

import app.PrintLog;
import app.adshandler.AHandler;
import app.adshandler.Ads;
import app.server.v2.Slave;

public class ANG_AdMobAds {
    public static String DEVICE_ID_1 = "4D2E8C60E5F131C0CAE9A48603FC911C";//RAJEEV*********
    public static String DEVICE_ID_2 = "1758E27B9C77911908C51E04FBA8D92B";//INDRAJEET MI*********

    public static String DEVICE_ID_3 = "5BB82485D78860E774E44B1BB2BC6187";//PANKAJ*******

    public static String DEVICE_ID_4 = "B2E8C59FA440F2104FAB612EBF7E903D";//OFFICE MI*********

    public static String DEVICE_ID_5 = "C0176D28CED6720B096DA6521EB60191";//BHANU***********
    public static String DEVICE_ID_6 = "0DF4CB1E1CB5D49730D8393DEC7B47F0";//RAKESH*******
    public static String DEVICE_ID_7 = "361ABD2EC1B66FD842533F87C2A1B63A";//SIMU

    public static String DEVICE_ID_8 = "775D8E280D276F09B53511F98DD08696";//My J5
    public static String DEVICE_ID_9 = "E3D94EE6C7BD2923CDA56004C34AAE50";//MEENU*******

    public static String DEVICE_ID_10 = "D9281CD323A5304F4F1D1D44E26C81ED";//OFC J2********


    static ANG_AdMobAds obj;
    private InterstitialAd mInterstitial;
    private AdView mAdView;

    public ANG_AdMobAds(Context context) {
        if (mInterstitial == null)
            mInterstitial = new InterstitialAd(context);
//		mInterstitial.setAdUnitId(context.getResources().getString(
//				R.string.pindi_ad_unit_id));
    }

    public LinearLayout admob_GetBannerAds(Context context, String id) {
        id = id.trim();
        PrintLog.print("stage 4 " + id);
        mAdView = new AdView(context);
        mAdView.setAdUnitId(id);

        mAdView.setAdSize(AdSize.BANNER);

        LinearLayout linLayout = new LinearLayout(context);

        linLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        LayoutParams linLayoutParam = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(DEVICE_ID_1)
                .addTestDevice(DEVICE_ID_2)
                .addTestDevice(DEVICE_ID_3)
                .addTestDevice(DEVICE_ID_4)
                .addTestDevice(DEVICE_ID_5)
                .addTestDevice(DEVICE_ID_6)
                .addTestDevice(DEVICE_ID_7)
                .addTestDevice(DEVICE_ID_8)
                .addTestDevice(DEVICE_ID_9)
                .addTestDevice(DEVICE_ID_10)

                .build();
//B37B9AF5653F23F6B7E6696BAC8B444E
        mAdView.loadAd(adRequest);
        // mAdView.loadAd(new AdRequest.Builder().build());

        linLayout.addView(mAdView);

        return linLayout;
    }


    public LinearLayout admob_GetNativeSmall(Context context, String id) {

        // ca-app-pub-3451337100490595/6415620460 small
        //  ca-app-pub-3451337100490595/4878938866 med
        // ca-app-pub-3451337100490595/6041700461  large
        //id="ca-app-pub-3451337100490595/6415620460";
        Ads.printdata(" 2017 request small from " + id);
        NativeExpressAdView mAdView = new NativeExpressAdView(context);
        mAdView.setAdUnitId(id);

        //mAdView.setAdSize(AdSize.FULL_WIDTH,20);
        mAdView.setAdSize(new AdSize(AdSize.FULL_WIDTH, 80));

        //	mAdView.setAdSize(new AdSize(360,320));-----Size for large
        //	mAdView.setAdSize(new AdSize(AdSize.FULL_WIDTH,80));---- Size for small
        //mAdView.setAdSize(new AdSize(AdSize.FULL_WIDTH,150));

        LinearLayout linLayout = new LinearLayout(context);

        linLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        LayoutParams linLayoutParam = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(DEVICE_ID_1)
                .addTestDevice(DEVICE_ID_2)
                .addTestDevice(DEVICE_ID_3)
                .addTestDevice(DEVICE_ID_4)
                .addTestDevice(DEVICE_ID_5)
                .addTestDevice(DEVICE_ID_6)
                .addTestDevice(DEVICE_ID_7)
                .addTestDevice(DEVICE_ID_8)
                .addTestDevice(DEVICE_ID_9)
                .addTestDevice(DEVICE_ID_10)

                .build();
//B37B9AF5653F23F6B7E6696BAC8B444E
        mAdView.loadAd(adRequest);
        //Toast.makeText(context,"Data "+mAdView,Toast.LENGTH_LONG).show();
        // mAdView.loadAd(new AdRequest.Builder().build());

        linLayout.addView(mAdView);

        return linLayout;
    }


    public LinearLayout admob_GetNativeMedium(Context context, String id) {
        id = id.trim();
        Ads.printdata(" 2017 request med from " + id);

        NativeExpressAdView mAdView = new NativeExpressAdView(context);
        mAdView.setAdUnitId(id);

        //mAdView.setAdSize(AdSize.FULL_WIDTH,20);
        mAdView.setAdSize(new AdSize(AdSize.FULL_WIDTH, 150));

        LinearLayout linLayout = new LinearLayout(context);

        linLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        LayoutParams linLayoutParam = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(DEVICE_ID_1)
                .addTestDevice(DEVICE_ID_2)
                .addTestDevice(DEVICE_ID_3)
                .addTestDevice(DEVICE_ID_4)
                .addTestDevice(DEVICE_ID_5)
                .addTestDevice(DEVICE_ID_6)
                .addTestDevice(DEVICE_ID_7)
                .addTestDevice(DEVICE_ID_8)
                .addTestDevice(DEVICE_ID_9)
                .addTestDevice(DEVICE_ID_10)

                .build();
//B37B9AF5653F23F6B7E6696BAC8B444E
        mAdView.loadAd(adRequest);
        //Toast.makeText(context,"Data "+mAdView,Toast.LENGTH_LONG).show();
        // mAdView.loadAd(new AdRequest.Builder().build());

        linLayout.addView(mAdView);

        return linLayout;
    }


    public LinearLayout admob_GetNativeMediumMicro(Context context, String id) {
        Ads.printdata(" 2017 request med from " + id);

        NativeExpressAdView mAdView = new NativeExpressAdView(context);
        mAdView.setAdUnitId(id);

        //mAdView.setAdSize(AdSize.FULL_WIDTH,20);
        mAdView.setAdSize(new AdSize(340, 132));

        LinearLayout linLayout = new LinearLayout(context);

        linLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        LayoutParams linLayoutParam = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(DEVICE_ID_1)
                .addTestDevice(DEVICE_ID_2)
                .addTestDevice(DEVICE_ID_3)
                .addTestDevice(DEVICE_ID_4)
                .addTestDevice(DEVICE_ID_5)
                .addTestDevice(DEVICE_ID_6)
                .addTestDevice(DEVICE_ID_7)
                .addTestDevice(DEVICE_ID_8)
                .addTestDevice(DEVICE_ID_9)
                .addTestDevice(DEVICE_ID_10)

                .build();
//B37B9AF5653F23F6B7E6696BAC8B444E
        mAdView.loadAd(adRequest);
        //Toast.makeText(context,"Data "+mAdView,Toast.LENGTH_LONG).show();
        // mAdView.loadAd(new AdRequest.Builder().build());

        linLayout.addView(mAdView);

        return linLayout;
    }

    public LinearLayout admob_GetNativeLarge(Context context, String id) {
        id = id.trim();
        NativeExpressAdView mAdView = new NativeExpressAdView(context);
        mAdView.setAdUnitId(id);

        //mAdView.setAdSize(AdSize.FULL_WIDTH,20);
        mAdView.setAdSize(new AdSize(360, 320));

        LinearLayout linLayout = new LinearLayout(context);

        linLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        LayoutParams linLayoutParam = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(DEVICE_ID_1)
                .addTestDevice(DEVICE_ID_2)
                .addTestDevice(DEVICE_ID_3)
                .addTestDevice(DEVICE_ID_4)
                .addTestDevice(DEVICE_ID_5)
                .addTestDevice(DEVICE_ID_6)
                .addTestDevice(DEVICE_ID_7)
                .addTestDevice(DEVICE_ID_8)
                .addTestDevice(DEVICE_ID_9)
                .addTestDevice(DEVICE_ID_10)

                .build();
//B37B9AF5653F23F6B7E6696BAC8B444E
        mAdView.loadAd(adRequest);
        //Toast.makeText(context,"Data "+mAdView,Toast.LENGTH_LONG).show();
        // mAdView.loadAd(new AdRequest.Builder().build());

        linLayout.addView(mAdView);

        return linLayout;
    }

    public LinearLayout admob_GetNativeLargeMicro(Context context, String id) {
        NativeExpressAdView mAdView = new NativeExpressAdView(context);
        mAdView.setAdUnitId(id);

        //mAdView.setAdSize(AdSize.FULL_WIDTH,20);
        mAdView.setAdSize(new AdSize(350, 250));

        LinearLayout linLayout = new LinearLayout(context);

        linLayout.setOrientation(LinearLayout.VERTICAL);
        // creating LayoutParams
        LayoutParams linLayoutParam = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(DEVICE_ID_1)
                .addTestDevice(DEVICE_ID_2)
                .addTestDevice(DEVICE_ID_3)
                .addTestDevice(DEVICE_ID_4)
                .addTestDevice(DEVICE_ID_5)
                .addTestDevice(DEVICE_ID_6)
                .addTestDevice(DEVICE_ID_7)
                .addTestDevice(DEVICE_ID_8)
                .addTestDevice(DEVICE_ID_9)
                .addTestDevice(DEVICE_ID_10)

                .build();
//B37B9AF5653F23F6B7E6696BAC8B444E
        mAdView.loadAd(adRequest);
        //Toast.makeText(context,"Data "+mAdView,Toast.LENGTH_LONG).show();
        // mAdView.loadAd(new AdRequest.Builder().build());

        linLayout.addView(mAdView);

        return linLayout;
    }


    public void onPauseBanner() {
        if (mAdView != null)
            mAdView.pause();

    }

    public void onResumeBanner() {
        if (mAdView != null)
            mAdView.resume();
    }

    public void onDestroyBanner() {
        if (mAdView != null)
            mAdView.destroy();

    }

    public void admob_InitFullAds(final Context context, String id, int from, final boolean isCallback, final AHandler.OnCacheFullAdLoaded l) {
        id = id.trim();
        final String _id = id;
        PrintLog.print("EngineV2 Startwhyyyy Full ads " + id + " and context is " + context);


        mInterstitial = new InterstitialAd(context);
        mInterstitial.setAdUnitId(id);
        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                if (isCallback && l != null)
                    l.onFullAdLoaded();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

                if (isCallback && l != null)
                    l.onFullAdFailed();
            }

            @Override
            public void onAdOpened() {

            }

            @Override
            public void onAdLeftApplication() {

            }

            @Override
            public void onAdClosed() {

            }
        });

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(DEVICE_ID_1)
                .addTestDevice(DEVICE_ID_2)
                .addTestDevice(DEVICE_ID_3)
                .addTestDevice(DEVICE_ID_4)
                .addTestDevice(DEVICE_ID_5)
                .addTestDevice(DEVICE_ID_6)
                .addTestDevice(DEVICE_ID_7)
                .addTestDevice(DEVICE_ID_8)
                .addTestDevice(DEVICE_ID_9)
                .addTestDevice(DEVICE_ID_10)

                .build();
        mInterstitial.loadAd(adRequest);
        PrintLog.print("EngineV2 Request Full is loaded " + mInterstitial.isLoaded());


        // mInterstitial.loadAd(new AdRequest.Builder().build());
    }

    public void admob_showFullAds(Context context, String id) {
        id = id.trim();
        System.out.println("1108 logs check 01" + " " + Slave.LAUNCH_FULL_ADS_ad_id + " " + id);

        if (mInterstitial != null)
            PrintLog.print("1108 logs check EngineV2 Start full display is loaded " + mInterstitial.isLoaded() + "  id " + id);

        if (mInterstitial.isLoaded()) {
            mInterstitial.show();
            admob_InitFullAds(context, id, 3, false, null);
        } else {
            admob_InitFullAds(context, id, 2, false, null);
            mInterstitial.show();
        }

    }

    public boolean isAdmobInterstialLoaded() {
        if (mInterstitial.isLoaded())
            return true;
        else
            return false;
    }

    public static ANG_AdMobAds getAdmobOBJ(Context context) {
        if (obj == null)
            return new ANG_AdMobAds(context);
        else
            return obj;

    }

}
