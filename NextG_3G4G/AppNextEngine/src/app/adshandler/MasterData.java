package app.adshandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.LinearLayout;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import app.PrintLog;
import app.serviceprovider.ANG_PictUtil;
import app.serviceprovider.Utils;

//import org.apache.http.HttpEntity;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;

public class MasterData {

    boolean isImageLoaded = false;
    String data = "";
    String url = "http://quantum4you.com/piqvalue.php?val=";
    Activity context;
    LinearLayout adslayout, adslayout2;
    String PID_VAL = "999";
    String PID_CP = "999";
    Utils utils;
    ANG_PromptHander promptHander;
    AHandler aHandler;

    String cp_data = null;
    int cp_count = -1;
    String cp_message = "";
    String cp_AppName = "";
    String cp_APPIcon_URL = "";
    String cp_showPlayStore = "";
    public static String cp_link = "";
    int cp_first_count = -1;

    String cp_currentpackage = "";
    String CP_STATUS = "1";

    String tempdata[];
    String cpadsdata[];
    public static Bitmap cp_ImageadsBitMap;
    boolean featch_server;
    boolean callnativemed_;


    public MasterData(Activity ctx) {
        context = ctx;
        utils = new Utils();
        // inludeserver=inserver;
    }
//
//    public void getMasterData(Context context, String pid, LinearLayout adl,
//                              String CPUID, LinearLayout ads2, boolean feathfromserver, boolean callnativemed) {
//        callnativemed_ = callnativemed;
//        featch_server = feathfromserver;
//new AHandler().initEngine((Activity) context);
//        adslayout2 = ads2;
//       // Utils.current_package = context.getPackageName();
//        PID_CP = CPUID;
//        utils = new Utils();
//        PID_VAL = pid;
//        promptHander = new PromptHander();
//        adslayout = adl;
//        aHandler = new AHandler();
//        new GPID().execute();
//       // DataHub.Is_App_Exit=false;
//
//    }

    class GPID extends AsyncTask<String, Integer, Integer> {
        boolean showpromo = true;

        protected Integer doInBackground(String... params) {
            try {

                data = getData(url + "" + PID_VAL);
                PrintLog.print("2017 PID_VAL server link" + url + PID_VAL + " data = " + data);

                DataHub.CP_TEXTDATA = getData(url + "" + PID_CP);
                PrintLog.print("Prompt Test curren " + data + " featch_server " + featch_server);
                if (featch_server || data == null) {
                    if (data != null && data.length() > 25
                            && !data.equals("No data Found")) {
                        PrintLog.print("Prompt Test curren in to else ifffffdf#####");
                        new DataHub().setDefaultConfig(context, data);
                        if (DataHub.CP_TEXTDATA != null)
                            new DataHub().setcpDefaultConfig(context, DataHub.CP_TEXTDATA);
                    } else {
                        data = new DataHub().getDefaultConfig(context);
                        DataHub.CP_TEXTDATA = new DataHub()
                                .getcpDefaultConfig(context);
                    }
                } else {
                    PrintLog.print("Prompt Test curren in to else");
                    data = new DataHub().getDefaultConfig(context);
                    DataHub.CP_TEXTDATA = new DataHub()
                            .getcpDefaultConfig(context);
                }

//                int current = new Utils().get_FeatchFromServerCount(context);
//                if (featch_server == false) {
//                 //   PrintLog.print("Prompt Test current " + current + " server " + DataHub.FEATCHFROMSRVERCOUNT);
//
//                    if (current % DataHub.FEATCHFROMSRVERCOUNT == 0)
//                        showpromo = true;
//                    else
//                        showpromo = false;
//                    new Utils().set_FeatchFromServerCount(context, current + 1);
//                }

            } catch (Exception e) {

            }
            return null;
        }

