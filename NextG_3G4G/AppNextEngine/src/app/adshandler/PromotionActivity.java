package app.adshandler;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import app.pnd.adshandler.R;

/**
 * Created by Chetan on 11/13/2016.
 */
public class PromotionActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView app_icon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promotion_acitivity);
        try {
            if (getIntent() != null) {
                String app_name = getIntent().getStringExtra("appname");
                String app_prompt_text = getIntent().getStringExtra("prompttext");
                String app_icon_url = getIntent().getStringExtra("appiconurl");
                String app_click_link = getIntent().getStringExtra("clicklink");
                String app_show_play_store = getIntent().getStringExtra("showplaystore");

                ((TextView) findViewById(R.id.app_name)).setText(app_name);
                ((TextView) findViewById(R.id.app_text)).setText(app_prompt_text);
                findViewById(R.id.btn_cross).setOnClickListener(this);
                app_icon = (ImageView) findViewById(R.id.app_icon);
                View download = findViewById(R.id.btn_download);
                download.setTag(app_click_link);
                download.setOnClickListener(this);
                View play = findViewById(R.id.play_download);
                if (app_show_play_store.equals("1")) {
                    play.setVisibility(View.VISIBLE);
                    play.setTag(app_click_link);
                    play.setOnClickListener(this);
                } else {
                    play.setVisibility(View.GONE);
                }
                new DownloadImage().execute(app_icon_url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_download || id == R.id.play_download) {
            String url = (String) v.getTag();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }
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

            app_icon.setImageBitmap(result);
        }
    }
}
