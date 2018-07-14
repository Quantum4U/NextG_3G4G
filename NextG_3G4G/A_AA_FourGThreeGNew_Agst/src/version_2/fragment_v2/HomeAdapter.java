package version_2.fragment_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appnextg.fourg.R;

/**
 * Created by black on 4/13/2017.
 */
public class HomeAdapter extends BaseAdapter {
    private String[] text = {"Internet Speed Test", "Mobile Number Tracker", "Sim Details", "Phone Details", "Switch 3G to 4G", "More"};
    private String[] BtnText = {"Check", "Track", "View", "View", "Switch", "View"};
    private int[] icon = {R.drawable.speed_test, R.drawable.version2_tracker, R.drawable.version2_simdetail, R.drawable.version2_phone_detail,
            R.drawable.version2_3g, R.drawable.more_icon};

    private Context context;
    private LayoutInflater layoutInflater;

    public HomeAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return text.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.homefrag_row, null);

            holder.text = (TextView) convertView.findViewById(R.id.text);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.btnText = (TextView) convertView.findViewById(R.id.btn_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(text[position]);
        holder.image.setImageResource(icon[position]);
        holder.btnText.setText(BtnText[position]);

        return convertView;
    }


    private static class ViewHolder {
        private TextView text, btnText;
        private ImageView image;
    }

}
