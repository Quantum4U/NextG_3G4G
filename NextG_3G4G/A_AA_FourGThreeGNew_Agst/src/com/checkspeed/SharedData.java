package com.checkspeed;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedData 
{
	private static final String PREFERENCE_NAME = "SUPER_WIFI";
	public static final String ACTVATE_DEACTIVATE_KEY="ACT_DE_ACT";
	public static final String AUTOBOOSTER_PRIORITY_KEY="AUTOBOOSTER_PRIORITY";
	public static final String OPENNET_PRIORITY_CONNECT_KEY="OPENNET_PRIORITY_CONNECT";
	public static final String ENABLE_POWER_SAVER_KEY="ENABLE_POWER_SAVER";
	public static final String GET_NOTIFICATION_KEY="GET_NOTIFICATION";
	private static final String STRENGTH_DIFFERENCE="STRENGTH_DIFFERENCE_TIME";
	private static final String DISPLAY_SPEED="DISPLAY_SPEED_UNIT";
	private static final String CONNECTED_WIFI="CONNECTED_WIFI_SSID";
	private static final String LAST_WIFI="LAST_WIFI_SSID";

	public static void set_Activate_Or_Deactivate(Context context, boolean value) 
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putBoolean(ACTVATE_DEACTIVATE_KEY, value);
		editor.commit();
	}

	public static boolean get_Activate_Or_Deactivate(Context context) 
	{
		boolean value;
		SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		value=sharedPreferences.getBoolean(ACTVATE_DEACTIVATE_KEY, true);
		return value;
	}
	
	public static void set_AutoBooster_Or_Priority(Context context, boolean value) 
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putBoolean(AUTOBOOSTER_PRIORITY_KEY, value);
		editor.commit();
	}
	
	public static boolean get_AutoBooster_Or_Priority(Context context) 
	{
		boolean value;
		SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		value=sharedPreferences.getBoolean(AUTOBOOSTER_PRIORITY_KEY, true);
		return value;
	}
	
	public static void set_AutoConnect_Open_Net_Or_Priority(Context context, boolean value) 
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putBoolean(OPENNET_PRIORITY_CONNECT_KEY, value);
		editor.commit();
	}

	public static boolean get_AutoConnect_Open_Net_Or_Priority(Context context) 
	{
		boolean value;
		SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		value=sharedPreferences.getBoolean(OPENNET_PRIORITY_CONNECT_KEY, false);
		return value;
	}
	
	public static void set_Enable_Power_Saver(Context context, boolean value) 
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putBoolean(ENABLE_POWER_SAVER_KEY, value);
		editor.commit();
	}

	public static boolean get_Enable_Power_Saver(Context context) 
	{
		boolean value;
		SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		value=sharedPreferences.getBoolean(ENABLE_POWER_SAVER_KEY, false);
		return value;
	}
	
	public static void set_Get_Notification(Context context, boolean value) 
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putBoolean(GET_NOTIFICATION_KEY, value);
		editor.commit();
	}

	public static boolean get_Get_Notification(Context context) 
	{
		boolean value;
		SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		value=sharedPreferences.getBoolean(GET_NOTIFICATION_KEY, false);
		return value;
	}
	
	public static void set_Strength_Difference_Time(Context context, int time) 
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putInt(STRENGTH_DIFFERENCE, time);
		editor.commit();
	}

	public static int get_Strength_Difference_Time(Context context) 
	{
		int time;
		SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		time=sharedPreferences.getInt(STRENGTH_DIFFERENCE, 10);
		return time;
	}
	
	public static void set_Display_Speed_Unit(Context context, String unit) 
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putString(DISPLAY_SPEED, unit);
		editor.commit();
	}

	public static String get_Display_Speed_Unit(Context context) 
	{
		String unit;
		SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		unit=sharedPreferences.getString(DISPLAY_SPEED, "KBPS");
		return unit;
	}
	
	public static void set_Connected_WiFi_SSID(Context context, String SSID) 
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putString(CONNECTED_WIFI, SSID);
		editor.commit();
	}

	public static String get_Connected_WiFi_SSID(Context context) 
	{
		String SSID;
		SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		SSID=sharedPreferences.getString(CONNECTED_WIFI, "---");
		return SSID;
	}
	
	public static void set_Last_WiFi_SSID(Context context, String SSID) 
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor=sharedPreferences.edit();
		editor.putString(LAST_WIFI, SSID);
		editor.commit();
	}

	public static String get_Last_WiFi_SSID(Context context) 
	{
		String SSID;
		SharedPreferences sharedPreferences=context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		SSID=sharedPreferences.getString(LAST_WIFI, "---");
		return SSID;
	}
}
