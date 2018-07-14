package app.adshandler;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import app.PrintLog;
import app.inapp.BillingListActivity;
import app.inapp.BillingPreference;
import app.pnd.adshandler.R;
import app.server.v2.DataHubConstant;
import app.server.v2.DataHubPreference;
import app.server.v2.Slave;
import app.serviceprovider.ANG_AdMobAds;
import app.serviceprovider.ANG_AdmobNativeAdv;
import app.serviceprovider.ANG_FbAdsProvider;
import app.serviceprovider.ANG_InHouseAds;
import app.serviceprovider.ANG_InMobiAdUtils;
import app.serviceprovider.ANG_SmaatoAdUtils;
import app.serviceprovider.ANG_StartupAdsProvider;
import app.serviceprovider.Utils;
import app.ui.ANG_AboutActivity;

public class AHandler {


    public static ANG_AdMobAds adMobAds = null;

    private ANG_StartupAdsProvider startupAdsProvider = null;
    private ANG_PromptHander promptHander;


    public AHandler() {
        promptHander = new ANG_PromptHander();
    }


    public void setFulladsCount(Activity activity, int data) {
        Utils ut = new Utils();
        int value = ut.get_fullservicecount(activity) + 1;
        if (data >= 0)
            value = data;
        PrintLog.print("Full Nav Adder setter " + value);
        ut.set_fullservicecount(activity, value);
    }


    public void showAboutUs(Activity mContext) {
        mContext.startActivity(new Intent(mContext, ANG_AboutActivity.class));
    }


    public int getFullAdsCount(Activity cActivity) {
        int data = new Utils().get_fullservicecount(cActivity);
        PrintLog.print("Full Nav Adder getter " + data);
        return data;
    }


    public void setFulladsCount_start(Activity activity, int data) {
        Utils ut = new Utils();
        int value = ut.get_fullservicecount_start(activity) + 1;
        if (data >= 0)
            value = data;
        PrintLog.print("Full Nav Start Adder setter " + value);
        ut.set_fullservicecount_start(activity, value);
    }


    public int getFullAdsCount_start(Activity cActivity) {
        int data = new Utils().get_fullservicecount_start(cActivity);
        PrintLog.print("Full Nav Start getter " + data);
        return data;
    }


    public void setFulladsCount_exit(Activity activity, int data) {
        Utils ut = new Utils();
        int value = ut.get_fullservicecount_exit(activity) + 1;
        if (data >= 0)
            value = data;
        PrintLog.print("Full Nav Exit Adder setter " + value);
        ut.set_fullservicecount_exit(activity, value);
    }


    public int getFullAdsCount_exit(Activity cActivity) {
        int data = new Utils().get_fullservicecount_exit(cActivity);
        PrintLog.print("Full Nav Exit getter " + data);
        return data;
    }


    public View getBannerHeader(Activity context) {
        if (!Slave.hasPurchased(context)) {
            try {
                if (getDaysDiff(context) >= getStringtoInt(Slave.TOP_BANNER_start_date)) {
                    if (Slave.TOP_BANNER_provider_id.equals(Slave.Provider_Admob_Banner)) {
                        return getAdmobBanner(Slave.TOP_BANNER_ad_id, context);
                    } else if (Slave.TOP_BANNER_provider_id.equals(Slave.Provider_Startapp_Banner)) {
                        return getstartAppObj(context, Slave.TOP_BANNER_ad_id).getBannerAds(context);
                    } else if (Slave.TOP_BANNER_provider_id.equals(Slave.Provider_Facebook_Banner)) {
                        return new ANG_FbAdsProvider().getFBBanner(context, Slave.TOP_BANNER_ad_id);
                    }

//                else if (Slave.TOP_BANNER_provider_id.equals(Slave.Provider_Admob_Native_Small)) {
//                    return showNativesmall(context);
//                }
                    else if (Slave.TOP_BANNER_provider_id.equals(Slave.Provider_Inhouse_Banner)) {
                        return getInhouseBanner(Slave.TOP_BANNER_src, Slave.TOP_BANNER_clicklink, context, ANG_InHouseAds.TYPE_NATIVE_SMALL);
                    } else if (Slave.TOP_BANNER_provider_id.equals(Slave.Provider_Startapp_Banner)) {
                        return getstartAppObj(context, Slave.TOP_BANNER_ad_id).getBannerAds(context);
                    } else if (Slave.TOP_BANNER_provider_id.equals(Slave.Provider_InMobi_BANNER)) {
                        return ANG_InMobiAdUtils.getInMobiObject().getInMobiBanner(context, Slave.TOP_BANNER_ad_id);
                    } else if (Slave.TOP_BANNER_provider_id.equals(Slave.Provider_Smaato_BANNER)) {
                        return ANG_SmaatoAdUtils.getSmaatoObj().getSmaatoBanner(context, Slave.TOP_BANNER_ad_id);
                    } else {
                        return getAdmobBanner(Slave.ADMOB_BANNER_ID_STATIC, context);
                    }
                } else
                    return new LinearLayout(context);

            } catch (Exception e) {
                return getAdmobBanner(Slave.ADMOB_BANNER_ID_STATIC, context);

            }
        } else {
            return getPremiumView(context);
        }
    }

    private View getPremiumView(Activity mActivity) {
        LinearLayout mLinearLayout = new LinearLayout(mActivity);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        mLinearLayout.setLayoutParams(layoutParams);
        return mLinearLayout;
    }

    public View getBannerFooter(Activity context) {
        if (!Slave.hasPurchased(context)) {
            try {
                if (getDaysDiff(context) >= getStringtoInt(Slave.BOTTOM_BANNER_start_date)) {
                    if (Slave.BOTTOM_BANNER_provider_id.equals(Slave.Provider_Admob_Banner)) {
                        return getAdmobBanner(Slave.BOTTOM_BANNER_ad_id, context);
                    } else if (Slave.BOTTOM_BANNER_provider_id.equals(Slave.Provider_Startapp_Banner)) {
                        return getstartAppObj(context, Slave.BOTTOM_BANNER_ad_id).getBannerAds(context);
                    } else if (Slave.BOTTOM_BANNER_provider_id.equals(Slave.Provider_Facebook_Banner)) {
                        return new ANG_FbAdsProvider().getFBBanner(context, Slave.BOTTOM_BANNER_ad_id);
                    }

//                else if (Slave.BOTTOM_BANNER_provider_id.equals(Slave.Provider_Admob_Native_Small)) {
//                    return showNativesmall(context);
//                }

                    else if (Slave.BOTTOM_BANNER_provider_id.equals(Slave.Provider_Inhouse_Banner)) {
                        return getInhouseBanner(Slave.BOTTOM_BANNER_src, Slave.BOTTOM_BANNER_clicklink, context, ANG_InHouseAds.TYPE_NATIVE_SMALL);
                    } else if (Slave.BOTTOM_BANNER_provider_id.equals(Slave.Provider_Startapp_Banner)) {
                        return getstartAppObj(context, Slave.TOP_BANNER_ad_id).getBannerAds(context);

                    } else if (Slave.BOTTOM_BANNER_provider_id.equals(Slave.Provider_InMobi_BANNER)) {
                        return ANG_InMobiAdUtils.getInMobiObject().getInMobiBanner(context, Slave.TOP_BANNER_ad_id);

                    } else if (Slave.BOTTOM_BANNER_provider_id.equals(Slave.Provider_Smaato_BANNER)) {
                        return ANG_SmaatoAdUtils.getSmaatoObj().getSmaatoBanner(context, Slave.TOP_BANNER_ad_id);
                    } else {
                        return getAdmobBanner(Slave.ADMOB_BANNER_ID_STATIC, context);
                    }
                } else
                    return new LinearLayout(context);

            } catch (Exception e) {
                return getAdmobBanner(Slave.ADMOB_BANNER_ID_STATIC, context);

            }
        } else
            return getPremiumView(context);
    }


