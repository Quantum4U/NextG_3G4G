package app.pnd.fourg;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import pnd.chache.SystemInfoUtil;
import com.appnextg.fourg.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.StatFs;
import android.support.v4.app.Fragment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import app.circular.CircularActivity;
import app.adshandler.AHandler;


public class CacheFragment extends Fragment {
    int cc = 0;

    String total = "0 MB";
    Context context;
    public static final String TOTAL_CACHE_KEY = "total_cache";
    public static final String APP_TOTAL_CACHE = "com.migital.phone.booster.app_total_cache";
    public static final String APP_TOTAL_CACHE_KEY = "app_total_cache";
    public static final String TOTAL_CACHE = "com.migital.phone.booster.total_cache";
    private static final String TAG = "CacheAppsListActivity";

    private TextView mCleanButton;
    AHandler aHandler;
    private ListView mListView;
    private TextView mNoCacheTipTxt, total_cache_txt;
    ;
    private RelativeLayout mLoadingLayout;
//private AnimationDrawable mAnimDrawable;

    private ArrayList<Item> mDataList = new ArrayList<CacheFragment.Item>();
    private List<PackageInfo> mAllData = null;
    private CacheAdapter mAdapter = null;
    private PackageManager mPkgMgr;
    private PackageSizeObserver mPkgSizeObserver;
    private Method getPackageSizeInfoMethod = null;
    private CleanCacheObserver mCleanCacheObserver;
    private Method freeStorageAndNotify = null;
    private boolean mExit = false;

    private int mState;
    private static final int STATE_INIT = 1;
    private static final int STATE_READY = 2;
    private static final int STATE_CLEANED = 3;
    private static final int STATE_NO_CLEAN_NEEDED = 4;
    private static final int DLG_CLEAN_CACHE = 1;

    private static final int MSG_GET_SIZE = 1;
    private static final int MSG_GET_SIZE_FINISH = 2;
    private static final int MSG_FILL_DATA = 3;
    private static final int MSG_CACHE_CLEANED = 4;
    LinearLayout adslayout1;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//		View view = inflater.inflate(R.layout.cache_cleaner, container, false);
        View view = null;
        adslayout1 = (LinearLayout) view.findViewById(R.id.adsfirtfrag);



        initUI(view);
        Intent in = new Intent("ON_GOING_NOTIFICATION");
        in.putExtra("bool", false);
        getActivity().sendBroadcast(in);
        new Load2().execute("");
        aHandler = new AHandler();
        return view;
    }

    @Override
    public void onPause() {
        System.out.println("First on pause");
        super.onPause();
    }

    @Override
    public void onAttach(Activity ac) {
        // TODO Auto-generated method stub
        super.onAttach(ac);
        context = ac;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_GET_SIZE:
                    fireGetSize();
                    break;
                case MSG_GET_SIZE_FINISH:
                    updateItemSize(msg);
                    break;
                case MSG_FILL_DATA:
                    fillData();
                    break;
                case MSG_CACHE_CLEANED:
//				dismissDialog(DLG_CLEAN_CACHE);
                    slowDown();
                    break;
            }
        }

        ;
    };

    private void slowDown() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {

                mState = STATE_CLEANED;
                updateTip();
//				Toast.makeText(context,result, Toast.LENGTH_SHORT).show();
            }
        };
        handler.postDelayed(r, 1500);
    }

    private void setLeftAdapter() {
//		AnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(mAdapter);
//		
//		animAdapter.setAbsListView(mListView);
        mListView.setAdapter(mAdapter);
    }

    private void updateTip() {
//		mTipLayout.setVisibility(View.VISIBLE);
        String tip = null;
        long count = 0;
        int size = mDataList.size();
        for (int i = 0; i < size; i++) {
            count += mDataList.get(i).orgSize;
        }
        switch (mState) {
            case STATE_INIT:
                mState = STATE_READY;
                break;
            case STATE_CLEANED:
                mListView.setVisibility(View.GONE);
                mNoCacheTipTxt.setVisibility(View.VISIBLE);
                total_cache_txt.setVisibility(View.GONE);
                break;
        }
    }

    private void fireGetSize() {
        if (!mAllData.isEmpty()) {
            if (getPackageSizeInfoMethod != null) {
                PackageInfo item = mAllData.remove(0);
                try {
                    getPackageSizeInfoMethod.invoke(mPkgMgr, item.packageName, mPkgSizeObserver);

                } catch (IllegalAccessException e) {
                    System.out.println("Cache SError : " + e);
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    System.out.println("Cache SError : " + e);
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    System.out.println("Cache SError : " + e);
                    e.printStackTrace();
                }
            }
        } else {
            mHandler.sendEmptyMessage(MSG_FILL_DATA);
        }
    }

    private void updateItemSize(Message msg) {
        PackageStats pStats = (PackageStats) msg.obj;
        if (pStats.cacheSize > 0) {
            extraPackageInfo(pStats.cacheSize, pStats.packageName, mDataList);
        }
        mHandler.sendEmptyMessage(MSG_GET_SIZE);
    }


