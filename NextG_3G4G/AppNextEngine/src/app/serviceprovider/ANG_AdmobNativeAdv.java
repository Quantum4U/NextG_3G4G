package app.serviceprovider;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;

import java.util.List;

import app.PrintLog;
import app.pnd.adshandler.R;


/**
 * Created by rajeev on 03/10/17.
 */

public class ANG_AdmobNativeAdv {

    private void populateAppInstallAdView(NativeAppInstallAd nativeAppInstallAd,
                                          NativeAppInstallAdView adView) {
        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAppInstallAd.getVideoController();

        // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
        // VideoController will call methods on this object when events occur in the video
        // lifecycle.
        vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
            public void onVideoEnd() {
                // Publishers should allow native ads to complete video playback before refreshing
                // or replacing them with another ad in the same UI location.
               // mRefresh.setEnabled(true);
                //mVideoStatus.setText("Video status: Video playback has ended.");
                super.onVideoEnd();
            }
        });

        adView.setHeadlineView(adView.findViewById(R.id.appinstall_headline));
        adView.setBodyView(adView.findViewById(R.id.appinstall_body));
        adView.setCallToActionView(adView.findViewById(R.id.appinstall_call_to_action));
        adView.setIconView(adView.findViewById(R.id.appinstall_app_icon));
        adView.setPriceView(adView.findViewById(R.id.appinstall_price));
        adView.setStarRatingView(adView.findViewById(R.id.appinstall_stars));
        adView.setStoreView(adView.findViewById(R.id.appinstall_store));

        // Some assets are guaranteed to be in every NativeAppInstallAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAppInstallAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAppInstallAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAppInstallAd.getCallToAction());
        ((ImageView) adView.getIconView()).setImageDrawable(
                nativeAppInstallAd.getIcon().getDrawable());

        MediaView mediaView = (MediaView) adView.findViewById(R.id.appinstall_media);
        ImageView mainImageView =(ImageView) adView.findViewById(R.id.appinstall_image);


        // Apps can check the VideoController's hasVideoContent property to determine if the
        // NativeAppInstallAd has a video asset.
        if (vc.hasVideoContent()) {
            adView.setMediaView(mediaView);
            mainImageView.setVisibility(View.GONE);
//            mVideoStatus.setText(String.format(Locale.getDefault(),
//                    "Video status: Ad contains a %.2f:1 video asset.",
//                    vc.getAspectRatio()));
        } else {
            adView.setImageView(mainImageView);
            mediaView.setVisibility(View.GONE);

            // At least one image is guaranteed.
            List<NativeAd.Image> images = nativeAppInstallAd.getImages();
            mainImageView.setImageDrawable(images.get(0).getDrawable());

//            mRefresh.setEnabled(true);
//            mVideoStatus.setText("Video status: Ad does not contain a video asset.");
        }

        // These assets aren't guaranteed to be in every NativeAppInstallAd, so it's important to
        // check before trying to display them.
        if (nativeAppInstallAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAppInstallAd.getPrice());
        }

        if (nativeAppInstallAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAppInstallAd.getStore());
        }

        if (nativeAppInstallAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAppInstallAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAppInstallAd);
    }

    /**
     * Populates a {@link NativeContentAdView} object with data from a given
     * {@link NativeContentAd}.
     *
     * @param nativeContentAd the object containing the ad's assets
     * @param adView          the view to be populated
     */
    private void populateContentAdView(NativeContentAd nativeContentAd,
                                       NativeContentAdView adView) {
//        mVideoStatus.setText("Video status: Ad does not contain a video asset.");
//        mRefresh.setEnabled(true);

        adView.setHeadlineView(adView.findViewById(R.id.contentad_headline));
        adView.setImageView(adView.findViewById(R.id.contentad_image));
        adView.setBodyView(adView.findViewById(R.id.contentad_body));
        adView.setCallToActionView(adView.findViewById(R.id.contentad_call_to_action));
        adView.setLogoView(adView.findViewById(R.id.contentad_logo));
        adView.setAdvertiserView(adView.findViewById(R.id.contentad_advertiser));

        // Some assets are guaranteed to be in every NativeContentAd.
        ((TextView) adView.getHeadlineView()).setText(nativeContentAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeContentAd.getBody());
        ((TextView) adView.getCallToActionView()).setText(nativeContentAd.getCallToAction());
        ((TextView) adView.getAdvertiserView()).setText(nativeContentAd.getAdvertiser());

        List<NativeAd.Image> images = nativeContentAd.getImages();

        if (images.size() > 0) {
            ((ImageView) adView.getImageView()).setImageDrawable(images.get(0).getDrawable());
        }

        // Some aren't guaranteed, however, and should be checked.
        NativeAd.Image logoImage = nativeContentAd.getLogo();

        if (logoImage == null) {
            adView.getLogoView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getLogoView()).setImageDrawable(logoImage.getDrawable());
            adView.getLogoView().setVisibility(View.VISIBLE);
        }

        // Assign native ad object to the native view.
        adView.setNativeAd(nativeContentAd);
    }

    /**
     * Creates a request for a new native ad based on the boolean parameters and calls the
     * corresponding "populate" method when one is successfully returned.
     *

     */
    public View getNativeAdvancedAds(final Activity context,  String AdsID,boolean requestAppInstallAds2) {
        AdsID = AdsID.trim();
        final LinearLayout linearLayout = new LinearLayout(context);



//        if (!requestAppInstallAds && !requestContentAds) {
//            Toast.makeText(context, "At least one ad format must be checked to request an ad.",
//                    Toast.LENGTH_SHORT).show();
//            return linearLayout;
//        }

        // mRefresh.setEnabled(false);
        PrintLog.print("requestAppInstallAds "+requestAppInstallAds2 +" AdsID "+AdsID);
        AdLoader.Builder builder = new AdLoader.Builder(context, AdsID);

        if (requestAppInstallAds2) {
            builder.forAppInstallAd(new NativeAppInstallAd.OnAppInstallAdLoadedListener() {
                @Override
                public void onAppInstallAdLoaded(NativeAppInstallAd ad) {
//                    FrameLayout frameLayout =
//                            context.findViewById(R.id.fl_adplaceholder);
//                    NativeAppInstallAdView adView = (NativeAppInstallAdView) context.getLayoutInflater()
//                            .inflate(R.layout.ang_ad_app_install, null);


                    NativeAppInstallAdView adView = (NativeAppInstallAdView) context.getLayoutInflater().inflate(R.layout.ang_ad_app_install, linearLayout, false);
                    // frameLayout.addView(adView);


                    populateAppInstallAdView(ad, adView);
                    linearLayout.removeAllViews();
                    linearLayout.addView(adView);
                }
            });
       }

         if (requestAppInstallAds2 == false) {
            builder.forContentAd(new NativeContentAd.OnContentAdLoadedListener() {
                @Override
                public void onContentAdLoaded(NativeContentAd ad) {
//                    FrameLayout frameLayout =
//                            findViewById(R.id.fl_adplaceholder);
                    NativeContentAdView adView = (NativeContentAdView) context.getLayoutInflater()
                            .inflate(R.layout.ang_ad_content, null);
                    populateContentAdView(ad, adView);
                    linearLayout.removeAllViews();
                    linearLayout.addView(adView);
                }
            });
             }

            VideoOptions videoOptions = new VideoOptions.Builder()
                    .setStartMuted(false)
                    .build();

            NativeAdOptions adOptions = new NativeAdOptions.Builder()
                    .setVideoOptions(videoOptions)
                    .build();

            builder.withNativeAdOptions(adOptions);

            AdLoader adLoader = builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // mRefresh.setEnabled(true);
//                Toast.makeText(MainActivity.this, "Failed to load native ad: "
//                        + errorCode, Toast.LENGTH_SHORT).show();
                }
            }).build();

            adLoader.loadAd(new AdRequest.Builder().build());

            // mVideoStatus.setText("");
            return linearLayout;
        }



}