    public View showNativeMedium(Activity context) {
        if (!Slave.hasPurchased(context)) {

            try {
                PrintLog.print("log check 1234 pos 1 " + Slave.NATIVE_MEDIUM_provider_id);
                if (getDaysDiff(context) >= getStringtoInt(Slave.NATIVE_MEDIUM_start_date)) {

                    if (Slave.NATIVE_MEDIUM_call_native.equalsIgnoreCase(Slave.NATIVE_TYPE_MEDIUM)) {
                        if (Slave.NATIVE_MEDIUM_provider_id.equalsIgnoreCase(Slave.Provider_Admob_Native_Medium)) {

//
//                        if (adMobAds == null)
//                            adMobAds = new ANG_AdMobAds(context);
//                        return adMobAds.admob_GetNativeMedium(context, Slave.NATIVE_MEDIUM_ad_id);

                            return new ANG_AdmobNativeAdv().getNativeAdvancedAds(context, Slave.NATIVE_MEDIUM_ad_id, false);


                        } else if (Slave.NATIVE_MEDIUM_provider_id.equalsIgnoreCase(Slave.Provider_Facebook_Native_Medium)) {
                            //return new ANG_FbAdsProvider().getNativetemplate(context, Slave.NATIVE_MEDIUM_ad_id, 2, 0, 0, 0);
                            PrintLog.print("log check 1234 pos 2");
                            return new ANG_FbAdsProvider().getNativeAds(false, Slave.NATIVE_MEDIUM_ad_id, context, 0, 0, 0, 0, 0);
                        } else if (Slave.NATIVE_MEDIUM_provider_id.equals(Slave.Provider_Inhouse_Medium)) {
                            return getInhouseBanner(Slave.NATIVE_MEDIUM_src, Slave.NATIVE_MEDIUM_clicklink, context, ANG_InHouseAds.TYPE_NATIVE_MEDIUM);

                        } else if (Slave.NATIVE_MEDIUM_provider_id.equals(Slave.Provider_Startapp_Native_Medium)) {
                            return getstartAppObj(context, Slave.NATIVE_MEDIUM_ad_id).showNativeMedium(context, Slave.NATIVE_MEDIUM_ad_id);

                        } else if (Slave.NATIVE_MEDIUM_provider_id.equals(Slave.Provider_InMobi_Native_Medium)) {
                            PrintLog.print("log check 1234 pos 3");
                            return ANG_InMobiAdUtils.getInMobiObject().getInMobiNative(context, false, Slave.NATIVE_MEDIUM_ad_id);

                        } else if (Slave.NATIVE_MEDIUM_provider_id.equals(Slave.Provider_Smaato_Native_Medium)) {
                            PrintLog.print("log check 1234 pos 4");
                            return ANG_SmaatoAdUtils.getSmaatoObj().getSmaatoMediumNative(context, Slave.NATIVE_MEDIUM_ad_id);

                        } else {
                            return new LinearLayout(context);
                        }

                    } else if (Slave.NATIVE_MEDIUM_call_native.equalsIgnoreCase(Slave.NATIVE_TYPE_LARGE)) {
                        return showNativeLarge(context);

                    } else if (Slave.NATIVE_MEDIUM_call_native.equalsIgnoreCase(Slave.TYPE_TOP_BANNER)) {
                        return getBannerHeader(context);

                    } else {
                        if (adMobAds == null)
                            adMobAds = new ANG_AdMobAds(context);
                        return adMobAds.admob_GetNativeMedium(context, Slave.ADMOB_NATIVE_MEDIUM_ID_STATIC);
                    }
                } else
                    return new LinearLayout(context);

            } catch (Exception e) {
                if (adMobAds == null)
                    adMobAds = new ANG_AdMobAds(context);
                return adMobAds.admob_GetNativeMedium(context, Slave.ADMOB_NATIVE_MEDIUM_ID_STATIC);
            }
        } else {
            return getPremiumView(context);
        }
    }

    public View showNativeLarge(Activity context) {
        if (!Slave.hasPurchased(context)) {
            try {

                if (getDaysDiff(context) >= getStringtoInt(Slave.NATIVE_LARGE_start_date)) {

                    if (Slave.NATIVE_LARGE_call_native.equalsIgnoreCase(Slave.NATIVE_TYPE_LARGE)) {
                        if (Slave.NATIVE_LARGE_provider_id.equalsIgnoreCase(Slave.Provider_Admob_Native_Large)) {


//                        if (adMobAds == null)
//                            adMobAds = new ANG_AdMobAds(context);
//                        return adMobAds.admob_GetNativeLarge(context, Slave.NATIVE_LARGE_ad_id);

                            return new ANG_AdmobNativeAdv().getNativeAdvancedAds(context, Slave.NATIVE_LARGE_ad_id, true);
                        }


                        if (Slave.NATIVE_LARGE_provider_id.equalsIgnoreCase(Slave.Provider_Facebook_Native_Large)) {
                            return new ANG_FbAdsProvider().getNativeAds(true, Slave.NATIVE_LARGE_ad_id, context, 0, 0, 0, 0, 0);

                            //return new ANG_FbAdsProvider().getNativetemplate(context, Slave.NATIVE_MEDIUM_ad_id, 3, 0, 0, 0);
                        } else if (Slave.NATIVE_LARGE_provider_id.equals(Slave.Provider_Inhouse_Large)) {
                            return getInhouseBanner(Slave.NATIVE_LARGE_src, Slave.NATIVE_LARGE_clicklink, context, ANG_InHouseAds.TYPE_NATIVE_LARGE);

                        } else if (Slave.NATIVE_LARGE_provider_id.equals(Slave.Provider_Startapp_Native_Large)) {
                            return getstartAppObj(context, Slave.NATIVE_LARGE_ad_id).showNativeLarge(context, Slave.NATIVE_LARGE_ad_id);

                        } else if (Slave.NATIVE_LARGE_provider_id.equals(Slave.Provider_InMobi_Native_Large)) {
                            return ANG_InMobiAdUtils.getInMobiObject().getInMobiNativeLarge(context, true, Slave.NATIVE_LARGE_ad_id);

                        } else if (Slave.NATIVE_LARGE_provider_id.equals(Slave.Provider_Smaato_Native_Large)) {
                            return ANG_SmaatoAdUtils.getSmaatoObj().getSmaatoLargeNative(context, Slave.NATIVE_LARGE_ad_id);

                        } else {
                            return new LinearLayout(context);
                        }


                    } else if (Slave.NATIVE_LARGE_call_native.equalsIgnoreCase(Slave.NATIVE_TYPE_MEDIUM)) {
                        return showNativeMedium(context);

//                else if (Slave.NATIVE_MEDIUM_call_native.equalsIgnoreCase(Slave.NATIVE_TYPE_SMALL))
//                    return showNativesmall(context);


                    } else if (Slave.NATIVE_LARGE_call_native.equalsIgnoreCase(Slave.TYPE_TOP_BANNER)) {
                        PrintLog.print("AHandler.showNativeLarge " + Slave.TOP_BANNER_call_native);
                        return getBannerHeader(context);


                    } else {
                        if (adMobAds == null)
                            adMobAds = new ANG_AdMobAds(context);
                        return adMobAds.admob_GetNativeLarge(context, Slave.ADMOB_NATIVE_LARGE_ID_STATIC);
                    }
                } else
                    return new LinearLayout(context);

            } catch (Exception e) {
                if (adMobAds == null)
                    adMobAds = new ANG_AdMobAds(context);
                return adMobAds.admob_GetNativeLarge(context, Slave.ADMOB_NATIVE_LARGE_ID_STATIC);
            }
        } else {
            return getPremiumView(context);
        }
    }

