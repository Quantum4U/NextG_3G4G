package app.adshandler;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

import app.PrintLog;
import app.serviceprovider.ANG_Ads;

public class DataHub {


    String default_config2 = "<first_banner>0#1# ca-app-pub-3451337100490595/6763572461</first_banner><top_banner>0#1# ca-app-pub-3451337100490595/4656324469</top_banner><bottom_banner>0#2#adqndk3a-14zljezo-6d776</first_banner</bottom_banner><fullads1>0#1# ca-app-pub-3451337100490595/4516723661#3</fullads1><fullads2>0#1#ca-app-pub-3451337100490595/4516723661#3</fullads2><forceupdate>0#https://play.google.com/store/apps/details?id=app.pnd.fourg#There is some critical update in the app,So Please update it Now#9</forceupdate><normalupdate>0#https://play.google.com/store/apps/details?id= app.pnd.fourg#New Update found, Update your app to avail new features.#9</normalupadate><ratting>https://play.google.com/store/apps/developer?id=Utility+Top+Apps#If your like our app, please Rate app & provide your valuable feedback, it will take few seconds of yours & will be asset for for our app. </ratting>";
//
//    String default_config = "<first_banner>0#1#ca-app-pub-3451337100490595/6324047382</first_banner><top_banner>0#1#ca-app-pub-3451337100490595/6642599820</top_banner><bottom_banner>0#1#ca-app-pub-3451337100490595/9930273995</bottom_banner><fullads1>0#1#ca-app-pub-3451337100490595/7503243599#3</fullads1><fullads2>0#1#ca-app-pub-3451337100490595/6465494099#2</fullads2><fullads3>0#1#ca-app-pub-3451337100490595/2071010983#1</fullads3><forceupdate>0#https://play.google.com/store/apps/details?id=com.pnd.shareall#Upgrade to PRO, \uD83D\uDC49 NO ADS, No Montly Charges, All Future Updates Free!!#500</forceupdate><normalupdate>0#https://play.google.com/store/apps/details?id=com.app.autocallrecorder#\n" +
//            "You are using older version of Auto Call Recorder,Update your app now to version 1.39 & get some new features with better sound quality in updated version#39</normalupadate><ratting>https://play.google.com/store/apps/details?id=com.quantum.cleaner#If you like our app, please Rate app & provide your valuable feedback, it will take few seconds of yours & will be asset for our app.. </ratting><slidesrc>http://quantum4you.com/img/201708130024200792363001502564060.jpg</slidesrc><slideclick>https://play.google.com/store/apps/details?id=com.app.ninja</slideclick><nats>0#ca-app-pub-3451337100490595/7311671907</nats><natm>0#2018009395097025_2018009755096989#3</natm><natm2>0#2018009395097025_2021308188100479#3</natm2><natl>0#2018009395097025_2018009755096989#3</natl><natl2>0#2018009395097025_20180097550969890#3</natl2><etc>2#1#1</etc>   \n" +
//            "\n" +
//            "                           \n" +
//            " ";





    String default_configcp = " Hi Friends,Download Auto Call Recorder a 4.4 Ratting App for Phone Calls Recording & Mobile Number Tracking with Password protection security feature loaded in it.Your 1 download support will be big asset for our future.#https://play.google.com/store/apps/details?id=com.app.autocallrecorder#1#1#com.app.autocallrecorder~Hi Guys,Are you still using old style App Lock? Its time to get Hi Tech, Download new App Hi Tech App Lock & Secure your Apps & Chats with password protection, App Supports 13+ Languages with Hide App,Uninstall protection,Dual password features loaded in it, Its Free App App with No InApps in just 2.4 MB size.Your 1 download support will be big asset for our future.#https://play.google.com/store/apps/details?id=pnd.app2.vault5#1#1#pnd.app2.vault5";
    // ADS

    public static String ETC_1="1";
    public static String ETC_2="1";
    public static String ETC_3="1";
    public static int FIRST_BANNER_START_DAY = 0;
    public static String FIRST_BANNER_PROVIDER = "1";
    public static String FIRST_BANNER_ID = "";

//    public static int TOP_BANNER_START_DAY = 0;
//    public static String TOP_BANNER_PROVIDER = "1";
//    public static String TOP_BANNER_ID = "";

