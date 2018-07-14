package pnd.chache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.RemoteException;
import android.os.StatFs;
import android.provider.Browser;
import android.provider.SearchRecentSuggestions;
import android.text.ClipboardManager;
import android.widget.Toast;
import app.pnd.fourg.CacheFragment;



public class SystemInfoUtil {
	
	WeakReference<Context> context;
	
	public SystemInfoUtil(Context context) {
		// TODO Auto-generated constructor stub
		this.context=new WeakReference<Context>(context);
	}
	
	public void AppToDownload(String pkg)
	{
		Intent i = new Intent(android.content.Intent.ACTION_VIEW);
		i.setData(Uri.parse("https://play.google.com/store/apps/details?id="+pkg));
		context.get().startActivity(i);
	}
	public List<PackageInfo> getALlAppsBasedPermission(String permissionName)
	{
		PackageManager pm = context.get().getPackageManager();
		List<PackageInfo> packages = new ArrayList<PackageInfo>();
		for(ResolveInfo info:  getAllDownloadedApps())
		{
			 PackageInfo pkgInfo = null;
				try {
					pkgInfo = pm.getPackageInfo(info.activityInfo.packageName, PackageManager.GET_META_DATA);
					if(pkgInfo!=null)
					{
						if(pm.checkPermission(permissionName, pkgInfo.packageName)==PackageManager.PERMISSION_GRANTED)
							packages.add(pkgInfo);
					}
					}
				catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			if(pm.checkPermission(permissionName, info.packageName)==PackageManager.PERMISSION_GRANTED&&  pm.getLaunchIntentForPackage(info.packageName) != null  )
			
		}
		return packages;
	}
	
	public String getFolderPath() {
		String folderpath = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/" + context.get().getPackageName() + "/";
		return folderpath;

	}
	public String getIconFolderPath() {
		String folderpath = Environment.getExternalStorageDirectory()
				.getAbsolutePath()
				+ "/" + context.get().getPackageName() + "/.icons/";
		return folderpath;

	}
	public String getFormattedSize(long size) {
		DecimalFormat format = new DecimalFormat("##.#");
		float value=size/1024;
		if(value<1024)
			return format.format(value)+"KB";
		else
		{
			value=value/1024;
			return format.format(value)+"MB";
		}
	}
//	public List<PackageInfo> getALlAppsHavingPermission()
//	{
//		PackageManager pm = context.get().getPackageManager();
//		List<PackageInfo> packages = new ArrayList<PackageInfo>();
//		for(PackageInfo info:  pm.getInstalledApplications(PackageManager.GET_META_DATA))
//		{
//			if(!isSystemPackage(info)){
//				try {
//					if(pm.getPackageInfo(info.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions.length>0)
//					packages.add(info);
//				} catch (NameNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		return packages;
//	}
		
	
	public static final int LOCATION_AUTO = 0;
	public static final int LOCATION_INTERNAL = 1;
	public static final int LOCATION_PREFEREX = 2;

