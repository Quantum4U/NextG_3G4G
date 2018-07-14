package app.serviceprovider;//package app.serviceprovider;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.view.View;
//
//import com.adiquity.android.AdiquityInAppAdView;
//import com.adiquity.android.AdiquityPostAppAdView;
//
//
//
//public class AdiQTAds {
//	AdiquityInAppAdView adiquityInAppAdView;
//	AdiquityPostAppAdView mAdViewInt;
//	public View getAdiQtBannerAds(Context context,String id){
//		adiquityInAppAdView=new AdiquityInAppAdView(context);
//		adiquityInAppAdView.setRefreshTime(10);
//		adiquityInAppAdView.setUserProfile(new AdiquitySampleAppUserProfile());
//		adiquityInAppAdView.init(id,(Activity)context, AdiquityInAppAdView.ADIQUITY_AD_UNIT_320_48);
//		adiquityInAppAdView.getSettings().setDomStorageEnabled(true);
//		adiquityInAppAdView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//		adiquityInAppAdView.startAds();
//		
//		return adiquityInAppAdView;
//	}
//	
//	
//	public void loadAdiQtFullAds(){
//		
//	}
//public void displayAdiQtFullAds(String id,Context context){
//	
//	ShowFullAdsAdiQT.ID=id;
//	System.out.println("Adsq 2 "+ShowFullAdsAdiQT.ID +"context "+context);
//	Intent adIntent =  new Intent(context,ShowFullAdsAdiQT.class);
//	context.startActivity(adIntent);
//	System.out.println("Adsq 2222222 "+ShowFullAdsAdiQT.ID);
//	
//	}
//	
//
//}