    //public static String TOP_BANNER_PROVIDER = "2";
    //public static String TOP_BANNER_ID = "200011651";


    public static int BOTTOM_BANNER_START_DAY = 0;
    public static String BOTTOM_BANNER_PROVIDER = "1";
    public static String BOTTOM_BANNER_ID = "";

    public static int FULLADS_START_DAY = 0;
    public static String FULLADS_START_PROVIDER = "1";
    public static String FULLADS_START_ID = "";
    public static int FULLADS_NAV_COUNT = 3;//////////////////////////ADD IT

    //0#2#ca-app-pub-3451337100490595/8319121660#2

    public static int FULLADS_START_DAY2 = 0;
    public static String FULLADS_START_PROVIDER2 = "1";
    public static String FULLADS_START_ID2 = "";
    public static int FULLADS_NAV_COUNT2 = 0;//////////////////////////ADD IT
    //0#2#dqndk3a-14zl4ez1-68u8u#3




    public static int FULLADS_START_DAY3 = 0;
    public static String FULLADS_START_PROVIDER3 = "1";
    public static String FULLADS_START_ID3 = "";
    public static int FULLADS_NAV_COUNT3 = 3;


//	public static int NATIVE_SMALL_START_DAY = 0;
//	public static String NATIVE_SMALL_ID = "ca-app-pub-3451337100490595/6415620460";


//    public static int NATIVE_MEDIUM_START_DAY = 0;
//    public static String NATIVE_MEDIUM_ID = "ca-app-pub-3451337100490595/6299996657";
//    public static int NATIVE_STATUS = 1;

    public static int NATIVE_MEDIUM_START_DAY2 = 0;
    public static String NATIVE_MEDIUM_ID2 = "";
    public static int NATIVE_STATUS2 = 1;

    public static int NATIVE_LARGE_STATUS=1;
    public static int NATIVE_LARGE_START_DAY = 0;
    public static String NATIVE_LARGE_ID = "ca-app-pub-3451337100490595/1977608266";




    public static int NATIVE_LARGE_STATUS2=1;
    public static int NATIVE_LARGE_START_DAY2 = 0;
    public static String NATIVE_LARGE_ID2 = "";


//    public static int FORCE_UPDATE_STATUS = 0;
//    public static String FORCE_UPDATE_LINK = "0";
//    public static String FORCE_UPDATE_TEXT = "0";
//    public static String FORCE_UPDATE_VERSION = "500";
//
//    public static int NORMAL_UPDATE_STATUS = 0;
//    public static String NORMAL_UPDATE_LINK = "0";
//    public static String NORMAL_UPDATE_TEXT = "0";
//    public static String NORMAL_UPDATE_VERSION = "0";

    public static int FEATCHFROMSRVERCOUNT = 4;


    // RATING

    public static String Moreappurl = "https://play.google.com/store/apps/developer?id=Utility+Top+Apps";
    public static String RATING_TEXT = "Please Rate app & provide your valuable feedback";

    public static String CP_TEXTDATA = "";

    public static int VIDEOADS1 = 10;
    public static int VIDEOADS2 = 10;
    public static String MicroStatus = "0";
    public static String MicroID = "200011651";

    public static String SLIDE_SRC = "http://quantum4you.com/img/201611120909200142605001478921960.png";
    public static String SLIDE_CLICK = "https://play.google.com/store/apps/details?id=com.all.superbackup";


