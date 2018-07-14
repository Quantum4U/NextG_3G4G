package app.pnd.fourg;

import version.ux.MajorActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AppLauncher extends Activity {
    EditText hintqn, hintans;
    Button submit;
    public static int Status = 1;
    DataBaseHandler dataBaseHandler;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Intent intent
                = getIntent();
        try {
            Bundle b = intent.getExtras();
            String str = b.getString("type2");
            System.out.println("152 this is my key" + " " + str);
            System.out.println("152 this is my bundle" + " " + b);
        } catch (Exception e) {
            System.out.println("152 excep" + " " + e);
        }


//		Bundle bundle = getIntent().getExtras();
//		if(bundle!=null) {
//			String value = bundle.getString("type1");
//			System.out.println("this is my push testing " + " " + bundle);
//			System.out.println("this is my push value " + " " + value);
//		}
//		finish();


//				Intent intent = new Intent(AppLauncher.this, MainActivity.class);
//		 intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//		startActivity(intent);
    }

}