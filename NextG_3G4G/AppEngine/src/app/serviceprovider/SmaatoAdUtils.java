package app.serviceprovider;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smaato.soma.AdDimension;
import com.smaato.soma.AdDownloaderInterface;
import com.smaato.soma.AdListenerInterface;
import com.smaato.soma.BannerView;
import com.smaato.soma.ErrorCode;
import com.smaato.soma.ReceivedBannerInterface;
import com.smaato.soma.SOMA;
import com.smaato.soma.bannerutilities.constant.BannerStatus;
import com.smaato.soma.interstitial.Interstitial;
import com.smaato.soma.interstitial.InterstitialAdListener;
import com.smaato.soma.nativead.NativeAd;

import app.PrintLog;
import app.pnd.adshandler.R;
import app.server.v2.Slave;


/**
 * Created by rajeev on 09/03/18.
 */

public class SmaatoAdUtils {
    LinearLayout adView;
    LinearLayout adsviewlocalMedium, adsviewlocalLarge;
    LinearLayout adsviewBannerlocal;
    private Interstitial mInterstitialAd;
    private BannerView mBannerAd;
    //private NativeAd nativeAdMedium, nativeAdLarge;
    LinearLayout mContainer;
    AdMobAds adMobAds;
    static SmaatoAdUtils smaatoAdUtils = null;
    String publisherID;
    String adspaceID;
    String stringIDS;

    public View getSmaatoBanner(final Activity ctx, String id) {
        id = id.trim();
        SOMA.init(ctx.getApplication());
        mBannerAd = new BannerView(ctx);
        //mBannerAd.setMinimumHeight(50);
        adsviewBannerlocal = new LinearLayout(ctx);
        mBannerAd.setGravity(RelativeLayout.CENTER_HORIZONTAL);
        adsviewBannerlocal.setGravity(Gravity.CENTER_HORIZONTAL);

        try {
            stringIDS = id;
            String[] parts = stringIDS.split("#");
            publisherID = parts[0];
            adspaceID = parts[1];

            mBannerAd.getAdSettings().setAdDimension(AdDimension.DEFAULT);
            mBannerAd.getAdSettings().setPublisherId(Long.parseLong(publisherID));
            mBannerAd.getAdSettings().setAdspaceId(Long.parseLong(adspaceID));
            mBannerAd.setAutoReloadFrequency(60);
            mBannerAd.asyncLoadNewBanner();
            setBannerLayoutParams(ctx);

        } catch (Exception e) {
            if (adMobAds == null)
                adMobAds = new AdMobAds(ctx);
            adsviewBannerlocal.addView(adMobAds.admob_GetBannerAds(ctx, Slave.ADMOB_BANNER_ID_STATIC));
            PrintLog.print("1401 SmaatoAdUtils catch banner admob");
        }

        mBannerAd.addAdListener(new AdListenerInterface() {
            @Override
            public void onReceiveAd(AdDownloaderInterface adDownloaderInterface, ReceivedBannerInterface receivedBanner) {
                if (receivedBanner.getStatus() == BannerStatus.SUCCESS) {
                    adsviewBannerlocal.addView(mBannerAd);
                    PrintLog.print("1401 SmaatoAdUtils banner loaded");
                } else if (receivedBanner.getErrorCode() != ErrorCode.NO_ERROR) {
//                    Toast.makeText(ctx,receivedBanner.getErrorMessage(),Toast.LENGTH_LONG).show();
                    if (adMobAds == null)
                        adMobAds = new AdMobAds(ctx);
                    adsviewBannerlocal.addView(adMobAds.admob_GetBannerAds(ctx, Slave.ADMOB_BANNER_ID_STATIC));
                    PrintLog.print("1401 SmaatoAdUtils banner ads " + receivedBanner.getErrorMessage());

                }
            }
        });


        return adsviewBannerlocal;
    }

    private void setBannerLayoutParams(Activity activity) {
        int width = toPixelUnits(activity, 320);
        int height = toPixelUnits(activity, 50);
        RelativeLayout.LayoutParams bannerLayoutParams = new RelativeLayout.LayoutParams(width, height);
        mBannerAd.setLayoutParams(bannerLayoutParams);
    }

    private int toPixelUnits(Activity activity, int dipUnit) {
        float density = activity.getResources().getDisplayMetrics().density;
        return Math.round(dipUnit * density);
    }

