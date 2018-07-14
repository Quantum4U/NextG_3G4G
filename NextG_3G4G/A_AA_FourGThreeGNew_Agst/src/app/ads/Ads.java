//package app.ads;
//
//import android.annotation.SuppressLint;
//import android.app.ActionBar.LayoutParams;
//import android.content.Context;
//import android.widget.LinearLayout;
//import app.pnd.fourg.R;
//
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
//
//
//public class Ads {
//	public static int count=1;
//	private InterstitialAd mInterstitial;
//	 private AdView mAdView;
//	
//	
//	 public static int FULLADSCOUNT=0;
//	 public static int MAXFULLADSCOUNTER=3;
//		public static boolean isFullAds(boolean increment){
//			if(increment==true)
//			FULLADSCOUNT++;
//			System.out.println(" FULLADSCOUNT "+FULLADSCOUNT+" MAXFULLADSCOUNTER "+MAXFULLADSCOUNTER);
//			if(FULLADSCOUNT>=MAXFULLADSCOUNTER){
//				if(increment==false)
//				FULLADSCOUNT=0;
//				return true;
//			}
//			else
//				return false;
//		}
//	
//	public  Ads(Context context){
//		if(mInterstitial==null)
//		    mInterstitial = new InterstitialAd(context);
//	        mInterstitial.setAdUnitId(context.getResources().getString(R.string.pindi_ad_unit_id_int));
//	}
//	
//	public LinearLayout  getBannerAds(Context context,String id){
//	
//
//        mAdView = new AdView(context);
//        mAdView.setAdUnitId(id);
//      
//          mAdView.setAdSize(AdSize.BANNER);
//      
//        
//      
//
//        LinearLayout linLayout = new LinearLayout(context);
//        // specifying vertical orientation
//        linLayout.setOrientation(LinearLayout.VERTICAL);
//        // creating LayoutParams  
//        LayoutParams linLayoutParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 
//       
//        
//        
//        AdRequest adRequest = new AdRequest.Builder() 
//        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//        .addTestDevice("B37B9AF5653F23F6B7E6696BAC8B444E")
//        .build();
//       
//        
//     
//
//        mAdView.loadAd(adRequest);
//        //mAdView.loadAd(new AdRequest.Builder().build());
//
//        linLayout.addView(mAdView);
//     
//       return linLayout;
//	}
//	 public void onPauseBanner() {
//		 if(mAdView!=null)
//	        mAdView.pause();
//	       
//	    }
//
//	    
//	    public void onResumeBanner() {
//	    	 if(mAdView!=null)
//	        mAdView.resume();
//	    }
//
//	    
//	    public void onDestroyBanner() {
//	    	 if(mAdView!=null)
//	        mAdView.destroy();
//	        
//	    }
//	    public void showFullAds(){
//	    	
//	    }
//	    
//	    
//	    
//	    
//	    //Full ads
//	    public void initFullAds(Context context){
////	    	if(mInterstitial==null)
////	    mInterstitial = new InterstitialAd(context);
////        mInterstitial.setAdUnitId(context.getResources().getString(R.string.ad_unit_id_int));
//        loadInterstitial(context);
//	    }
//	   
//	    
//	    
//	    public void loadInterstitial(Context context) {
//	    	System.out.println("Onrec load");
//	    	 mInterstitial = new InterstitialAd(context);
//	    	 if(count==1){
//	         mInterstitial.setAdUnitId(context.getResources().getString(R.string.pindi_ad_unit_id_int));
//	         count=2;
//	    	 }
//	    	 else if(count==2){
//		         mInterstitial.setAdUnitId(context.getResources().getString(R.string.pindi_ad_unit_id_int));
//		         count=3;
//		    	 }
//	    	 else{
//	    		 mInterstitial.setAdUnitId(context.getResources().getString(R.string.pindi_ad_unit_id_int));
//		         count=1;
//	    	 }
//	    	 
//	    	 AdRequest adRequest = new AdRequest.Builder() 
//	         .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//	         .addTestDevice("B37B9AF5653F23F6B7E6696BAC8B444E")
//	         .build();
//	    	 mInterstitial.loadAd(adRequest);
//	        // mInterstitial.loadAd(new AdRequest.Builder().build());
//	    }
//
//	    public void showFullAds(Context context) {
//	    	System.out.println("Onrec2222 "+mInterstitial.isLoaded());
//	        if (mInterstitial.isLoaded()) {
//	        	System.out.println("setting trueeeeeee  loaddeddddddd");
//	            mInterstitial.show();
//	        }
//	        else{
//	        	System.out.println("setting trueeeeeee  notttttt loaddeddddddd");
//	        	initFullAds(context);
//	        	 mInterstitial.show();
//	        }
//	      
//	    }
//
//}
