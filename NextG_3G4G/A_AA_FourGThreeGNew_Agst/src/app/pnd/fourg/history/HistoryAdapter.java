package app.pnd.fourg.history;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.appnextg.fourg.R;

import com.checkspeed.Utility;

public class HistoryAdapter extends BaseAdapter {
    private WeakReference<Context> context;
    private LayoutInflater inflate;
    public boolean[] mCheckStates;
    public ArrayList<HistoryData> arrayList;

    public HistoryAdapter(Context c, ArrayList<HistoryData> arrayList) {
        this.context = new WeakReference<Context>(c);
        this.arrayList = arrayList;
        inflate = LayoutInflater.from(c);
        mCheckStates = new boolean[arrayList.size()];
    }

    public void update(ArrayList<HistoryData> arrayList) {
        this.arrayList = arrayList;
        mCheckStates = new boolean[arrayList.size()];
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderHistory holder;
        if (convertView == null) {
            inflate = LayoutInflater.from(context.get());
            convertView = inflate.inflate(R.layout.history_row_new, parent,
                    false);
            holder = new ViewHolderHistory();
            // holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.upload = (TextView) convertView.findViewById(R.id.upload);
            holder.download = (TextView) convertView
                    .findViewById(R.id.download);
            holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderHistory) convertView.getTag();
        }
        HistoryData data = arrayList.get(position);
        // holder.icon.setImageResource(R.drawable.version_internet_balance);
        try {
            holder.upload.setText(String.format("%.2f",
                    Double.parseDouble(data.upload)));
            holder.download.setText(String.format("%.2f",
                    Double.parseDouble(data.download)));
        } catch (NumberFormatException e) {

        }

        holder.date.setText(Utility.getDate(Long.parseLong(data.date)));

        String main[] = Utility.getDate(Long.parseLong(data.date)).split(" ");
        String major = main[0];
        String minor = main[1];
        holder.date.setText(major);

        holder.cb.setChecked(mCheckStates[position]);
        holder.cb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckBox cbs = (CheckBox) v;
                // if (cbs.isChecked()) {
                // sp.setHistoryDelete(context.get(), position);
                // sp.setHistoryCounter(context.get(),
                // sp.getHistoryCounter(context.get()) + 1);
                // cbs.setChecked(true);
                // } else {
                // sp.setHistoryDelete(context.get(), 100);
                // sp.setHistoryCounter(context.get(),
                // sp.getHistoryCounter(context.get()) + 1);
                // cbs.setChecked(false);
                // }
                mCheckStates[position] = cbs.isChecked();
            }
        });
        return convertView;
    }

    private class ViewHolderHistory {
        private TextView date, upload, download;
        private CheckBox cb;
    }
}
