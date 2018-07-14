package app.adshandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import app.PrintLog;
import app.pnd.adshandler.R;
import app.server.v2.DataHubConstant;
import app.server.v2.Slave;
import app.serviceprovider.Utils;

public class ANG_PromptHander {

    public void checkForNormalUpdate(Context context) {

        //PrintLog.print("NPrompt 1 " + DataHub.NORMAL_UPDATE_STATUS);
        PrintLog.print("checking " + Slave.UPDATES_updateTYPE);
        if (Slave.UPDATES_updateTYPE.equals(Slave.IS_NORMAL_UPDATE)) {

            if (!getCurrentAppVersion(context).equals(
                    Slave.UPDATES_version)) {
                if (DataHubConstant.APP_LAUNCH_COUNT % 3 == 0)
                    openNormalupdateAlert(context, Slave.UPDATES_prompttext,
                            "Update App", Slave.UPDATES_appurl, false);

            }
        }

    }

    public void checkForForceUpdate(Context context) {
        try {
            if (Slave.UPDATES_updateTYPE.equals(Slave.IS_FORCE_UPDATE)) {

                if (!getCurrentAppVersion(context).equals(
                        Slave.UPDATES_version)) {

                    openNormalupdateAlert(context, Slave.UPDATES_prompttext,
                            "Update App", Slave.UPDATES_appurl, true);

                }
            }
        } catch (Exception e) {

        }

    }