        protected void onPostExecute(Integer result) {
            //  new DataHub().parseData(data);

            try {
                PrintLog.print("Prompt Test current showpromo " + showpromo);
                if (showpromo)
                    //     manageCp(DataHub.CP_TEXTDATA, context,DataHub.CP_START);
                    if (featch_server) {
                        promptHander.checkForNormalUpdate(context);
                        promptHander.checkForForceUpdate(context);
                    }

                if (utils.get_ISFIRSTTIME(context)) {
                    //	PrintLog.print("Is first timer");
                    if (adslayout != null) {
                        if (callnativemed_)
                            adslayout.addView(aHandler.showNativeMedium(context));
//                        else
//                            adslayout.addView(aHandler.getBannerFirstAds(context));
                    }
                } else {

                    if (adslayout != null) {
                        if (callnativemed_)
                            adslayout.addView(aHandler.showNativeMedium(context));
                        else
                            adslayout.addView(aHandler.getBannerHeader(context));


                    }

                }
                //	Ads.printdata(" banner footer adslayout2"+adslayout2);
//                if (adslayout2 != null)
//                    adslayout2.addView(aHandler.getBannerFooter2(context));
                PrintLog.print("featch_server " + featch_server);

            } catch (Exception e) {
                PrintLog.print("featch_serverexxxx " + e);
            }

            // new PromptHander().checkForForceUpdate(context);

            super.onPostExecute(result);
        }

        protected void onPreExecute() {
            super.onPreExecute();

        }
    }


    public static String getData(String myurl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    // Reads an InputStream and converts it to a String.
    public static String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(stream);
        char[] buffer = new char[1024];
        int length;
        while ((length = reader.read(buffer)) != -1) {
            builder.append(buffer, 0, length);
        }
        return builder.toString();
    }


