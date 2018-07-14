package com.checkspeed;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import app.adshandler.AHandler;
import app.adshandler.MasterData;
import app.adshandler.ANG_PromptHander;
import app.pnd.fourg.HistoryActivity;
import app.pnd.fourg.history.AppSharedPreferences;
import com.appnextg.fourg.R;
import app.serviceprovider.Utils;
import version_2.fragment_v2.HomeFragment;

public class SpeedFragment extends Fragment {
    Context context;
    private DecimalFormat decimalFormat, decimalFormatForWidget;
    private TextView txtViewForPing, txtViewForDownload, txtViewForUpload;
    private Button btnStart;
    private ProgressBar progressBarForPing, progressBarForDownload,
            progressBarForUpload;

    private SpeedMeter speedMeter;
    private RelativeLayout relativeLayout;
    private String displaySpeedUnit;

    private final int PING_STATUS = 0;
    private final int DOWNLOAD_STATUS = 1;
    private final int UPLOAD_STATUS = 2;
    private final int UPDATE_STATUS = 3;
    private final int TESTING_PING = 4;
    private final int NO_NETWORK = 5;
    private final int FILE_NOT_FOUND = 6;
    private final int UPLOAD_STATUS_NEW = 7;

    private final static int UPDATE_THRESHOLD = 300;
    private static final int EXPECTED_SIZE_IN_BYTES = 1048576; // 1MB=1024*1024=1048576
    private static final double BYTE_TO_KILOBIT = 0.0078125;
    private static final double KILOBIT_TO_MEGABIT = 0.0009765625;

    private String downloadURL = "http://www.migital.com/SuperWiFi.txt";
    private String uploadURL = "http://scms1.migital.com/voicereminder_android/testupload.php";
    private AppSharedPreferences preferences;

