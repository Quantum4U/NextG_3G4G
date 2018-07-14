package app.pnd.fourg;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.adshandler.AHandler;
import app.adshandler.MasterData;
import app.adshandler.ANG_PromptHander;
import app.pnd.fourg.history.AppSharedPreferences;
import app.serviceprovider.Utils;
import com.appnextg.fourg.R;
import version.ux.ImageLoader;

public class RootFragment extends Fragment {
   // boolean isref=false;
    TelephonyManager telephonyManager;
    private static final String TAG = "RootFragment";
    LayoutInflater infalInflater;
    Context context;
    ArrayList<String> data;
    Display mDisplay;
    ListView list;
    public static int DEVICE_H = 800;
    ImageLoader image;
    public int ad_counter;

    private AppSharedPreferences appSharedPreferences;
    private AdptView adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.root_fragment, container, false);
        image = new ImageLoader(getActivity());
       // doEngineWork(view);
        telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        data = new ArrayList<String>();

        mDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        DEVICE_H = mDisplay.getHeight();
        infalInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        appSharedPreferences = new AppSharedPreferences(getActivity());
        // transaction.replace(R.id.root_frame, new FirstFragment());

        transaction.commit();
        list = (ListView) view.findViewById(R.id.listView1);
        init_Data();
        adapter = new AdptView();
        list.setAdapter(adapter);
        refreshRoot();
       // adslayout1 = (LinearLayout) view.findViewById(R.id.adsbanner);
      //  adslayout1.addView(new AHandler().showNativesmall(getActivity()));
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        System.out.print("2017 frag20 onAttach");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.print("2017 frag20 onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.print("2017 frag20 onStart");
    }



    private AHandler aHandler;
    private LinearLayout adslayout1;
    private ANG_PromptHander promptHander;
    private Utils utils;


    private void doEngineWork(View view) {
        aHandler = new AHandler();
        adslayout1 = (LinearLayout) view.findViewById(R.id.adsbanner);
        promptHander = new ANG_PromptHander();
        utils = new app.serviceprovider.Utils();
//        new MasterData(getActivity()).getMasterData(getActivity(), App_PID,
//                adslayout1, PID_CP, null, true);
       // adslayout1.addView(new AHandler().showNativesmall(getActivity()));
    }


    @Override
    public void onResume() {
        super.onResume();
        System.out.println("groot onresume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("groot onSaveInstanceState");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        System.out.println("groot onViewStateRestored");
    }

    public void refreshRoot() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        context = activity;
        super.onAttach(activity);
        System.out.println("groot onattach");
    }

    class AdptView extends BaseAdapter {
        private String[] text = {"Phone Model:","",  "Device Manufacturer:",
                "Phone Resolution:", "Device IMEI:", "Android OS Name:",
                "API Level:", "Device Network Country ISO:"};

//        private String[] text = context.getResources().getStringArray(R.array.root_array);

        private int[] icon = {R.drawable.version_3g,R.drawable.version_3g,
                R.drawable.version_3g, R.drawable.version_3g,
                R.drawable.version_3g, R.drawable.version_3g,
                R.drawable.version_3g, R.drawable.version_3g};

        private int[] color1 = {Color.GREEN, Color.MAGENTA, Color.BLUE,
                Color.CYAN, Color.GREEN, Color.BLACK, Color.RED};

        public int getCount() {
            // TODO Auto-generated method stub
            System.out.println("Test Got Size " + data.size());
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            Holder appholder;
            if (convertView == null) {
                appholder = new Holder();
                view = infalInflater.inflate(R.layout.version_listdata, null);

                appholder.name = (TextView) view
                        .findViewById(R.id.tv_phone_post);
                System.out.println("Setting text as " + data.get(position));
                appholder.text = (TextView) view
                        .findViewById(R.id.tv_phone_pre);
                appholder.icon = (ImageView) view.findViewById(R.id.iv_phone);
                appholder.appLayout = (LinearLayout) view.findViewById(R.id.linearLayout1);
                appholder.quant_ad = (TextView) view.findViewById(R.id.quant_ad);
                appholder.nativeads = (LinearLayout) view.findViewById(R.id.nativeads);

                view.setTag(appholder);
//            } else {
//                appholder = (Holder) view.getTag();
//            }




//            if (position == 1) {
//
//                appholder.name.setVisibility(View.VISIBLE);
//                appholder.quant_ad.setVisibility(View.VISIBLE);
//                appholder.icon.setVisibility(View.VISIBLE);
//                appholder.appLayout.setVisibility(View.VISIBLE);
//                appholder.name.setVisibility(View.VISIBLE);
//                appholder.nativeads.setVisibility(View.GONE);
//
//                appholder.quant_ad.setVisibility(View.VISIBLE);
//                try {
//                    System.out.println("here is my app 0" + " " + appSharedPreferences.getQUANTUM_AppName(0));
//                    System.out.println("here is my app 1" + " " + appSharedPreferences.getQUANTUM_AppName(1));
//                    System.out.println("here is my app 2" + " " + appSharedPreferences.getQUANTUM_AppName(2));
//                    System.out.println("here is my count" + " " + appSharedPreferences.getRootCounter());
//                    System.out.println("here is my package exist 0" + " " + isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0)));
//                    System.out.println("here is my package exist 1" + " " + isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0)));
//                    System.out.println("here is my package exist 2" + " " + isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0)));
//                    if (appSharedPreferences.getRootCounter() == 0 || appSharedPreferences.getRootCounter() == 3) {
//                        System.out.println("here is my bhanu 0");
//                        if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                            appholder.name.setText("");
//                            appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(0));
//                            image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(0), R.drawable.downloadsy, appholder.icon);
//                        } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                            appholder.name.setText("");
//                            appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(1));
//                            image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(1), R.drawable.downloadsy, appholder.icon);
//                        } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                            appholder.name.setText("");
//                            appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(2));
//                            image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(1), R.drawable.downloadsy, appholder.icon);
//                        } else {
//                            appholder.name.setText("");
//                            appholder.text.setText("Recommended Apps");
//                            appholder.icon.setImageResource(R.drawable.downloadsy);
//                        }
//
//                    } else if (appSharedPreferences.getRootCounter() == 1) {
//                        System.out.println("here is my bhanu 1");
//                        if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                            System.out.println("here is my bhanu 1 asas" + " " + appSharedPreferences.getQUANTUM_AppName(2));
//                            appholder.name.setText("");
//                            appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(2));
//                            image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(2), R.drawable.downloadsy, appholder.icon);
//                        } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                            appholder.name.setText("");
//                            appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(1));
//                            image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(1), R.drawable.downloadsy, appholder.icon);
//                        } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                            appholder.name.setText("");
//                            appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(0));
//                            image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(0), R.drawable.downloadsy, appholder.icon);
//                        } else {
//                            appholder.name.setText("");
//                            appholder.text.setText("Recommended For You");
//                            appholder.icon.setImageResource(R.drawable.downloadsy);
//                        }
//
//                    } else if (appSharedPreferences.getRootCounter() == 2) {
//                        System.out.println("here is my bhanu 2");
//                        if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                            appholder.name.setText("");
//                            appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(1));
//                            image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(1), R.drawable.downloadsy, appholder.icon);
//                        } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                            appholder.name.setText("");
//                            appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(2));
//                            image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(2), R.drawable.downloadsy, appholder.icon);
//                        } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                            appholder.name.setText("");
//                            appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(0));
//                            image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(0), R.drawable.downloadsy, appholder.icon);
//                        } else {
//                            appholder.name.setText("");
//                            appholder.text.setText("Recommended For You");
//                            appholder.icon.setImageResource(R.drawable.downloadsy);
//                        }
//
//                    }
//
//                    appholder.appLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
////                        new Utils().moreApps(getActivity());
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            if (appSharedPreferences.getRootCounter() == 0 || appSharedPreferences.getRootCounter() == 3) {
//                                System.out.println("here is my bhanu 0 onclick");
//                                if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                                    try {
//                                        intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(0)));
//                                        startActivity(intent);
//                                    } catch (ActivityNotFoundException e) {
//                                    }
//                                } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                                    try {
//                                        intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(1)));
//                                        startActivity(intent);
//                                    } catch (ActivityNotFoundException e) {
//                                    }
//                                } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                                    try {
//                                        intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(2)));
//                                        startActivity(intent);
//                                    } catch (ActivityNotFoundException e) {
//                                    }
//                                } else {
//                                    new Utils().moreApps(getActivity());
//                                }
//
//                            } else if (appSharedPreferences.getRootCounter() == 1) {
//                                System.out.println("here is my bhanu 1");
//                                if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                                    try {
//                                        System.out.println("here is my bhanu 1 asas" + " " + appSharedPreferences.getQUANTUM_AppName(2));
//                                        intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(2)));
//                                        startActivity(intent);
//                                    } catch (ActivityNotFoundException e) {
//                                    }
//                                } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                                    try {
//                                        intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(1)));
//                                        startActivity(intent);
//                                    } catch (ActivityNotFoundException e) {
//                                    }
//                                } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                                    try {
//                                        intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(0)));
//                                        startActivity(intent);
//                                    } catch (ActivityNotFoundException e) {
//                                    }
//                                } else {
//                                    new Utils().moreApps(getActivity());
//                                }
//
//                            } else if (appSharedPreferences.getRootCounter() == 2) {
//                                System.out.println("here is my bhanu 2");
//                                if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                                    try {
//                                        intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(1)));
//                                        startActivity(intent);
//                                    } catch (ActivityNotFoundException e) {
//                                    }
//                                } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                                    try {
//                                        intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(2)));
//                                        startActivity(intent);
//                                    } catch (ActivityNotFoundException e) {
//                                    }
//                                } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                                    try {
//                                        intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(0)));
//                                        startActivity(intent);
//                                    } catch (ActivityNotFoundException e) {
//                                    }
//                                } else {
//                                    new Utils().moreApps(getActivity());
//                                }
//
//                            }
//
//
//                        }
//                    });
//                } catch (Exception e) {
//                    System.out.println("exception in position == 1 root fragment" + " " + e);
//                }
//            }

            if (position == 1) {
                // if(isref==false) {
                System.out.print("Call from Root f");
                View v=new AHandler().getBannerHeader(getActivity());
                System.out.print("Call from Root view "+v);
                appholder.nativeads.addView(v);
                appholder.nativeads.setVisibility(View.VISIBLE);

                appholder.name.setVisibility(View.GONE);
                appholder.quant_ad.setVisibility(View.GONE);
                appholder.icon.setVisibility(View.GONE);
                appholder.appLayout.setVisibility(View.GONE);
                appholder.name.setVisibility(View.GONE);
                // isref=true;
                // }


            } else {
                appholder.name.setVisibility(View.VISIBLE);
                appholder.quant_ad.setVisibility(View.GONE);
                appholder.icon.setVisibility(View.VISIBLE);
                appholder.appLayout.setVisibility(View.VISIBLE);
                appholder.name.setVisibility(View.VISIBLE);
                appholder.nativeads.setVisibility(View.GONE);

                appholder.name.setText(data.get(position));
                appholder.text.setText(text[position]);
                appholder.icon.setImageDrawable(getActivity().getResources()
                        .getDrawable(icon[position]));
            }
        }
            return view;
        }

    }

    public class Holder {
        TextView name, text, quant_ad;
        ImageView icon;
        LinearLayout appLayout,nativeads;

    }

    public void init_Data() {
        data.add(" " + Build.MODEL);
        data.add(" " + "");
        data.add(" " + Vendor());
        data.add(" " + mDisplay.getWidth() + " * " + mDisplay.getHeight());

        data.add(" " + getImei());
        data.add(" " + AndroidOSName());
        // data.add(" " + AndroidOS());
        data.add(" " + getAPI());
        data.add(" " + getNetworkCountryIso());
        // data.add(" " + getIP());
        // data.add(" " + getDataState());
        // data.add(" " + getMac());

        // data.add(" " + isRoaming());

    }

    // public void init_Data() {
    // data.add(" " + android.os.Build.MODEL);
    // data.add(" " + Vendor());
    // data.add(" " + mDisplay.getWidth() + " * " + mDisplay.getHeight());
    //
    // data.add(" " + getImei());
    // data.add(" " + AndroidOSName());
    // data.add(" " + AndroidOS());
    //
    // data.add(" " + getNetworkCountryIso());
    // data.add(" " + getIP());
    // data.add(" " + getDataState());
    // data.add(" " + getMac());
    //
    // data.add(" " + isRoaming());
    //
    // }

    // public void init_Data() {
    // data.add("" + getResources().getString(R.string.pindi_pindi_phonemodle)
    // + " : " + android.os.Build.MODEL);
    // data.add("" + getResources().getString(R.string.pindi_devicemanuf)
    // + " : " + Vendor());
    // data.add("" + getResources().getString(R.string.pindi_phoneresolution)
    // + " : " + mDisplay.getWidth() + " * " + mDisplay.getHeight());
    //
    // data.add("" + getResources().getString(R.string.pindi_deviceimei)
    // + " : " + getImei());
    // data.add("" + getResources().getString(R.string.pindi_androidosname)
    // + " : " + AndroidOSName());
    // data.add("" + getResources().getString(R.string.pindi_apilevel) + " : "
    // + AndroidOS());
    //
    // data.add("" + getResources().getString(R.string.pindi_devicenetworkiso)
    // + "  : " + getNetworkCountryIso());
    // data.add("" + getResources().getString(R.string.pindi_deviceip) + " : "
    // + getIP());
    // data.add("" + getResources().getString(R.string.pindi_mobiledata)
    // + " : " + getDataState());
    // data.add("" + getResources().getString(R.string.pindi_devicemacads)
    // + " : " + getMac());
    //
    // data.add("" + getResources().getString(R.string.pindi_roaming) + " : "
    // + isRoaming());
    //
    // }

    public String getImei() {

        return telephonyManager.getDeviceId();
    }

    public String getSoftwareV() {
        return telephonyManager.getDeviceSoftwareVersion();
    }

    public String getNetworkCountryIso() {
        return new String(telephonyManager.getNetworkCountryIso())
                .toUpperCase();

    }

    public String getAPI() {
        return Integer.toString(Build.VERSION.SDK_INT);
    }

    public String getDataState() {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        int a = telephonyManager.getDataState();
        System.out.println("Got data as " + a);
        if (a == 2)
            return "Enabled";
        else
            return "Disabled";
    }

    public String getIP() {

        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String IP = wInfo.getMacAddress();

        return IP;

    }

    public String getMac() {

        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();

        return macAddress;

    }

    public String isRoaming() {
        boolean state;
        try {
            // return true or false if data roaming is enabled or not
            state = Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.DATA_ROAMING) == 1;
        } catch (Exception e) {
            // return null if no such settings exist (device with no radio data
            // ?)
            return null;

        }
        if (!state)
            return "Roaming Enabled";
        else
            return "Not Roaming";
    }

    public String AndroidOS() {

        return Build.VERSION.SDK;

    }

    public String AndroidOSName() {

        String os = Build.VERSION.SDK;
        System.out.println("here is my os" + " " + os);
        if (os.equals("23")) {
            return "Marshmallow";
        } else if (os.equals("21")) {
            return "Lollipop";
        } else if (os.equals("22")) {
            return "LOLLIPOP_MR1";
        } else if (os.equals("20")) {
            return "KitKat";
        } else if (os.equals("19")) {
            return "KitKat";
        } else if (os.equals("18")) {
            return "Jelly Bean";
        } else if (os.equals("17")) {

            return "Jelly Bean";
        } else if (os.equals("16")) {
            return "Jelly Bean";
        } else if (os.equals("15")) {
            return "Ice Cream Sandwich";
        } else if (os.equals("14")) {
            return "Ice Cream Sandwich";
        } else if (os.equals("13")) {
            return "Honeycomb";
        } else if (os.equals("12")) {
            return "Honeycomb";
        } else if (os.equals("11")) {
            return "Honeycomb";
        } else if (os.equals("10")) {
            return "Gingerbread";
        } else if (os.equals("9")) {
            return "Froyo";
        } else if (os.equals("8")) {
            return "Froyo";
        } else {
            return "Not Found";
        }

    }

    public String Vendor() {

        return Build.MANUFACTURER;
    }

    public String getBrand() {

        return Build.BRAND;
    }

    public boolean isPackageExisted(String targetPackage) {
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = getActivity().getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage))
                return true;
        }
        return false;
    }
}
