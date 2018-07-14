package app.pnd.fourg;

import java.util.ArrayList;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import app.adshandler.AHandler;
import app.pnd.fourg.history.AppSharedPreferences;
import app.pnd.fourg.history.HistoryAdapter;
import app.pnd.fourg.history.HistoryData;
import version_2.MainActivity_V2;

import com.appnextg.fourg.R;

public class HistoryActivity extends Activity {

    private ListView listView;
    private AppSharedPreferences preferences;
    private ArrayList<HistoryData> list;
    private HistoryAdapter adapter;
    private Button deleteButton;
    private LinearLayout adsbanner;
    private TextView noHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        listView = (ListView) findViewById(R.id.listView);
        deleteButton = (Button) findViewById(R.id.delete);
        adsbanner = (LinearLayout) findViewById(R.id.adsbanner);

        adsbanner.addView(new AHandler().getBannerHeader(HistoryActivity.this));

        preferences = new AppSharedPreferences(HistoryActivity.this);
        noHistory = (TextView) findViewById(R.id.noHistory);
        list = new ArrayList<HistoryData>();

        for (int i = 0; i < preferences.getCounter(HistoryActivity.this); i++) {
            if (!preferences.getHistoryDelete(i)) {
                HistoryData data = new HistoryData();
                data.id = i;
                data.date = preferences.getHistoryDate(this, i);
                data.upload = preferences.getHistoryUPLOAD(this, i);
                data.download = preferences.getHistoryDOWNLOAD(this, i);
                System.out.println("123 checking history content "
                        + preferences.getHistoryDOWNLOAD(this, i));
                System.out.println("123 checking history date" + " "
                        + preferences.getHistoryDate(this, i));
                System.out.println("123 checking history uri" + " "
                        + preferences.getHistoryUPLOAD(this, i));
                list.add(data);
            }
        }
        adapter = new HistoryAdapter(HistoryActivity.this, list);
        listView.setAdapter(adapter);

        deleteButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AHandler().showFullAds(HistoryActivity.this, false);
                if (list.size() == 0)
                    Toast.makeText(HistoryActivity.this, "No Record Found!!", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(HistoryActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < list.size(); i++) {
                    if (adapter.mCheckStates[i]) {
                        // File f = new File(arrayList.get(i).path);
                        // if (f.exists())
                        // f.delete();
                        preferences.setHistoryDelete(list.get(i).id, true);
                        list.remove(i);
                    }
                }
                adapter.update(list);

            }
        });
        if (list.size() > 0) {
            deleteButton.setVisibility(View.VISIBLE);
            noHistory.setVisibility(View.GONE);
        } else {
            noHistory.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
        }
    }


    public void showPrompt() {
        AlertDialog.Builder dia = new AlertDialog.Builder(this).setTitle(getApplicationContext()
                .getResources().getString(R.string.pindi_app_name))
                .setMessage(getApplicationContext().getResources().getString(R.string.forAlert))
                .setPositiveButton(getApplicationContext().getResources().getString(R.string.forOption), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //aHandler.showFullAds(MainActivity.this,false);
                        HistoryActivity.this.finish();
                    }
                })
                .setNegativeButton(getApplicationContext().getResources().getString(R.string.forMoreOption), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new app.serviceprovider.Utils().moreApps(HistoryActivity.this);
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.version_ic_icon).setCancelable(false);
        dia.setCancelable(true);
        dia.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        new AHandler().showFullAds(this, false);
        return super.onKeyDown(keyCode, event);
    }
}
