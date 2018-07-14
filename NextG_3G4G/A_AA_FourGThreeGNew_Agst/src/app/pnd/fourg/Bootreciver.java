package app.pnd.fourg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class Bootreciver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent service = new Intent(context, PasswordService.class);
            context.startService(service);

            }


    }

}
