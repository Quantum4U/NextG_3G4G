package app.campaign;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import app.fcm.MapperUtils;
import app.server.v2.DataHubConstant;

import static com.smaato.soma.SOMA.getPackageName;

/**
 * Created by hp on 7/20/2017.
 */
public class CampaignConstant {

    private final String CAMPAIGN_URL = "http://quantum4you.com/engine/dashboard/newbanners?app_id=shareall2";


    public static final String FORMAT_IMAGE = "image";
    public static final String FORMAT_TEXT = "text";

    public static final String CAMPAIGN_TYPE_URL = "URL";
    public static final String CAMPAIGN_TYPE_DEEPLINK = "DEEPLINK";
    public static final String CAMPAIGN_TYPE_ADS = "ADS";

    public static final String ADS_BANNER = "BANNER";
    public static final String ADS_NATIVE_LARGE = "N_L";
    public static final String ADS_NATIVE_MEDIUM = "N_M";
    public static final String ADS_NATIVE_SMALL = "N_S";

    public static final String DEEPLINK_KEY = MapperUtils.keyValue;


    private Context mContext;

    public CampaignConstant(Context c) {
        this.mContext = c;
    }

    public String requestServerForResult() {
        StringBuilder sb = new StringBuilder();

        try {
            URL url = null;

            System.out.println("URL_CAMPAIGN" + " " + CAMPAIGN_URL);
            url = new URL(CAMPAIGN_URL);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = null;
            while ((str = in.readLine()) != null) {
                sb.append(str + "\n");
            }
            in.close();
        } catch (MalformedURLException e) {
            System.out.println("Exception MalformedURL" + " " + e);
        } catch (IOException e) {
            System.out.println("Exception IOException" + " " + e);
        }
        return sb.toString();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("campaign.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void openURL(Context c, String url) {
        PackageManager manager = c.getPackageManager();
        if (url != null && !url.equalsIgnoreCase("") && url.contains("http://")) {
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
            if (intent.resolveActivity(manager) != null)
                c.startActivity(intent);
            else
                Toast.makeText(mContext, "Oops! No application found to handle this", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDeepLink(Context c, String deeplink) {
        // Toast.makeText(mContext, "DeepLinke Id" + " " + deeplink, Toast.LENGTH_SHORT).show();
//        c.startActivity(new Intent(c, MapperActivity.class).
//                putExtra(CampaignConstant.DEEPLINK_KEY, deeplink));

        Intent intent = new Intent(DataHubConstant.CUSTOM_ACTION);
        intent.addCategory(getPackageName());
        intent.putExtra(CampaignConstant.DEEPLINK_KEY, deeplink);
        c.startActivity(intent);
    }

    public String readFromAssets(String filename) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(mContext.getAssets().open(filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // do reading, usually loop until end of file reading
        StringBuilder sb = new StringBuilder();
        String mLine = null;
        try {
            mLine = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (mLine != null) {
            sb.append(mLine); // process line
            try {
                mLine = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("check for logs 01");
        return sb.toString();
    }
}
