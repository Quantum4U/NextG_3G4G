package app.serviceprovider;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.InMobiNative;
import com.inmobi.sdk.InMobiSdk;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.PrintLog;
import app.pnd.adshandler.R;
import app.server.v2.Slave;


/**
 * Created by rajeev on 09/03/18.
 */

public class ANG_InMobiAdUtils {
    private final List<InMobiNative> mNativeAds = new ArrayList<>();
    // private LinearLayout adView,adViewLarge;
    // private LinearLayout adsviewlocal, adsviewlocalLarge;
    private LinearLayout adsviewBannerlocal;
    private InMobiInterstitial mInterstitialAd;
    private InMobiBanner mBannerAd;
    // private InMobiNative nativeAd,nativeAdLarge;
    // LinearLayout mContainer, mContainerLarge;
   // View nativeView, nativeViewLarge;
    ANG_AdMobAds adMobAds;

    static ANG_InMobiAdUtils inMobiAdUtils = null;

    public View getInMobiBanner(final Activity ctx, String id) {
        id = id.trim();
        InMobiSdk.init(ctx, id);
        mBannerAd = new InMobiBanner(ctx, Long.parseLong(id));
        adsviewBannerlocal = new LinearLayout(ctx);

//        LayoutInflater inflater = LayoutInflater.from(ctx);
//        adView = (LinearLayout) inflater.inflate(R.layout.ang_inmobi_banner_ads,
//                adsviewlocal, false);
        // adsviewlocal.addView(adView);
        adsviewBannerlocal.setOrientation(LinearLayout.HORIZONTAL);
        adsviewBannerlocal.setGravity(Gravity.CENTER_HORIZONTAL);
        //  mContainer = (LinearLayout) adView.findViewById(R.id.ad_container);
        mBannerAd.setRefreshInterval(40);
        setBannerLayoutParams(ctx);
        mBannerAd.setListener(new InMobiBanner.BannerAdListener() {
            @Override
            public void onAdLoadSucceeded(InMobiBanner inMobiBanner) {
                adsviewBannerlocal.addView(mBannerAd);
                PrintLog.print("1401 Banner onAdLoadSucceeded");
            }

            @Override
            public void onAdLoadFailed(InMobiBanner inMobiBanner,
                                       InMobiAdRequestStatus inMobiAdRequestStatus) {
                // Toast.makeText(ctx,inMobiAdRequestStatus.getMessage(),Toast.LENGTH_LONG).show();
                if (adMobAds == null)
                    adMobAds = new ANG_AdMobAds(ctx);
                adsviewBannerlocal.addView(adMobAds.admob_GetBannerAds(ctx, Slave.ADMOB_BANNER_ID_STATIC));

                PrintLog.print("1401 Banner ad failed to load with error: " +
                        inMobiAdRequestStatus.getMessage());
            }

            @Override
            public void onAdDisplayed(InMobiBanner inMobiBanner) {
                PrintLog.print("1401 Banner onAdDisplayed");
            }

            @Override
            public void onAdDismissed(InMobiBanner inMobiBanner) {
                PrintLog.print("1401 Banner onAdDismissed");
            }

            @Override
            public void onAdInteraction(InMobiBanner inMobiBanner, Map<Object, Object> map) {
                PrintLog.print("1401 Banner onAdInteraction");
            }

            @Override
            public void onUserLeftApplication(InMobiBanner inMobiBanner) {
                PrintLog.print("1401 Banner onUserLeftApplication");
            }

            @Override
            public void onAdRewardActionCompleted(InMobiBanner inMobiBanner, Map<Object, Object> map) {
                PrintLog.print("1401 Banner onAdRewardActionCompleted");
            }
        });

        mBannerAd.load();
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

    public View getInMobiNative(final Activity ctx, final boolean isnativelarge, String id) {
        id = id.trim();
        PrintLog.print("1401 ANG_InMobiAdUtils.getInMobiNative " + id);
        InMobiSdk.init(ctx, id);
        final LinearLayout adsviewlocal = new LinearLayout(ctx);

        final InMobiNative nativeAd = new InMobiNative(ctx, Long.parseLong(id),
                new InMobiNative.NativeAdListener() {
                    @Override
                    public void onAdLoadSucceeded(@NonNull InMobiNative inMobiNative) {
                        PrintLog.print("1401 native onAdLoadSucceeded");
                        LayoutInflater inflater = LayoutInflater.from(ctx);
                        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.ang_inmobi_native_ads,
                                adsviewlocal, false);
                        adsviewlocal.addView(adView);
                        LinearLayout mContainer = (LinearLayout) adView.findViewById(R.id.container);

//                        if (isnativelarge) {
//                            nativeView = loadLargeNativeAdsView(inMobiNative, ctx);
//                            if (mContainer != null && mContainer.getChildCount() > 0) {
//                                mContainer.removeAllViews();
//                            }
//
//                            mContainer.addView(nativeView);
//                        }
                        // else {
                        View nativeView = loadMediumNativeAdsView(inMobiNative, ctx);
                        if (mContainer != null && mContainer.getChildCount() > 0) {
                            mContainer.removeAllViews();
                        }
                        mContainer.addView(nativeView);
                        // }


                    }

                    @Override
                    public void onAdLoadFailed(@NonNull InMobiNative inMobiNative, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                        // Toast.makeText(ctx,inMobiAdRequestStatus.getMessage(),Toast.LENGTH_LONG).show();
                        PrintLog.print("1401 native ad failed to load with error: " +
                                inMobiAdRequestStatus.getMessage());
                        loadAdmobNative(isnativelarge, ctx, adsviewlocal);
                    }

                    @Override
                    public void onAdFullScreenDismissed(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdFullScreenWillDisplay(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdFullScreenDisplayed(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onUserWillLeaveApplication(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdImpressed(@NonNull InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdClicked(@NonNull InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onMediaPlaybackComplete(@NonNull InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdStatusChanged(@NonNull InMobiNative inMobiNative) {

                    }
                });
        nativeAd.load();
        mNativeAds.add(nativeAd);
        adsviewlocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nativeAd.reportAdClickAndOpenLandingPage();
            }
        });
        return adsviewlocal;
    }

    public View getInMobiNativeLarge(final Activity ctx, final boolean isnativelarge, String id) {
        id = id.trim();
        PrintLog.print("1401 ANG_InMobiAdUtils.getInMobiNative large" + id);
        InMobiSdk.init(ctx, id);
        final LinearLayout adsviewlocalLarge = new LinearLayout(ctx);

        final InMobiNative nativeAdLarge = new InMobiNative(ctx, Long.parseLong(id),
                new InMobiNative.NativeAdListener() {
                    @Override
                    public void onAdLoadSucceeded(@NonNull InMobiNative inMobiNative) {
                        PrintLog.print("1401 native onAdLoadSucceeded large ");
                        LayoutInflater inflater = LayoutInflater.from(ctx);
                        LinearLayout adViewLarge = (LinearLayout) inflater.inflate(R.layout.ang_inmobi_banner_ads,
                                adsviewlocalLarge, false);
                        adsviewlocalLarge.addView(adViewLarge);
                        LinearLayout mContainerLarge = (LinearLayout) adViewLarge.findViewById(R.id.ad_container);

                        if (isnativelarge) {
                            View nativeViewLarge = loadLargeNativeAdsView(inMobiNative, ctx, adsviewlocalLarge);
                            if (mContainerLarge != null && mContainerLarge.getChildCount() > 0) {
                                mContainerLarge.removeAllViews();
                            }

                            mContainerLarge.addView(nativeViewLarge);
                        }
//                        else {
//                            nativeView = loadMediumNativeAdsView(inMobiNative, ctx);
//                            if (mContainer != null && mContainer.getChildCount() > 0) {
//                                mContainer.removeAllViews();
//                            }
//                            mContainer.addView(nativeView);
//                        }

                       // mNativeAds.add(nativeAdLarge);
                    }

                    @Override
                    public void onAdLoadFailed(@NonNull InMobiNative inMobiNative, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                        // Toast.makeText(ctx,inMobiAdRequestStatus.getMessage(),Toast.LENGTH_LONG).show();
                        PrintLog.print("1401 native ad failed to load with error: large " +
                                inMobiAdRequestStatus.getMessage());
                        loadAdmobNative(isnativelarge, ctx,adsviewlocalLarge);
                    }

                    @Override
                    public void onAdFullScreenDismissed(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdFullScreenWillDisplay(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdFullScreenDisplayed(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onUserWillLeaveApplication(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdImpressed(@NonNull InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdClicked(@NonNull InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onMediaPlaybackComplete(@NonNull InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdStatusChanged(@NonNull InMobiNative inMobiNative) {

                    }
                });
        nativeAdLarge.load();
        mNativeAds.add(nativeAdLarge);
        adsviewlocalLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nativeAdLarge.reportAdClickAndOpenLandingPage();
            }
        });
        return adsviewlocalLarge;
    }


    public void loadAdmobNative(boolean isnativelarge, Activity ctx, LinearLayout linearLayout) {
        if (adMobAds == null)
            adMobAds = new ANG_AdMobAds(ctx);

        if (isnativelarge) {
            if (linearLayout != null && linearLayout.getChildCount() > 0)
                linearLayout.removeAllViews();
            linearLayout.addView(new ANG_AdmobNativeAdv().getNativeAdvancedAds((Activity) ctx, Slave.ADMOB_NATIVE_LARGE_ID_STATIC, true));

        } else {
            if (linearLayout != null && linearLayout.getChildCount() > 0)
                linearLayout.removeAllViews();
            linearLayout.addView(new ANG_AdmobNativeAdv().getNativeAdvancedAds((Activity) ctx, Slave.ADMOB_NATIVE_LARGE_ID_STATIC, false));

        }
        PrintLog.print("1401 Inmobi admob loaded now ");
    }


    private View loadLargeNativeAdsView(@NonNull final InMobiNative inMobiNative, Activity ctx, LinearLayout linear) {
        View adView = LayoutInflater.from(ctx).inflate(R.layout.ang_inmobi_ad_layout, null);

        ImageView icon = (ImageView) adView.findViewById(R.id.adIcon);
        TextView title = (TextView) adView.findViewById(R.id.adTitle);
        TextView description = (TextView) adView.findViewById(R.id.adDescription);
        Button action = (Button) adView.findViewById(R.id.adAction);
        FrameLayout content = (FrameLayout) adView.findViewById(R.id.adContent);
        RatingBar ratingBar = (RatingBar) adView.findViewById(R.id.adRating);

        Picasso.with(ctx)
                .load(inMobiNative.getAdIconUrl())
                .into(icon);

        title.setText(inMobiNative.getAdTitle());
        description.setText(inMobiNative.getAdDescription());
        action.setText(inMobiNative.getAdCtaText());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        content.addView(inMobiNative.getPrimaryViewOfWidth(content, linear, displayMetrics.widthPixels));

        float rating = inMobiNative.getAdRating();
        if (rating != 0) {
            ratingBar.setRating(rating);
        }
        ratingBar.setVisibility(rating != 0 ? View.VISIBLE : View.GONE);

        adView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nativeAd.reportAdClickAndOpenLandingPage();
            }
        });

        return adView;
    }

    private View loadMediumNativeAdsView(@NonNull final InMobiNative inMobiNative, Activity ctx) {
        View adView = LayoutInflater.from(ctx).inflate(R.layout.ang_inmobi_ad_medi_layout, null);

        ImageView icon = (ImageView) adView.findViewById(R.id.adIcon);
        TextView title = (TextView) adView.findViewById(R.id.adTitle);
        // TextView description = (TextView) adView.findViewById(R.id.adDescription);
        Button action = (Button) adView.findViewById(R.id.adAction);
        // FrameLayout content = (FrameLayout) adView.findViewById(R.id.adContent);
        RatingBar ratingBar = (RatingBar) adView.findViewById(R.id.adRating);

        Picasso.with(ctx)
                .load(inMobiNative.getAdIconUrl())
                .into(icon);

        title.setText(inMobiNative.getAdTitle());
        //description.setText(inMobiNative.getAdDescription());
        action.setText(inMobiNative.getAdCtaText());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //content.addView(inMobiNative.getPrimaryViewOfWidth(content, adsviewlocal, displayMetrics.widthPixels));

        float rating = inMobiNative.getAdRating();
        if (rating != 0) {
            ratingBar.setRating(rating);
        }
        ratingBar.setVisibility(rating != 0 ? View.VISIBLE : View.GONE);

        adView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nativeAd.reportAdClickAndOpenLandingPage();
            }
        });

        return adView;
    }


    public void loadInMobiFullAds(final Activity activity, String id) {
        id = id.trim();
        InMobiSdk.init(activity, id);
        mInterstitialAd = new InMobiInterstitial(activity, Long.parseLong(id),
                new InMobiInterstitial.InterstitialAdListener2() {
                    @Override
                    public void onAdLoadFailed(InMobiInterstitial inMobiInterstitial, InMobiAdRequestStatus inMobiAdRequestStatus) {
                        PrintLog.print("1401 fullAds Unable to load interstitial ad (error message: " +
                                inMobiAdRequestStatus.getMessage());
                        //  Toast.makeText(activity,inMobiAdRequestStatus.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onAdReceived(InMobiInterstitial inMobiInterstitial) {
                        PrintLog.print("1401 fullAds onAdReceived");
                    }

                    @Override
                    public void onAdLoadSucceeded(InMobiInterstitial inMobiInterstitial) {
                        PrintLog.print("1401 fullAds onAdLoadSuccessful");

                    }

                    @Override
                    public void onAdRewardActionCompleted(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                        PrintLog.print("1401 fullAds onAdRewardActionCompleted " + map.size());
                    }

                    @Override
                    public void onAdDisplayFailed(InMobiInterstitial inMobiInterstitial) {
                        PrintLog.print("1401 fullAds onAdDisplayFailed " + "FAILED");
                    }

                    @Override
                    public void onAdWillDisplay(InMobiInterstitial inMobiInterstitial) {
                        PrintLog.print("onAdWillDisplay " + inMobiInterstitial);
                    }

                    @Override
                    public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {
                        PrintLog.print("onAdDisplayed " + inMobiInterstitial);
                    }

                    @Override
                    public void onAdInteraction(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
                        PrintLog.print("1401 fullAds onAdInteraction " + inMobiInterstitial);
                    }

                    @Override
                    public void onAdDismissed(InMobiInterstitial inMobiInterstitial) {
                        PrintLog.print("1401 fullAds onAdDismissed " + inMobiInterstitial);
                    }

                    @Override
                    public void onUserLeftApplication(InMobiInterstitial inMobiInterstitial) {
                        PrintLog.print("1401 fullAds onUserWillLeaveApplication " + inMobiInterstitial);
                    }
                });

        if (mInterstitialAd != null && mInterstitialAd.isReady() == false) {
            mInterstitialAd.load();
        }


    }


    public void showInMobiFullAds(Activity activity, String id) {
        id = id.trim();
        if (mInterstitialAd != null && mInterstitialAd.isReady()) {
            mInterstitialAd.show();
            PrintLog.print("1401 fullAds onAdShowSuccessful");
        } else {
            if (adMobAds == null)
                adMobAds = new ANG_AdMobAds(activity);
            adMobAds.admob_showFullAds(activity, Slave.ADMOB_FULL_ID_STATIC);
        }
        loadInMobiFullAds(activity, id);

    }

    public static ANG_InMobiAdUtils getInMobiObject() {
        if (inMobiAdUtils == null)
            inMobiAdUtils = new ANG_InMobiAdUtils();
        return inMobiAdUtils;
    }


 /*   public View getInMobiIconNative(final Activity ctx, final boolean isnativelarge, String id) {
        InMobiSdk.init(ctx, id);
        adsviewlocal = new LinearLayout(ctx);

        LayoutInflater inflater = LayoutInflater.from(ctx);
        adView = (LinearLayout) inflater.inflate(R.layout.ang_inmobi_native_ads,
                adsviewlocal, false);
        adsviewlocal.addView(adView);
        mContainer = (LinearLayout) adView.findViewById(R.id.container);

        nativeAd = new InMobiNative(ctx, Long.parseLong(id),
                new InMobiNative.NativeAdListener() {
                    @Override
                    public void onAdLoadSucceeded(@NonNull InMobiNative inMobiNative) {
                        PrintLog.print("1401 native onAdLoadSucceeded");
                        if (isnativelarge) {
                            nativeView = loadLargeNativeAdsView(inMobiNative, ctx);
                            if (mContainer != null && mContainer.getChildCount() > 0) {
                                mContainer.removeAllViews();
                            }

                            mContainer.addView(nativeView);
                        } else {
                            nativeView = loadMediumNativeAdsView(inMobiNative, ctx);
                            if (mContainer != null && mContainer.getChildCount() > 0) {
                                mContainer.removeAllViews();
                            }
                            mContainer.addView(nativeView);
                        }
                        PrintLog.print("1401 native icon onAdLoadSucceeded");

                    }

                    @Override
                    public void onAdLoadFailed(@NonNull InMobiNative inMobiNative, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                        PrintLog.print("1401 native ad failed to load with error: " +
                                inMobiAdRequestStatus.getMessage());
                    }

                    @Override
                    public void onAdFullScreenDismissed(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdFullScreenWillDisplay(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdFullScreenDisplayed(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onUserWillLeaveApplication(InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdImpressed(@NonNull InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdClicked(@NonNull InMobiNative inMobiNative) {

                    }

                    @Overridequantum@inmobi2018
                    public void onMediaPlaybackComplete(@NonNull InMobiNative inMobiNative) {

                    }

                    @Override
                    public void onAdStatusChanged(@NonNull InMobiNative inMobiNative) {

                    }
                });
        nativeAd.load();
        mNativeAds.add(nativeAd);

        return adsviewlocal;
    }

    private View loadIconNativeAdsView(@NonNull final InMobiNative inMobiNative, Activity ctx) {
        View adView = LayoutInflater.from(ctx).inflate(R.layout.inmobi_ad_icon_layout, null);

        ImageView icon = (ImageView) adView.findViewById(R.id.adIcon);
        TextView title = (TextView) adView.findViewById(R.id.adTitle);
        TextView description = (TextView) adView.findViewById(R.id.adDescription);
        Button action = (Button) adView.findViewById(R.id.adAction);
        FrameLayout content = (FrameLayout) adView.findViewById(R.id.adContent);
        RatingBar ratingBar = (RatingBar) adView.findViewById(R.id.adRating);

        Picasso.with(ctx)
                .load(inMobiNative.getAdIconUrl())
                .into(icon);

        title.setText(inMobiNative.getAdTitle());
        //description.setText(inMobiNative.getAdDescription());
        action.setText(inMobiNative.getAdCtaText());

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        content.addView(inMobiNative.getPrimaryViewOfWidth(content, adsviewlocal, displayMetrics.widthPixels));

        float rating = inMobiNative.getAdRating();
        if (rating != 0) {
            ratingBar.setRating(rating);
        }
        ratingBar.setVisibility(rating != 0 ? View.VISIBLE : View.GONE);

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nativeAd.reportAdClickAndOpenLandingPage();
            }
        });

        return adView;
    }*/
}
