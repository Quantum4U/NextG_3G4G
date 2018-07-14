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
import android.os.Bundle;
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
import android.widget.ScrollView;
import android.widget.TextView;

import app.ads.UtilsApp;
import app.adshandler.AHandler;
import app.pnd.fourg.history.AppSharedPreferences;
import app.serviceprovider.Utils;
import version.ux.ImageLoader;
import com.appnextg.fourg.R;

public class StaticFragment extends Fragment {
    private TelephonyManager telephonyManager;
    private LayoutInflater infalInflater;
    private Context context;
    private ArrayList<String> data2;
    private Display mDisplay;
    private ListView list;
    private String encodedHash = Uri.encode("#");
    private int AIRTEL = 1, IDEA = 2, RELIENCE = 3, VODAFONE = 4, AIRCEL = 5,
            TATA = 6, BSNL = 7, TELENOR = 8, VIDEOCON = 9, MTNL = 10, MTS = 11;
    private int CURRENT_PROVIDER = 1;
    private LinearLayout checkmainblc, checkdatablc, checkoffers, checknumber;

    private String arraymainblance[] = {};
    private String ussd;
    private ScrollView scrollViews;
    private static final String TAG = "StaticFragment";
    private AppSharedPreferences appSharedPreferences;
    private ImageLoader image;
    private AdptView adapter;
    private LinearLayout adsbanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.version_root_fragment2,
                container, false);
        appSharedPreferences = new AppSharedPreferences(getActivity());
        image = new ImageLoader(getActivity());


        //adsbanner = (LinearLayout) view.findViewById(R.id.adsbanner);
