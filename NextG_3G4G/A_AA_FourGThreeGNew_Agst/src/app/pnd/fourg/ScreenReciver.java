package app.pnd.fourg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import app.ads.UtilsApp;

public class ScreenReciver extends BroadcastReceiver{
	IntentFilter ift;
	
	public boolean check=false;
	public void onReceive(Context context, Intent intent) {
	
if(intent.getAction().equalsIgnoreCase("android.intent.action.SCREEN_OFF")){
	PasswordService.screen_is_on=false;
	check=true;
	
}
if(intent.getAction().equalsIgnoreCase("android.intent.action.SCREEN_ON")){
	
	PasswordService.screen_is_on=true;
	if(new UtilsApp().get_screen(context))
		PasswordService.password_enable=true;

}


//if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
//
//String phoneNubmer = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
////String phoneNubmer = "6565";
//
//try{
//if ((phoneNubmer.equals("1234") || phoneNubmer.equals("1234")) && new DataHandler(context).getIsStealthMode(context)) {
//    setResultData(null);
//    Toast.makeText(context, "OutGoing stage 3 ",Toast.LENGTH_LONG).show();
//    PackageManager packageManager = context.getPackageManager();
//    ComponentName componentName = new ComponentName(context,
//            MainActivity.class);
//    packageManager.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//            PackageManager.DONT_KILL_APP);
//
//    Intent appIntent = new Intent(context, MainActivity.class);
//    appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//    appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//    context.startActivity(appIntent);
//}
//}
//catch (Exception e) {
//	Toast.makeText(context, "Exception "+e,Toast.LENGTH_LONG).show();
//	// TODO: handle exception
//}
//
//}

	}
	
	

}
