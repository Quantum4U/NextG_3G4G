package pnd.chache;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class AppUsage {
	private PackageManager manager;
	private ApplicationInfo applicationInfo;
	private double AppUsage;
	private String AppPackageName;
	public  boolean isChecked=false;
	private int AppUid;

	public AppUsage(PackageManager manager, ApplicationInfo applicationInfo) {
		this.manager = manager;
		this.applicationInfo = applicationInfo;
	}

	public int getAppUid() {
		return AppUid;
	}

	public void setAppUid(int appUid) {
		AppUid = appUid;
	}

	public String GetAppName() {
		return manager.getApplicationLabel(applicationInfo).toString();
	}

	public double getAppUsage() {
		return AppUsage;
	}

	public void setAppUsage(double ramusage) {
		AppUsage = ramusage;
	}

	public String getAppPackageName() {
		return AppPackageName;
	}

	public void setAppPackageName(String appPackageName) {
		AppPackageName = appPackageName;
	}

	public Drawable getAppIcon() {
		return manager.getApplicationIcon(applicationInfo);
	}
}