	public int chkInstallLocation(String packageName, Activity activity){
		// Experimentally determined
	
		AssetManager am;
		XmlResourceParser xml;
		int eventType;
		int location=-1;
		try {
			am = activity.createPackageContext(packageName, 0).getAssets();
			xml = am.openXmlResourceParser("AndroidManifest.xml");
			eventType = xml.getEventType();
			xmlloop:
			while (eventType != XmlPullParser.END_DOCUMENT) {
			    switch (eventType) {
			        case XmlPullParser.START_TAG:
			            if (! xml.getName().matches("manifest")) {
			                break xmlloop;
			            } else {
			                attrloop:
			                for (int j = 0; j < xml.getAttributeCount(); j++) {
			                    if (xml.getAttributeName(j).matches("installLocation")) {
			                    	location= Integer.parseInt(xml.getAttributeValue(j));
			                        break attrloop;
			                    }
			                }
			            }
			            break;
			        }
			        eventType = xml.nextToken();
			    }
			return location;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}
	public Set<String>  getAllDownloadedPackags() {
		
		final PackageManager pm = context.get().getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		Set<String> temp_data = new HashSet<String>();
		for(ResolveInfo info : pm.queryIntentActivities(intent, PackageManager.GET_META_DATA))
		{
			String str = info.activityInfo.packageName;
			if(!temp_data.contains(str))
			{
				temp_data.add(str);
			
			}
			
		}
		return temp_data;
		
//		return pm.getInstalledPackages(PackageManager.GET_META_DATA);
		
	}
	
	public List<ResolveInfo>  getAllDownloadedApps() {
		List<ResolveInfo> data = new ArrayList<ResolveInfo>();
		final PackageManager pm = context.get().getPackageManager();
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		Set<String> temp_data = new HashSet<String>();
		for(ResolveInfo info : pm.queryIntentActivities(intent, PackageManager.GET_META_DATA))
		{
			String str = info.activityInfo.packageName;
			if(!temp_data.contains(str))
			{
				temp_data.add(str);
				data.add(info);
			}
			
		}
		return data;
		
//		return pm.getInstalledPackages(PackageManager.GET_META_DATA);
		
	}
	
	public ApplicationInfo getAppInfo(String packageName)
	{
		final PackageManager pm = context.get().getPackageManager();
		try {
			return pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	public PackageInfo getPkgInfo(String packageName)
	{
		final PackageManager pm = context.get().getPackageManager();
		try {
			return pm.getPackageInfo(packageName, PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public List<RunningTaskInfo> getRunningApps(){
		ActivityManager actvityManager = (ActivityManager) context.get().getSystemService(context.get().ACTIVITY_SERVICE);
		return actvityManager.getRunningTasks(1000);
		
	}
	public ArrayList<AppUsage> getFilterRunningApps()
	{
		ArrayList<AppUsage> appRamUsages = new ArrayList<AppUsage>();
		ActivityManager am = (ActivityManager) context.get().getSystemService(Context.ACTIVITY_SERVICE);
		PackageManager pm = context.get().getPackageManager();
			List<RunningAppProcessInfo> list2= am.getRunningAppProcesses();
			 Set<String> allPackages = getAllDownloadedPackags();
			int pid[] = new int[1];

			for (RunningAppProcessInfo ti : list2) {
				System.out.println("136 process found is here " + ti.processName);
				if(!allPackages.contains(ti.processName))
					continue;
				if (ti.processName.equals("system")
						|| ti.processName.equals("com.android.phone")
						|| ti.processName.equals(context.get().getPackageName())) {
					continue;
				}

				try {

					AppUsage appRamUsage = new AppUsage(pm, pm.getApplicationInfo(
							ti.processName, 0));
					appRamUsage.setAppUid(ti.pid);
					pid[0] = ti.pid;
					android.os.Debug.MemoryInfo[] memoryInfoArray = am
							.getProcessMemoryInfo(pid);

					appRamUsage.setAppPackageName(ti.processName);

					System.out.println("got value "
							+ memoryInfoArray[0].getTotalPss());
					double ramusage = (memoryInfoArray[0].getTotalPss() / 1024.0f);
					double usage = (int) Math.round(ramusage * 100) / (double) 100;

					appRamUsage.setAppUsage(usage);
					appRamUsages.add(appRamUsage);
				
				} catch (Exception e) {
				}// if (adaptor != null) {
				}
			return appRamUsages;
	}
	public void showAppInfoWithActiivtyResult(String pkgName, Activity activity,int requestCode)
	{
		
		try {
		    //Open the specific App Info page:
		    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		    intent.setData(Uri.parse("package:" + pkgName));
		    activity.startActivityForResult(intent, requestCode);
//		    context.get().startActivity(intent);

		} catch ( ActivityNotFoundException e ) {
		    //e.printStackTrace();

		    //Open the generic Apps page:
		    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
		    activity.startActivityForResult(intent, requestCode);

		}
	}
	public void showAppInfo(String pkgName, Activity activity)
	{
		
		try {
		    //Open the specific App Info page:
		    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		    intent.setData(Uri.parse("package:" + pkgName));
		    activity.startActivity(intent);
//		    context.get().startActivity(intent);

		} catch ( ActivityNotFoundException e ) {
		    //e.printStackTrace();

		    //Open the generic Apps page:
		    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
		    activity.startActivity(intent);

		}
	}
	public void launchAnApp(String pkgName)
	{
		Intent i = context.get().getPackageManager().getLaunchIntentForPackage(pkgName);
        try {
           if (i != null) {
              context.get().startActivity(i);
           } else {
              i = new Intent(pkgName);
              context.get().startActivity(i);
           }
        } catch (ActivityNotFoundException err) {
           Toast.makeText(context.get(), "Error launching app", Toast.LENGTH_SHORT).show();
        }
	}
	
	public boolean isSystemPackage(ApplicationInfo info) {
        return ((info.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }
	
	public void deleteCache(String string){
		//	new android.provider.SearchRecentSuggestions(context.get(),string,1))
			new SearchRecentSuggestions(context.get(), string, 1).clearHistory();
			
		}
	
	public void clearBrowserHistory()
	{
//		 Browser.clearSearches(context.get().getContentResolver());
//		 Browser.clearHistory(context.get().getContentResolver());
//	     context.get().getContentResolver().delete(Uri.parse("content://browser/bookmarks"), null, null);
	}
	
	public void clearMapHistory()
	{
		try{
		deleteCache("com.google.android.maps.SearchHistoryProvider");
		context.get().getContentResolver().delete(Uri.parse("content://com.google.android.maps.SearchHistoryProvider/history"), null, null);
		}
		catch(Exception e){
			
		}
		}
	
	public void clearPlayHistory()
	{
		 new SearchRecentSuggestions(context.get(), "com.google.android.finsky.RecentSuggestionsProvider", 1).clearHistory();
	     new SearchRecentSuggestions(context.get(), "com.android.vending.SuggestionsProvider", 1).clearHistory();
	}
	
	public void clearGmailHistory()
	{
		deleteCache("com.google.android.gmail.SuggestionProvider");	
	}
	
	public void clearClipBoard() {
		((ClipboardManager)context.get().getSystemService("clipboard")).setText(null);
	}

	
	long totalCache=0;
	int count;
	boolean temp_bool;
	
	List<PackageInfo> allInstalledPackages=new ArrayList<PackageInfo>();
	
	public void getTotalChacheSize()
	{ 
		PackageManager pm = context.get().getPackageManager();
		if(count==0)
		{
			totalCache=0;
			allInstalledPackages.clear();
			for(ResolveInfo info : getAllDownloadedApps())
			{
				 PackageInfo pkgInfo = null;
				try {
					pkgInfo = pm.getPackageInfo(info.activityInfo.packageName, PackageManager.GET_META_DATA);
					if(pkgInfo!=null && !isSystemPackage(pkgInfo.applicationInfo))
					{
						allInstalledPackages.add(pkgInfo);
					}
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		Method getPackageSizeInfo;
		
			try {
				getPackageSizeInfo = pm.getClass().getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
				try {
					getPackageSizeInfo.invoke(pm, allInstalledPackages.get(count).packageName, new IPackageStatsObserver.Stub() {

						        @Override
						        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
						            throws RemoteException {
						        	totalCache+=pStats.cacheSize;
						        	System.out.println("369 on method invokation onGetStatsCompleted"+totalCache);
						        	count++;
						        	if(count<allInstalledPackages.size())
						        		getTotalChacheSize();
						        	else {
						        		count=0;
						        		System.out.println("369 Total Cache : "+totalCache);
						        		broadcastIntent(CacheFragment.TOTAL_CACHE, totalCache);
						        	}
						        }
						       
						    });
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	StringBuilder tmp_str;
	public void getPackageChacheSize()
	{ 
		PackageManager pm = context.get().getPackageManager();
		if(count==0)
		{
//			totalCache=0;	
			tmp_str=new StringBuilder();
			allInstalledPackages.clear();
			for(ResolveInfo info : getAllDownloadedApps())
			{
				 PackageInfo pkgInfo = null;
				try {
					pkgInfo = pm.getPackageInfo(info.activityInfo.packageName, PackageManager.GET_META_DATA);
					if(pkgInfo!=null && !isSystemPackage(pkgInfo.applicationInfo))
					{
						allInstalledPackages.add(pkgInfo);
					}
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		Method getPackageSizeInfo;
		
			try {
				getPackageSizeInfo = pm.getClass().getMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
				try {
					getPackageSizeInfo.invoke(pm, allInstalledPackages.get(count).packageName, new IPackageStatsObserver.Stub() {

						        @Override
						        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
						            throws RemoteException {
						        	
						        	count++;
						        	if(count<allInstalledPackages.size())
						        	{
						        		if(pStats.cacheSize>0)
						        		tmp_str.append(pStats.packageName+":"+pStats.cacheSize+",");
						        		getPackageChacheSize();
						        	}
						        	else {
						        		count=0;
						        		if(tmp_str.length()>0)
						        		tmp_str.deleteCharAt(tmp_str.length()-1);
						        		broadcastIntent(CacheFragment.APP_TOTAL_CACHE, tmp_str.toString());
						        	}
						        }
						       
						    });
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	
	public void broadcastIntent(String s, long cache)
    {
       Intent intent = new Intent();
       intent.setAction(s);
       if(s.equals(CacheFragment.TOTAL_CACHE))
       {
    	  intent.putExtra(CacheFragment.TOTAL_CACHE_KEY, cache);
       }
    
       context.get().sendBroadcast(intent);
    }
	public void broadcastIntent(String s, String cache)
    {
       Intent intent = new Intent();
       intent.setAction(s);
       if(s.equals(CacheFragment.APP_TOTAL_CACHE))
    	   {
    	    intent.putExtra(CacheFragment.APP_TOTAL_CACHE_KEY, cache);
    	   }
    
       context.get().sendBroadcast(intent);
    }

	
	
	public int getCountRunningApps(){
		ArrayList<AppUsage> appRamUsages = new ArrayList<AppUsage>();
		ActivityManager am = (ActivityManager) context.get().getSystemService(Context.ACTIVITY_SERVICE);
		PackageManager pm = context.get().getPackageManager();
			List<RunningAppProcessInfo> list2= am.getRunningAppProcesses();
			 Set<String> allPackages = getAllDownloadedPackags();
			int pid[] = new int[1];

			for (RunningAppProcessInfo ti : list2) {
				System.out.println("136 process found is here " + ti.processName);
				if(!allPackages.contains(ti.processName))
					continue;
				if (ti.processName.equals("system")
						|| ti.processName.equals("com.android.phone")
						|| ti.processName.equals(context.get().getPackageName())) {
					continue;
				}

				try {

					AppUsage appRamUsage = new AppUsage(pm, pm.getApplicationInfo(
							ti.processName, 0));
					appRamUsage.setAppUid(ti.pid);
					pid[0] = ti.pid;
					android.os.Debug.MemoryInfo[] memoryInfoArray = am
							.getProcessMemoryInfo(pid);

					appRamUsage.setAppPackageName(ti.processName);

					System.out.println("got value "
							+ memoryInfoArray[0].getTotalPss());
					double ramusage = (memoryInfoArray[0].getTotalPss() / 1024.0f);
					double usage = (int) Math.round(ramusage * 100) / (double) 100;

					appRamUsage.setAppUsage(usage);
					appRamUsages.add(appRamUsage);
				
				} catch (Exception e) {
				}// if (adaptor != null) {
				}
			return appRamUsages.size();
	}
	
	public long getAvailInternalStorage()
	{
		StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
		long bytesAvailable = (long)stat.getFreeBlocks() * (long)stat.getBlockSize();
		return  bytesAvailable / 1048576;
		
	}
	
	public long getTotalInternalStorage()
	{
		StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
		long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
//		return bytesAvailable / 1048576;
		return bytesAvailable;
	}
	public long getAvailExternalStorage()
	{
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		long bytesAvailable = (long)stat.getFreeBlocks() * (long)stat.getBlockSize();
		return bytesAvailable / 1048576;
	}
	public long getTotalExternalStorage()
	{
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		long bytesAvailable = (long)stat.getBlockSize() *(long)stat.getBlockCount();
		return bytesAvailable / 1048576;
	}
	
	public void saveBitmap(Bitmap bmp, String pkg, String folder_path) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 40, bytes);

		//you can create a new file name "test.jpg" in sdcard folder.
		File f = new File( folder_path, pkg+".png");
		try {
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(bytes.toByteArray());
			fo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//write the bytes in file
		
	}
	
	public long getRamInfo() {
		ActivityManager actManager = (ActivityManager) context.get().getSystemService(context.get().ACTIVITY_SERVICE); 
		MemoryInfo memInfo = new ActivityManager.MemoryInfo(); 
		actManager.getMemoryInfo(memInfo);
		return 0;
//		return memInfo.totalMem;
	}
	}