    public String getCurrentAppVersion(Context context) {
        try {
            PackageInfo pInfo = null;
            try {
                pInfo = context.getPackageManager().getPackageInfo(
                        context.getPackageName(), 0);
            } catch (NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String version = pInfo.versionName;

            int verCode = pInfo.versionCode;
            return "" + verCode;

        } catch (Exception e) {
            return "100";
        }

    }

    public void setNormalUpdate(Context context, String version) {
        SharedPreferences NormalUpdate = context.getSharedPreferences(
                "NormalUpdate", Context.MODE_WORLD_WRITEABLE);
        Editor editor = NormalUpdate.edit();
        editor.putString("NormalUpdate", version);
        editor.commit();
    }

    /*-get NetOneUserRegistration filds-*/

    public String getormalUpdate(Context context) {
        SharedPreferences NormalUpdate = context.getSharedPreferences(
                "NormalUpdate", Context.MODE_WORLD_WRITEABLE);
        return NormalUpdate.getString("NormalUpdate", "100");
    }

    private void openNormalupdateAlert(final Context context, String msg,
                                       String title, final String url, final boolean is_force) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(R.drawable.downloadsy);
        alertDialogBuilder.setMessage(msg);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("UPDATE NOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                        dialog.cancel();
                        if (is_force)
                            ((Activity) context).finish();
                    }
                });
        // set negative button: No message
        alertDialogBuilder.setNegativeButton("NO THANKS!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel the alert box and put a Toast to the user
                        dialog.cancel();
                        if (is_force)
                            ((Activity) context).finish();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
        if (is_force)
            alertDialog.setCancelable(false);
        else
            alertDialog.setCancelable(true);
    }


    private void openForceupdateAlert(final Context context, String msg,
                                      String title, final String url, final boolean is_force) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(R.drawable.downloadsy);
        alertDialogBuilder.setMessage(msg);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("UPDATE NOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                        dialog.cancel();
                        if (is_force)
                            ((Activity) context).finish();
                    }
                });
        // set negative button: No message
        alertDialogBuilder.setNegativeButton("Exit from App",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // cancel the alert box and put a Toast to the user
                        dialog.cancel();
                        if (is_force)
                            ((Activity) context).finish();
                        System.exit(1);

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
        if (is_force)
            alertDialog.setCancelable(false);
        else
            alertDialog.setCancelable(true);
    }

    public void crossPromotionDialog(Context context, OnCPDialogClick onCPDialogClick, String header, String mDescription, String bgColor, String textColor, String cpURL) {
        mCPDialogClick = onCPDialogClick;
        final Dialog dialog = new Dialog(context, R.style.BaseTheme);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.remove_ads);

        Picasso.with(context).load(cpURL).placeholder(R.drawable.app_icon).into((ImageView) dialog.findViewById(R.id.cpIcon));

        TextView btn_dismiss = (TextView) dialog.findViewById(R.id.btn_dismiss);
        TextView btn_download_now = (TextView) dialog.findViewById(R.id.btn_download_now);

        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView description = (TextView) dialog.findViewById(R.id.description);
        FrameLayout frame_dialog = (FrameLayout) dialog.findViewById(R.id.frame_dialog);

        GradientDrawable gd = (GradientDrawable) frame_dialog.getBackground();
        gd.setColor(Color.parseColor(bgColor));
        frame_dialog.setBackground(gd);

        title.setText(textColor);
        description.setText(textColor);

        title.setText(header);
        description.setText(mDescription);

        btn_download_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                mCPDialogClick.clickOK();
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private OnCPDialogClick mCPDialogClick;

    public interface OnCPDialogClick {
        void clickOK();
    }


    public static int getStringtoInt(String data) {
        try {
            return Integer.parseInt(data);
        } catch (Exception e) {
            return 0;
        }

    }

    @SuppressWarnings("deprecation")
    public void ratee(final Context context, Drawable appimage, final boolean isFromMapper) {
//        PrintLog.print("1036 rate app navigation" + " " + Slave.RATE_APP_nevigation + " " + DataHubConstant.APP_LAUNCH_COUNT);
//        int server_launchCount = getStringtoInt(Slave.RATE_APP_nevigation);
//        if (DataHubConstant.APP_LAUNCH_COUNT % server_launchCount == 0) {
//            if (getDaysDiff(context) >= getStringtoInt(Slave.CP_startday)) {


        final RatingBar ratingBar;
        PrintLog.print("EngineV2 rate url " + Slave.RATE_APP_appurl + " Rate Text " + Slave.RATE_APP_prompttext);

        /*
         * final Dialog dialog = new Dialog(MainMenu.this,
         * android.R.style.Theme_Light_NoTitleBar);
         */
        final Dialog dialog = new Dialog(context, R.style.BaseTheme);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rate_us_v2);

        ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar1);
        final LinearLayout parent = (LinearLayout) dialog.findViewById(R.id.parent);

        GradientDrawable gd = (GradientDrawable) parent.getBackground();
        PrintLog.print("1313 bg color rate" + " " + Slave.RATE_APP_BG_COLOR);

        try {

            gd.setColor(Color.parseColor(Slave.RATE_APP_BG_COLOR));
            parent.setBackground(gd);

        }catch (Exception e){

        }

        final TextView tvRateDescription = (TextView) dialog.findViewById(R.id.tvRateDescription);
        final TextView tv_Header = (TextView) dialog.findViewById(R.id.tv_Header);
        tvRateDescription.setText(Slave.RATE_APP_rateapptext);
        tv_Header.setText(Slave.RATE_APP_HEADER_TEXT);

        final Button submit = (Button) dialog.findViewById(R.id.submitlinear);
        final Button dismiss = (Button) dialog.findViewById(R.id.remindlinear);


        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (isFromMapper) {
                    ((Activity) context).finish();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float n = ratingBar.getRating();
                if (n == 0) {
                    Toast.makeText(context, "Please select rating stars", Toast.LENGTH_SHORT).show();
                } else if (n <= 3 & n > 0) {
                    PrintLog.print("Rate App URL is 0 PPPP");
                    dialog.dismiss();
                    new Utils().sendFeedback(context);
                    if (isFromMapper) {
                        PrintLog.print("Rate App URL is 0 PPPP 1");
                        ((Activity) context).finish();
                    }
                } else {
                    PrintLog.print("Rate App URL is 0 ");
                    new Utils().rateUs(context);
                    dialog.dismiss();
                    if (isFromMapper) {
                        PrintLog.print("Rate App URL is 0 1");
                        ((Activity) context).finish();
                    }

                }
            }
        });

        ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                float n = ratingBar.getRating();
                if (n <= 0) {
//                    submit.setEnabled(false);
                    submit.setTextColor(context.getResources().getColor(R.color.dark_grey));
                } else {
                    submit.setTextColor(context.getResources().getColor(android.R.color.white));
                    submit.setEnabled(true);
                }
            }
        });
        dialog.show();
//            }
//        }
    }

    public long getDaysDiff(Context context) {
        try {
            PrintLog.print("getInstalltionTime(context) 0 stage ");
            PrintLog.print("getInstalltionTime(context) " + getInstalltionTime(context));
            long day = System.currentTimeMillis() - getInstalltionTime(context);
            day = day / (1000 * 60 * 60 * 24);
            return day;


        } catch (Exception e) {
            return 0;
        }
    }

    public long getInstalltionTime(Context context) {

        long installed = System.currentTimeMillis();
        try {
            installed = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0).firstInstallTime;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return installed;
    }

}