    public void showFullAds(Activity context, boolean is_force_ad) {
        //Slave.FULL_ADS_nevigation="5";
        if (!Slave.hasPurchased(context)) {

            try {
                if (getDaysDiff(context) >= getStringtoInt(Slave.FULL_ADS_start_date)) {

                    setFulladsCount(context, -1);
                    if (Slave.FULL_ADS_provider_id.equals(Slave.Provider_Admob_FullAds)) {

                        if (is_force_ad) {

                            displayAdmobFull(context, Slave.FULL_ADS_ad_id);

                        } else {
                            PrintLog.print("Full ANG_Ads Navigation COunt and is force is " + Slave.FULL_ADS_nevigation + " and " + is_force_ad);
                            if (getFullAdsCount(context) >= getStringtoInt(Slave.FULL_ADS_nevigation)) {
                                setFulladsCount(context, 0);
                                displayAdmobFull(context, Slave.FULL_ADS_ad_id);
                            }
                        }
                    } else if (Slave.FULL_ADS_provider_id.equals(Slave.Provider_Startapp_FullAds)) {

                        if (is_force_ad) {
                            getstartAppObj(context, Slave.FULL_ADS_ad_id).showfullAds(context, getStringtoInt(Slave.FULL_ADS_ad_id));
                        } else {
                            if (getFullAdsCount(context) >= getStringtoInt(Slave.FULL_ADS_nevigation)) {
                                setFulladsCount(context, 0);
                                getstartAppObj(context, Slave.FULL_ADS_ad_id).showfullAds(context, getStringtoInt(Slave.FULL_ADS_ad_id));

                            }

                        }
                    } else if (Slave.FULL_ADS_provider_id.equals(Slave.Provider_Facebook_Full_Page_Ads)) {
                        if (is_force_ad) {

                            ANG_FbAdsProvider.getFbObject().loadFBFullAds(Slave.FULL_ADS_ad_id, context);
                            ANG_FbAdsProvider.getFbObject().showFBFullAds(Slave.FULL_ADS_ad_id, context);

                        } else {

                            if (getFullAdsCount(context) >= getStringtoInt(Slave.FULL_ADS_nevigation)) {
                                setFulladsCount(context, 0);
                                ANG_FbAdsProvider.getFbObject().showFBFullAds(Slave.FULL_ADS_ad_id, context);


                            }


                        }

                    } else if (Slave.FULL_ADS_provider_id.equals(Slave.Provider_Inhouse_FullAds)) {
                        //return getInhouseBanner(Slave.NATIVE_LARGE_src,Slave.NATIVE_LARGE_clicklink,context,ANG_InHouseAds.TYPE_NATIVE_LARGE);

                        PrintLog.print("Slave.Provider_Inhouse_FullAds " + Slave.FULL_ADS_nevigation + " is force " + is_force_ad);

                        if (is_force_ad) {

                            Intent intent = new Intent(context, FullPagePromo.class);


                            intent.putExtra("src", Slave.FULL_ADS_src);
                            intent.putExtra("link", Slave.FULL_ADS_clicklink);


                            context.startActivity(intent);
                        } else {
                            if (getFullAdsCount(context) >= getStringtoInt(Slave.FULL_ADS_nevigation)) {

                                Intent intent = new Intent(context, FullPagePromo.class);
                                intent.putExtra("src", Slave.FULL_ADS_src);
                                intent.putExtra("link", Slave.FULL_ADS_clicklink);
                                context.startActivity(intent);
                                setFulladsCount(context, 0);
                            }


                        }

                    } else if (Slave.FULL_ADS_provider_id.equals(Slave.Provider_InMobi_Full_Page_Ads)) {
                        if (is_force_ad) {

                            ANG_InMobiAdUtils.getInMobiObject().loadInMobiFullAds(context, Slave.FULL_ADS_ad_id);
                            ANG_InMobiAdUtils.getInMobiObject().showInMobiFullAds(context, Slave.FULL_ADS_ad_id);

                        } else {

                            if (getFullAdsCount(context) >= getStringtoInt(Slave.FULL_ADS_nevigation)) {
                                setFulladsCount(context, 0);
                                ANG_InMobiAdUtils.getInMobiObject().showInMobiFullAds(context, Slave.FULL_ADS_ad_id);


                            }


                        }

                    } else if (Slave.FULL_ADS_provider_id.equals(Slave.Provider_Smaato_Full_Page_Ads)) {
                        if (is_force_ad) {

                            ANG_SmaatoAdUtils.getSmaatoObj().loadSmaatoFullAds(context, Slave.FULL_ADS_ad_id);
                            ANG_SmaatoAdUtils.getSmaatoObj().showSmaatoFullAds(context, Slave.FULL_ADS_ad_id);

                        } else {

                            if (getFullAdsCount(context) >= getStringtoInt(Slave.FULL_ADS_nevigation)) {
                                setFulladsCount(context, 0);
                                ANG_SmaatoAdUtils.getSmaatoObj().showSmaatoFullAds(context, Slave.FULL_ADS_ad_id);


                            }


                        }

                    } else {
                        if (getFullAdsCount(context) >= getStringtoInt(Slave.FULL_ADS_nevigation)) {
                            setFulladsCount(context, 0);
                            displayAdmobFull(context, Slave.ADMOB_FULL_ID_STATIC);
                        }
                    }

                }
            } catch (Exception e) {
                PrintLog.print("Full ANG_Ads Navigation COunt and is force exppp ");
                if (getFullAdsCount(context) >= getStringtoInt(Slave.FULL_ADS_nevigation)) {
                    setFulladsCount(context, 0);
                    displayAdmobFull(context, Slave.FULL_ADS_nevigation);
                }

            }
            //ANG_Ads.printdata(" banner full  ads stage 11 "+DataHub.FULLADS_START_ID);
        }
    }

