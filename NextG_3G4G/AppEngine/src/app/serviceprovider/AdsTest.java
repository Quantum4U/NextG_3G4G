package app.serviceprovider;//package app.serviceprovider;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//
//import android.app.Activity;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.LinearLayout;
//import app.adshandler.AHandler;
//import app.adshandler.DataHub;
//import app.adshandler.PromptHander;
//import app.pnd.adshandler.R;
//
//public class AdsTest extends Activity {
//	String data;
//	String url = "http://quantum4you.com/piqvalue.php?val=999";
//	LinearLayout firstads, topads, bottomads, fullads, fullads2;
//	AHandler aHandler;
//
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		aHandler = new AHandler();
//		setContentView(R.layout.apk_scan);
//		
//		firstads = (LinearLayout) findViewById(R.id.firstads);
//		topads = (LinearLayout) findViewById(R.id.topads);
//		bottomads = (LinearLayout) findViewById(R.id.bottomads);
//		fullads = (LinearLayout) findViewById(R.id.callfullads);
//		fullads2 = (LinearLayout) findViewById(R.id.callfullads2);
//		fullads.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//				aHandler.showFullAds(AdsTest.this, false);
//
//			}
//		});
//
//		fullads2.setOnClickListener(new View.OnClickListener() {
//
//			public void onClick(View v) {
//			//	new PromptHander().ratee(AdsTest.this);
//				aHandler.showFullAds2(AdsTest.this, false);
//
//			}
//		});
//new GPID().execute();
//	}
//
//	public void dotheAd() {
//		//firstads.addView(aHandler.getBannerFirstAds(AdsTest.this));
//		topads.addView(aHandler.getBannerHeader(AdsTest.this));
//		//bottomads.addView(aHandler.getBannerFooter(AdsTest.this));
//	}
//
//	class GPID extends AsyncTask<String, Integer, Integer> {
//
//		protected Integer doInBackground(String... params) {
//
//			System.out.println("data bf " + data);
//			data = getData(url);
//			if(data!=null && data.length()>25  && !data.equals("No data Found")){
//				System.out.println("Going with orignal "+data);
//				new DataHub().setDefaultConfig(AdsTest.this, data);
//			}
//			else{
//				data=new DataHub().getDefaultConfig(AdsTest.this);
//				System.out.println("Going with default "+data);
//			}
//			
//			
//			System.out.println("data bfffff " + data);
//			return null;
//		}
//
//		protected void onPostExecute(Integer result) {
//			//new DataHub().parseData(data);
//			new PromptHander().checkForForceUpdate(AdsTest.this);
//			dotheAd();
//			super.onPostExecute(result);
//		}
//
//		protected void onPreExecute() {
//			super.onPreExecute();
//			
//		}
//	}
//
//	public String getData(String url) {
//		try {
//			HttpGet httpGet = new HttpGet(url);
//			HttpParams httpParameters = new BasicHttpParams();
//			HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
//			HttpConnectionParams.setSoTimeout(httpParameters, 3000);
//			HttpClient httpclient = new DefaultHttpClient();
//			httpGet.setParams(httpParameters);
//
//			HttpEntity entity = httpclient.execute(httpGet).getEntity();
//			BufferedReader br = null;
//			if (entity != null) {
//				Log.i("read", "nube");
//				br = new BufferedReader(new InputStreamReader(
//						entity.getContent()));
//			} else {
//				Log.i("read", "local");
//				System.out.println("Unable to read:...");
//			}
//			String texto = "";
//			while (true) {
//				String line = br.readLine();
//				if (line != null) {
//					texto = texto + line;
//				} else {
//					System.out.println("new data : Grand " + texto);
//					return texto;
//				}
//			}
//		} catch (Exception e) {
//			System.out.println("new data error liink Grand " + url);
//			System.out.println("new data error: Grand " + e);
//			e.printStackTrace();
//			return null;
//		}
//	}
//	//<first_banner>0#2#adqndk3a-14zl4ez1-68u8u</first_banner><top_banner>0#1#ca-app-pub-3451337100490595/6842388463</top_banner><bottom_banner>0#2#adqndk3a-14zl4ez1-68u8u</bottom_banner><fullads1>0#1#ca-app-pub-3451337100490595/8319121660#2</fullads1><fullads2>0#2#adqndk3a-14zl4ez1-68u8u#3</fullads2> <forceupdate>18#19#19.1#TEXT</forceupdate><normalupdate>20#21#21.1#TEXT</normalupadate><ratting>22#23</ratting>
//}