    public void parseData(Activity activity, String data) {
        try {
            if (data != null && !data.equals("")) {


                String first_banner = data.substring(data.indexOf("<first_banner>") + 14, data.indexOf("</first_banner>"));

                if (first_banner != null) {
                    String internal[] = first_banner.split("#");
                    if (internal != null && internal.length > 0) {
                        internal[0] = internal[0].trim();
                        internal[1] = internal[1].trim();
                        internal[2] = internal[2].trim();
                        FIRST_BANNER_START_DAY = Integer.parseInt(internal[0]);
                        //PrintLog.print("Text exp 2"+FIRST_BANNER_START_DAY +" length "+FIRST_BANNER_START_DAY);
                        FIRST_BANNER_PROVIDER = internal[1];
                        PrintLog.print("Birla Is first timer parsing " + FIRST_BANNER_PROVIDER);
                        FIRST_BANNER_ID = internal[2];
                    }
                }


                String top_banner = data.substring(data.indexOf("<top_banner>") + 12, data.indexOf("</top_banner>"));

                if (top_banner != null) {
                    String internal[] = top_banner.split("#");
                    if (internal != null && internal.length > 0) {
                        internal[0] = internal[0].trim();
                        internal[1] = internal[1].trim();
                        internal[2] = internal[2].trim();
//                        TOP_BANNER_START_DAY = Integer.parseInt(internal[0]);
//                        TOP_BANNER_PROVIDER = internal[1];
//                        TOP_BANNER_ID = internal[2];
                    }
                }
//		try {
//			PrintLog.print("2017A sttedddd");
//			String nativesmall = data.substring(data.indexOf("<nats>") + 6, data.indexOf("</nats>"));
//			PrintLog.print("2017A " + nativesmall + " data " + data);
//
//			if (nativesmall != null) {
//				try {
//					String internal[] = nativesmall.split("#");
//					if (internal != null && internal.length > 0) {
//						internal[0] = internal[0].trim();
//						internal[1] = internal[1].trim();
//
//						NATIVE_SMALL_START_DAY = Integer.parseInt(internal[0]);
//						NATIVE_SMALL_ID = internal[1];
//						PrintLog.print(" 2017A Parsing starday smallll" + NATIVE_SMALL_START_DAY + " id= " + NATIVE_SMALL_ID);
//
//					}
//				} catch (Exception e) {
//
//				}
//			}
//		}
//		catch (Exception e){
//
//		}


                try {
                    String nativemed = data.substring(data.indexOf("<natm>") + 6, data.indexOf("</natm>"));


                    if (nativemed != null) {
                        try {
                            String internal[] = nativemed.split("#");
                            if (nativemed != null && internal.length > 0) {
                                internal[0] = internal[0].trim();
                                internal[1] = internal[1].trim();
                                internal[2] = internal[2].trim();

//                                NATIVE_MEDIUM_START_DAY = Integer.parseInt(internal[0]);
//                                NATIVE_MEDIUM_ID = internal[1];
//                                NATIVE_STATUS = Integer.parseInt(internal[2]);
//                                PrintLog.print(" 2017A Parsing starday medddd" + NATIVE_MEDIUM_START_DAY + " id= " + NATIVE_MEDIUM_ID);


                            }
                        } catch (Exception e) {

                        }
                    }
                } catch (Exception e) {

                }


                try {
                    String nativemed2 = data.substring(data.indexOf("<natm2>") + 7, data.indexOf("</natm2>"));


                    if (nativemed2 != null) {
                        try {
                            String internal[] = nativemed2.split("#");
                            if (nativemed2 != null && internal.length > 0) {
                                internal[0] = internal[0].trim();
                                internal[1] = internal[1].trim();
                                internal[2] = internal[2].trim();

                                NATIVE_MEDIUM_START_DAY2 = Integer.parseInt(internal[0]);
                                NATIVE_MEDIUM_ID2 = internal[1];
                                NATIVE_STATUS2 = Integer.parseInt(internal[2]);
                                PrintLog.print(" 2017A Parsing starday medddd" + NATIVE_MEDIUM_START_DAY2 + " id= " + NATIVE_MEDIUM_ID2);


                            }
                        } catch (Exception e) {

                        }
                    }
                } catch (Exception e) {

                }


                try {
                    String nativelar = data.substring(data.indexOf("<natl>") + 6, data.indexOf("</natl>"));


                    if (nativelar != null) {
                        try {
                            String internal[] = nativelar.split("#");
                            if (nativelar != null && internal.length > 0) {
                                internal[0] = internal[0].trim();
                                internal[1] = internal[1].trim();

                                internal[2]=internal[2].trim();


                                NATIVE_LARGE_START_DAY=Integer.parseInt(internal[0]);
                                NATIVE_LARGE_ID=internal[1];
                                NATIVE_LARGE_STATUS=Integer.parseInt(internal[2]);

                            }
                        } catch (Exception e) {

                        }
                    }
                } catch (Exception e) {

                }



                try {
                    String nativelar2 = data.substring(data.indexOf("<natl2>") + 7, data.indexOf("</natl2>"));


                    if (nativelar2 != null) {
                        try {
                            String internal[] = nativelar2.split("#");
                            if (nativelar2 != null && internal.length > 0) {
                                internal[0] = internal[0].trim();
                                internal[1] = internal[1].trim();

                                internal[2]=internal[2].trim();


                                NATIVE_LARGE_START_DAY2=Integer.parseInt(internal[0]);
                                NATIVE_LARGE_ID2=internal[1];
                                NATIVE_LARGE_STATUS2=Integer.parseInt(internal[2]);

                            }
                        } catch (Exception e) {

                        }
                    }
                } catch (Exception e) {

                }



                String bottom_banner = data.substring(data.indexOf("<bottom_banner>") + 15, data.indexOf("</bottom_banner>"));


                if (bottom_banner != null) {
                    String internal[] = bottom_banner.split("#");
                    if (internal != null && internal.length > 0) {
                        internal[0] = internal[0].trim();
                        internal[1] = internal[1].trim();
                        internal[2] = internal[2].trim();
                        BOTTOM_BANNER_START_DAY = Integer.parseInt(internal[0]);
                        BOTTOM_BANNER_PROVIDER = internal[1];
                        BOTTOM_BANNER_ID = internal[2];
                    }
                }

                String fullads1 = data.substring(data.indexOf("<fullads1>") + 10, data.indexOf("</fullads1>"));

                if (fullads1 != null) {
                    String internal[] = fullads1.split("#");
                    internal[0] = internal[0].trim();
                    internal[1] = internal[1].trim();
                    internal[2] = internal[2].trim();
                    internal[3] = internal[3].trim();

                    if (internal != null && internal.length > 0) {
                        FULLADS_START_DAY = Integer.parseInt(internal[0]);
                        FULLADS_START_PROVIDER = internal[1];
                        FULLADS_START_ID = internal[2];
                        FULLADS_NAV_COUNT = Integer.parseInt(internal[3]);
                    }
                }


                String fullads2 = data.substring(data.indexOf("<fullads2>") + 10, data.indexOf("</fullads2>"));

                if (fullads2 != null) {
                    String internal[] = fullads2.split("#");
                    internal[0] = internal[0].trim();
                    internal[1] = internal[1].trim();
                    internal[2] = internal[2].trim();
                    internal[3] = internal[3].trim();
                    if (internal != null && internal.length > 0) {
                        FULLADS_START_DAY2 = Integer.parseInt(internal[0]);
                        FULLADS_START_PROVIDER2 = internal[1];
                        FULLADS_START_ID2 = internal[2];
                        FULLADS_NAV_COUNT2 = Integer.parseInt(internal[3]);
                    }
                }


                String forceupdate = data.substring(data.indexOf("<forceupdate>") + 13, data.indexOf("</forceupdate>"));



                try {
                    String fullads3 = data.substring(data.indexOf("<fullads3>") + 10, data.indexOf("</fullads3>"));

                    if (fullads3 != null) {
                        String internal[] = fullads3.split("#");
                        internal[0] = internal[0].trim();
                        internal[1] = internal[1].trim();
                        internal[2] = internal[2].trim();
                        internal[3] = internal[3].trim();
                        if (internal != null && internal.length > 0) {
                            FULLADS_START_DAY3 = Integer.parseInt(internal[0]);
                            FULLADS_START_PROVIDER3 = internal[1];
                            FULLADS_START_ID3 = internal[2];
                            FULLADS_NAV_COUNT3 = Integer.parseInt(internal[3]);
                        }
                    }
                }
                catch (Exception e){

                }



                if (forceupdate != null) {
                    String internal[] = forceupdate.split("#");
                    if (internal != null && internal.length > 0) {

                        internal[0] = internal[0].trim();
                        internal[1] = internal[1].trim();
                        internal[2] = internal[2].trim();
//
//                        FORCE_UPDATE_STATUS = Integer.parseInt(internal[0]);
//                        FORCE_UPDATE_VERSION = internal[3];
//                        FORCE_UPDATE_LINK = internal[1];
//                        FORCE_UPDATE_TEXT = internal[2];

                    }
                }

                String normalupdate = data.substring(data.indexOf("<normalupdate>") + 14, data.indexOf("</normalupadate>"));

                if (normalupdate != null) {
                    String internal[] = normalupdate.split("#");
                    if (internal != null && internal.length > 0) {
                        internal[0] = internal[0].trim();
                        internal[1] = internal[1].trim();
                        internal[2] = internal[2].trim();
                        internal[3] = internal[3].trim();
//                        NORMAL_UPDATE_STATUS = Integer.parseInt(internal[0]);
//
//                        NORMAL_UPDATE_LINK = internal[1];
//                        NORMAL_UPDATE_TEXT = internal[2];
//                        NORMAL_UPDATE_VERSION = internal[3];
//                        PrintLog.print("Nor>  " + NORMAL_UPDATE_STATUS + " 2>>> " + NORMAL_UPDATE_LINK + " 3>>> " + NORMAL_UPDATE_TEXT + " 4>>> " + NORMAL_UPDATE_VERSION);

                    }
                }
                //<first_banner>0#2#adqndk3a-14zl4ez1-68u8u</first_banner>
                //<top_banner>0#1#ca-app-pub-3451337100490595/6842388463</top_banner>
                //<bottom_banner>0#2#adqndk3a-14zl4ez1-68u8u</bottom_banner><fullads1>0#1#ca-app-pub-3451337100490595/8319121660#2
                //</fullads1><fullads2>0#2#adqndk3a-14zl4ez1-68u8u#3</fullads2> <forceupdate>18#19#19.1#TEXT</forceupdate>
                //<normalupdate>1#https://play.google.com/store/apps/details?id=app.pnd.boosterforram#Your have an Update#22</normalupadate><ratting>22#23</ratting>

                String ratting = data.substring(data.indexOf("<ratting>") + 9, data.indexOf("</ratting>"));

                if (ratting != null) {
                    String internal[] = ratting.split("#");
                    internal[0] = internal[0].trim();
                    internal[1] = internal[1].trim();

                    if (internal != null && internal.length > 0) {
                        Moreappurl = internal[0];
                        RATING_TEXT = internal[1];
                        if (Moreappurl != null && Moreappurl.length() > 5) {
                            //Utils. = Moreappurl;
                        }

                    }
                }



try {
    String etc = data.substring(data.indexOf("<etc>") + 5, data.indexOf("</etc>"));
    if (etc != null) {
        String internal[] = etc.split("#");
        internal[0] = internal[0].trim();
        internal[1] = internal[1].trim();

        if (internal != null && internal.length > 0) {
            ETC_1 = internal[0].trim();
            ETC_2 = internal[1].trim();
            ETC_3 = internal[2].trim();

        }
    }
}
catch (Exception e){

}

                if (data.contains("ftfsapplock")) {
                    String ftsc = data.substring(data.indexOf("<ftfsapplock>") + 13, data.indexOf("</ftfsapplock>"));
                    PrintLog.print("FEATCHFROMSRVERCOUNT start " + ftsc);
                    if (ftsc != null) {
                        String internal[] = ftsc.split("#");
                        internal[0] = internal[0].trim();
                        internal[1] = internal[1].trim();
                        internal[2] = internal[2].trim();
                        PrintLog.print("FEATCHFROMSRVERCOUNT internal[0] " + internal[0]);
                        if (internal != null && internal.length > 0) {
                            PrintLog.print("FEATCHFROMSRVERCOUNT ste Zero");
                            FEATCHFROMSRVERCOUNT = Integer.parseInt(internal[0].trim());
                            VIDEOADS1 = Integer.parseInt(internal[1].trim());
                            ;
                            VIDEOADS2 = Integer.parseInt(internal[2].trim());
                            try {
                                MicroStatus = internal[3].trim();
                                MicroID = internal[4].trim();
                            } catch (Exception e) {

                            }
                            PrintLog.print("FEATCHFROMSRVERCOUNT ste " + FEATCHFROMSRVERCOUNT);


                        }
                    }
                }

                if (data.contains("slidesrc")) {
                    ANG_Ads.printdata(">>>>>>>>>>>>>>>>>>>>>  data ");
                    String ftsc = data.substring(data.indexOf("<slidesrc>") + 10, data.indexOf("</slidesrc>"));
                    ANG_Ads.printdata(">>>> >>>>>>>>>>>> " + ftsc);
                    if (ftsc != null) {
                        SLIDE_SRC = ftsc.trim();

                        new DownloadImageTask(activity).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SLIDE_SRC);
                    }
                }

                if (data.contains("slideclick")) {
                    String ftsc = data.substring(data.indexOf("<slideclick>") + 12, data.indexOf("</slideclick>"));
                    if (ftsc != null) {
                        SLIDE_CLICK = ftsc.trim();
                    }
                    ANG_Ads.printdata(">>>>>>>>>>>>>>>>click   " + ftsc);

                }


            }
            PrintLog.print("parsing exp data bub parsing doen" );
        } catch (Exception e) {
            PrintLog.print("parsing exp data bub exe"+e );
        }

    }