    public void showFullAdsLaunch(Activity context, boolean is_force_ad) {

        try {
            if (getDaysDiff(context) >= getStringtoInt(Slave.LAUNCH_FULL_ADS_start_date)) {

                setFulladsCount_start(context, -1);
                PrintLog.print("EngineV2 Start ANG_Ads launch " + Slave.LAUNCH_FULL_ADS_provider_id);
                if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Admob_FullAds)) {

                    if (is_force_ad) {

                        displayAdmobFull(context, Slave.LAUNCH_FULL_ADS_ad_id);

                    } else {
                        if (getFullAdsCount_start(context) >= getStringtoInt(Slave.LAUNCH_FULL_ADS_nevigation)) {
                            setFulladsCount_start(context, 0);
                            displayAdmobFull(context, Slave.LAUNCH_FULL_ADS_ad_id);
                            PrintLog.print("EngineV2 Start ANG_Ads showing >> with id " + Slave.LAUNCH_FULL_ADS_ad_id);
                        }
                    }
                } else if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Startapp_FullAds)) {
                    PrintLog.print("Startapp test case" + " " + Slave.Provider_Startapp_FullAds + " " + Slave.LAUNCH_FULL_ADS_nevigation + " " + getFullAdsCount_start(context));
                    if (is_force_ad) {
                        getstartAppObj(context, Slave.LAUNCH_FULL_ADS_ad_id).showfullAds(context, getStringtoInt(Slave.LAUNCH_FULL_ADS_ad_id));
                    } else {
                        if (getFullAdsCount_start(context) >= getStringtoInt(Slave.LAUNCH_FULL_ADS_nevigation)) {
                            setFulladsCount_start(context, 0);
                            getstartAppObj(context, Slave.LAUNCH_FULL_ADS_ad_id).showfullAds(context, getStringtoInt(Slave.LAUNCH_FULL_ADS_ad_id));

                        }

                    }
                } else if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Facebook_Full_Page_Ads)) {
                    if (is_force_ad) {

                        ANG_FbAdsProvider.getFbObject().loadFBFullAds(Slave.LAUNCH_FULL_ADS_ad_id, context);
                        ANG_FbAdsProvider.getFbObject().showFBFullAds(Slave.LAUNCH_FULL_ADS_ad_id, context);

                    } else {

                        if (getFullAdsCount_start(context) >= getStringtoInt(Slave.LAUNCH_FULL_ADS_nevigation)) {
                            setFulladsCount_start(context, 0);
                            ANG_FbAdsProvider.getFbObject().showFBFullAds(Slave.LAUNCH_FULL_ADS_ad_id, context);


                        }


                    }

                } else if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Inhouse_FullAds)) {
                    //return getInhouseBanner(Slave.NATIVE_LARGE_src,Slave.NATIVE_LARGE_clicklink,context,ANG_InHouseAds.TYPE_NATIVE_LARGE);

                    PrintLog.print("EngineV2 Start ANG_Ads launch 22222 " + Slave.LAUNCH_FULL_ADS_provider_id);
                    if (is_force_ad) {

                        Intent intent = new Intent(context, FullPagePromo.class);


                        intent.putExtra("src", Slave.LAUNCH_FULL_ADS_src);
                        intent.putExtra("link", Slave.LAUNCH_FULL_ADS_clicklink);
                        context.startActivity(intent);

                    } else {
                        if (getFullAdsCount_start(context) >= getStringtoInt(Slave.LAUNCH_FULL_ADS_nevigation)) {

                            Intent intent = new Intent(context, FullPagePromo.class);
                            intent.putExtra("src", Slave.LAUNCH_FULL_ADS_src);
                            intent.putExtra("link", Slave.LAUNCH_FULL_ADS_clicklink);
                            context.startActivity(intent);
                            setFulladsCount_start(context, 0);

                        }
                    }


                } else if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_InMobi_Full_Page_Ads)) {
                    if (is_force_ad) {

                        ANG_InMobiAdUtils.getInMobiObject().loadInMobiFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);
                        ANG_InMobiAdUtils.getInMobiObject().showInMobiFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);

                    } else {

                        if (getFullAdsCount_start(context) >= getStringtoInt(Slave.LAUNCH_FULL_ADS_nevigation)) {
                            setFulladsCount_start(context, 0);
                            ANG_InMobiAdUtils.getInMobiObject().showInMobiFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);


                        }


                    }

                } else if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Smaato_Full_Page_Ads)) {
                    if (is_force_ad) {

                        ANG_SmaatoAdUtils.getSmaatoObj().loadSmaatoFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);
                        ANG_SmaatoAdUtils.getSmaatoObj().showSmaatoFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);

                    } else {

                        if (getFullAdsCount_start(context) >= getStringtoInt(Slave.LAUNCH_FULL_ADS_nevigation)) {
                            setFulladsCount_start(context, 0);
                            ANG_SmaatoAdUtils.getSmaatoObj().showSmaatoFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);


                        }


                    }

                } else {
                    if (getFullAdsCount_start(context) >= getStringtoInt(Slave.LAUNCH_FULL_ADS_nevigation)) {
                        setFulladsCount_start(context, 0);
                        displayAdmobFull(context, Slave.ADMOB_FULL_ID_STATIC);
                    }
                }


            }
        } catch (Exception e) {
            if (getFullAdsCount_start(context) >= getStringtoInt(Slave.LAUNCH_FULL_ADS_nevigation)) {
                setFulladsCount_start(context, 0);
                displayAdmobFull(context, Slave.ADMOB_FULL_ID_STATIC);
            }

        }
        //ANG_Ads.printdata(" banner full  ads stage 11 "+DataHub.FULLADS_START_ID);
    }


    public void showFullAdsExit(Activity context, boolean is_force_ad) {
        try {
            if (getDaysDiff(context) >= getStringtoInt(Slave.EXIT_FULL_ADS_start_date)) {
                setFulladsCount_exit(context, -1);
                if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Admob_FullAds)) {

                    if (is_force_ad) {

                        displayAdmobFull(context, Slave.EXIT_FULL_ADS_ad_id);

                    } else {

                        PrintLog.print("Slave.EXIT_FULL_ADS_nevigation " + Slave.EXIT_FULL_ADS_nevigation);
                        if (getFullAdsCount_exit(context) >= getStringtoInt(Slave.EXIT_FULL_ADS_nevigation)) {
                            setFulladsCount_exit(context, 0);
                            displayAdmobFull(context, Slave.EXIT_FULL_ADS_ad_id);
                        }
                    }
                } else if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Startapp_FullAds)) {

                    if (is_force_ad) {
                        getstartAppObj(context, Slave.EXIT_FULL_ADS_ad_id).showfullAds(context, getStringtoInt(Slave.EXIT_FULL_ADS_ad_id));
                    } else {
                        if (getFullAdsCount_exit(context) >= getStringtoInt(Slave.EXIT_FULL_ADS_nevigation)) {
                            setFulladsCount_exit(context, 0);
                            getstartAppObj(context, Slave.EXIT_FULL_ADS_ad_id).showfullAds(context, getStringtoInt(Slave.EXIT_FULL_ADS_ad_id));

                        }

                    }
                } else if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Facebook_Full_Page_Ads)) {
                    if (is_force_ad) {

                        ANG_FbAdsProvider.getFbObject().loadFBFullAds(Slave.EXIT_FULL_ADS_ad_id, context);
                        ANG_FbAdsProvider.getFbObject().showFBFullAds(Slave.EXIT_FULL_ADS_ad_id, context);

                    } else {

                        if (getFullAdsCount_exit(context) >= getStringtoInt(Slave.EXIT_FULL_ADS_nevigation)) {
                            setFulladsCount_exit(context, 0);
                            ANG_FbAdsProvider.getFbObject().showFBFullAds(Slave.EXIT_FULL_ADS_ad_id, context);


                        }


                    }

                } else if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Inhouse_FullAds)) {
                    //return getInhouseBanner(Slave.NATIVE_LARGE_src,Slave.NATIVE_LARGE_clicklink,context,ANG_InHouseAds.TYPE_NATIVE_LARGE);


                    if (is_force_ad) {

                        Intent intent = new Intent(context, FullPagePromo.class);

                        intent.putExtra("src", Slave.EXIT_FULL_ADS_src);
                        intent.putExtra("link", Slave.EXIT_FULL_ADS_clicklink);
                        context.startActivity(intent);

                    } else {
                        if (getFullAdsCount_exit(context) >= getStringtoInt(Slave.EXIT_FULL_ADS_nevigation)) {

                            Intent intent = new Intent(context, FullPagePromo.class);
                            intent.putExtra("src", Slave.EXIT_FULL_ADS_src);
                            intent.putExtra("link", Slave.EXIT_FULL_ADS_clicklink);
                            context.startActivity(intent);
                            setFulladsCount_exit(context, 0);

                        }
                    }

                } else if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_InMobi_Full_Page_Ads)) {
                    if (is_force_ad) {

                        ANG_InMobiAdUtils.getInMobiObject().loadInMobiFullAds(context, Slave.EXIT_FULL_ADS_ad_id);
                        ANG_InMobiAdUtils.getInMobiObject().showInMobiFullAds(context, Slave.EXIT_FULL_ADS_ad_id);

                    } else {

                        if (getFullAdsCount_exit(context) >= getStringtoInt(Slave.EXIT_FULL_ADS_nevigation)) {
                            setFulladsCount_exit(context, 0);
                            ANG_InMobiAdUtils.getInMobiObject().showInMobiFullAds(context, Slave.EXIT_FULL_ADS_ad_id);

                        }


                    }

                } else if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Smaato_Full_Page_Ads)) {
                    if (is_force_ad) {

                        ANG_SmaatoAdUtils.getSmaatoObj().loadSmaatoFullAds(context, Slave.EXIT_FULL_ADS_ad_id);
                        ANG_SmaatoAdUtils.getSmaatoObj().showSmaatoFullAds(context, Slave.EXIT_FULL_ADS_ad_id);

                    } else {

                        if (getFullAdsCount_exit(context) >= getStringtoInt(Slave.EXIT_FULL_ADS_nevigation)) {
                            setFulladsCount_exit(context, 0);
                            ANG_SmaatoAdUtils.getSmaatoObj().showSmaatoFullAds(context, Slave.EXIT_FULL_ADS_ad_id);

                        }


                    }

                } else {
                    if (getFullAdsCount_exit(context) >= getStringtoInt(Slave.EXIT_FULL_ADS_nevigation)) {
                        setFulladsCount_exit(context, 0);
                        displayAdmobFull(context, Slave.ADMOB_FULL_ID_STATIC);
                    }
                }

            }
        } catch (Exception e) {
            PrintLog.print("Slave.EXIT_FULL_ADS_nevigation stage -2");

            if (getFullAdsCount_exit(context) >= getStringtoInt(Slave.FULL_ADS_nevigation)) {
                setFulladsCount_exit(context, 0);
                displayAdmobFull(context, Slave.ADMOB_FULL_ID_STATIC);
            }

        }
        //ANG_Ads.printdata(" banner full  ads stage 11 "+DataHub.FULLADS_START_ID);
    }