    public View getSmaatoLargeNative(final Activity ctx, String id) {
        id = id.trim();
        SOMA.init(ctx.getApplication());
        adsviewlocalLarge = new LinearLayout(ctx);
        NativeAd nativeAdLarge = new NativeAd(ctx);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        try {
            adView = (LinearLayout) inflater.inflate(R.layout.smaato_native_large_ads,
                    adsviewlocalLarge, false);

            stringIDS = id;
            String[] parts = stringIDS.split("#");
            publisherID = parts[0];
            adspaceID = parts[1];

            nativeAdLarge.getAdSettings().setPublisherId(Long.parseLong(publisherID));
            nativeAdLarge.getAdSettings().setAdspaceId(Long.parseLong(adspaceID));

            if (mContainer != null && mContainer.getChildCount() > 0) {
                mContainer.removeAllViews();
                PrintLog.print("SmaatoAdUtils.getSmaatoNative");
            }

            mContainer = (LinearLayout) adView.findViewById(R.id.container);
            ImageView iconImage = (ImageView) adView.findViewById(R.id.nativeIconImage);
            ImageView mainImage = (ImageView) adView.findViewById(R.id.nativeMainImage);
            TextView nativeText = (TextView) adView.findViewById(R.id.nativeText);
            Button ctaButton = (Button) adView.findViewById(R.id.ctatButton);
            TextView nativeTitle = (TextView) adView.findViewById(R.id.nativeTitle);
            RelativeLayout nativeRelativeLayout = (RelativeLayout) adView.findViewById(R.id.nativeAdLayout);
            RatingBar ratingBar = (RatingBar) adView.findViewById(R.id.ratingBarNative);

            nativeAdLarge.setClickToActionButton(ctaButton).setIconImageView(iconImage)
                    .setMainImageView(mainImage).setTextView(nativeText)
                    .setTitleView(nativeTitle).setMainLayout(nativeRelativeLayout).setRatingBar(ratingBar);

            adsviewlocalLarge.addView(adView);

        } catch (Exception e) {
            if (adMobAds == null)
                adMobAds = new AdMobAds(ctx);
            adsviewlocalLarge.addView(new AdmobNativeAdvanced().getNativeAdvancedAds((Activity) ctx,
                    Slave.ADMOB_NATIVE_LARGE_ID_STATIC, true));
            PrintLog.print("1401 SmaatoAdUtils LargeNative catch admob");
        }

        nativeAdLarge.setAdListener(new AdListenerInterface() {
            @Override
            public void onReceiveAd(AdDownloaderInterface adDownloaderInterface, ReceivedBannerInterface receivedBanner) {
                if (receivedBanner.getStatus() == BannerStatus.SUCCESS) {
                    mContainer.setVisibility(View.VISIBLE);
                    // adsviewlocal.addView(adView);
                    PrintLog.print("1401 SmaatoAdUtils LargeNative ads loaded");

                } else if (receivedBanner.getErrorCode() != ErrorCode.NO_ERROR) {
                    // Toast.makeText(ctx,receivedBanner.getErrorMessage(),Toast.LENGTH_LONG).show();
                    if (adMobAds == null)
                        adMobAds = new AdMobAds(ctx);
                    adsviewlocalLarge.addView(new AdmobNativeAdvanced().getNativeAdvancedAds((Activity) ctx,
                            Slave.ADMOB_NATIVE_LARGE_ID_STATIC, true));
                    PrintLog.print("1401 SmaatoAdUtils LargeNative ads " + receivedBanner.getErrorMessage());
                }
            }
        });

        nativeAdLarge.asyncLoadNewBanner();
        return adsviewlocalLarge;
    }