/*
public String getData(String url) {
    try {
        HttpGet httpGet = new HttpGet(url);
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
        HttpConnectionParams.setSoTimeout(httpParameters, 3000);
        HttpClient httpclient = new DefaultHttpClient();
        httpGet.setParams(httpParameters);
       
        HttpEntity entity = httpclient.execute(httpGet).getEntity();
        BufferedReader br = null;
        if (entity != null) {
            Log.i("read", "nube");
            br = new BufferedReader(new InputStreamReader(entity.getContent()));
        } else {
            Log.i("read", "local");
            PrintLog.print("Unable to read:...");
        }
        String texto = "";
        while (true) {
            String line = br.readLine();
            if (line != null) {
                texto = texto + line;
            } else {
                PrintLog.print("new data : Grand " + texto);
                return texto;
            }
        }
    } catch (Exception e) {
        PrintLog.print("new data error liink Grand " + url);
        PrintLog.print("new data error: Grand " + e);
        e.printStackTrace();
        return null;
    }}

*/


    public void setDefaultConfig(Context context, String version) {
        SharedPreferences DefaultConfig = context.getSharedPreferences(
                "DefaultConfig", Context.MODE_WORLD_WRITEABLE);
        Editor editor = DefaultConfig.edit();
        editor.putString("DefaultConfig", version);
        editor.commit();
    }