//    public void showFullAds2(Activity context, boolean is_force_ad) {
//        is_force_ad=false;
//        try {
//            if (getDaysDiff(context) >= DataHub.FULLADS_START_DAY2) {
//                setFulladsCount(context, -1);
//                if (DataHub.FULLADS_START_PROVIDER2.equals("1")) {
//                    if (is_force_ad) {
//                        displayAdmobFull(context, DataHub.FULLADS_START_ID);
//                    } else {
//
//                        if (getFullAdsCount(context) >= DataHub.FULLADS_NAV_COUNT2) {
//                            setFulladsCount(context, 0);
//                            displayAdmobFull(context, DataHub.FULLADS_START_ID2);
//                            //loadAdmobFullAds(context, DataHub.FULLADS_START_ID2);
//
//                        }
//
//                    }
//
//                } else  if (DataHub.FULLADS_START_PROVIDER2.equals("2")) {
//
//                    if (is_force_ad)
//                        //displayAdqtFull(context, DataHub.FULLADS_START_ID2);
//                        getstartAppObj(context, DataHub.FULLADS_START_ID2).showfullAds(context, DataHub.VIDEOADS2);
//                    else {
//                        if (getFullAdsCount(context) >= DataHub.FULLADS_NAV_COUNT2) {
//                            setFulladsCount(context, 0);
////						displayAdqtFull(context, DataHub.FULLADS_START_ID2);
////						loadAdQtFullAds(context, DataHub.FULLADS_START_ID2);
//                            getstartAppObj(context, DataHub.FULLADS_START_ID2).showfullAds(context, DataHub.VIDEOADS2);
//
//                        }
//
//                    }
//                }
//
//                 else {
//                    if (is_force_ad) {
//
//                        ANG_FbAdsProvider.getFbObject().loadFBFullAds(DataHub.FULLADS_START_ID2, context);
//                        ANG_FbAdsProvider.getFbObject().showFBFullAds(DataHub.FULLADS_START_ID2, context);
//
//                    } else {
//
//                        if (getFullAdsCount(context) >= DataHub.FULLADS_NAV_COUNT2) {
//                            setFulladsCount(context, 0);
//                            ANG_FbAdsProvider.getFbObject().showFBFullAds(DataHub.FULLADS_START_ID2, context);
//
//
//                        }
//
//
//                    }
//
//
//                }}
//
//        } catch (Exception e) {
//
//        }
//    }


//    public void showFullAds3(Activity context, boolean is_force_ad) {
//        try {
//            if (getDaysDiff(context) >= DataHub.FULLADS_START_DAY3) {
//
//                setFulladsCount(context, -1);
//
//                if (DataHub.FULLADS_START_PROVIDER3.equals("1")) {
//                    if (is_force_ad) {
//                        displayAdmobFull(context, DataHub.FULLADS_START_ID3);
//
//                    } else {
//
//                        if (getFullAdsCount(context) >= DataHub.FULLADS_NAV_COUNT3) {
//                            setFulladsCount(context, 0);
//                            displayAdmobFull(context, DataHub.FULLADS_START_ID3);
//                            //loadAdmobFullAds(context, DataHub.FULLADS_START_ID2);
//
//                        }
//
//                    }
//
//                }
//
//                else if (DataHub.FULLADS_START_PROVIDER3.equals("2")) {
//
//                    if (is_force_ad)
//                        //displayAdqtFull(context, DataHub.FULLADS_START_ID2);
//                        getstartAppObj(context, DataHub.FULLADS_START_ID3).showfullAds(context, DataHub.VIDEOADS2);
//                    else {
//                        if (getFullAdsCount(context) >= DataHub.FULLADS_NAV_COUNT3) {
//                            setFulladsCount(context, 0);
////						displayAdqtFull(context, DataHub.FULLADS_START_ID2);
////						loadAdQtFullAds(context, DataHub.FULLADS_START_ID2);
//                            getstartAppObj(context, DataHub.FULLADS_START_ID3).showfullAds(context, DataHub.VIDEOADS2);
//
//                        }
//
//                    }
//                }
//
//                else {
//                    if (is_force_ad) {
//
//                        ANG_FbAdsProvider.getFbObject().loadFBFullAds(DataHub.FULLADS_START_ID3, context);
//                        ANG_FbAdsProvider.getFbObject().showFBFullAds(DataHub.FULLADS_START_ID3, context);
//
//                    } else {
//
//                        if (getFullAdsCount(context) >= DataHub.FULLADS_NAV_COUNT3) {
//                            setFulladsCount(context, 0);
//                            ANG_FbAdsProvider.getFbObject().showFBFullAds(DataHub.FULLADS_START_ID3, context);
//
//
//                        }
//
//
//                    }
//
//
//                }
//
//
//
//            }
//
//        } catch (Exception e) {
//
//        }
//    }


    public void displayAdmobFull(Context context, String id) {


        try {
            if (adMobAds == null)
                adMobAds = new ANG_AdMobAds(context);
            adMobAds.admob_showFullAds(context, id);
        } catch (Exception e) {
            System.out.println("exception display admob ad" + " " + e);
        }
    }

    public void loadAdmobFullAds(Context context, String id, OnCacheFullAdLoaded l) {
        try {
            if (adMobAds == null)
                adMobAds = new ANG_AdMobAds(context);
            if (id != null && id.length() > 5)
                adMobAds.admob_InitFullAds(context, id, 1, true, l);
        } catch (Exception e) {

        }

    }

    private OnCacheFullAdLoaded l;

    public interface OnCacheFullAdLoaded {
        void onFullAdLoaded();

        void onFullAdFailed();
    }


    public LinearLayout getAdmobBanner(String bannerID, Context context) {
        if (adMobAds == null)
            adMobAds = new ANG_AdMobAds(context);
        return adMobAds.admob_GetBannerAds(context, bannerID);
    }


    public ANG_StartupAdsProvider getstartAppObj(Activity activity, String appid) {
        if (startupAdsProvider == null)
            startupAdsProvider = new ANG_StartupAdsProvider(activity, appid);
        return startupAdsProvider;

    }

    public long getInstalltionTime(Context context) {

        long installed = System.currentTimeMillis();
        try {
            installed = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0).firstInstallTime;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return installed;
    }

    public long getDaysDiff(Context context) {
        try {
            PrintLog.print("getInstalltionTime(context) 0 stage ");
            PrintLog.print("getInstalltionTime(context) " + getInstalltionTime(context));
            long day = System.currentTimeMillis() - getInstalltionTime(context);
            day = day / (1000 * 60 * 60 * 24);
            return day;


        } catch (Exception e) {
            return 0;
        }
    }

    public void clickMicro(Activity avActivity) {
        PrintLog.print(" avActivity " + avActivity + " DataHub.MicroID " + DataHub.MicroID);
        getstartAppObj(avActivity, DataHub.MicroID).clickNativeAds(avActivity);
    }


