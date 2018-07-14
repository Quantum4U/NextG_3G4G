package app.adshandler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import app.PrintLog;
import app.pnd.adshandler.R;

public class FullPagePromo extends Activity {
    ImageView adsimage;
    Button exit;
    public static String src = "";
    public static String link = "";

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fullpageprompt);
        adsimage = (ImageView) findViewById(R.id.adsimage);
        exit = (Button) findViewById(R.id.exit);

        PrintLog.print(" Enginev2 CP SRC " + src);
        PrintLog.print(" Enginev2 CP SRImageC " + link);

        src = getIntent().getExtras().getString("src");
        link = getIntent().getExtras().getString("link");
        if (src != null && !src.equalsIgnoreCase("")) {
            Picasso.with(this)
                    .load(src)
                    .into(adsimage);
        }
        exit.setOnClickListener(new View.OnClickListener() {


            public void onClick(View arg0) {
                finish();

            }
        });


        adsimage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                //i.setData(Uri.parse(MasterData.cp_link));
                i.setData(Uri.parse(link));
                startActivity(i);
                finish();
            }
        });
    }

}
