package app.inapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.pnd.adshandler.R;
import app.server.v2.Billing;
import app.server.v2.BillingResponseHandler;
import app.server.v2.Slave;

/**
 * Created by quantum4u1 on 14/04/18.
 */

public class BillingListActivity extends AppCompatActivity {

    private ArrayList<Billing> mBillingList;
    private FrameLayout mFreeLayout, mWeeklyLayout, mProLayout, mMonthlyLayout, mHalfYearLayout, mYearlyLayout;
    private RelativeLayout rl_FreeLayout, rl_mWeeklyLayout, rl_mProLayout, rl_mMonthlyLayout, rl_mHalfYearLayout, rl_mYearlyLayout;
    private Button btn_pro, btn_weekly, btn_monthly, btn_yearly, btn_halfyearly;
    private BillingPreference preference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_list_layout);

        preference = new BillingPreference(this);

        mBillingList = BillingResponseHandler.getInstance().getBillingResponse();

        mFreeLayout = (FrameLayout) findViewById(R.id.parentFree);
        mProLayout = (FrameLayout) findViewById(R.id.parentPro);
        mWeeklyLayout = (FrameLayout) findViewById(R.id.parentweekly);
        mMonthlyLayout = (FrameLayout) findViewById(R.id.parentMonthly);
        mHalfYearLayout = (FrameLayout) findViewById(R.id.parentHalfYear);
        mYearlyLayout = (FrameLayout) findViewById(R.id.parentYearly);

        rl_FreeLayout = (RelativeLayout) findViewById(R.id.rl_parentFree);
        rl_mProLayout = (RelativeLayout) findViewById(R.id.rl_parentPro);
        rl_mWeeklyLayout = (RelativeLayout) findViewById(R.id.rl_parentweekly);
        rl_mMonthlyLayout = (RelativeLayout) findViewById(R.id.rl_parentMonthly);
        rl_mHalfYearLayout = (RelativeLayout) findViewById(R.id.rl_parentHalfYear);
        rl_mYearlyLayout = (RelativeLayout) findViewById(R.id.rl_parentYearly);

        btn_pro = (Button) findViewById(R.id.btn_pro);
        btn_weekly = (Button) findViewById(R.id.btn_weekly);
        btn_monthly = (Button) findViewById(R.id.btn_monthly);
        btn_yearly = (Button) findViewById(R.id.btn_yearly);
        btn_halfyearly = (Button) findViewById(R.id.btn_halfyearly);

        btn_pro.setOnClickListener(mOnCliCkListener);
        btn_weekly.setOnClickListener(mOnCliCkListener);
        btn_monthly.setOnClickListener(mOnCliCkListener);
        btn_yearly.setOnClickListener(mOnCliCkListener);
        btn_halfyearly.setOnClickListener(mOnCliCkListener);

        mFreeLayout.setOnClickListener(mOnCliCkListener);
        mWeeklyLayout.setOnClickListener(mOnCliCkListener);
        mMonthlyLayout.setOnClickListener(mOnCliCkListener);
        mProLayout.setOnClickListener(mOnCliCkListener);
        mHalfYearLayout.setOnClickListener(mOnCliCkListener);
        mYearlyLayout.setOnClickListener(mOnCliCkListener);

        if (Slave.IS_PRO) {
            rl_mWeeklyLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));
            rl_mMonthlyLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));
            rl_mHalfYearLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));
            rl_mYearlyLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));
            rl_FreeLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));

            rl_mProLayout.setBackgroundResource(R.drawable.corner_color);


        } else if (Slave.IS_YEARLY) {
            rl_mWeeklyLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));
            rl_mMonthlyLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));
            rl_mHalfYearLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));
            rl_FreeLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));

            rl_mYearlyLayout.setBackgroundResource(R.drawable.corner_color);
        } else if (Slave.IS_HALFYEARLY) {
            rl_mWeeklyLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));
            rl_mMonthlyLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));
            rl_FreeLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));

            rl_mHalfYearLayout.setBackgroundResource(R.drawable.corner_color);
        } else if (Slave.IS_MONTHLY) {
            rl_mWeeklyLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));
            rl_FreeLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));

            rl_mMonthlyLayout.setBackgroundResource(R.drawable.corner_color);
        } else if (Slave.IS_WEEKLY) {
            rl_mWeeklyLayout.setBackgroundResource(R.drawable.corner_color);
            rl_FreeLayout.setBackgroundColor(getResources().getColor(R.color.color_disable));
        } else {
            rl_FreeLayout.setBackgroundResource(R.drawable.corner_color);
        }


        for (int i = 0; i < mBillingList.size(); i++) {
            if (mBillingList.get(i).billing_type.equalsIgnoreCase("pro")) {
                mProLayout.setVisibility(View.VISIBLE);

                ImageView ivPro = (ImageView) findViewById(R.id.iv_pro);
                TextView tvProTitle = (TextView) findViewById(R.id.tv_pro_title);
                TextView tvProSubTitle = (TextView) findViewById(R.id.tv_pro_subtitle);
                TextView tvProPrice = (TextView) findViewById(R.id.tv_price_pro);
                ImageView ivOffer = (ImageView) findViewById(R.id.iv_offer_pro);

                Picasso.with(this).load(mBillingList.get(i).feature_src).into(ivPro);
                tvProTitle.setText(mBillingList.get(i).product_offer_text);
                tvProSubTitle.setText(mBillingList.get(i).product_offer_sub_text);
                tvProPrice.setText(Html.fromHtml(mBillingList.get(i).product_price));

                if (mBillingList.get(i).product_offer_status) {
                    ivOffer.setVisibility(View.VISIBLE);
                    if (mBillingList.get(i).product_offer_src != null && !mBillingList.get(i).product_offer_src.equalsIgnoreCase(""))
                        Picasso.with(this).load(mBillingList.get(i).product_offer_src).into(ivOffer);
                }


            } else if (mBillingList.get(i).billing_type.equalsIgnoreCase("weekly")) {

                mWeeklyLayout.setVisibility(View.VISIBLE);

                ImageView ivWeekly = (ImageView) findViewById(R.id.iv_weekly);
                TextView tvWeeklyTitle = (TextView) findViewById(R.id.tv_weekly_title);
                TextView tvWeeklySubTitle = (TextView) findViewById(R.id.tv_weekly_subtitle);
                TextView tvWeeklyPrice = (TextView) findViewById(R.id.tv_weekly_price);
                ImageView ivOffer = (ImageView) findViewById(R.id.iv_offer_weekly);


                Picasso.with(this).load(mBillingList.get(i).feature_src).into(ivWeekly);
                tvWeeklyTitle.setText(mBillingList.get(i).product_offer_text);
                tvWeeklySubTitle.setText(mBillingList.get(i).product_offer_sub_text);
                tvWeeklyPrice.setText(Html.fromHtml(mBillingList.get(i).product_price));

                if (mBillingList.get(i).product_offer_status) {
                    ivOffer.setVisibility(View.VISIBLE);
                    if (mBillingList.get(i).product_offer_src != null && !mBillingList.get(i).product_offer_src.equalsIgnoreCase(""))
                        Picasso.with(this).load(mBillingList.get(i).product_offer_src).into(ivOffer);
                }


            } else if (mBillingList.get(i).billing_type.equalsIgnoreCase("monthly")) {

                mMonthlyLayout.setVisibility(View.VISIBLE);

                ImageView ivMonthly = (ImageView) findViewById(R.id.iv_monthly);
                TextView tvMonthlyTitle = (TextView) findViewById(R.id.tv_monthly_title);
                TextView tvMonthlySubTitle = (TextView) findViewById(R.id.tv_monthly_subTitle);
                TextView tvMonthlyPrice = (TextView) findViewById(R.id.tv_monthly_price);
                ImageView ivOffer = (ImageView) findViewById(R.id.iv_offer_monthly);


                Picasso.with(this).load(mBillingList.get(i).feature_src).into(ivMonthly);
                tvMonthlyTitle.setText(mBillingList.get(i).product_offer_text);
                tvMonthlySubTitle.setText(mBillingList.get(i).product_offer_sub_text);
                tvMonthlyPrice.setText(Html.fromHtml(mBillingList.get(i).product_price));

                if (mBillingList.get(i).product_offer_status) {
                    ivOffer.setVisibility(View.VISIBLE);
                    if (mBillingList.get(i).product_offer_src != null && !mBillingList.get(i).product_offer_src.equalsIgnoreCase(""))
                        Picasso.with(this).load(mBillingList.get(i).product_offer_src).into(ivOffer);
                }


            } else if (mBillingList.get(i).billing_type.equalsIgnoreCase("halfYear")) {

                mHalfYearLayout.setVisibility(View.VISIBLE);

                ImageView ivHalfYear = (ImageView) findViewById(R.id.iv_halfYear);
                TextView tvHalfYearTitle = (TextView) findViewById(R.id.tv_halfYear_title);
                TextView tvHalfYearSubTitle = (TextView) findViewById(R.id.tv_halfYear_subTitle);
                TextView tvHalfYearPrice = (TextView) findViewById(R.id.tv_halfYear_price);
                ImageView ivOffer = (ImageView) findViewById(R.id.iv_offer_halfYear);


                Picasso.with(this).load(mBillingList.get(i).feature_src).into(ivHalfYear);
                tvHalfYearTitle.setText(mBillingList.get(i).product_offer_text);
                tvHalfYearSubTitle.setText(mBillingList.get(i).product_offer_sub_text);
                tvHalfYearPrice.setText(Html.fromHtml(mBillingList.get(i).product_price));

                if (mBillingList.get(i).product_offer_status) {
                    ivOffer.setVisibility(View.VISIBLE);
                    if (mBillingList.get(i).product_offer_src != null && !mBillingList.get(i).product_offer_src.equalsIgnoreCase(""))
                        Picasso.with(this).load(mBillingList.get(i).product_offer_src).into(ivOffer);
                }


            } else if (mBillingList.get(i).billing_type.equalsIgnoreCase("yearly")) {

                mYearlyLayout.setVisibility(View.VISIBLE);

                ImageView ivYear = (ImageView) findViewById(R.id.iv_Year);
                TextView tvYearTitle = (TextView) findViewById(R.id.tv_Year_title);
                TextView tvYearSubTitle = (TextView) findViewById(R.id.tv_Year_subTitle);
                TextView tvYearPrice = (TextView) findViewById(R.id.tv_Year_price);

                Picasso.with(this).load(mBillingList.get(i).feature_src).into(ivYear);
                tvYearTitle.setText(mBillingList.get(i).product_offer_text);
                tvYearSubTitle.setText(mBillingList.get(i).product_offer_sub_text);
                tvYearPrice.setText(Html.fromHtml(mBillingList.get(i).product_price));

                ImageView ivOffer = (ImageView) findViewById(R.id.iv_offer_Year);

                if (mBillingList.get(i).product_offer_status) {
                    ivOffer.setVisibility(View.VISIBLE);
                    if (mBillingList.get(i).product_offer_src != null && !mBillingList.get(i).product_offer_src.equalsIgnoreCase(""))
                        Picasso.with(this).load(mBillingList.get(i).product_offer_src).into(ivOffer);
                }


            } else if (mBillingList.get(i).billing_type.equalsIgnoreCase("free")) {

                mFreeLayout.setVisibility(View.VISIBLE);

                ImageView ivFree = (ImageView) findViewById(R.id.iv_free);
                TextView tvFreeTitle = (TextView) findViewById(R.id.tv_free_title);
                TextView tvFreeSubTitle = (TextView) findViewById(R.id.tv_free_subtitle);
                TextView tvFreePrice = (TextView) findViewById(R.id.tv_price_free);

                Picasso.with(this).load(mBillingList.get(i).feature_src).into(ivFree);
                tvFreeTitle.setText(mBillingList.get(i).product_offer_text);
                tvFreeSubTitle.setText(mBillingList.get(i).product_offer_sub_text);
                tvFreePrice.setText(Html.fromHtml(mBillingList.get(i).product_price));
            }


        }

    }

    View.OnClickListener mOnCliCkListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.parentFree) {
                if (Slave.hasPurchased(BillingListActivity.this)) {
                    Toast.makeText(BillingListActivity.this, "You are already a premium member", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mBillingList.size(); i++) {
                        if (mBillingList.get(i).billing_type.equals("free")) {
                            Billing b = mBillingList.get(i);

                            startActivity(new Intent(BillingListActivity.this, BillingDetailActivity.class)
                                    .putExtra("billing", b));
                        }
                    }
                }

            } else if (view.getId() == R.id.parentPro) {
                if (Slave.hasPurchasedPro(BillingListActivity.this)) {
                    Toast.makeText(BillingListActivity.this, "You are already a premium member", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mBillingList.size(); i++) {
                        if (mBillingList.get(i).billing_type.equals("pro")) {
                            Billing b = mBillingList.get(i);

                            startActivity(new Intent(BillingListActivity.this, BillingDetailActivity.class)
                                    .putExtra("billing", b));
                        }
                    }
                }
            } else if (view.getId() == R.id.parentweekly) {
                if (Slave.hasPurchasedMonthly(BillingListActivity.this) ||
                        Slave.hasPurchasedHalfYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedPro(BillingListActivity.this) ||
                        Slave.hasPurchasedWeekly(BillingListActivity.this)) {
                    Toast.makeText(BillingListActivity.this, "You are already a premium member", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mBillingList.size(); i++) {
                        if (mBillingList.get(i).billing_type.equals("weekly")) {
                            Billing b = mBillingList.get(i);

                            startActivity(new Intent(BillingListActivity.this, BillingDetailActivity.class)
                                    .putExtra("billing", b));
                        }
                    }
                }

            } else if (view.getId() == R.id.parentMonthly) {
                if (Slave.hasPurchasedHalfYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedPro(BillingListActivity.this) ||
                        Slave.hasPurchasedMonthly(BillingListActivity.this)) {
                    Toast.makeText(BillingListActivity.this, "You are already a premium member", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mBillingList.size(); i++) {
                        if (mBillingList.get(i).billing_type.equals("monthly")) {
                            Billing b = mBillingList.get(i);

                            startActivity(new Intent(BillingListActivity.this, BillingDetailActivity.class)
                                    .putExtra("billing", b));
                        }
                    }
                }

            } else if (view.getId() == R.id.parentHalfYear) {
                if (Slave.hasPurchasedYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedPro(BillingListActivity.this) ||
                        Slave.hasPurchasedHalfYearly(BillingListActivity.this)) {
                    Toast.makeText(BillingListActivity.this, "You are already a premium member", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mBillingList.size(); i++) {
                        if (mBillingList.get(i).billing_type.equals("halfYear")) {
                            Billing b = mBillingList.get(i);

                            startActivity(new Intent(BillingListActivity.this, BillingDetailActivity.class)
                                    .putExtra("billing", b));
                        }
                    }
                }

            } else if (view.getId() == R.id.parentYearly) {
                if (Slave.hasPurchasedYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedPro(BillingListActivity.this)) {
                    Toast.makeText(BillingListActivity.this, "You are already a premium member", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mBillingList.size(); i++) {
                        if (mBillingList.get(i).billing_type.equals("yearly")) {
                            Billing b = mBillingList.get(i);

                            startActivity(new Intent(BillingListActivity.this, BillingDetailActivity.class)
                                    .putExtra("billing", b));
                        }
                    }
                }

            } else if (view.getId() == R.id.btn_pro) {
                if (Slave.hasPurchasedPro(BillingListActivity.this)) {
                    Toast.makeText(BillingListActivity.this, "You are already a premium member", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mBillingList.size(); i++) {
                        if (mBillingList.get(i).billing_type.equals("pro")) {
                            Billing b = mBillingList.get(i);

                            startActivity(new Intent(BillingListActivity.this, BillingDetailActivity.class)
                                    .putExtra("billing", b));
                        }
                    }
                }
            } else if (view.getId() == R.id.btn_weekly) {
                if (Slave.hasPurchasedMonthly(BillingListActivity.this) ||
                        Slave.hasPurchasedHalfYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedPro(BillingListActivity.this) ||
                        Slave.hasPurchasedWeekly(BillingListActivity.this)) {
                    Toast.makeText(BillingListActivity.this, "You are already a premium member", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mBillingList.size(); i++) {
                        if (mBillingList.get(i).billing_type.equals("weekly")) {
                            Billing b = mBillingList.get(i);

                            startActivity(new Intent(BillingListActivity.this, BillingDetailActivity.class)
                                    .putExtra("billing", b));
                        }
                    }
                }
            } else if (view.getId() == R.id.btn_monthly) {
                if (Slave.hasPurchasedHalfYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedPro(BillingListActivity.this) ||
                        Slave.hasPurchasedMonthly(BillingListActivity.this)) {
                    Toast.makeText(BillingListActivity.this, "You are already a premium member", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mBillingList.size(); i++) {
                        if (mBillingList.get(i).billing_type.equals("monthly")) {
                            Billing b = mBillingList.get(i);

                            startActivity(new Intent(BillingListActivity.this, BillingDetailActivity.class)
                                    .putExtra("billing", b));
                        }
                    }
                }
            } else if (view.getId() == R.id.btn_halfyearly) {
                if (Slave.hasPurchasedYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedPro(BillingListActivity.this) ||
                        Slave.hasPurchasedHalfYearly(BillingListActivity.this)) {
                    Toast.makeText(BillingListActivity.this, "You are already a premium member", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mBillingList.size(); i++) {
                        if (mBillingList.get(i).billing_type.equals("halfYear")) {
                            Billing b = mBillingList.get(i);

                            startActivity(new Intent(BillingListActivity.this, BillingDetailActivity.class)
                                    .putExtra("billing", b));
                        }
                    }
                }
            } else if (view.getId() == R.id.btn_yearly) {
                if (Slave.hasPurchasedYearly(BillingListActivity.this) ||
                        Slave.hasPurchasedPro(BillingListActivity.this)) {
                    Toast.makeText(BillingListActivity.this, "You are already a premium member", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < mBillingList.size(); i++) {
                        if (mBillingList.get(i).billing_type.equals("yearly")) {
                            Billing b = mBillingList.get(i);

                            startActivity(new Intent(BillingListActivity.this, BillingDetailActivity.class)
                                    .putExtra("billing", b));
                        }
                    }
                }
            }
        }
    };
}