//    public void initEngine(Activity context) {
//        try {
////            new DataHub().parseData(context,new DataHub().getDefaultConfig(context));
//        } catch (Exception e) {
//
//        }
//    }

    public static int getStringtoInt(String data) {
        try {
            return Integer.parseInt(data);
        } catch (Exception e) {
            return 0;
        }

    }

    ANG_InHouseAds inHouseAds;

    private View getInhouseBanner(String src, final String link, final Context context, int type) {
        if (!Slave.hasPurchased(context)) {
            if (Utils.isNetworkConnected(context)) {
                if (inHouseAds == null)
                    inHouseAds = new ANG_InHouseAds();

                LinearLayout temp = inHouseAds.getBannerAds(src, context, type);
                temp.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(link));
                        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(browserIntent);
                    }
                });
                return temp;
            } else {
                return new LinearLayout(context);
            }

        } else {
            return getPremiumView((Activity) context);
        }
    }

    public void cacheFullPageAd(Activity context, OnCacheFullAdLoaded l) {
        if (!Slave.hasPurchased(context)) {
            try {
//            if (Slave.FULL_ADS_provider_id.equals(Slave.Provider_Admob_FullAds)) {
//                ANG_AdMobAds.getAdmobOBJ(context).admob_InitFullAds(context, Slave.FULL_ADS_ad_id, 90);
//
//            }
//            if (Slave.FULL_ADS_provider_id.equals(Slave.Provider_Facebook_Full_Page_Ads)) {
//                ANG_FbAdsProvider.getFbObject().loadFBFullAds(Slave.FULL_ADS_ad_id, context);
//
//            }
                PrintLog.print("EngineV2 Startwhyyyy ANG_Ads INit with id stage zero " + Slave.LAUNCH_FULL_ADS_provider_id);
                if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Admob_FullAds)) {
                    System.out.println("1108 logs check 03" + " " + Slave.LAUNCH_FULL_ADS_ad_id);
                    PrintLog.print("EngineV2 Startwhyyyy ANG_Ads INit with id " + Slave.LAUNCH_FULL_ADS_ad_id);
                    // ANG_AdMobAds.getAdmobOBJ(context).admob_InitFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id, 1);
                    loadAdmobFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id, l);
                    //ANG_InMobiAdUtils.getInMobiObject().loadInMobiFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);
                }
                if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Facebook_Full_Page_Ads)) {
                    ANG_FbAdsProvider.getFbObject().loadFBFullAds(Slave.LAUNCH_FULL_ADS_ad_id, context);

                }

                if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_InMobi_Full_Page_Ads)) {
                    ANG_InMobiAdUtils.getInMobiObject().loadInMobiFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);
                    PrintLog.print("1716 checking full ads 00");

                }
                if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Smaato_Full_Page_Ads)) {
                    ANG_SmaatoAdUtils.getSmaatoObj().loadSmaatoFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);

                }

//            if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Admob_FullAds)) {
//                ANG_AdMobAds.getAdmobOBJ(context).admob_InitFullAds(context, Slave.EXIT_FULL_ADS_ad_id, 1);
//
//            }
//            if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Facebook_Full_Page_Ads)) {
//                ANG_FbAdsProvider.getFbObject().loadFBFullAds(Slave.EXIT_FULL_ADS_ad_id, context);
//
//            }
            } catch (Exception e) {
                PrintLog.print("EngiveV2 Error in cacheinga full ads " + e.getMessage());
            }
        }
    }

    //"cpname": "New Cp",
    //"navigation_count": "2",
