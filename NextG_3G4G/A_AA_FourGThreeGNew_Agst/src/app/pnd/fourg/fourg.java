package app.pnd.fourg;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.ads.StringPickerDialog;

import app.adshandler.AHandler;

import com.appnextg.fourg.R;

public class fourg extends Fragment implements
        View.OnClickListener, StringPickerDialog.OnClickListener {

    AHandler aHandler;

    LinearLayout adslayout1;
    Button buttonfloating;
    //    com.gc.materialdesign.views.ButtonFloat more;
    TextView click_text;
    static int a = 0;
    boolean is4g = false;
    ImageView imageView;
    LinearLayout click;
    private LinearLayout nativeads;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.version_fourg, container, false);
        //   adsbanner = (LinearLayout) view.findViewById(R.id.adsbanner);
        nativeads = (LinearLayout) view.findViewById(R.id.nativeads);
        nativeads.addView(new AHandler().showNativeMedium(getActivity()));


//        adsbanner.addView(new AHandler().getBannerHeader(getActivity()));

        imageView = (ImageView) view.findViewById(R.id.image);
//        more = (com.gc.materialdesign.views.ButtonFloat) view.findViewById(R.id.buttonFloat);
        click_text = (TextView) view.findViewById(R.id.click_text);
        click = (LinearLayout) view.findViewById(R.id.click);
        aHandler = new AHandler();
//        more.setVisibility(View.GONE);
//        more.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                if (a % 2 == 0) {
//                    try {
//                        Toast.makeText(getActivity(), " " + getResources().getString(R.string.pindi_pleaserate), Toast.LENGTH_LONG).show();
//                        new UtilsApp().rateUs(getActivity());
//                    } catch (Exception e) {
//
//                    }
//                } else {
//                    try {
//                        new UtilsApp().moreApps(getActivity());
//                    } catch (Exception e) {
//                    }
//                }
//                a++;
//
        //showPicker();


//            }
//        });


        click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    new AsyncData().execute();
                } catch (Exception e) {
                }
            }
        });


        return view;

    }

    ProgressDialog pd;

    class AsyncData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("" + getResources().getString(R.string.pindi_pleasewait));
            pd.show();

        }


        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            try {
                if (is4g) {
                    Toast.makeText(getActivity(), "" + getResources().getString(R.string.pindi_converto3g), Toast.LENGTH_LONG).show();
                    click_text.setText(" " + getResources().getString(R.string.pindi_converto4g));
                    is4g = false;
                    imageView.setBackgroundResource(R.drawable.version_3g);
                } else {
                    is4g = true;
                    imageView.setBackgroundResource(R.drawable.version_4g);
                    Toast.makeText(getActivity(), "" + getResources().getString(R.string.pindi_converto4g), Toast.LENGTH_LONG).show();
                    click_text.setText("" + getResources().getString(R.string.pindi_converto3g));
                }
                pd.cancel();
                aHandler.showFullAds(getActivity(), false);
            } catch (Exception e) {
            }
        }

    }

    public void showPicker() {
        final String TAG = StringPickerDialog.class.getSimpleName();

        Bundle bundle = new Bundle();
        bundle.putStringArray(getString(R.string.pindi_string_picker_dialog_title), getStringArray());
        StringPickerDialog dialog = new StringPickerDialog();
        dialog.setArguments(bundle);
        dialog.show(getActivity().getSupportFragmentManager(), TAG);
    }

    @Override
    public void onClick(String value) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    private String[] getStringArray() {
        return new String[]{"System Language", "English UK", "English US", "French", "German", "Arebic", "US Eng", "Nepali"};
    }

}