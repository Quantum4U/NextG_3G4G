package app.pnd.fourg;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by rajeev on 25/05/18.
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

}