//                "is_start": "no",
//                "is_exit": "yes",
//                "startday": "10",
//                "package_name": "Start",
//                "camp_img": "http:\/\/192.168.1.14\/railway1\/assets\/admin\/dashboard\/lt8qUYrIT7m5qarLuh26nj8nitlW3Kcx.png",
//                "camp_click_link": "ht


    public void showCPStart(Activity context) {
        if (!Slave.hasPurchased(context)) {
//            int server_launchCount = getStringtoInt(Slave.CP_navigation_count);
            if (Slave.CP_is_start.equals(Slave.CP_YES)) {
                if (!isPackageInstalled(Slave.CP_package_name, context)) {
//                    if (DataHubConstant.APP_LAUNCH_COUNT % server_launchCount == 0) {
                    if (getDaysDiff(context) >= getStringtoInt(Slave.CP_startday)) {


                        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

                        if (netInfo != null) {

                            Intent intent = new Intent(context, FullPagePromo.class);
                            intent.putExtra("src", Slave.CP_camp_img);
                            intent.putExtra("link", Slave.CP_camp_click_link);
                            context.startActivity(intent);


                            CP_SHOWN = true;
                        }
//                        }
                    }

                }

            }
        }
    }

    public void showCPExit(Activity context) {
        if (!Slave.hasPurchased(context)) {
//        int server_launchCount = getStringtoInt(Slave.CP_navigation_count);
            if (Slave.CP_is_exit.equals(Slave.CP_YES)) {
                if (!isPackageInstalled(Slave.CP_package_name, context)) {
//                if (DataHubConstant.APP_LAUNCH_COUNT % server_launchCount == 0) {
                    if (getDaysDiff(context) >= getStringtoInt(Slave.CP_startday)) {

                        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

                        if (netInfo != null) {

                            Intent intent = new Intent(context, FullPagePromo.class);
                            intent.putExtra("src", Slave.CP_camp_img);
                            intent.putExtra("link", Slave.CP_camp_click_link);
                            context.startActivity(intent);
                            CP_SHOWN = true;
                        }
                    }
//                }

                }

            }
        }
    }


    private boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static boolean CP_SHOWN = false;


    public void vANG_CallOnSplash(final Context context, OnCacheFullAdLoaded l) {
        DataHubPreference dP = new DataHubPreference(context);

        dP.setAppName(getAppName(context));
        DataHubConstant.APP_LAUNCH_COUNT = Integer.parseInt(dP.getAppLaunchCount());

        EngineHandler engineHandler = new EngineHandler(context);
        engineHandler.initServices(false);
        cacheFullPageAd((Activity) context, l);
        engineHandler.initServices(true);

    }

    private String getAppName(Context context) {
        final PackageManager pm = context.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(context.getPackageName(), 0);
        } catch (final NameNotFoundException e) {
            ai = null;
        }
        final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "Dear");

        return applicationName;
    }


    public void vANG_CallOnBGLaunch(Activity activity) {
        EngineHandler engineHandler = new EngineHandler(activity);
        engineHandler.initServices(false);

    }

    public void vANG_ManageAppExit(Context context) {
        CP_SHOWN = false;
        if (!Slave.hasPurchased(context)) {
            handle_exit_prompt(context);
        }
    }


    public void vANG_CallonAppLaunch(Activity context) {
        if (!Slave.hasPurchased(context)) {

            PrintLog.print("CHECK CHECK 1 PRO" + " " + Slave.IS_PRO);
            PrintLog.print("CHECK CHECK 2 WEEKLY" + " " + Slave.IS_WEEKLY);
            PrintLog.print("CHECK CHECK 3 MONTHLY" + " " + Slave.IS_MONTHLY);
            PrintLog.print("CHECK CHECK 4 HALF_YEARLY" + " " + Slave.IS_HALFYEARLY);
            PrintLog.print("CHECK CHECK 5 YEARLY" + " " + Slave.IS_YEARLY);
            PrintLog.print("here inside applaunch 02");

            handle_launch_prompt(context);
        }


        if (promptHander == null)
            promptHander = new ANG_PromptHander();
        promptHander.checkForForceUpdate(context);
        promptHander.checkForNormalUpdate(context);

        PrintLog.print("here inside applaunch 01");
        BillingPreference bf = new BillingPreference(context);
        System.out.println("CHECK BILLING PRE PRO" + " " + bf.isPro());
        System.out.println("CHECK BILLING PRE WEEKLY" + " " + bf.isWeekly());
        System.out.println("CHECK BILLING PRE MONTHLY" + " " + bf.isMonthly());
        System.out.println("CHECK BILLING PRE HALF YEAR" + " " + bf.isHalfYearly());
        System.out.println("CHECK BILLING PRE YEAR" + " " + bf.isYearly());

        System.out.println("CHECKING CHECKING DING" + " " + Slave.hasPurchased(context));

    }

    private void handle_launch_prompt(Context context) {
        int rate_nonRepeat = 0, cp_nonRepeat = 0, full_nonRepeat = 0, removeads_nonRepeat = 0;

        if (Slave.LAUNCH_NON_REPEAT_COUNT != null && Slave.LAUNCH_NON_REPEAT_COUNT.size() > 0) {
            for (int i = 0; i < Slave.LAUNCH_NON_REPEAT_COUNT.size(); i++) {
                rate_nonRepeat = getStringtoInt(Slave.LAUNCH_NON_REPEAT_COUNT.get(i).launch_rate);
                cp_nonRepeat = getStringtoInt(Slave.LAUNCH_NON_REPEAT_COUNT.get(i).launch_exit);
                full_nonRepeat = getStringtoInt(Slave.LAUNCH_NON_REPEAT_COUNT.get(i).launch_full);
                removeads_nonRepeat = getStringtoInt(Slave.LAUNCH_NON_REPEAT_COUNT.get(i).launch_removeads);

                PrintLog.print("handle exit" + " " + DataHubConstant.APP_LAUNCH_COUNT + " " + rate_nonRepeat + " " + cp_nonRepeat + " " + full_nonRepeat + " " + removeads_nonRepeat);
                if (DataHubConstant.APP_LAUNCH_COUNT == rate_nonRepeat) {
                    PrintLog.print("handle exit inside 1 rate");
                    if (promptHander == null)
                        promptHander = new ANG_PromptHander();

                    promptHander.ratee(context, context.getResources()
                            .getDrawable(R.drawable.iconmain), false);

                    return;
                } else if (DataHubConstant.APP_LAUNCH_COUNT == cp_nonRepeat) {
                    PrintLog.print("handle exit inside 2 cp exit");
                    showCPStart((Activity) context);
                    return;
                } else if (DataHubConstant.APP_LAUNCH_COUNT == full_nonRepeat) {
                    PrintLog.print("handle exit inside 3 fullads");
                    showFullAdsOnLaunch((Activity) context, false, "non repeat");
                    return;
                } else if (DataHubConstant.APP_LAUNCH_COUNT == removeads_nonRepeat) {
                    PrintLog.print("handle exit inside 4 removeads");
                    showRemoveAdsPrompt(context);
                    return;
                }
            }
        }
        PrintLog.print("handle exit repease" + " " + DataHubConstant.APP_LAUNCH_COUNT + " " + Slave.LAUNCH_REPEAT_REMOVEADS);
        if (Slave.LAUNCH_REPEAT_FULL_ADS != null && !Slave.LAUNCH_REPEAT_FULL_ADS.equalsIgnoreCase("") && DataHubConstant.APP_LAUNCH_COUNT % getStringtoInt(Slave.LAUNCH_REPEAT_FULL_ADS) == 0) {
            PrintLog.print("handle exit inside 13 fullads");
            showFullAdsOnLaunch((Activity) context, false, "repeat");
        } else if (Slave.LAUNCH_REPEAT_EXIT != null && !Slave.LAUNCH_REPEAT_EXIT.equalsIgnoreCase("") && DataHubConstant.APP_LAUNCH_COUNT % getStringtoInt(Slave.LAUNCH_REPEAT_EXIT) == 0) {
            PrintLog.print("handle exit inside 12 cp exit");
            showCPStart((Activity) context);
        } else if (Slave.LAUNCH_REPEAT_RATE != null && !Slave.LAUNCH_REPEAT_RATE.equalsIgnoreCase("") && DataHubConstant.APP_LAUNCH_COUNT % getStringtoInt(Slave.LAUNCH_REPEAT_RATE) == 0) {
            PrintLog.print("handle exit inside 11 rate");
            if (promptHander == null)
                promptHander = new ANG_PromptHander();

            promptHander.ratee(context, context.getResources()
                    .getDrawable(R.drawable.iconmain), false);
        } else if (Slave.LAUNCH_REPEAT_REMOVEADS != null && !Slave.LAUNCH_REPEAT_REMOVEADS.equalsIgnoreCase("") && DataHubConstant.APP_LAUNCH_COUNT % getStringtoInt(Slave.LAUNCH_REPEAT_REMOVEADS) == 0) {
            PrintLog.print("handle exit inside 14 removeads");
            showRemoveAdsPrompt(context);
        }
    }

    private void handle_exit_prompt(Context context) {
        int rate_nonRepeat = 0, exit_nonRepeat = 0, full_nonRepeat = 0, removeads_nonRepeat = 0;

        if (Slave.EXIT_NON_REPEAT_COUNT != null && Slave.EXIT_NON_REPEAT_COUNT.size() > 0) {
            for (int i = 0; i < Slave.EXIT_NON_REPEAT_COUNT.size(); i++) {
                rate_nonRepeat = getStringtoInt(Slave.EXIT_NON_REPEAT_COUNT.get(i).rate);
                exit_nonRepeat = getStringtoInt(Slave.EXIT_NON_REPEAT_COUNT.get(i).exit);
                full_nonRepeat = getStringtoInt(Slave.EXIT_NON_REPEAT_COUNT.get(i).full);
                removeads_nonRepeat = getStringtoInt(Slave.EXIT_NON_REPEAT_COUNT.get(i).removeads);

                PrintLog.print("handle exit" + " " + DataHubConstant.APP_LAUNCH_COUNT + " " + rate_nonRepeat + " " + exit_nonRepeat + " " + full_nonRepeat + " " + removeads_nonRepeat);
                if (DataHubConstant.APP_LAUNCH_COUNT == rate_nonRepeat) {
                    PrintLog.print("handle exit inside 1 rate");
                    if (promptHander == null)
                        promptHander = new ANG_PromptHander();

                    promptHander.ratee(context, context.getResources()
                            .getDrawable(R.drawable.iconmain), false);

                    return;
                } else if (DataHubConstant.APP_LAUNCH_COUNT == exit_nonRepeat) {
                    PrintLog.print("handle exit inside 2 cp exit");
                    showCPExit((Activity) context);
                    return;
                } else if (DataHubConstant.APP_LAUNCH_COUNT == full_nonRepeat) {
                    PrintLog.print("handle exit inside 3 fullads");
                    showFullAdsOnExit((Activity) context, false, "non repeat");
                    return;
                } else if (DataHubConstant.APP_LAUNCH_COUNT == removeads_nonRepeat) {
                    PrintLog.print("handle exit inside 4 removeads");
                    showRemoveAdsPrompt(context);
                    return;
                }
            }
        }
        PrintLog.print("handle exit repease" + " " + DataHubConstant.APP_LAUNCH_COUNT + " " + Slave.EXIT_REPEAT_REMOVEADS);
        if (Slave.EXIT_REPEAT_FULL_ADS != null && !Slave.EXIT_REPEAT_FULL_ADS.equalsIgnoreCase("") && DataHubConstant.APP_LAUNCH_COUNT % getStringtoInt(Slave.EXIT_REPEAT_FULL_ADS) == 0) {
            PrintLog.print("handle exit inside 13 fullads");
            showFullAdsOnExit((Activity) context, false, "repeat");
        } else if (Slave.EXIT_REPEAT_EXIT != null && !Slave.EXIT_REPEAT_EXIT.equalsIgnoreCase("") && DataHubConstant.APP_LAUNCH_COUNT % getStringtoInt(Slave.EXIT_REPEAT_EXIT) == 0) {
            PrintLog.print("handle exit inside 12 cp exit");
            showCPExit((Activity) context);
        } else if (Slave.EXIT_REPEAT_RATE != null && !Slave.EXIT_REPEAT_RATE.equalsIgnoreCase("") && DataHubConstant.APP_LAUNCH_COUNT % getStringtoInt(Slave.EXIT_REPEAT_RATE) == 0) {
            PrintLog.print("handle exit inside 11 rate");
            if (promptHander == null)
                promptHander = new ANG_PromptHander();

            promptHander.ratee(context, context.getResources()
                    .getDrawable(R.drawable.iconmain), false);
        } else if (Slave.EXIT_REPEAT_REMOVEADS != null && !Slave.EXIT_REPEAT_REMOVEADS.equalsIgnoreCase("") && DataHubConstant.APP_LAUNCH_COUNT % getStringtoInt(Slave.EXIT_REPEAT_REMOVEADS) == 0) {
            PrintLog.print("handle exit inside 14 removeads");
            showRemoveAdsPrompt(context);
        }
    }

    public void showRemoveAdsPrompt(Context mContext) {
        Intent intent = new Intent(mContext, BillingListActivity.class);
        mContext.startActivity(intent);
    }


    public void showFullAdsOnLaunch(Activity context, boolean is_force_ad, String from) {
        PrintLog.print("1600 from: " + " " + from + " " + "01");
        try {
            PrintLog.print("1600 from: " + " " + from + " " + "02" + " " + Slave.LAUNCH_FULL_ADS_start_date + " " + Slave.LAUNCH_FULL_ADS_provider_id + " " + Slave.LAUNCH_FULL_ADS_ad_id);
            if (getDaysDiff(context) >= getStringtoInt(Slave.LAUNCH_FULL_ADS_start_date)) {
                PrintLog.print("1600 from: " + " " + from + " " + "03");
                if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Admob_FullAds)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "04");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "05");
                        displayAdmobFull(context, Slave.LAUNCH_FULL_ADS_ad_id);
                    } else {
                        System.out.println("1108 logs check 01" + " " + Slave.LAUNCH_FULL_ADS_ad_id);
                        PrintLog.print("1600 from: " + " " + from + " " + "06");
                        displayAdmobFull(context, Slave.LAUNCH_FULL_ADS_ad_id);
                    }
                } else if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Startapp_FullAds)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "07");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "08");
                        getstartAppObj(context, Slave.LAUNCH_FULL_ADS_ad_id).showfullAds(context, getStringtoInt(Slave.LAUNCH_FULL_ADS_ad_id));
                    } else {
                        PrintLog.print("1600 from: " + " " + from + " " + "09");
                        getstartAppObj(context, Slave.LAUNCH_FULL_ADS_ad_id).showfullAds(context, getStringtoInt(Slave.LAUNCH_FULL_ADS_ad_id));
                    }
                } else if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Facebook_Full_Page_Ads)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "10");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "11");
                        ANG_FbAdsProvider.getFbObject().loadFBFullAds(Slave.LAUNCH_FULL_ADS_ad_id, context);
                        ANG_FbAdsProvider.getFbObject().showFBFullAds(Slave.LAUNCH_FULL_ADS_ad_id, context);
                    } else {
                        PrintLog.print("1600 from: " + " " + from + " " + "12");
                        ANG_FbAdsProvider.getFbObject().showFBFullAds(Slave.LAUNCH_FULL_ADS_ad_id, context);
                    }
                } else if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Inhouse_FullAds)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "12");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "13");
                        Intent intent = new Intent(context, FullPagePromo.class);
                        intent.putExtra("src", Slave.LAUNCH_FULL_ADS_src);
                        intent.putExtra("link", Slave.LAUNCH_FULL_ADS_clicklink);
                        context.startActivity(intent);
                    } else {
                        PrintLog.print("1600 from: " + " " + from + " " + "014");
                        Intent intent = new Intent(context, FullPagePromo.class);
                        intent.putExtra("src", Slave.LAUNCH_FULL_ADS_src);
                        intent.putExtra("link", Slave.LAUNCH_FULL_ADS_clicklink);
                        context.startActivity(intent);
                    }
                } else if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_InMobi_Full_Page_Ads)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "015");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "016");
                        ANG_InMobiAdUtils.getInMobiObject().loadInMobiFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);
                        ANG_InMobiAdUtils.getInMobiObject().showInMobiFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);
                    } else {
                        PrintLog.print("1600 from: " + " " + from + " " + "017");
                        PrintLog.print("1716 checking full ads 02" + " ");
                        ANG_InMobiAdUtils.getInMobiObject().showInMobiFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);
                    }
                } else if (Slave.LAUNCH_FULL_ADS_provider_id.equals(Slave.Provider_Smaato_Full_Page_Ads)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "018");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "019");
                        ANG_SmaatoAdUtils.getSmaatoObj().loadSmaatoFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);
                        ANG_SmaatoAdUtils.getSmaatoObj().showSmaatoFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);
                    } else {
                        PrintLog.print("1600 from: " + " " + from + " " + "020");
                        ANG_SmaatoAdUtils.getSmaatoObj().showSmaatoFullAds(context, Slave.LAUNCH_FULL_ADS_ad_id);
                    }
                }/* else {
                    displayAdmobFull(context, Slave.ADMOB_FULL_ID_STATIC);
                }*/


            }

        } catch (Exception e) {
            PrintLog.print("exception 1600 from: " + " " + from + " " + "022");
            displayAdmobFull(context, Slave.ADMOB_FULL_ID_STATIC);
        }
    }

    public void showFullAdsOnExit(Activity context, boolean is_force_ad, String from) {
        PrintLog.print("1600 from: " + " " + from + " " + "01");
        try {
            PrintLog.print("1600 from: " + " " + from + " " + "02" + " " + Slave.EXIT_FULL_ADS_start_date + " " + Slave.EXIT_FULL_ADS_provider_id + " " + Slave.EXIT_FULL_ADS_ad_id);
            if (getDaysDiff(context) >= getStringtoInt(Slave.EXIT_FULL_ADS_start_date)) {
                PrintLog.print("1600 from: " + " " + from + " " + "03");
                if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Admob_FullAds)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "04");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "05");
                        displayAdmobFull(context, Slave.EXIT_FULL_ADS_ad_id);
                    } else {
                        PrintLog.print("1600 from: " + " " + from + " " + "06");
                        displayAdmobFull(context, Slave.EXIT_FULL_ADS_ad_id);
                    }
                } else if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Startapp_FullAds)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "07");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "08");
                        getstartAppObj(context, Slave.EXIT_FULL_ADS_ad_id).showfullAds(context, getStringtoInt(Slave.EXIT_FULL_ADS_ad_id));
                    } else {
                        PrintLog.print("1600 from: " + " " + from + " " + "09");
                        getstartAppObj(context, Slave.EXIT_FULL_ADS_ad_id).showfullAds(context, getStringtoInt(Slave.EXIT_FULL_ADS_ad_id));
                    }
                } else if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Facebook_Full_Page_Ads)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "10");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "11");
                        ANG_FbAdsProvider.getFbObject().loadFBFullAds(Slave.EXIT_FULL_ADS_ad_id, context);
                        ANG_FbAdsProvider.getFbObject().showFBFullAds(Slave.EXIT_FULL_ADS_ad_id, context);
                    } else {
                        PrintLog.print("1600 from: " + " " + from + " " + "12");
                        ANG_FbAdsProvider.getFbObject().showFBFullAds(Slave.EXIT_FULL_ADS_ad_id, context);
                    }
                } else if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Inhouse_FullAds)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "12");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "13");
                        Intent intent = new Intent(context, FullPagePromo.class);
                        intent.putExtra("src", Slave.EXIT_FULL_ADS_src);
                        intent.putExtra("link", Slave.EXIT_FULL_ADS_clicklink);
                        context.startActivity(intent);
                    } else {
                        PrintLog.print("1600 from: " + " " + from + " " + "014");
                        Intent intent = new Intent(context, FullPagePromo.class);
                        intent.putExtra("src", Slave.EXIT_FULL_ADS_src);
                        intent.putExtra("link", Slave.EXIT_FULL_ADS_clicklink);
                        context.startActivity(intent);
                    }
                } else if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_InMobi_Full_Page_Ads)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "015");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "016");
                        ANG_InMobiAdUtils.getInMobiObject().loadInMobiFullAds(context, Slave.EXIT_FULL_ADS_ad_id);
                        ANG_InMobiAdUtils.getInMobiObject().showInMobiFullAds(context, Slave.EXIT_FULL_ADS_ad_id);
                    } else {
                        PrintLog.print("1600 from: " + " " + from + " " + "017");
                        ANG_InMobiAdUtils.getInMobiObject().showInMobiFullAds(context, Slave.EXIT_FULL_ADS_ad_id);
                    }
                } else if (Slave.EXIT_FULL_ADS_provider_id.equals(Slave.Provider_Smaato_Full_Page_Ads)) {
                    PrintLog.print("1600 from: " + " " + from + " " + "018");
                    if (is_force_ad) {
                        PrintLog.print("1600 from: " + " " + from + " " + "019");
                        ANG_SmaatoAdUtils.getSmaatoObj().loadSmaatoFullAds(context, Slave.EXIT_FULL_ADS_ad_id);
                        ANG_SmaatoAdUtils.getSmaatoObj().showSmaatoFullAds(context, Slave.EXIT_FULL_ADS_ad_id);
                    } else {
                        PrintLog.print("1600 from: " + " " + from + " " + "020");
                        ANG_SmaatoAdUtils.getSmaatoObj().showSmaatoFullAds(context, Slave.EXIT_FULL_ADS_ad_id);
                    }
                }/* else {
                    displayAdmobFull(context, Slave.ADMOB_FULL_ID_STATIC);
                }*/


            }

        } catch (Exception e) {
            PrintLog.print("1600 from: " + " " + from + " " + "022");
            displayAdmobFull(context, Slave.ADMOB_FULL_ID_STATIC);
        }
    }

    public void openBillingPage(Context context) {
        context.startActivity(new Intent(context, BillingListActivity.class));
    }

}
