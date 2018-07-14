package version_2.fragment_v2;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.appnextg.fourg.R;

import java.util.ArrayList;

import app.campaign.CampaignHandler;
import app.server.v2.Slave;
import app.serviceprovider.Utils;

/**
 * Created by rajeev on 25/05/18.
 */

public class HomePagerAdapter extends PagerAdapter {

    private HomeStaticFragment staticFragment;

    private ArrayList<View> viewList;
    private CampaignHandler handler;
    private int viewNumber;
    private Activity activity;
    private LayoutInflater inflator;
    private static final int STATIC_DASHBOARD_LAYOUT = 1;

    public HomePagerAdapter(Context c) {
        staticFragment = new HomeStaticFragment();

        viewList = new ArrayList<>();
        handler = CampaignHandler.getInstance();


        if (!Slave.hasPurchased(c)) {

            if (handler.loadLargeCampaignList() != null) {
                viewNumber = handler.loadLargeCampaignList().size();
            } else {
                viewNumber = 0;
            }
            this.activity = (Activity) c;
            inflator = LayoutInflater.from(this.activity);

            viewList.add(staticFragment.onCreateView(activity, inflator, null, null));
            if (viewNumber > 0) {
                for (int i = 0; i < viewNumber; i++) {
                    viewList.add(handler.getLargeAdsViewsForDashBoard((Activity) c, i));

                }
            }

        } else {
            this.activity = (Activity) c;
            inflator = LayoutInflater.from(this.activity);
            viewList.add(staticFragment.onCreateView(activity, inflator, null, null));
        }


    }


    @Override
    public int getCount() {
        if (!Slave.hasPurchased(activity)) {
            return viewNumber + STATIC_DASHBOARD_LAYOUT;
        } else {
            return 1;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View v = null;
        System.out.println("here is the list size " + " " + viewList.size());
        if (viewList != null) {
            v = viewList.get(position);
            if (v != null) {
                container.addView(v);
            }
        }
        return v;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    public void animateTrackView() {
        Animation a = AnimationUtils.loadAnimation(activity, R.anim.shake);
        staticFragment.speendTest.startAnimation(a);
    }
}