    //	String cp_AppName = "";
//	String cp_APPIcon_URL = "";
//	String cp_showPlayStore = "";
    public void manageCp(String data, Context context, String cp_from) {


        data = data.trim();
        try {
            PrintLog.print("Manage CP data = " + data);
            Utils utils = new Utils();
            int currentlaunccount = utils.get_count(context);
//            if(cp_from.equals(DataHub.CP_START)) {
//
//
//
//                utils.set_count(context, currentlaunccount + 1);
//            }

            if (data != null && data.length() > 0) {
                cpadsdata = data.split("~");
                tempdata = cpadsdata[0].split("#");
                cp_message = tempdata[0];
                cp_link = tempdata[1];
                cp_count = Integer.parseInt(tempdata[2]);
                cp_first_count = Integer.parseInt(tempdata[3]);
                cp_currentpackage = tempdata[4];

                cp_AppName = tempdata[5];
                cp_APPIcon_URL = tempdata[6];
                cp_showPlayStore = tempdata[7];
                CP_STATUS = tempdata[8];


                cp_currentpackage = cp_currentpackage.trim();
                PrintLog.print("Manage CP cp_first_count = "
                        + cp_first_count + " cp_count " + cp_count);

                if (!isPackageInstalled(cp_currentpackage, context)) {
                    if (((currentlaunccount == cp_first_count) || (currentlaunccount
                            % cp_count == 0))
                            && !context.getPackageName().equals(
                            cp_currentpackage)) {
//                        if(cp_from.equals(CP_STATUS) && DataHub.Is_App_Exit==false)
//                        showPromptCP(cp_message, cp_link, context, cp_AppName, cp_APPIcon_URL, cp_showPlayStore);
                    }
                } else {
                    tempdata = cpadsdata[1].split("#");
                    cp_message = tempdata[0];
                    // cp_message="http://dotcommate.com/wp-content/uploads/2015/05/android-dot-com-mate.jpeg";
                    cp_link = tempdata[1];
                    cp_count = Integer.parseInt(tempdata[2]);
                    cp_first_count = Integer.parseInt(tempdata[3]);
                    cp_currentpackage = tempdata[4];


                    cp_AppName = tempdata[5];
                    cp_APPIcon_URL = tempdata[6];
                    cp_showPlayStore = tempdata[7];
                    CP_STATUS = tempdata[8];


                    cp_currentpackage = cp_currentpackage.trim();
                    PrintLog.print("Manage CP 22 cp_first_count = "
                            + cp_first_count + " cp_count " + cp_count);

                    PrintLog.print("Manage CP 22 !isPackageInstalled(cp_currentpackage " + !isPackageInstalled(cp_currentpackage, context));
                    PrintLog.print("Manage CP 22 currentlaunccount " + currentlaunccount);
                    PrintLog.print("Manage CP 22 cp_first_count " + cp_first_count);

                    PrintLog.print("Manage CP 22 cp_count " + cp_count);

                    //  PrintLog.print("Manage CP 22 currentlaunccount "+currentlaunccount);

                    if (!isPackageInstalled(cp_currentpackage, context)) {
                        if (((currentlaunccount == cp_first_count) || (currentlaunccount
                                % cp_count == 0))
                                && !context.getPackageName().equals(
                                cp_currentpackage)) {
                            PrintLog.print("Manage CP 22 STATUS  " + CP_STATUS + " cp_from " + cp_from);
//                            if(cp_from.equals(CP_STATUS) && DataHub.Is_App_Exit==false)
//                            showPromptCP(cp_message, cp_link, context, cp_AppName, cp_APPIcon_URL, cp_showPlayStore);


                        }
                    }
                }
            }
        } catch (Exception e) {

        }
//        if(cp_from.equals(DataHub.CP_EXIT)) {
//            DataHub.Is_App_Exit = true;
//        }

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

    public void showPromptCP(String message, final String link,
                             final Context context, String Appname, String Appdownloadurl, String showplaystore) {

        if (message != null)
            message = message.trim();
        if (!message.startsWith("http")) {
//			AlertDialog prompt = new AlertDialog.Builder(context).create();
//			prompt.setIcon(context.getResources().getDrawable(
//					R.drawable.downloadsy));
//			prompt.setTitle("New App");
//			prompt.setMessage(message);
//
//			prompt.setButton("Download Now!",
//					new DialogInterface.OnClickListener() {
//						public void onClick(DialogInterface dia, int arg1) {
//							Intent browserIntent = new Intent(
//									Intent.ACTION_VIEW, Uri.parse(link));
//							browserIntent
//									.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//							context.startActivity(browserIntent);
//						}
//					});
//
//			prompt.show();

            Intent intent = new Intent(context, PromotionActivity.class);
            intent.putExtra("appname", Appname);
            intent.putExtra("appiconurl", Appdownloadurl);
            intent.putExtra("prompttext", message);
            intent.putExtra("clicklink", link);
            intent.putExtra("showplaystore", showplaystore);
            context.startActivity(intent);
        } else {
//            String oldimage = utils.get_AdsImageURL(context);
//            PrintLog.print("Imageads oldimage " + oldimage);
//            if (!oldimage.equals(message)) {
//                isImageLoaded = false;
//            } else {
//                isImageLoaded = true;
//            }
//            new DownloadImage().execute();


            FullPagePromo.src = message;
            FullPagePromo.link = link;
            Intent ac = new Intent(context, FullPagePromo.class);
            context.startActivity(ac);

            //

        }

    }

    class DownloadImage extends AsyncTask<String, Integer, Integer> {

        protected Integer doInBackground(String... params) {
            if (isImageLoaded) {
                cp_ImageadsBitMap = ANG_PictUtil.Ads_loadFromCacheFile();
                PrintLog.print("Imageads cp_ImageadsBitMap "
                        + cp_ImageadsBitMap);

                if (cp_ImageadsBitMap == null) {
                    featchNewImage();
                }
            } else {
                featchNewImage();
            }

            return null;
        }

        protected void onPostExecute(Integer result) {
            Intent intent = new Intent(context, FullPagePromo.class);
            context.startActivity(intent);
            super.onPostExecute(result);
        }

        protected void onPreExecute() {
            super.onPreExecute();

        }
    }

    public void featchNewImage() {
        try {
            URL url = new URL(cp_message);
            InputStream is = new BufferedInputStream(url.openStream());
            cp_ImageadsBitMap = BitmapFactory.decodeStream(is);
            ANG_PictUtil.Ads_saveToCacheFile(cp_ImageadsBitMap);
            utils.set_AdsImageURL(context, cp_message);
        } catch (Exception e) {

        }
    }

    // pkgid/src/link/id

    // <first_banner>0#2#adqndk3a-14zl4ez1-68u8u</first_banner><top_banner>0#1#ca-app-pub-3451337100490595/6842388463</top_banner><bottom_banner>0#2#adqndk3a-14zl4ez1-68u8u</bottom_banner><fullads1>0#1#ca-app-pub-3451337100490595/8319121660#2</fullads1><fullads2>0#2#adqndk3a-14zl4ez1-68u8u#3</fullads2>
    // <forceupdate>18#19#19.1#TEXT</forceupdate><normalupdate>20#21#21.1#TEXT</normalupadate><ratting>22#23</ratting>
}
