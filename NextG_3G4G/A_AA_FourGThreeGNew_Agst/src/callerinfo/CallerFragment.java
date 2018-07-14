package callerinfo;

import android.appwidget.AppWidgetHostView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import app.adshandler.AHandler;
import com.appnextg.fourg.R;

/**
 * Created by black on 11/24/2016.
 */
public class CallerFragment extends Fragment {
    private Button btn_launch;
    private EditText et_phone;
    private CallUtils utils;
    private TextView number, network, location;
    private LinearLayout parent;
LinearLayout nativeads;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.callerfragment, container, false);
        utils = new CallUtils();
        btn_launch = (Button) view.findViewById(R.id.btn_launch);
        et_phone = (EditText) view.findViewById(R.id.et_phone);

        number = (TextView) view.findViewById(R.id.mobileNumber);
        network = (TextView) view.findViewById(R.id.network);
        location = (TextView) view.findViewById(R.id.location);
        parent = (LinearLayout) view.findViewById(R.id.parent);
        nativeads=(LinearLayout)view.findViewById(R.id.nativeads);
        nativeads.addView(new AHandler().showNativeMedium(getActivity()));

        btn_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_phone.getText().toString().length() == 10) {
                    parent.setVisibility(View.VISIBLE);
                    number.setText(et_phone.getText().toString());

                    searchNumber(et_phone.getText().toString(), getActivity(), network, location);
                }else{
                    Toast.makeText(getActivity(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    private void searchNumber(String number, Context context, TextView tv_network, TextView tv_location) {
        if (number.length() == 11)
            number = number.substring(1, 11);
        if (number.length() == 12)
            number = number.substring(2, 12);
        if (number.length() == 13)
            number = number.substring(3, 13);
        String orignal = number;
        GetCallerDetails getCallerDetails = new GetCallerDetails();
        System.out.println("Caller User Input " + number);
        if (number != null && number.length() > 4) {
            number = number.substring(0, 4);
            System.out.println("Caller User substring " + number);
            String tempnum = number;
            number = tempnum.substring(0, 2) + "," + tempnum.substring(2, 4);
            System.out.println("Caller User replacement " + number);

            String filedata = loadAssetTextAsString(context, "data.txt");

            int index = filedata.indexOf(number);
            //System.out.println("Sub Index "+index);
            String caller_info = filedata.substring(index + 11, index + 100);
            getCallerDetails.getDetails(caller_info);

            if (orignal.length() < 10) {
//                textView.setText("Mobile No: " + orignal + "\n(Unknown Location)");
                tv_location.setText("Location: Unknown Location");
            } else {
                String network_name = getCallerDetails.getDetails(caller_info).OPARATOR_NAME;
                String location = getCallerDetails.getDetails(caller_info).LOCATION;

                if (network_name.length() < 4 || location.length() < 4) {
//                    textView.setText("Mobile No: " + orignal + "\n(Unknown Location)   ");
                    tv_location.setText("Location: Unknown Location");
                } else {
//                    textView.setText("Mobile No: " + orignal + "\n Network:" + getCallerDetails.getDetails(caller_info).OPARATOR_NAME
//                            + "    \n" + "Location:" + getCallerDetails.getDetails(caller_info).LOCATION + "      ");
                    tv_location.setText("Location:" + " " + getCallerDetails.getDetails(caller_info).LOCATION);
                    tv_network.setText("Network:" + " " + getCallerDetails.getDetails(caller_info).OPARATOR_NAME);
                }

            }
        } else {
//            textView.setText("Mobile No " + orignal + "(Unknown Location)");
            tv_location.setText("Location: Unknown Location");

        }
    }


    private String loadAssetTextAsString(Context context, String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ((str = in.readLine()) != null) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            System.out.println("sub ex" + e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("sub ex 2 " + e);
                }
            }
        }

        return null;
    }

}
