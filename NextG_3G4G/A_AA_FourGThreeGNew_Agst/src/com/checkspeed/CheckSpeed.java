package com.checkspeed;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.appnextg.fourg.R;

public class CheckSpeed extends Activity implements OnClickListener {
    private DecimalFormat decimalFormat, decimalFormatForWidget;
    private TextView txtViewForPing, txtViewForDownload, txtViewForUpload;
    private Button btnStart;
    private ProgressBar progressBarForPing, progressBarForDownload, progressBarForUpload;

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

    private final static int UPDATE_THRESHOLD = 300;
    private static final int EXPECTED_SIZE_IN_BYTES = 1048576;        //	1MB=1024*1024=1048576
    private static final double BYTE_TO_KILOBIT = 0.0078125;
    private static final double KILOBIT_TO_MEGABIT = 0.0009765625;

    private String downloadURL = "http://www.migital.com/SuperWiFi.txt";
    private String uploadURL = "http://scms1.migital.com/voicereminder_android/testupload.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.check_speed);
//Git Master

        //Master2
        displaySpeedUnit = SharedData.get_Display_Speed_Unit(CheckSpeed.this);

        speedMeter = new SpeedMeter(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutForSpeedMeterView);
        relativeLayout.addView(speedMeter);

        decimalFormat = new DecimalFormat("##.##");
        decimalFormatForWidget = new DecimalFormat("##");

        txtViewForPing = (TextView) findViewById(R.id.txtViewForPing);
        txtViewForDownload = (TextView) findViewById(R.id.txtViewForDownload);
        txtViewForUpload = (TextView) findViewById(R.id.txtViewForUpload);

        progressBarForPing = (ProgressBar) findViewById(R.id.progressBarForPing);
        progressBarForDownload = (ProgressBar) findViewById(R.id.progressBarForDownload);
        progressBarForUpload = (ProgressBar) findViewById(R.id.progressBarForUpload);

