package app.adshandler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

/**
 * Created by rajeev on 30/06/17.
 */

public class CustomLayout extends LinearLayout implements com.squareup.picasso.Target {

    public CustomLayout(Context context) {
        super(context);
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        setBackground(new BitmapDrawable(getResources(), bitmap));

    }


    public void onBitmapFailed(Drawable errorDrawable) {
        //Set your error drawable
    }

    public void onPrepareLoad(Drawable placeHolderDrawable) {
        //Set your placeholder
    }
}