package version_2.fragment_v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.appnextg.fourg.R;

import app.adshandler.AHandler;
import version_2.SpeedCheckActivity;

/**
 * Created by rajeev on 25/05/18.
 */

public class HomeStaticFragment {
    private Activity activity;
    Button speendTest;

    public View onCreateView(Activity a, LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.activity = a;
        View view = inflater.inflate(R.layout.home_static_fragment, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.dashboard);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AHandler().showFullAds((activity), false);
                activity.startActivity(new Intent(activity, SpeedCheckActivity.class));
            }
        });

        speendTest = (Button) view.findViewById(R.id.btnSpeedTest);
        speendTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AHandler().showFullAds((activity), false);
                activity.startActivity(new Intent(activity, SpeedCheckActivity.class));
            }
        });

        if (view != null) {
            System.out.println("1234 if not null");
        }
        return view;
    }
}
