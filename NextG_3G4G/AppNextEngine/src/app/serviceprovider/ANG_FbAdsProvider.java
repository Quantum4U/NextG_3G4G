package app.serviceprovider;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;

import java.util.ArrayList;
import java.util.List;

import app.PrintLog;
import app.pnd.adshandler.R;
import app.server.v2.Slave;


/**
 * Created by sony on 06-09-2017.
 */
public class ANG_FbAdsProvider {
//    public static String NATIVE_MEDIUM_ID_STATIC = "ca-app-pub-3451337100490595/6299996657";
//    public static String NATIVE_LARGE_ID_STATIC = "ca-app-pub-3451337100490595/1977608266";


    private AdView adView2;
    private NativeAd nativeAd;
    private LinearLayout adView;
    public InterstitialAd interstitialAd;

    LinearLayout adsTemp;
    View view;
    ANG_AdMobAds adMobAds;


    static ANG_FbAdsProvider fbAdsProvider = null;


    LinearLayout adsviewlocal;

    public View getFBBanner(final Activity ctx, String ads_ID) {
        ads_ID = ads_ID.trim();
        // Instantiate an AdView view
        adView2 = new AdView(ctx, ads_ID, AdSize.BANNER_HEIGHT_50);
        adsviewlocal = new LinearLayout(ctx);
        adView2.setAdListener(new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                if (adMobAds == null)
                    adMobAds = new ANG_AdMobAds(ctx);

                adsviewlocal.addView(adMobAds.admob_GetBannerAds(ctx, Slave.ADMOB_BANNER_ID_STATIC));

            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

        // Add the ad view to your activity layout

        adsviewlocal.addView(adView2);
        adView2.loadAd();
        return adsviewlocal;
    }


    LinearLayout nativeAdContainer;


    public View getNativeAds(final boolean isnativelarge, String adsID, final Activity ctx, final int background_colour, final int header_text_Color, final int footer_text_Color, final int button_baclground_Color, final int button_text_clour) {
       adsID = adsID.trim();
        try {
            PrintLog.print(" NativeadsQ new Request from  " + adsID);
            //adsID="2018009395097025_2022404887990809";
            PrintLog.print("log check 1234 pos 3");
            nativeAd = new NativeAd(ctx, adsID);
            nativeAdContainer = new LinearLayout(ctx);
            nativeAd.setAdListener(new AdListener() {

                @Override
                public void onError(Ad ad, AdError error) {
                    PrintLog.print(" NativeadsQ Error " + error.getErrorMessage());
                    loadAdmobNative(isnativelarge, ctx);
                }

                @Override
                public void onAdLoaded(Ad ad) {

                    // Add the Ad view into the ad container.
                    // nativeAdContainer = (LinearLayout) getView().findViewById(R.id.native_ad_container);

                    LayoutInflater inflater = LayoutInflater.from(ctx);
                    if (isnativelarge)
                        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout,
                                nativeAdContainer, false);
                    else
                        adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout_small,
                                nativeAdContainer, false);
                    nativeAdContainer.addView(adView);
                    PrintLog.print("log check 1234 pos 4");

                    // Create native UI using the ad metadata.
                    ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.native_ad_icon);
                    TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
                    MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
                    TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id
                            .native_ad_social_context);
                    TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
                    Button nativeAdCallToAction = (Button) adView.findViewById(R.id
                            .native_ad_call_to_action);

                    // Set the Text.
                    nativeAdTitle.setText(nativeAd.getAdTitle());
                    nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                    nativeAdBody.setText(nativeAd.getAdBody());
                    nativeAdCallToAction.setText(nativeAd.getAdCallToAction());

                    // Download and display the ad icon.
                    PrintLog.print("FBAds Adding FB ANG_Ads Logo");
                    NativeAd.Image adIcon = nativeAd.getAdIcon();
                    NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

                    // Download and display the cover image.
                    nativeAdMedia.setNativeAd(nativeAd);

                    PrintLog.print("log check 1234 pos 5");                    // Add the AdChoices icon
                    try {
                        LinearLayout adChoicesContainer = (LinearLayout) ctx.findViewById(R.id
                                .ad_choices_container);
                        AdChoicesView adChoicesView = new AdChoicesView(ctx, nativeAd, true);
                        adChoicesContainer.addView(adChoicesView);


                    } catch (Exception e) {
                        PrintLog.print("log check 1234 pos exc");
                        PrintLog.print(" Native Error error in adchoice container");
                    }

                    // Register the Title and CTA button to listen for clicks.
                    List<View> clickableViews = new ArrayList<>();
                    clickableViews.add(nativeAdTitle);
                    clickableViews.add(nativeAdCallToAction);
                    nativeAd.registerViewForInteraction(nativeAdContainer, clickableViews);
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // On logging impression callback
                }
            });

            // Request an ad
            nativeAd.loadAd();
            PrintLog.print("log check 1234 pos final");
            return nativeAdContainer;
        } catch (Exception e) {
            if (isnativelarge)
                return new ANG_AdmobNativeAdv().getNativeAdvancedAds(ctx, Slave.ADMOB_NATIVE_LARGE_ID_STATIC, true);

            else
                return new ANG_AdmobNativeAdv().getNativeAdvancedAds(ctx, Slave.ADMOB_NATIVE_MEDIUM_ID_STATIC, false);
        }
    }


    public void loadAdmobNative(boolean isnativelarge, Context ctx) {
        if (nativeAdContainer != null && nativeAdContainer.getChildCount() > 0)
            nativeAdContainer.removeAllViews();
        // adsviewlocal.removeAllViews();
        if (adMobAds == null)
            adMobAds = new ANG_AdMobAds(ctx);
        if (isnativelarge)
            nativeAdContainer.addView(new ANG_AdmobNativeAdv().getNativeAdvancedAds((Activity) ctx, Slave.ADMOB_NATIVE_LARGE_ID_STATIC, true));
        else
            nativeAdContainer.addView(new ANG_AdmobNativeAdv().getNativeAdvancedAds((Activity) ctx, Slave.ADMOB_NATIVE_LARGE_ID_STATIC, false));
        PrintLog.print("FBAds.onError admob loaded now ");
    }


    public void loadFBFullAds(String id, Activity activity) {
        id = id.trim();
//if(interstitialAd==null)
        interstitialAd = new InterstitialAd(activity, id);


        // Toast.makeText(activity,"full ads  obj creation  "+interstitialAd+" id = "+id,Toast.LENGTH_LONG).show();
        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial displayed callback
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                PrintLog.print("MainActivity.onError " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Show the ad when it's done loading.
                //   interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        });

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        if (interstitialAd != null && interstitialAd.isAdLoaded() == false) {
            // Toast.makeText(activity,"Requesting new full ads "+interstitialAd.isAdLoaded(),Toast.LENGTH_LONG).show();
            interstitialAd.loadAd();
        }
    }


    public void showFBFullAds(String id, Activity activity) {
        id = id.trim();
//        if (interstitialAd!=null )
//            Toast.makeText(activity,"full ads  is loaded "+interstitialAd.isAdLoaded(),Toast.LENGTH_LONG).show();
//        else
//            Toast.makeText(activity,"Object is null ",Toast.LENGTH_LONG).show();


        if (interstitialAd != null && interstitialAd.isAdLoaded()) {
            interstitialAd.show();
        } else {
            if (adMobAds == null)
                adMobAds = new ANG_AdMobAds(activity);
            adMobAds.admob_showFullAds(activity, Slave.ADMOB_FULL_ID_STATIC);
        }

        loadFBFullAds(id, activity);

    }


    public static ANG_FbAdsProvider getFbObject() {
        if (fbAdsProvider == null)
            fbAdsProvider = new ANG_FbAdsProvider();
        return fbAdsProvider;

    }


}
