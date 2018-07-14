package app;

import app.adshandler.DataHub;
import app.server.v2.DataHubConstant;

/**
 * Created by quantum4u1 on 30/04/18.
 */

public class PrintLog {


    public static void print(String str) {
        if (!DataHubConstant.IS_LIVE)
            System.out.println(str);
    }

}


