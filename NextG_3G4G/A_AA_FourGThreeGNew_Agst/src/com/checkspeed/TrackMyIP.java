package com.checkspeed;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.appnextg.fourg.R;

public class TrackMyIP extends Fragment {
    ProgressDialog pDialog;
    Context context;
    private ProgressDialog progressDialog;
    static final int DIALOG_WAIT_PROGRESS = 1;

    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private ConnectivityManager connectivityManager;
    private NetworkInfo wifi_connectivity, mobile_connectivity;
    private String strPrivateIP = null, strPublicIP = null, strMACAddress = null;

    TextView txtViewForPrivateIP, txtViewForPublicIP, txtViewForMACAddress;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.track_my_ip, container, false);


        txtViewForPrivateIP = (TextView) view.findViewById(R.id.txtViewForPrivateIP);
        txtViewForPublicIP = (TextView) view.findViewById(R.id.txtViewForPublicIP);
        txtViewForMACAddress = (TextView) view.findViewById(R.id.txtViewForMACAddress);

        new TrackingIP().execute();
        return view;
    }

    class TrackingIP extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(10000);

                connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                wifi_connectivity = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                mobile_connectivity = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                wifiInfo = wifiManager.getConnectionInfo();

                strPrivateIP = getPrivateIpAddress();
                strPublicIP = getPublicIpAddress();
                strMACAddress = wifiInfo.getMacAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(Void result) {
            pDialog.cancel();

            if (strPrivateIP != null) {
                txtViewForPrivateIP.setText(strPrivateIP);
            } else {
                txtViewForPrivateIP.setText("- . - . - . -");
            }

            if (strPublicIP != null) {
                txtViewForPublicIP.setText(strPublicIP);
            } else {
                txtViewForPublicIP.setText("- . - . - . -");
            }

            if (strMACAddress != null) {
                txtViewForMACAddress.setText(strMACAddress);
            } else {
                txtViewForMACAddress.setText("- : - : - : - : - : -");
            }
        }

        ;
    }

    public String getPrivateIpAddress() {
        if (wifi_connectivity.isConnected()) {
            return Formatter.formatIpAddress(wifiInfo.getIpAddress());
        } else if (mobile_connectivity.isConnected()) {
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
//		                    return inetAddress.getHostAddress().toString();
                            return Formatter.formatIpAddress(inetAddress.hashCode());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        return null;
    }

    public String getPublicIpAddress() {
        String ip = null;
        try {
//        	HttpClient httpclient = new DefaultHttpClient();
//        	HttpGet httpget = new HttpGet("http://api.externalip.net/ip/");
//        	HttpResponse response= httpclient.execute(httpget);
//        	HttpEntity entity = response.getEntity();
//        	entity.getContentLength();
//        	ip = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }


    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_WAIT_PROGRESS:
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Tracking Your IP...");
                progressDialog.setMessage("Please Wait...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(false);
                progressDialog.show();
                return progressDialog;
            default:
                return null;
        }
    }

    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context = activity;
    }
}