//	protected Dialog onCreateDialog2(int id) {
//		switch (id) {
//		case DLG_CLEAN_CACHE:
//			AlertDialog dlg = new AlertDialog.Builder(context).create();
//			dlg.setCancelable(false);
//			View v = getActivity().getLayoutInflater().inflate(R.layout.loading, null);
//			v.setPadding(20, 10, 20, 10);
//			v.setBackgroundColor(getResources().getColor(R.color.grey));
//			TextView tv = (TextView)v.findViewById(R.id.progress_txt);
//			tv.setText("Cleaning...");
//			dlg.setView(v, 0, 0, 0, 0);
//			dlg.getWindow().getAttributes().windowAnimations = R.style.inflateDialogAnim;
//			return dlg;
//		}
//		return context.onCreateDialog(id);
//	}

    public static long getEnvironmentSize() {
        File f = Environment.getDataDirectory();
        String path = f.getPath();
        StatFs sf = new StatFs(path);
        long blk = sf.getBlockSize();
        return sf.getBlockCount() * blk;
    }

    private void initUI(View view) {
//		mCleanButton = (TextView)view. findViewById(R.id.txt_clean);

//		total_cache_txt = (TextView)view. findViewById(R.id.txt_ttl_cache);
        mCleanButton.setOnClickListener(new OnClickListener() {

                                            @Override
                                            public void onClick(View v) {

                                                //new Load().execute("");
                                                if (mState == STATE_READY) {

                                                    if (freeStorageAndNotify != null) {
//					showDialog(DLG_CLEAN_CACHE);

                                                        try {
//						setAnimation(mListView.getChildAt(0));
                                                            setLeftAdapter();
                                                            freeStorageAndNotify.invoke(mPkgMgr, getEnvironmentSize() - 1L,
                                                                    mCleanCacheObserver);
                                                        } catch (Exception e) {
                                                            Log.e(TAG, "Exception", e);
                                                        }
                                                    } else {

//					Toast.makeText(getApplicationContext(),
//							"clean cache fail", Toast.LENGTH_SHORT)
//							.show();
                                                    }
                                                } else if (mState == STATE_CLEANED || mState == STATE_NO_CLEAN_NEEDED) {
                                                    System.out.println("Click 3");
//				Toast.makeText(getApplicationContext(), "Cache Cleaned", Toast.LENGTH_SHORT)
//						.show();
                                                }
                                                startActivity(new Intent(context, CircularActivity.class));
                                                new AHandler().showFullAds(getActivity(), false);
                                            }


                                        }
        );
        mListView = (ListView) view.findViewById(android.R.id.list);
        mLoadingLayout = (RelativeLayout) view.findViewById(R.id.loading_layout);
//		mNoCacheTipTxt = (TextView)view.findViewById(R.id.no_data);
        mNoCacheTipTxt.setText("No Cache");
        mAdapter = new CacheAdapter();
        mListView.setAdapter(mAdapter);
        mState = STATE_INIT;

    }

    private SystemInfoUtil systemInfoUtil;

    private void initData() {
        systemInfoUtil = new SystemInfoUtil(context);
        PackageManager pm = context.getPackageManager();
        if (mDataList != null && !mDataList.isEmpty()) {
            mDataList.clear();
        } else {
            mDataList = new ArrayList<Item>();
        }
        mPkgMgr = context.getPackageManager();
        mPkgSizeObserver = new PackageSizeObserver();
        try {
            getPackageSizeInfoMethod = mPkgMgr.getClass().getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
        } catch (NoSuchMethodException e) {
            System.out.println("Cache S " + e);
            getPackageSizeInfoMethod = null;
        }

        if (mAllData != null && !mAllData.isEmpty()) {
            mAllData.clear();
        } else {
            mAllData = new ArrayList<PackageInfo>();
        }
        for (ResolveInfo info : systemInfoUtil.getAllDownloadedApps()) {
            PackageInfo pkgInfo = null;
            try {
                pkgInfo = pm.getPackageInfo(info.activityInfo.packageName, PackageManager.GET_META_DATA);
                if (pkgInfo != null && !systemInfoUtil.isSystemPackage(pkgInfo.applicationInfo)) {
                    mAllData.add(pkgInfo);
                }
            } catch (NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }

    private void extraPackageInfo(long cacheSize, String pkgName,
                                  ArrayList<Item> dataList) {
        Drawable icon;
        String label;
        File cacheFile;
        PackageInfo pInfo = null;
        try {
            pInfo = mPkgMgr.getPackageInfo(pkgName, 0);
            icon = mPkgMgr.getApplicationIcon(pInfo.applicationInfo);
            label = mPkgMgr.getApplicationLabel(pInfo.applicationInfo)
                    .toString();
            cacheFile = context.createPackageContext(pkgName, 0).getCacheDir();
            dataList.add(new Item(icon, label, pkgName, cacheSize, Formatter.formatFileSize(context, cacheSize), cacheFile));
            mAdapter.notifyDataSetChanged();
        } catch (NameNotFoundException e) {

        }
    }

    long total_cache;

    private class PackageSizeObserver extends IPackageStatsObserver.Stub {

        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
                throws RemoteException {
            total_cache += pStats.cacheSize;
            Message msg = mHandler.obtainMessage(MSG_GET_SIZE_FINISH);
            msg.obj = pStats;
            mHandler.sendMessage(msg);
        }

    }

    private class CleanCacheObserver extends IPackageDataObserver.Stub {

        @Override
        public void onRemoveCompleted(String packageName, boolean succeeded) {
            Log.e(TAG, "packageName : " + packageName + ",succeeded : "
                    + succeeded);
            mHandler.sendEmptyMessage(MSG_CACHE_CLEANED);
            total_cache = 0;
        }
    }

    private void fillData() {
        // Log.e(TAG, "data size : " + mDataList.size());
        System.err.println("In to fill data");
        if (mLoadingLayout != null)
            mLoadingLayout.setVisibility(View.GONE);
        System.out.println("Bhanu Cache " + mDataList.size());
        if (mDataList.size() == 0) {

            mState = STATE_NO_CLEAN_NEEDED;
            mNoCacheTipTxt.setVisibility(View.VISIBLE);
            total_cache_txt.setVisibility(View.GONE);
        } else {
            mNoCacheTipTxt.setVisibility(View.GONE);
            total_cache_txt.setVisibility(View.VISIBLE);
            DecimalFormat format = new DecimalFormat("##.#");
            float value = total_cache / 1024;
            System.out.println("Total Cache " + total_cache + " value " + value);
            if (value < 1024) {
                total_cache_txt.setText("Total Cache: " + format.format(value) + "KB");
                total = format.format(value) + "KB";
            } else {
                value = value / 1024;
                total_cache_txt.setText("Total Cache: " + format.format(value) + "MB");
                total = format.format(value) + "MB";
            }


            //Collections.sort(mDataList, new Sort());
            mAdapter.notifyDataSetChanged();
            updateTip();
            mCleanCacheObserver = new CleanCacheObserver();

            try {
                freeStorageAndNotify = mPkgMgr.getClass().getMethod("freeStorageAndNotify", Long.TYPE, IPackageDataObserver.class);

            } catch (NoSuchMethodException e) {
                freeStorageAndNotify = null;
                System.out.println("Cache Cleaning tryingg " + e);
                Log.e(TAG, "NoSuchMethodException", e);
            }
        }
    }


    private class CacheAdapter extends BaseAdapter {
        public View v = null;

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            System.err.println("Cache position " + position);
//			for(int i=0;i<mDataList.size();i++){
//				System.out.println("");
//			}

            Item item = mDataList.get(position);
            if (convertView == null) {
                //item = mDataList.get(position);
                convertView = getActivity().getLayoutInflater().inflate(
                        R.layout.cache_info_item, null);
                viewHolder = new ViewHolder();
                viewHolder.icon = (ImageView) convertView
                        .findViewById(R.id.img_icon);
                viewHolder.label = (TextView) convertView
                        .findViewById(R.id.txt_title);
                viewHolder.cacheSize = (TextView) convertView
                        .findViewById(R.id.txt_cache_size);

                viewHolder.appclick = (RelativeLayout) convertView
                        .findViewById(R.id.id_parent);

                System.err.println("Cache setting pkg " + item.pkgName);
                viewHolder.appclick.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        System.err.println("Cache getting pkg " + v.getTag().toString());
                        new AHandler().showFullAds(getActivity(), true);
                        cc++;
                        showInstalledAppDetails(context, v.getTag().toString());
//                        new newUtilsApp().showToast(context, "Please click on " + "Clear Cache" + " Button");


                    }
                });
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.appclick.setTag("" + item.pkgName);
            viewHolder.icon.setImageDrawable(item.icon);
            viewHolder.label.setText(item.label);
            //System.err.println(" item.label "+item.label +"item.pkg "+item.pkgName);

            viewHolder.cacheSize.setText(item.size);
            System.out.println("Color toal " + item.size);
            try {
                String st = item.size;
                if (st.contains("MB")) {
                    st = st.substring(0, st.length() - 2);
                    if (Float.parseFloat(st) > 1) {
                        System.out.println("Color toal inside");
                        viewHolder.cacheSize.setTextColor(context.getResources().getColor(R.color.ornage));
                    }
                }

            } catch (Exception e) {
                System.out.println("Color e" + e);
            }

            v = convertView;
            return convertView;
        }

    }

    private class ViewHolder {
        private ImageView icon;
        private TextView label;
        private TextView cacheSize;
        private RelativeLayout appclick;
    }

    private class Item {
        private Drawable icon;
        private String label;
        private String pkgName;
        private long orgSize;
        private String size;
        private File cacheFile;

        public Item(Drawable icon, String label, String pkgName, long orgSize,
                    String size, File cacheFile) {
            this.icon = icon;
            this.label = label;
            this.pkgName = pkgName;
            this.orgSize = orgSize;
            this.size = size;
            this.cacheFile = cacheFile;
        }
    }

    private class Sort implements Comparator<Item> {

        @Override
        public int compare(Item o1, Item o2) {
            return -(int) (o1.orgSize - o2.orgSize);
        }
    }

    public void onResume() {
        super.onResume();
        if (!mExit) {
            mExit = !mExit;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    initData();
                    mHandler.sendEmptyMessage(MSG_GET_SIZE);
                }
            }).start();
        }
    }

    public static void showInstalledAppDetails(Context context, String packageName) {
        try {
            //Open the specific App Info page:
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            context.startActivity(intent);

        } catch (ActivityNotFoundException e) {
            //e.printStackTrace();

            //Open the generic Apps page:
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            context.startActivity(intent);

        }
    }

    class Load extends AsyncTask<String, Integer, Integer> {
        ProgressDialog pDialog;

        protected Integer doInBackground(String... params) {
            System.out.println("Click 1 " + mState);

            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Integer result) {

            super.onPostExecute(result);
            Toast.makeText(context, total + " Cache released sucessfully", Toast.LENGTH_LONG).show();
            pDialog.cancel();
        }

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait Cache Cleaing process in Progress..");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();

        }

    }

    class Load2 extends AsyncTask<String, Integer, Integer> {
        ProgressDialog pDialog;

        protected Integer doInBackground(String... params) {
            System.out.println("Click 1 " + mState);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Integer result) {

            super.onPostExecute(result);
            //Toast.makeText(context, total+" Cache released sucessfully", Toast.LENGTH_LONG).show();
            pDialog.cancel();
        }

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();

        }

    }


}