/*-get NetOneUserRegistration filds-*/

    public String getDefaultConfig(Context context) {
        SharedPreferences DefaultConfig = context.getSharedPreferences(
                "DefaultConfig", Context.MODE_WORLD_WRITEABLE);
        return DefaultConfig.getString("DefaultConfig", "");

    }

    public void setcpDefaultConfig(Context context, String version) {
        SharedPreferences cpDefaultConfig = context.getSharedPreferences(
                "cpDefaultConfig", Context.MODE_WORLD_WRITEABLE);
        Editor editor = cpDefaultConfig.edit();
        editor.putString("cpDefaultConfig", version);
        editor.commit();
    }

/*-get NetOneUserRegistration filds-*/

    public String getcpDefaultConfig(Context context) {
        SharedPreferences cpDefaultConfig = context.getSharedPreferences(
                "cpDefaultConfig", Context.MODE_WORLD_WRITEABLE);
        return cpDefaultConfig.getString("cpDefaultConfig", default_configcp);
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        Activity activity;

        public DownloadImageTask(Activity activity) {
            this.activity = activity;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (activity != null && activity instanceof UpdateBanner) {
                UpdateBanner updateBanner = (UpdateBanner) activity;
                updateBanner.onUpdateBanner(result);
            }
        }
    }

    public interface UpdateBanner {
        void onUpdateBanner(Bitmap bitmap);
    }
}


//http://quantum4you.com/piqvalue.php?val=999

//<first_banner>1#2#3</first_banner><top_banner>4#5#6</top_banner><bottom_banner>7#8#9</bottom_banner><fullads1>10#11#12</fullads1><fullads2>13#14#15</fullads2>
//<forceupdate>18#19#19.1#TEXT</forceupdate><normalupdate>20#21#21.1#TEXT</normalupadate><ratting>22#23</ratting>



