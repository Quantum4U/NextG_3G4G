package app.serviceprovider;//package app.serviceprovider;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.KeyEvent;
//import app.pnd.adshandler.R;
//
//
//import com.adiquity.android.AdiquityPostAppAdView;
//import com.adiquity.android.adlistener.AdIquityPostAppAdListener;
//
//public class ShowFullAdsAdiQT extends Activity {
//	public static String ID="";
//	
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		AdiquityPostAppAdView mAdViewInt;
//		setContentView(R.layout.activity_show_ad);
//		System.out.println("Adsq 3 showwww");
//		mAdViewInt = (AdiquityPostAppAdView) findViewById(R.id.adview2);
//		mAdViewInt.init(ID, ShowFullAdsAdiQT.this);
//		mAdViewInt.setDelayCloseButtonTime(2);
//		mAdViewInt.setUserProfile(new AdiquitySampleAppUserProfile());
//		
//		
//		
//    	mAdViewInt.setAdListener(new AdIquityPostAppAdListener() {
//			
//			
//			public void onInterstitialClosed(AdiquityPostAppAdView arg0) {
//				// TODO Auto-generated method stub
//				System.out.println("Adsq 3 showwww fns 1");
//				Log.d("AdiquitySampleAddTestApp", "onInterstitialClosed");
//				finish();
//			}
//			
//			
//			public void onAdClick(AdiquityPostAppAdView arg0) {
//				System.out.println("Adsq 3 showwww fns 2");
//				Log.d("AdiquitySampleAddTestApp", "onAdClick");
//			}
//			
//			
//			public void adRequestFailed(AdiquityPostAppAdView arg0) {
//				System.out.println("Adsq 3 showwww fns 3");
//				Log.d("AdiquitySampleAddTestApp", "adRequestFailed");
//				finish();
//			}
//			
//			
//			public void adRequestCompletedNoAd(AdiquityPostAppAdView arg0) {
//				System.out.println("Adsq 3 showwww fns 4");
//				Log.d("AdiquitySampleAddTestApp", "adRequestCompletedNoAd");
//				finish();
//			}
//			
//			
//			public void adRequestCompleted(AdiquityPostAppAdView arg0) {
//				// TODO Auto-generated method stub
//				Log.d("AdiquitySampleAddTestApp", "adRequestCompleted");
//			}
//		});
//    	mAdViewInt.startAds();	
//		
//		mAdViewInt.show();
//		
//	}
//	
//	
//	protected void onResume() {
//		Log.d("ShowAdActivity","onResume");
//		super.onResume();
//	}
//	
//	
//	protected void onPause() {
//		Log.d("ShowAdActivity","onPause");
//		//finish();
//		super.onPause();
//	}
//	
//	
//	public void onStop() {
//		Log.d("ShowAdActivity","onStop");
//		super.onStop();
//	}
//	
//	 public boolean onKeyDown(int keyCode, KeyEvent event) {
//	if (keyCode == KeyEvent.KEYCODE_BACK) {
//		System.out.println("Adsq 3 showwww fns 999");
//		finish();
//		
//			return true;
//		}
//		
//		
//
//	return super.onKeyDown(keyCode, event);
//}
//	
//
//}
