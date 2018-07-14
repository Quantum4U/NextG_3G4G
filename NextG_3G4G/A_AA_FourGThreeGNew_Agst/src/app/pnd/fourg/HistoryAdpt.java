package app.pnd.fourg;

import java.util.ArrayList;

import com.appnextg.fourg.R;

import pnd.chache.SystemInfoUtil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryAdpt extends BaseAdapter {
    SystemInfoUtil syt;
    ViewHolder viewHolder = null;
    static ArrayList<String> current_lock = new ArrayList();
    private LayoutInflater inflater = null;
    String appNames[];

    String[] appPack;
    Context context;

    public HistoryAdpt(String names[], String pack[], Context con) {
        appNames = names;

        context = con;
        appPack = pack;
        syt = new SystemInfoUtil(context);

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public int getCount() {
        // TODO Auto-generated method stub
        return appNames.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    @SuppressLint("ResourceAsColor")
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        // packageManager=activity.getPackageManager();

        if (convertView == null) {
            vi = inflater.inflate(R.layout.history_app_list_data, null);
            viewHolder = new ViewHolder();
            viewHolder.appName = (TextView) vi.findViewById(R.id.appname);
            viewHolder.appIcon = (ImageView) vi.findViewById(R.id.appicon);
            viewHolder.appStatus = (Button) vi.findViewById(R.id.appstatus);


            vi.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.appName.setText("" + appNames[position]);
        //viewHolder.appIcon.setBackgroundResource(appIdes[position]);
        setAppIcon(appPack[position], viewHolder.appIcon);

        viewHolder.appStatus.setId(position);


        viewHolder.appStatus.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint({"ResourceAsColor", "NewApi"})
            public void onClick(View v) {

                //rateUs(context,appPack[v.getId()]);
                if (v.getId() == 0) {
                    new Load2().execute("");

                    syt.clearBrowserHistory();
                    Toast.makeText(context, "Browser History Cleaned sucessfully", Toast.LENGTH_LONG).show();
                } else if (v.getId() == 1) {
                    new Load2().execute("");

                    syt.clearGmailHistory();
                    Toast.makeText(context, "Gmail History Cleaned sucessfully", Toast.LENGTH_LONG).show();

                } else if (v.getId() == 2) {
                    new Load2().execute("");

                    syt.clearPlayHistory();
                    Toast.makeText(context, "Play Store History Cleaned sucessfully", Toast.LENGTH_LONG).show();

                } else {
                    new Load2().execute("");

                    syt.clearMapHistory();
                    Toast.makeText(context, "Google Maps History Cleaned sucessfully", Toast.LENGTH_LONG).show();

                }
            }
        });
        return vi;
    }

    public class ViewHolder {
        TextView appName;
        ImageView appIcon;
        Button appStatus;
    }


    public void setAppIcon(String package_name, ImageView image) {

        try {
            ApplicationInfo app = context.getPackageManager().getApplicationInfo(package_name, 0);
            PackageManager packageManager = context.getPackageManager();
            Drawable icon = packageManager.getApplicationIcon(app);
            //  dd=icon;
            image.setBackgroundDrawable(icon);

            CharSequence name = packageManager.getApplicationLabel(app);


        } catch (NameNotFoundException e) {
            //  Toast toast = Toast.makeText(this, "error in getting icon", Toast.LENGTH_SHORT);
            //  toast.show();
            //  e.printStackTrace();

        }


    }

    class Load2 extends AsyncTask<String, Integer, Integer> {
        ProgressDialog pDialog;

        protected Integer doInBackground(String... params) {
            //System.out.println("Click 1 "+mState);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Integer result) {

            super.onPostExecute(result);
            //Toast.makeText(context, total+" Cache released sucessfully", Toast.LENGTH_LONG).show();
            pDialog.cancel();
        }

        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();

        }

    }


}
