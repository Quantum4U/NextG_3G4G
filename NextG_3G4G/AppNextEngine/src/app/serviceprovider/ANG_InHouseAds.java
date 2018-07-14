package app.serviceprovider;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import app.adshandler.Ads;
import app.pnd.adshandler.R;

/**
 * Created by rajeev on 29/06/17.
 */

public class ANG_InHouseAds {
    public static int TYPE_NATIVE_SMALL = 1;
    public static int TYPE_NATIVE_MEDIUM = 2;

    public static int TYPE_NATIVE_LARGE = 3;


    public LinearLayout getBannerAds(String src, Context context, int type) {
        Ads.printdata(" Nonsence Going to Load Inhouse type getbanner1 5");


        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
//        int height;
//        if (type == TYPE_NATIVE_SMALL)
//            height = display.getHeight() / 10;
//        else if (type == TYPE_NATIVE_MEDIUM)
//            height = display.getHeight() / 7;
//
//        else
//            height = display.getWidth();


        ImageView iv = new ImageView(context);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = new LinearLayout(context);
//        params.gravity = Gravity.CENTER;
        ll.setLayoutParams(params);
        iv.setLayoutParams(params);
        ll.addView(iv);
//       Picasso.with(context).load(src)
//                    .fit().placeholder(R.drawable.star_filled_big).into(iv);


        Picasso.with(context)
                .load(src)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .resize(display.getWidth(), iv.getHeight())
                .placeholder(R.drawable.blank)
                .into(iv);
        Drawable bmp = iv.getDrawable();
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setBackground(bmp);
        return ll;

    }


}