        btnStart = (Button) findViewById(R.id.btnStart);

        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        txtViewForPing.setText("");
        txtViewForDownload.setText("");
        txtViewForUpload.setText("");
        btnStart.setEnabled(false);
        btnStart.setBackgroundResource(R.drawable.version_ic_icon);
        new Speed().execute("");
    }

    class Speed extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            getPingSpeed();
            getDownloadSpeed();
            getUploadSpeed();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressBarForPing.setProgress(0);
            progressBarForDownload.setProgress(0);
            progressBarForUpload.setProgress(0);
            btnStart.setEnabled(true);
            btnStart.setBackgroundResource(R.drawable.version_ic_icon);
            btnStart.setText("Restart");
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
                    SpeedInfo speedInfo_ping = (SpeedInfo) msg.obj;
                    txtViewForPing.setText("" + msg.arg1 + " ms");
                    progressBarForPing.setVisibility(View.INVISIBLE);
                    break;

                case DOWNLOAD_STATUS:
                    SpeedInfo speedInfo_download = (SpeedInfo) msg.obj;

                    if (displaySpeedUnit.equalsIgnoreCase("KBPS")) {
                        txtViewForDownload.setText("" + decimalFormat.format(speedInfo_download.kilobits) + " kbps");
                    } else if (displaySpeedUnit.equalsIgnoreCase("MBPS")) {
                        txtViewForDownload.setText("" + decimalFormat.format(speedInfo_download.megabits) + " mbps");
                    }

                    progressBarForDownload.setVisibility(View.INVISIBLE);
                    speedMeter.calculateAngleOfDeviation(0, "0.0");
                    break;

                case UPLOAD_STATUS:
                    SpeedInfo speedInfo_upload = (SpeedInfo) msg.obj;

                    if (displaySpeedUnit.equalsIgnoreCase("KBPS")) {
                        txtViewForUpload.setText("" + decimalFormat.format(speedInfo_upload.kilobits) + " kbps");
                    } else if (displaySpeedUnit.equalsIgnoreCase("MBPS")) {
                        txtViewForUpload.setText("" + decimalFormat.format(speedInfo_upload.megabits) + " mbps");
                    }

                    progressBarForUpload.setVisibility(View.INVISIBLE);
                    speedMeter.calculateAngleOfDeviation(0, LanguageDB.strSpeedCheckCompleted);
                    break;

                case UPDATE_STATUS:
                    SpeedInfo speedInfo_update = (SpeedInfo) msg.obj;

                    if (displaySpeedUnit.equalsIgnoreCase("KBPS"))
                        speedMeter.calculateAngleOfDeviation((float) speedInfo_update.kilobits, decimalFormat.format(speedInfo_update.kilobits));
                    else if (displaySpeedUnit.equalsIgnoreCase("MBPS"))
                        speedMeter.calculateAngleOfDeviation((float) speedInfo_update.kilobits, decimalFormat.format(speedInfo_update.megabits));

                    break;

                case TESTING_PING:
                    speedMeter.calculateAngleOfDeviation(0, getResources().getString(R.string.pindi_checkingspeed));
                    break;

                case NO_NETWORK:
                    Toast.makeText(CheckSpeed.this, getResources().getString(R.string.pindi_nonetworkcon), Toast.LENGTH_SHORT).show();
                    break;

                case FILE_NOT_FOUND:
                    Toast.makeText(CheckSpeed.this, getResources().getString(R.string.pindi_strSDRequirement), Toast.LENGTH_SHORT).show();
                    break;

                default:
                    super.handleMessage(msg);
            }
        }

        ;
    };

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
//				progressBarForDownload.setProgress(inputBytes*8);

                if (updateDelta >= UPDATE_THRESHOLD) {
                    progress = (int) ((inputBytes / (double) EXPECTED_SIZE_IN_BYTES) * 100);
                    progressBarForDownload.setProgress(progress);

                    Message message = Message.obtain(handler, UPDATE_STATUS, calculateSpeed(updateDelta, bytesInThreshold));
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

            Message message = Message.obtain(handler, DOWNLOAD_STATUS, calculateSpeed(updateDelta, bytesInThreshold));
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
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
            dataOutputStream.writeBytes("Content-Disposition: post-data; name=uploadedfile;filename=" + Utility.File_PATH + "" + lineEnd);
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

//				progressBarForUpload.setProgress(inputBytes*8);
                dataOutputStream.write(buffer, 0, bufferSize);
                availableBytes = fileInputStream.available();
                availableBytes = Math.min(availableBytes, maxBufferSize);
                ;
                readBytes = fileInputStream.read(buffer, 0, bufferSize);

                if (updateDelta >= UPDATE_THRESHOLD) {
                    progressBarForUpload.setProgress(inputBytes * 8);

                    Message message = Message.obtain(handler, UPDATE_STATUS, calculateSpeed(updateDelta, tempBytes));
                    message.arg1 = inputBytes;
                    handler.sendMessage(message);
                    startTime = System.currentTimeMillis();
                    tempBytes = 0;
                }

                updateDelta = System.currentTimeMillis() - startTime;
            }
            dataOutputStream.writeBytes(lineEnd);
            dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            fileInputStream.close();
            dataOutputStream.flush();
            dataOutputStream.close();

            uploadTime = System.currentTimeMillis() - startUpload;
            if (uploadTime == 0) {
                uploadTime = 1;
            }

            Message message = Message.obtain(handler, UPLOAD_STATUS, calculateSpeed(updateDelta, tempBytes));
            handler.sendMessage(message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Message msg = Message.obtain(handler, FILE_NOT_FOUND);
            handler.sendMessage(msg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Message msg = Message.obtain(handler, NO_NETWORK);
            handler.sendMessage(msg);
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

//	private void updateWidgetDownloadSpeed(String speed)
//	{
//		RemoteViews remoteViews=new RemoteViews(getPackageName(), R.layout.widget);
//		ComponentName componentName=new ComponentName(CheckSpeed.this, Widget.class);
//		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(CheckSpeed.this);
//		remoteViews.setTextViewText(R.id.txtViewForDownloadSpeed, speed);
//		appWidgetManager.updateAppWidget(componentName, remoteViews);
//	}
//	
//	private void updateWidgetUploadSpeed(String speed)
//	{
//		RemoteViews remoteViews=new RemoteViews(getPackageName(), R.layout.widget);
//		ComponentName componentName=new ComponentName(CheckSpeed.this, Widget.class);
//		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(CheckSpeed.this);
//		remoteViews.setTextViewText(R.id.txtViewForUploadSpeed, speed);
//		appWidgetManager.updateAppWidget(componentName, remoteViews);
//	}

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

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
