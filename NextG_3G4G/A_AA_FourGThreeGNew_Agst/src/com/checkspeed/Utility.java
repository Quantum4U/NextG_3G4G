package com.checkspeed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Utility {
    public static final String FOLDER_PATH = Environment.getExternalStorageDirectory() + "/.speed";
    public static final String File_PATH = FOLDER_PATH + "/" + "speed.tmp";

    public static final String INAPP_Text = "inapps";

    public static void hideKeyBoard(Activity activity, View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
        return date;
    }

    public static void copyAssets(Context context) {
        AssetManager assetManager = context.getAssets();
        String[] files = null;

        try {
            files = assetManager.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String filename : files) {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = assetManager.open(filename);
                outputStream = new FileOutputStream(File_PATH);
                copyFile(inputStream, outputStream);
                inputStream.close();
                inputStream = null;
                outputStream.flush();
                outputStream.close();
                outputStream = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public static void MakeFolder() {
        File file = new File(FOLDER_PATH);
        if (!file.exists()) {
            if (file.mkdir()) {
                // directory is created
            }
        }
    }
}
