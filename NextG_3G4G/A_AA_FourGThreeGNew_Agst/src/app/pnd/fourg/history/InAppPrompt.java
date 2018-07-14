package app.pnd.fourg.history;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;


import com.checkspeed.Utility;

import java.io.InputStream;

import app.pnd.fourg.AppLauncher;
import app.pnd.fourg.MainActivity;

import com.appnextg.fourg.R;

public class InAppPrompt extends Activity {
    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inapp);

        Intent intent = getIntent();

        String[] s = intent.getStringExtra(Utility.INAPP_Text).split(",");
        if (s[0] != null)
            new DownloadImage().execute(s[0]);

        final String txt = s[1];
        iv = (ImageView) findViewById(R.id.iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txt.contains("http")) {
                    Class aClass = null;
                    try {
                        aClass = Class.forName(txt);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (aClass != null) {
                        finish();
                        Intent i = new Intent(InAppPrompt.this, aClass);
                        startActivity(i);
                    } else {
                        callMain();
                    }
                } else {
                    try {
                        finish();
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(txt));
                        startActivity(intent);
                    } catch (Exception e) {
                        callMain();
                    }
                }
            }
        });
    }

    private void callMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            iv.setImageBitmap(result);
        }
    }
}