//
//        adsbanner.addView(new AHandler().getBannerHeader(getActivity()));

        telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        data2 = new ArrayList<String>();
        // scrollView = (ScrollView) view.findViewById(R.id.scrollView1);
        mDisplay = ((Activity) context).getWindowManager().getDefaultDisplay();
        infalInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        checkmainblc = (LinearLayout) view.findViewById(R.id.checkbalance);

        checkdatablc = (LinearLayout) view.findViewById(R.id.checkdata);

        checkoffers = (LinearLayout) view.findViewById(R.id.ckeckbestoffer);

        checknumber = (LinearLayout) view.findViewById(R.id.checkmobnumber);

        checkmainblc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AHandler().showFullAds(getActivity(), false);
                if (CURRENT_PROVIDER == AIRTEL)
                    ussd = "*" + "123" + encodedHash;
                else if (CURRENT_PROVIDER == IDEA)
                    ussd = "*" + "121" + encodedHash;
                else if (CURRENT_PROVIDER == VODAFONE)
                    ussd = "*" + "141" + encodedHash;
                else if (CURRENT_PROVIDER == RELIENCE)
                    ussd = "*" + "367" + encodedHash;
                else if (CURRENT_PROVIDER == AIRCEL)
                    ussd = "*" + "125" + encodedHash;
                else if (CURRENT_PROVIDER == TATA)
                    ussd = "*" + "111" + encodedHash;
                else if (CURRENT_PROVIDER == BSNL)
                    ussd = "*" + "123" + encodedHash;
                else if (CURRENT_PROVIDER == TELENOR)
                    ussd = "*" + "222" + "*2" + encodedHash;
                else if (CURRENT_PROVIDER == VIDEOCON)
                    ussd = "*" + "123" + encodedHash;
                else if (CURRENT_PROVIDER == MTNL)
                    ussd = "*" + "444" + encodedHash;
                else if (CURRENT_PROVIDER == MTS)
                    ussd = "*" + "123" + encodedHash;

                System.out.println("USSD is  " + ussd);
                startActivityForResult(new Intent("android.intent.action.CALL",
                        Uri.parse("tel:" + ussd)), 1);

            }
        });

        checkdatablc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AHandler().showFullAds(getActivity(), false);

                if (CURRENT_PROVIDER == AIRTEL)
                    ussd = "*" + "123" + "*11" + encodedHash;
                else if (CURRENT_PROVIDER == IDEA)
                    ussd = "*" + "125" + encodedHash;
                else if (CURRENT_PROVIDER == VODAFONE)
                    ussd = "*" + "111" + "*6" + "*2" + encodedHash;
                else if (CURRENT_PROVIDER == RELIENCE)
                    ussd = "*" + "367" + "*3" + encodedHash;
                else if (CURRENT_PROVIDER == AIRCEL)
                    ussd = "*" + "126" + "*4" + encodedHash;
                else if (CURRENT_PROVIDER == TATA)
                    ussd = "*" + "111" + "*1" + encodedHash;
                else if (CURRENT_PROVIDER == BSNL)
                    ussd = "*" + "112" + encodedHash;
                else if (CURRENT_PROVIDER == TELENOR)
                    ussd = "*" + "363" + "*4" + encodedHash;
                else if (CURRENT_PROVIDER == VIDEOCON)
                    ussd = "*" + "141" + encodedHash;
                else if (CURRENT_PROVIDER == MTNL)
                    ussd = "*" + "446" + encodedHash;
                System.out.println("USSD is  " + ussd);
                startActivityForResult(new Intent("android.intent.action.CALL",
                        Uri.parse("tel:" + ussd)), 1);

            }
        });

        checkoffers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AHandler().showFullAds(getActivity(), false);
                if (CURRENT_PROVIDER == AIRTEL)
                    ussd = "*" + "121" + encodedHash;
                else if (CURRENT_PROVIDER == IDEA)
                    ussd = ussd = "*" + "111" + encodedHash;
                else if (CURRENT_PROVIDER == VODAFONE)
                    ussd = ussd = "*" + "121" + encodedHash;
                else if (CURRENT_PROVIDER == RELIENCE)
                    ussd = ussd = "*" + "777" + encodedHash;
                else if (CURRENT_PROVIDER == AIRCEL)
                    ussd = ussd = "*" + "789" + encodedHash;
                else if (CURRENT_PROVIDER == TATA)
                    ussd = "*" + "191" + "*9" + "*8" + encodedHash;

                startActivityForResult(new Intent("android.intent.action.CALL",
                        Uri.parse("tel:" + ussd)), 1);

            }
        });

        checknumber.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AHandler().showFullAds(getActivity(), false);
                if (CURRENT_PROVIDER == AIRTEL)
                    ussd = "*" + "282" + encodedHash;
                else if (CURRENT_PROVIDER == IDEA)
                    ussd = "*" + "789" + encodedHash;
                else if (CURRENT_PROVIDER == VODAFONE)
                    ussd = "*" + "111" + "*2" + encodedHash;
                else if (CURRENT_PROVIDER == RELIENCE)
                    ussd = "*" + "1" + encodedHash;
                else if (CURRENT_PROVIDER == AIRCEL)
                    ussd = "*" + "1" + encodedHash;
                else if (CURRENT_PROVIDER == TATA)
                    ussd = "*" + "1" + encodedHash;
                System.out.println("USSD is  " + ussd);
                startActivityForResult(new Intent("android.intent.action.CALL",
                        Uri.parse("tel:" + ussd)), 1);
            }
        });

        // transaction.replace(R.id.root_frame, new FirstFragment());

        list = (ListView) view.findViewById(R.id.listView1);
        init_Data();
        adapter = new AdptView();
        list.setAdapter(adapter);

        return view;
    }

    public void refreshStatic() {
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        adapter.notify();
        System.out.println("finally static adapter notify");
    }


    @Override
    public void onAttach(Activity activity) {
        context = activity;
        super.onAttach(activity);
    }

    class AdptView extends BaseAdapter {
//        private String[] text = context.getResources().getStringArray(R.array.static_array);

        private String[] text = {"Operator Name:", "SIM Number:", "QUANTUM:",
                "SIM CountryISO:", "SIM Operator Code:"};


        private int[] icon = {R.drawable.version_3g,
                R.drawable.version_3g, R.drawable.downloadsy, R.drawable.version_3g,
                R.drawable.version_3g};

        private int[] color1 = {Color.GREEN, Color.MAGENTA, Color.BLUE,
                Color.CYAN};

        public int getCount() {
            // TODO Auto-generated method stub
            System.out.println("Test Got Size " + data2.size());
            return data2.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView;
            Holder appholder;
            if (convertView == null) {
                appholder = new Holder();
                view = infalInflater.inflate(R.layout.version_listdata, null);

                appholder.name = (TextView) view
                        .findViewById(R.id.tv_phone_post);
                appholder.text = (TextView) view.findViewById(R.id.tv_phone_pre);
                appholder.icon = (ImageView) view.findViewById(R.id.iv_phone);
                appholder.appLayout = (LinearLayout) view.findViewById(R.id.linearLayout1);
                appholder.nativeads = (LinearLayout) view.findViewById(R.id.nativeads);
                appholder.quant_ad = (TextView) view.findViewById(R.id.quant_ad);


//                if (position == 2) {
//                    try {
//                        appholder.name.setVisibility(View.VISIBLE);
//                        appholder.quant_ad.setVisibility(View.VISIBLE);
//                        appholder.icon.setVisibility(View.VISIBLE);
//                        appholder.appLayout.setVisibility(View.VISIBLE);
//                        appholder.name.setVisibility(View.VISIBLE);
//                        appholder.nativeads.setVisibility(View.GONE);
//                        appholder.quant_ad.setVisibility(View.VISIBLE);
//                        System.out.println("here is my app 0" + " " + appSharedPreferences.getQUANTUM_AppName(0));
//                        System.out.println("here is my app 1" + " " + appSharedPreferences.getQUANTUM_AppName(1));
//                        System.out.println("here is my app 2" + " " + appSharedPreferences.getQUANTUM_AppName(2));
//                        System.out.println("here is my count" + " " + appSharedPreferences.getSimCounter());
//                        System.out.println("here is my package exist 0" + " " + isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0)));
//                        System.out.println("here is my package exist 1" + " " + isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0)));
//                        System.out.println("here is my package exist 2" + " " + isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0)));
//                        if (appSharedPreferences.getSimCounter() == 0 || appSharedPreferences.getSimCounter() == 3) {
//                            System.out.println("here is my bhanu 0");
//                            if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                                appholder.name.setText("");
//                                appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(0));
//                                image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(0), R.drawable.downloadsy, appholder.icon);
//                            } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                                appholder.name.setText("");
//                                appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(1));
//                                image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(1), R.drawable.downloadsy, appholder.icon);
//                            } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                                appholder.name.setText("");
//                                appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(2));
//                                image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(1), R.drawable.downloadsy, appholder.icon);
//                            } else {
//                                appholder.name.setText("");
//                                appholder.text.setText("Recommended Apps");
//                                appholder.icon.setImageResource(R.drawable.downloadsy);
//                            }
//
//                        } else if (appSharedPreferences.getSimCounter() == 1) {
//                            System.out.println("here is my bhanu 1");
//                            if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                                System.out.println("here is my bhanu 1 asas" + " " + appSharedPreferences.getQUANTUM_AppName(2));
//                                appholder.name.setText("");
//                                appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(2));
//                                image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(2), R.drawable.downloadsy, appholder.icon);
//                            } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                                appholder.name.setText("");
//                                appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(1));
//                                image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(1), R.drawable.downloadsy, appholder.icon);
//                            } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                                appholder.name.setText("");
//                                appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(0));
//                                image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(0), R.drawable.downloadsy, appholder.icon);
//                            } else {
//                                appholder.name.setText("");
//                                appholder.text.setText("Recommended For You");
//                                appholder.icon.setImageResource(R.drawable.downloadsy);
//                            }
//
//                        } else if (appSharedPreferences.getSimCounter() == 2) {
//                            System.out.println("here is my bhanu 2");
//                            if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                                appholder.name.setText("");
//                                appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(1));
//                                image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(1), R.drawable.downloadsy, appholder.icon);
//                            } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                                appholder.name.setText("");
//                                appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(2));
//                                image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(2), R.drawable.downloadsy, appholder.icon);
//                            } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                                appholder.name.setText("");
//                                appholder.text.setText(appSharedPreferences.getQUANTUM_AppName(0));
//                                image.DisplayImage(appSharedPreferences.getQUANTUM_SRC(0), R.drawable.downloadsy, appholder.icon);
//                            } else {
//                                appholder.name.setText("");
//                                appholder.text.setText("Recommended For You");
//                                appholder.icon.setImageResource(R.drawable.downloadsy);
//                            }
//
//                        }
//
//                        appholder.appLayout.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
////                        new Utils().moreApps(getActivity());
//                                Intent intent = new Intent(Intent.ACTION_VIEW);
//                                if (appSharedPreferences.getSimCounter() == 0 || appSharedPreferences.getSimCounter() == 3) {
//                                    System.out.println("here is my bhanu 0 onclick");
//                                    if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                                        try {
//                                            intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(0)));
//                                            startActivity(intent);
//                                        } catch (ActivityNotFoundException e) {
//                                        }
//                                    } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                                        try {
//                                            intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(1)));
//                                            startActivity(intent);
//                                        } catch (ActivityNotFoundException e) {
//                                        }
//                                    } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                                        try {
//                                            intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(2)));
//                                            startActivity(intent);
//                                        } catch (ActivityNotFoundException e) {
//                                        }
//                                    } else {
//                                        new Utils().moreApps(getActivity());
//                                    }
//
//                                } else if (appSharedPreferences.getSimCounter() == 1) {
//                                    System.out.println("here is my bhanu 1");
//                                    if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                                        System.out.println("here is my bhanu 1 asas" + " " + appSharedPreferences.getQUANTUM_AppName(2));
//                                        try {
//                                            intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(2)));
//                                            startActivity(intent);
//                                        } catch (ActivityNotFoundException e) {
//                                        }
//                                    } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                                        try {
//                                            intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(1)));
//                                            startActivity(intent);
//                                        } catch (ActivityNotFoundException e) {
//                                        }
//                                    } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                                        try {
//                                            intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(0)));
//                                            startActivity(intent);
//                                        } catch (ActivityNotFoundException e) {
//                                        }
//                                    } else {
//                                        new Utils().moreApps(getActivity());
//                                    }
//
//                                } else if (appSharedPreferences.getSimCounter() == 2) {
//                                    System.out.println("here is my bhanu 2");
//                                    if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(1))) {
//                                        try {
//                                            intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(1)));
//                                            startActivity(intent);
//                                        } catch (ActivityNotFoundException e) {
//                                        }
//                                    } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(2))) {
//                                        try {
//                                            intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(2)));
//                                            startActivity(intent);
//                                        } catch (ActivityNotFoundException e) {
//                                        }
//                                    } else if (!isPackageExisted(appSharedPreferences.getQUANTUM_PKG(0))) {
//                                        try {
//                                            intent.setData(Uri.parse(appSharedPreferences.getQUANTUM_CLICK(0)));
//                                            startActivity(intent);
//                                        } catch (ActivityNotFoundException e) {
//                                        }
//                                    } else {
//                                        new Utils().moreApps(getActivity());
//                                    }
//
//                                }
//
//
//                            }
//                        });
//                    } catch (Exception e) {
//                        System.out.println("Exception is static fragment" + " " + e);
//                    }
//                }e

                 if(position==2){
                     System.out.print("Call from Static f");
                     appholder.nativeads.addView(new AHandler().getBannerHeader(getActivity()));
                    appholder.nativeads.setVisibility(View.VISIBLE);

                    appholder.name.setVisibility(View.GONE);
                    appholder.quant_ad.setVisibility(View.GONE);
                    appholder.icon.setVisibility(View.GONE);
                    appholder.appLayout.setVisibility(View.GONE);
                    appholder.name.setVisibility(View.GONE);






                }


                else {
                    appholder.name.setVisibility(View.VISIBLE);
                    appholder.quant_ad.setVisibility(View.VISIBLE);
                    appholder.icon.setVisibility(View.VISIBLE);
                    appholder.appLayout.setVisibility(View.VISIBLE);
                    appholder.name.setVisibility(View.VISIBLE);
                    appholder.nativeads.setVisibility(View.GONE);
//                    pre==text ; post==name
                    // post.setTextColor(color1[position]);
                    appholder.quant_ad.setVisibility(View.GONE);

                    appholder.text.setText(text[position]);

                    // post.setTextColor(color1[position]);
                    System.out.println("Setting text as " + data2.get(position));

                    appholder.icon.setImageDrawable(getActivity().getResources().getDrawable(
                            this.icon[position]));

                    appholder.name.setText(data2.get(position));
                    if (position == 1) {
                        appholder.name.setTextSize(14);
                    }
                }
            }
            return view;
        }

    }

    public static String operator;

    public class Holder {
        TextView name, text, quant_ad;
        ImageView icon;
        LinearLayout appLayout,nativeads;
    }

    public void init_Data() {
        data2.add("" + getSIMoperatorNmae());

        data2.add("" + getSIMNumber());
        data2.add("" + "QUANTUM ADS");
        data2.add("" + getSimCountryIso());
        data2.add("" + getSimOperatorCode());

        operator = getSIMoperatorNmae();
        System.out.println(" CURRENT_PROVIDER " + operator);
        operator = operator.toLowerCase();

        if (operator.contains("airtel"))
            CURRENT_PROVIDER = AIRTEL;
        else if (operator.contains("idea"))
            CURRENT_PROVIDER = IDEA;
        else if (operator.contains("relia"))
            CURRENT_PROVIDER = RELIENCE;
        else if (operator.contains("vodaf"))
            CURRENT_PROVIDER = VODAFONE;
        else if (operator.contains("aircel"))
            CURRENT_PROVIDER = AIRCEL;
        else if (operator.contains("tata"))
            CURRENT_PROVIDER = TATA;
        else if (operator.contains("bsnl"))
            CURRENT_PROVIDER = BSNL;
        else if (operator.contains("telenor"))
            CURRENT_PROVIDER = TELENOR;
        else if (operator.contains("videocon"))
            CURRENT_PROVIDER = VIDEOCON;
        else if (operator.contains("mtnl"))
            CURRENT_PROVIDER = MTNL;
        else if (operator.contains("mts"))
            CURRENT_PROVIDER = MTS;
        else
            // scrollView.setVisibility(View.GONE);
            System.out.println(" CURRENT_PROVIDER " + CURRENT_PROVIDER);
    }

    // public void init_Data() {
    // data2.add("" + getResources().getString(R.string.pindi_operatorname)
    // + " : " + getSIMoperatorNmae());
    //
    // data2.add("" + getResources().getString(R.string.pindi_simnumber)
    // + " : " + getSIMNumber());
    // data2.add("" + getResources().getString(R.string.pindi_simcountryiso)
    // + " : " + getSimCountryIso());
    // data2.add(""
    // + getResources().getString(R.string.pindi_seimoperratorcode)
    // + " : " + getSimOperatorCode());
    //
    // operator = getSIMoperatorNmae();
    // System.out.println(" CURRENT_PROVIDER " + operator);
    // operator = operator.toLowerCase();
    //
    // // operator="sdfsd";
    // if (operator.contains("airtel"))
    // CURRENT_PROVIDER = AIRTEL;
    // else if (operator.contains("idea"))
    // CURRENT_PROVIDER = IDEA;
    // else if (operator.contains("relia"))
    // CURRENT_PROVIDER = RELIENCE;
    // else if (operator.contains("vodaf"))
    // CURRENT_PROVIDER = VODAFONE;
    // else if (operator.contains("aircel"))
    // CURRENT_PROVIDER = AIRCEL;
    // else if (operator.contains("tata"))
    // CURRENT_PROVIDER = TATA;
    // else
    // // scrollView.setVisibility(View.GONE);
    // System.out.println(" CURRENT_PROVIDER " + CURRENT_PROVIDER);
    // }

    public String getSIMNumber() {

        return telephonyManager.getSimSerialNumber();

    }

    public String getSIMoperatorNmae() {
        operator = telephonyManager.getSimOperatorName();
        return telephonyManager.getSimOperatorName();

    }

    public String getSimCountryIso() {

        return telephonyManager.getSimCountryIso();

    }

    public String getSimOperatorCode() {

        return telephonyManager.getSimOperator();

    }

    public String getSimState() {

        return "" + telephonyManager.getSimState();

    }

    public static int navigation = 1;

    public void setUses() {
        int current = new UtilsApp().get_use_count(getActivity());
        int tempcount = current + 1;
        new UtilsApp().set_use_count(getActivity(), tempcount);

        if (tempcount > 5) {
            navigation = 1;
        } else if (tempcount > 3) {
            navigation = 2;
        } else {
            navigation = 2;
        }
        System.out.println("Got navigation = " + navigation + "temp nav "
                + tempcount);
    }

    int thiscount = 0;

    // public boolean showFullAds() {
    // thiscount++;
    // if (thiscount % navigation == 0) {
    // System.out.println("Got navigation true");
    // return true;
    // } else {
    // System.out.println("Got navigation false navigation " + navigation
    // + " thiscount " + thiscount);
    // return false;
    // }

    // }
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