    public View getSmaatoMediumNative(final Activity ctx, String id) {
        id = id.trim();
        SOMA.init(ctx.getApplication());
        adsviewlocalMedium = new LinearLayout(ctx);
        final NativeAd nativeAdMedium = new NativeAd(ctx);

        LayoutInflater inflater = LayoutInflater.from(ctx);

        try {
            adView = (LinearLayout) inflater.inflate(R.layout.smaato_native_medium_ads,
                    adsviewlocalMedium, false);

            stringIDS = id;
            String[] parts = stringIDS.split("#");
            publisherID = parts[0];
            adspaceID = parts[1];

            nativeAdMedium.getAdSettings().setPublisherId(Long.parseLong(publisherID));
            nativeAdMedium.getAdSettings().setAdspaceId(Long.parseLong(adspaceID));


            if (mContainer != null && mContainer.getChildCount() > 0) {
                mContainer.removeAllViews();
                PrintLog.print("SmaatoAdUtils.getSmaatoNative");
            }
            mContainer = (LinearLayout) adView.findViewById(R.id.container);
            RelativeLayout nativeLay = (RelativeLayout) adView.findViewById(R.id.nativeAdLayout);

            nativeAdMedium.setMainLayout(nativeLay);
            // nativeAd.setTitleTextSize(16);
            nativeAdMedium.setBtn_textSize(11);
            //adsviewlocal.addView(adView);

        } catch (Exception e) {
            if (adMobAds == null)
                adMobAds = new AdMobAds(ctx);
            adsviewlocalMedium.addView(new AdmobNativeAdvanced().getNativeAdvancedAds((Activity) ctx,
                    Slave.ADMOB_NATIVE_LARGE_ID_STATIC, false));
            PrintLog.print("1401 SmaatoAdUtils MediumNative catch admob");
        }

        nativeAdMedium.asyncLoadNativeType(NativeAd.NativeType.NEWS_FEED, new NativeAd.NativeAdTypeListener() {
            @Override
            public void onError(ErrorCode errorCode, String errorMessage) {
                // No Ad Response is fine. Check for errorCode & errorMessage.
                if (errorMessage != null) {
                    if (adMobAds == null)
                        adMobAds = new AdMobAds(ctx);
                    adsviewlocalMedium.addView(new AdmobNativeAdvanced().getNativeAdvancedAds((Activity) ctx,
                            Slave.ADMOB_NATIVE_LARGE_ID_STATIC, false));
                    PrintLog.print("1401 SmaatoAdUtils MediumNative OnErrorResponse" + errorCode + " " + errorMessage);
                }

            }

            @Override
            public void onAdResponse(ViewGroup nativeView) {
                if (nativeView != null) {
                    PrintLog.print("1401 SmaatoAdUtils MediumNative Generated RelativeLayout (NEWS_FEED) view received");
                    // Configure & Format as required on the generated Layout elements before binding.
                    // IMPORTANT step to be invoked after attaching ad response to Screen.
                    // IMPORTANT FOR IMPRESSIONS COUNTS
                    nativeAdMedium.bindAdResponse(nativeView);
                    adsviewlocalMedium.addView(adView);
                }
            }
        });

        nativeAdMedium.setAdListener(new AdListenerInterface() {
            @Override
            public void onReceiveAd(AdDownloaderInterface adDownloaderInterface, ReceivedBannerInterface receivedBanner) {
                //  Toast.makeText(ctx,receivedBanner.getErrorMessage(),Toast.LENGTH_LONG).show();
                if (receivedBanner.getStatus() == BannerStatus.SUCCESS) {
                    // adsviewlocal.addView(adView);
                    PrintLog.print("1401 SmaatoAdUtils MediumNative ads loaded");

                } else if (receivedBanner.getErrorCode() != ErrorCode.NO_ERROR) {
                    if (adMobAds == null)
                        adMobAds = new AdMobAds(ctx);
                    adsviewlocalMedium.addView(new AdmobNativeAdvanced().getNativeAdvancedAds((Activity) ctx,
                            Slave.ADMOB_NATIVE_LARGE_ID_STATIC, false));
                    PrintLog.print("1401 SmaatoAdUtils MediumNative ads " + receivedBanner.getErrorMessage());
                }

            }
        });

        return adsviewlocalMedium;
    }

    public void loadSmaatoFullAds(final Activity activity, String id) {
        id = id.trim();
        SOMA.init(activity.getApplication());
        mInterstitialAd = new Interstitial(activity);
        try {
            stringIDS = id;
            String[] parts = stringIDS.split("#");
            publisherID = parts[0];
            adspaceID = parts[1];

            mInterstitialAd.getAdSettings().setPublisherId(Long.parseLong(publisherID));
            mInterstitialAd.getAdSettings().setAdspaceId(Long.parseLong(adspaceID));

        } catch (Exception e) {
            if (adMobAds == null)
                adMobAds = new AdMobAds(activity);
            adMobAds.admob_showFullAds(activity, Slave.ADMOB_FULL_ID_STATIC);
            PrintLog.print("1401 SmaatoAdUtils fullads catch admob");
        }

        mInterstitialAd.setInterstitialAdListener(new InterstitialAdListener() {
            @Override
            public void onReadyToShow() {
                PrintLog.print("1401 SmaatoAdUtils full ads loaded ");
            }

            @Override
            public void onWillShow() {

            }

            @Override
            public void onWillOpenLandingPage() {

            }

            @Override
            public void onWillClose() {

            }

            @Override
            public void onFailedToLoadAd() {
                // Toast.makeText(activity,"Full Ads Failed",Toast.LENGTH_LONG).show();
                PrintLog.print("1401 SmaatoAdUtils full ads failed ");

            }
        });

        if (mInterstitialAd != null && mInterstitialAd.isInterstitialReady() == false) {
            mInterstitialAd.asyncLoadNewBanner();
        }


    }


    public void showSmaatoFullAds(Activity activity, String id) {
        id = id.trim();
        if (mInterstitialAd != null && mInterstitialAd.isInterstitialReady()) {
            mInterstitialAd.show();
            PrintLog.print("1401 SmaatoAdUtils full show ");
        } else {
            if (adMobAds == null)
                adMobAds = new AdMobAds(activity);
            adMobAds.admob_showFullAds(activity, Slave.ADMOB_FULL_ID_STATIC);
        }
        loadSmaatoFullAds(activity, id);


    }

    public static SmaatoAdUtils getSmaatoObj() {
        if (smaatoAdUtils == null)
            smaatoAdUtils = new SmaatoAdUtils();
        return smaatoAdUtils;

    }
}
