package app.pnd.fourg;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import app.adshandler.AHandler;
import com.appnextg.fourg.R;

public class HistoryFrag extends Fragment {
	ViewPager mPager;
	Context context;
	 LinearLayout adslayout1,adslayout2;
	private static final String TAG = "FirstFragment";
	//int drable_ides[]={R.drawable.icon_cache,R.drawable.icon_callblack,R.drawable.icon_applock,R.drawable.icon_root_unist,R.drawable.icon_chat,R.drawable.icon_who_calling,R.drawable.icon_light,R.drawable.icon_gallery,R.drawable.icon_app_tosd,R.drawable.icon_share_myapp,R.drawable.icon_sms};
	String app_names[]={"Browser History","Gmail History","Google Play Store History","Google Maps History"};
	String app_packs[]={"com.android.browser","com.google.android.gm","com.android.vending","com.google.android.apps.maps"};
	HistoryAdpt moreAdaptor;
ListView listView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.history_applistlayout, container, false);
		 adslayout1=(LinearLayout)view.findViewById(R.id.adsbannernew);
		 adslayout2=(LinearLayout)view.findViewById(R.id.adsfirtfrag);
		moreAdaptor=new HistoryAdpt(app_names, app_packs, context);
		
		   adslayout1.addView(new AHandler().getBannerHeader(getActivity()));
	//	   adslayout2.addView(ads.getBannerAds(context,getResources().getString(R.string.pindi_ad_unit_id2)));
		listView=(ListView)view.findViewById(R.id.applistview);
		listView.setAdapter(moreAdaptor);
		return view;
	}
	
	public void onAttach(Activity ac) {
		// TODO Auto-generated method stub
		super.onAttach(ac);
		context=ac;
	}
	
	

	
}