    private ImageView showHistory;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.check_speed, container, false);
        preferences = new AppSharedPreferences(getActivity());
        displaySpeedUnit = SharedData.get_Display_Speed_Unit(context);
        //adsbanner = (LinearLayout) view.findViewById(R.id.adsbanner);
        showHistory = (ImageView) view.findViewById(R.id.showHistory);
        showHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HistoryActivity.class));
            }
        });
       // adsbanner.addView(new AHandler().getBannerHeader(getActivity()));
        speedMeter = new SpeedMeter(context);
        relativeLayout = (RelativeLayout) view
                .findViewById(R.id.relativeLayoutForSpeedMeterView);

        relativeLayout.addView(speedMeter);

        decimalFormat = new DecimalFormat("##.##");
        decimalFormatForWidget = new DecimalFormat("##");

        txtViewForPing = (TextView) view.findViewById(R.id.txtViewForPing);
        txtViewForDownload = (TextView) view
                .findViewById(R.id.txtViewForDownload);
        txtViewForUpload = (TextView) view.findViewById(R.id.txtViewForUpload);

        progressBarForPing = (ProgressBar) view
                .findViewById(R.id.progressBarForPing);
        progressBarForDownload = (ProgressBar) view
                .findViewById(R.id.progressBarForDownload);
        progressBarForUpload = (ProgressBar) view
                .findViewById(R.id.progressBarForUpload);

        btnStart = (Button) view.findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                txtViewForPing.setText("");
                txtViewForDownload.setVisibility(View.VISIBLE);
                txtViewForDownload.setText("");
                txtViewForUpload.setText("");
                btnStart.setEnabled(false);

                new Speed().execute("");

            }
        });
        doEngineWork(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);
        context = activity;
    }

    class Speed extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            getPingSpeed();
            getDownloadSpeed();
            getDownloadSpeedNew();
            // getUploadSpeed("");
            preferences.setHistoryDate(getActivity(),
                    Long.toString(System.currentTimeMillis()));
            preferences.setCounter(getActivity(),
                    preferences.getCounter(getActivity()) + 1);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBarForPing.setProgress(0);
            progressBarForDownload.setProgress(0);
            progressBarForUpload.setProgress(0);
            btnStart.setEnabled(true);
            // btnStart.setText(""+getResources().getString(R.string.pindi_ReCheck));
            new AHandler().showFullAds(getActivity(), true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarForPing.setProgress(0);
            progressBarForDownload.setProgress(0);
            progressBarForUpload.setProgress(0);
            progressBarForPing.setVisibility(View.VISIBLE);
            progressBarForDownload.setVisibility(View.VISIBLE);
            progressBarForUpload.setVisibility(View.VISIBLE);
        }
    }

    private static class SpeedInfo {
        public double kilobits = 0;
        public double megabits = 0;
        public double downloadSpeed = 0;
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PING_STATUS:
                    try {
                        SpeedInfo speedInfo_ping = (SpeedInfo) msg.obj;
                        txtViewForPing.setText("" + msg.arg1 + " ms");
                        progressBarForPing.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {

                    }
                    break;

                case DOWNLOAD_STATUS:
                    try {
                        SpeedInfo speedInfo_download = (SpeedInfo) msg.obj;

                        if (displaySpeedUnit.equalsIgnoreCase("KBPS")) {
                            txtViewForDownload.setText(""
                                    + decimalFormat.format(speedInfo_download.kilobits)
                                    + " kbps");
                            // txtViewForUpload.setVisibility(View.VISIBLE);
                            // txtViewForUpload.setText(""
                            // + decimalFormat.format(speedInfo_download.kilobits)
                            // + " kbps");
                            preferences
                                    .setHistoryDOWNLOAD(
                                            getActivity(),
                                            Double.toString(speedInfo_download.kilobits / 1024));
                        } else if (displaySpeedUnit.equalsIgnoreCase("MBPS")) {
                            txtViewForDownload.setText(""
                                    + decimalFormat.format(speedInfo_download.megabits)
                                    + " mbps");
                            // txtViewForUpload.setText(""
                            // + decimalFormat.format(speedInfo_download.megabits)
                            // + " mbps");
                            preferences.setHistoryDOWNLOAD(getActivity(),
                                    Double.toString(speedInfo_download.megabits));
                        }

                        progressBarForDownload.setVisibility(View.INVISIBLE);
                        speedMeter.calculateAngleOfDeviation(0, "0.0");
                        // getDownloadSpeedNew(msg);
                    } catch (Exception e) {

                    }
                    break;

                case UPLOAD_STATUS:
                    try {
                        SpeedInfo speedInfo_upload = (SpeedInfo) msg.obj;

                        if (displaySpeedUnit.equalsIgnoreCase("KBPS")) {
                            txtViewForUpload.setText(""
                                    + decimalFormat.format(speedInfo_upload.kilobits)
                                    + " kbps");
                        } else if (displaySpeedUnit.equalsIgnoreCase("MBPS")) {
                            txtViewForUpload.setText(""
                                    + decimalFormat.format(speedInfo_upload.megabits)
                                    + " mbps");
                        }

                        progressBarForUpload.setVisibility(View.INVISIBLE);
                        speedMeter.calculateAngleOfDeviation(0,
                                LanguageDB.strSpeedCheckCompleted);
                    } catch (Exception e) {

                    }
                    break;

                case UPDATE_STATUS:
                    try {
                        SpeedInfo speedInfo_update = (SpeedInfo) msg.obj;

                        if (displaySpeedUnit.equalsIgnoreCase("KBPS"))
                            speedMeter.calculateAngleOfDeviation(
                                    (float) speedInfo_update.kilobits,
                                    decimalFormat.format(speedInfo_update.kilobits));
                        else if (displaySpeedUnit.equalsIgnoreCase("MBPS"))
                            speedMeter.calculateAngleOfDeviation(
                                    (float) speedInfo_update.kilobits,
                                    decimalFormat.format(speedInfo_update.megabits));
                    } catch (Exception e) {

                    }
                    break;

                case TESTING_PING:
                    try {
                        speedMeter.calculateAngleOfDeviation(0, getResources()
                                .getString(R.string.pindi_checkingspeed));
                    } catch (Exception e) {

                    }
                    break;

                case NO_NETWORK:
                    try {
                        Toast.makeText(
                                context,
                                ""
                                        + getResources().getString(
                                        R.string.pindi_nonetworkcon),
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }

                    break;

                case FILE_NOT_FOUND:
                    try {
                        Toast.makeText(
                                context,
                                ""
                                        + getResources().getString(
                                        R.string.pindi_strSDRequirement),
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {

                    }


                    break;
                case UPLOAD_STATUS_NEW:
                    try {
                        SpeedInfo speedInfo_download1 = (SpeedInfo) msg.obj;

                        if (displaySpeedUnit.equalsIgnoreCase("KBPS")) {
                            // txtViewForDownload.setText(""
                            // + decimalFormat.format(speedInfo_download1.kilobits)
                            // + " kbps");
                            txtViewForUpload.setVisibility(View.VISIBLE);
                            txtViewForUpload.setText(""
                                    + decimalFormat
                                    .format(speedInfo_download1.kilobits)
                                    + " kbps");
                            preferences.setHistoryUPLOAD(getActivity(), Double
                                    .toString(speedInfo_download1.kilobits / 1024));
                        } else if (displaySpeedUnit.equalsIgnoreCase("MBPS")) {
                            // txtViewForDownload.setText(""
                            // + decimalFormat.format(speedInfo_download1.megabits)
                            // + " mbps");
                            txtViewForUpload.setText(""
                                    + decimalFormat
                                    .format(speedInfo_download1.megabits)
                                    + " mbps");
                            preferences.setHistoryUPLOAD(getActivity(),
                                    Double.toString(speedInfo_download1.megabits));
                        }

                        progressBarForUpload.setVisibility(View.INVISIBLE);
                        speedMeter.calculateAngleOfDeviation(0, "0.0");
                    } catch (Exception e) {

                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }

        ;
    };

    private void getDownloadSpeedNew() {
        InputStream inputStream = null;
        int inputBytes = 0, currentBytes = 0, bytesInThreshold = 0, progress = 0;
        long startTime = 0, downloadTime = 0, startUpdate = 0, updateDelta = 0;

        try {
            startTime = startUpdate = System.currentTimeMillis();

            URL url = new URL("http://quantum4you.com/SuperWiFi.txt");
            URLConnection urlConnection = url.openConnection();
            urlConnection.setUseCaches(false);
            inputStream = urlConnection.getInputStream();

            while ((currentBytes = inputStream.read()) != -1) {
                inputBytes++;
                bytesInThreshold++;
                // progressBarForDownload.setProgress(inputBytes*8);

                if (updateDelta >= UPDATE_THRESHOLD) {
                    progress = (int) ((inputBytes / (double) EXPECTED_SIZE_IN_BYTES) * 100);
                    progressBarForUpload.setProgress(progress);

                    Message message = Message.obtain(handler, UPDATE_STATUS,
                            calculateSpeed(updateDelta, bytesInThreshold));
                    message.arg1 = progress;
                    message.arg2 = inputBytes;
                    handler.sendMessage(message);

                    startUpdate = System.currentTimeMillis();
                    bytesInThreshold = 0;
                }
                updateDelta = System.currentTimeMillis() - startUpdate;
            }

            downloadTime = System.currentTimeMillis() - startTime;
            if (downloadTime == 0) {
                downloadTime = 1;
            }

            Message message = Message.obtain(handler, UPLOAD_STATUS_NEW,
                    calculateSpeed(updateDelta, bytesInThreshold));
            message.arg1 = inputBytes;
            handler.sendMessage(message);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Message msg = Message.obtain(handler, NO_NETWORK);
            handler.sendMessage(msg);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void getPingSpeed() {
        InputStream inputStream = null;
        long startTime = 0, connectionLatency = 0;

        try {
            startTime = System.currentTimeMillis();

            Message msg = Message.obtain(handler, TESTING_PING);
            handler.sendMessage(msg);

            URL url = new URL(downloadURL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setUseCaches(false);
            connectionLatency = System.currentTimeMillis() - startTime;

            progressBarForPing.setProgress(50);

            inputStream = urlConnection.getInputStream();

            Message message = Message.obtain(handler, PING_STATUS);
            message.arg1 = (int) connectionLatency;
            handler.sendMessage(message);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Message msg = Message.obtain(handler, NO_NETWORK);
            handler.sendMessage(msg);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getDownloadSpeed() {
        InputStream inputStream = null;
        int inputBytes = 0, currentBytes = 0, bytesInThreshold = 0, progress = 0;
        long startTime = 0, downloadTime = 0, startUpdate = 0, updateDelta = 0;

        try {
            startTime = startUpdate = System.currentTimeMillis();

            URL url = new URL(downloadURL);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setUseCaches(false);
            inputStream = urlConnection.getInputStream();

            while ((currentBytes = inputStream.read()) != -1) {
                inputBytes++;
                bytesInThreshold++;
                // progressBarForDownload.setProgress(inputBytes*8);

                if (updateDelta >= UPDATE_THRESHOLD) {
                    progress = (int) ((inputBytes / (double) EXPECTED_SIZE_IN_BYTES) * 100);
                    progressBarForDownload.setProgress(progress);

                    Message message = Message.obtain(handler, UPDATE_STATUS,
                            calculateSpeed(updateDelta, bytesInThreshold));
                    message.arg1 = progress;
                    message.arg2 = inputBytes;
                    handler.sendMessage(message);

                    startUpdate = System.currentTimeMillis();
                    bytesInThreshold = 0;
                }
                updateDelta = System.currentTimeMillis() - startUpdate;
            }

            downloadTime = System.currentTimeMillis() - startTime;
            if (downloadTime == 0) {
                downloadTime = 1;
            }

            Message message = Message.obtain(handler, DOWNLOAD_STATUS,
                    calculateSpeed(updateDelta, bytesInThreshold));
            message.arg1 = inputBytes;
            handler.sendMessage(message);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Message msg = Message.obtain(handler, NO_NETWORK);
            handler.sendMessage(msg);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getUploadSpeed(String s) {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        FileInputStream fileInputStream = null;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dataOutputStream = null;

        byte[] buffer;
        int availableBytes = 0, maxBufferSize = 8, bufferSize = 0;
        int readBytes = 0, inputBytes = 0, tempBytes = 0;
        long startTime = 0, uploadTime = 0, updateDelta = 0, startUpload = 0;

        try {
            fileInputStream = new FileInputStream(new File(Utility.File_PATH));
            URL url = new URL(uploadURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            dataOutputStream = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            dataOutputStream
                    .writeBytes("Content-Disposition: post-data; name=uploadedfile;filename="
                            + Utility.File_PATH + "" + lineEnd);
            dataOutputStream.writeBytes(lineEnd);

            availableBytes = fileInputStream.available();
            progressBarForUpload.setMax(availableBytes);
            bufferSize = Math.min(availableBytes, maxBufferSize);
            buffer = new byte[bufferSize];

            readBytes = fileInputStream.read(buffer, 0, bufferSize);
            startTime = startUpload = System.currentTimeMillis();

            while (readBytes > 0) {
                inputBytes++;
                tempBytes++;

                // progressBarForUpload.setProgress(inputBytes*8);
                dataOutputStream.write(buffer, 0, bufferSize);
                availableBytes = fileInputStream.available();
                availableBytes = Math.min(availableBytes, maxBufferSize);

                readBytes = fileInputStream.read(buffer, 0, bufferSize);

                if (updateDelta >= UPDATE_THRESHOLD) {
                    progressBarForUpload.setProgress(inputBytes * 8);

                    Message message = Message.obtain(handler, UPDATE_STATUS,
                            calculateSpeed(updateDelta, tempBytes));
                    message.arg1 = inputBytes;
                    handler.sendMessage(message);
                    startTime = System.currentTimeMillis();
                    tempBytes = 0;
                }

                updateDelta = System.currentTimeMillis() - startTime;
            }
            dataOutputStream.writeBytes(lineEnd);
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);
            fileInputStream.close();
            dataOutputStream.flush();
            dataOutputStream.close();

            uploadTime = System.currentTimeMillis() - startUpload;
            if (uploadTime == 0) {
                uploadTime = 1;
            }

            Message message = Message.obtain(handler, UPLOAD_STATUS,
                    calculateSpeed(updateDelta, tempBytes));
            // handler.sendMessage(message);
        } catch (FileNotFoundException e) {
            System.out.println("FILE_NOT_FOUND " + e);
            e.printStackTrace();
            Message msg = Message.obtain(handler, FILE_NOT_FOUND);
            // handler.sendMessage(msg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Message msg = Message.obtain(handler, NO_NETWORK);
            // handler.sendMessage(msg);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getUploadSpeed() {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        FileInputStream fileInputStream = null;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dataOutputStream = null;

        byte[] buffer;
        int availableBytes = 0, maxBufferSize = 8, bufferSize = 0;
        int readBytes = 0, inputBytes = 0, tempBytes = 0;
        long startTime = 0, uploadTime = 0, updateDelta = 0, startUpload = 0;

        try {
            fileInputStream = new FileInputStream(new File(Utility.File_PATH));
            URL url = new URL(uploadURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            dataOutputStream = new DataOutputStream(
                    httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            dataOutputStream
                    .writeBytes("Content-Disposition: post-data; name=uploadedfile;filename="
                            + Utility.File_PATH + "" + lineEnd);
            dataOutputStream.writeBytes(lineEnd);

            availableBytes = fileInputStream.available();
            progressBarForUpload.setMax(availableBytes);
            bufferSize = Math.min(availableBytes, maxBufferSize);
            buffer = new byte[bufferSize];

            readBytes = fileInputStream.read(buffer, 0, bufferSize);
            startTime = startUpload = System.currentTimeMillis();

            while (readBytes > 0) {
                inputBytes++;
                tempBytes++;

                // progressBarForUpload.setProgress(inputBytes*8);
                dataOutputStream.write(buffer, 0, bufferSize);
                availableBytes = fileInputStream.available();
                availableBytes = Math.min(availableBytes, maxBufferSize);
                ;
                readBytes = fileInputStream.read(buffer, 0, bufferSize);

                if (updateDelta >= UPDATE_THRESHOLD) {
                    progressBarForUpload.setProgress(inputBytes * 8);

                    Message message = Message.obtain(handler, UPDATE_STATUS,
                            calculateSpeed(updateDelta, tempBytes));
                    message.arg1 = inputBytes;
                    handler.sendMessage(message);
                    startTime = System.currentTimeMillis();
                    tempBytes = 0;
                }

                updateDelta = System.currentTimeMillis() - startTime;
            }
            dataOutputStream.writeBytes(lineEnd);
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);
            fileInputStream.close();
            dataOutputStream.flush();
            dataOutputStream.close();

            uploadTime = System.currentTimeMillis() - startUpload;
            if (uploadTime == 0) {
                uploadTime = 1;
            }

            Message message = Message.obtain(handler, UPLOAD_STATUS,
                    calculateSpeed(updateDelta, tempBytes));
            // handler.sendMessage(message);
        } catch (FileNotFoundException e) {
            System.out.println("FILE_NOT_FOUND " + e);
            e.printStackTrace();
            Message msg = Message.obtain(handler, FILE_NOT_FOUND);
            // handler.sendMessage(msg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Message msg = Message.obtain(handler, NO_NETWORK);
            // handler.sendMessage(msg);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // private void updateWidgetDownloadSpeed(String speed)
    // {
    // RemoteViews remoteViews=new RemoteViews(getPackageName(),
    // R.layout.widget);
    // ComponentName componentName=new ComponentName(CheckSpeed.this,
    // Widget.class);
    // AppWidgetManager appWidgetManager =
    // AppWidgetManager.getInstance(CheckSpeed.this);
    // remoteViews.setTextViewText(R.id.txtViewForDownloadSpeed, speed);
    // appWidgetManager.updateAppWidget(componentName, remoteViews);
    // }
    //
    // private void updateWidgetUploadSpeed(String speed)
    // {
    // RemoteViews remoteViews=new RemoteViews(getPackageName(),
    // R.layout.widget);
    // ComponentName componentName=new ComponentName(CheckSpeed.this,
    // Widget.class);
    // AppWidgetManager appWidgetManager =
    // AppWidgetManager.getInstance(CheckSpeed.this);
    // remoteViews.setTextViewText(R.id.txtViewForUploadSpeed, speed);
    // appWidgetManager.updateAppWidget(componentName, remoteViews);
    // }

    private SpeedInfo calculateSpeed(final long time, final long bytes) {
        SpeedInfo speedInfo = new SpeedInfo();
        long downloadSpeed = (bytes / time) * 1000;
        double kilobits = downloadSpeed * BYTE_TO_KILOBIT;
        double megabits = kilobits * KILOBIT_TO_MEGABIT;
        speedInfo.downloadSpeed = downloadSpeed;
        speedInfo.kilobits = kilobits;
        speedInfo.megabits = megabits;

        return speedInfo;
    }

    Utils utils;
    ANG_PromptHander promptHander;
    //	AHandler aHandler;
    LinearLayout adslayout1;
    // public static String App_PID = "02_New";//ShareAll

AHandler aHandler;

    public void doEngineWork(View v) {
        aHandler = new AHandler();
        System.out.print("2017 Mstage1");
        adslayout1 = (LinearLayout) v.findViewById(R.id.adsbanner);
        promptHander = new ANG_PromptHander();
        utils = new app.serviceprovider.Utils();
//        new MasterData(getActivity()).getMasterData(getActivity(), HomeFragment.App_PID,
//                adslayout1, HomeFragment.PID_CP, null, true);
        // new GPID().execute();


        //new QuantamAds().execute();

    }


    private class QuantamAds extends AsyncTask<Void, Void, String> {
        private String data;
//        private ProgressDialog bar;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            bar = new ProgressDialog(MainActivity.this);
//            bar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            bar.setCancelable(false);
//            bar.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                data = getData(Utils.QUANTUM_URL);
                System.out.println("1225 here is doInBackGround" + " " + data);
            } catch (Exception e) {
                System.out.println("exception in doinbackground main activity" + e);
            }
            return data;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            System.out.println("1225 here is json" + " " + json);
            try {
                parseData(json);
            } catch (Exception e) {
                System.out.println("exception in onPostExecute main activity" + e);
            }
//            if (bar.isShowing()) {
//                bar.cancel();
//            }

        }
    }


    public void parseData(String data) {
        try {
            if (data != null && !data.equals("")) {
                System.out.println("bhanu check 1");
                String src = data.substring(data.indexOf("<src>") + 5, data.indexOf("</src>"));

                System.out.println("bhanu check 2 source" + " " + src);
                if (src != null) {
                    System.out.println("bhanu check 3");
                    String icon[] = src.split("#");
                    System.out.println("bhanu check 4");
                    if (icon != null && icon.length > 0) {
                        System.out.println("bhanu check 5");
                        for (int i = 0; i < icon.length; i++) {
                            preferences.setQUANTUM_SRC(icon[i], i);
                            System.out.println("bhanu check 6");
                        }
                    }
                }


                String click = data.substring(data.indexOf("<click>") + 7, data.indexOf("</click>"));
                System.out.println("bhanu check 2 click" + " " + click);
                if (click != null) {
                    String deeplink[] = click.split("#");
                    if (deeplink != null && deeplink.length > 0) {


                        for (int i = 0; i < deeplink.length; i++) {
                            preferences.setQUANTUM_CLICK(deeplink[i], i);
                            System.out.println("bhanu check 6");
                        }
                    }
                }


                String pkg = data.substring(data.indexOf("<pkg>") + 5, data.indexOf("</pkg>"));
                System.out.println("bhanu check 2 pkg" + " " + pkg);
                if (pkg != null) {
                    String checkPackage[] = pkg.split("#");
                    if (checkPackage != null && checkPackage.length > 0) {

                        for (int i = 0; i < checkPackage.length; i++) {
                            preferences.setQUANTUM_PKG(checkPackage[i], i);
                            System.out.println("bhanu check 6");

                        }
                    }
                }


                String appname = data.substring(data.indexOf("<appname>") + 9, data.indexOf("</appname>"));
                System.out.println("bhanu check 2 appname" + " " + appname);
                if (appname != null) {
                    String checkPackage[] = appname.split("#");
                    if (checkPackage != null && checkPackage.length > 0) {

                        for (int i = 0; i < checkPackage.length; i++) {
                            preferences.setQUANTUM_AppName(checkPackage[i], i);
                            System.out.println("bhanu check 6");

                        }
                    }
                }
            }
        } catch (Exception e) {
        }

    }

   /* public String getData(String url) {
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
            HttpConnectionParams.setSoTimeout(httpParameters, 15000);
            HttpClient httpclient = new DefaultHttpClient();
            httpGet.setParams(httpParameters);

            HttpEntity entity = httpclient.execute(httpGet).getEntity();
            BufferedReader br = null;
            if (entity != null) {
                Log.i("read", "nube");
                br = new BufferedReader(new InputStreamReader(
                        entity.getContent()));
            } else {
                Log.i("read", "local");
                System.out.println("Unable to read:...");
            }
            String texto = "";
            while (true) {
                String line = br.readLine();
                if (line != null) {
                    texto = texto + line;
                } else {
                    System.out.println("new data : Grand " + texto);
                    return texto;
                }
            }
        } catch (Exception e) {
            System.out.println("new data error liink Grand " + url);
            System.out.println("new data error: Grand " + e);
            e.printStackTrace();
            return null;
        }
    }
*/

    public static String getData(String myurl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(stream);
        char[] buffer = new char[1024];
        int length;
        while ((length = reader.read(buffer)) != -1) {
            builder.append(buffer, 0, length);
        }
        return builder.toString();
    }


}