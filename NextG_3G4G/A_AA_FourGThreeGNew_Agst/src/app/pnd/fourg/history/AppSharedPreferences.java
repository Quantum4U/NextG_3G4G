package app.pnd.fourg.history;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class AppSharedPreferences {
    private android.content.SharedPreferences preferences;
    private Editor editor;

    private static final String DATE = "date";
    private static final String UPLOAD = "upload";
    private static final String DOWNLOAD = "download";
    private static final String COUNTER = "counter";
    private static final String HISTORY_DELETE = "delete";

    private static final String SRC_ONE = "one_src";
    private static final String SRC_TWO = "one_two";
    private static final String SRC_THREE = "one_three";

    private static final String QUANTUM_SRC = "quant_src";
    private static final String QUANTUM_APP_NAME = "quant_app_name";
    private static final String QUANTUM_CLICK = "quant_click";
    private static final String QUANTUM_PKG = "quant_pkg";

    private static final String root_counter = "root_counter";

    private static final String sim_counter = "sim_counter";

    private static final String media_path = "media_path";

    private static final String media_name = "media_name";

    private static final String setting_image = "enable_image";
    private static final String setting_audio = "enable_audio";
    private static final String setting_video = "enable_video";
    private static final String setting_apk = "enable_apk";


    private Context context;

    public AppSharedPreferences(Context context) {
        setPreferences(PreferenceManager.getDefaultSharedPreferences(context));
        editor = getPreferences().edit();
        this.context = context;
    }

    private android.content.SharedPreferences getPreferences() {
        return preferences;
    }

    private void setPreferences(android.content.SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public String getHistoryDate(Context context, int pos) {
        return getPreferences().getString(DATE + pos, "");
    }

    public void setHistoryDate(Context context, String newValue) {
        editor.putString(DATE + getCounter(context), newValue);
        editor.commit();
    }

    public String getHistoryUPLOAD(Context context, int pos) {
        return getPreferences().getString(UPLOAD + pos, "na");
    }

    public void setHistoryUPLOAD(Context context, String newValue) {
        editor.putString(UPLOAD + getCounter(context), newValue);
        editor.commit();
    }

    public String getHistoryDOWNLOAD(Context context, int pos) {
        return getPreferences().getString(DOWNLOAD + pos, "na");
    }

    public void setHistoryDOWNLOAD(Context context, String newValue) {
        editor.putString(DOWNLOAD + getCounter(context), newValue);
        editor.commit();
    }

    public int getCounter(Context context) {
        return getPreferences().getInt(COUNTER, 0);
    }

    public void setCounter(Context context, int newValue) {
        editor.putInt(COUNTER, newValue);
        editor.commit();
    }

    public boolean getHistoryDelete(int pos) {
        return getPreferences().getBoolean(HISTORY_DELETE + pos, false);
    }

    public void setHistoryDelete(int pos, boolean status) {
        editor.putBoolean(HISTORY_DELETE + String.valueOf(pos), status);
        editor.commit();
    }


    public void setQUANTUM_SRC(String src, int pos) {
        editor.putString(QUANTUM_SRC + pos, src);
        editor.commit();
    }

    public String getQUANTUM_SRC(int pos) {
        return getPreferences().getString(QUANTUM_SRC + pos, "NA");
    }


    public void setQUANTUM_AppName(String src, int pos) {
        editor.putString(QUANTUM_APP_NAME + pos, src);
        editor.commit();
    }

    public String getQUANTUM_AppName(int pos) {
        return getPreferences().getString(QUANTUM_APP_NAME + pos, "More Apps");
    }


    public void setQUANTUM_PKG(String src, int pos) {
        editor.putString(QUANTUM_PKG + pos, src);
        editor.commit();
    }

    public String getQUANTUM_PKG(int pos) {
        return getPreferences().getString(QUANTUM_PKG + pos, "NA");
    }

    public void setQUANTUM_CLICK(String src, int pos) {
        editor.putString(QUANTUM_CLICK + pos, src);
        editor.commit();
    }

    public String getQUANTUM_CLICK(int pos) {
        return getPreferences().getString(QUANTUM_CLICK + pos, "NA");
    }

    public void setRootCounter(int counter) {
        editor.putInt(root_counter, counter);
        editor.commit();
    }

    public int getRootCounter() {
        return getPreferences().getInt(root_counter, 0);
    }

    public void setSimCounter(int counter) {
        editor.putInt(sim_counter, counter);
        editor.commit();
    }

    public int getSimCounter() {
        return getPreferences().getInt(sim_counter, 0);
    }

    public void setMediaPath(String path) {
        editor.putString(media_path, path);
        editor.commit();
    }

    public String getMediaPath() {
        return getPreferences().getString(media_path, "NA");
    }

    public void setMediaName(String name) {
        editor.putString(media_name, name);
        editor.commit();
    }

    public String getMediaName() {
        return getPreferences().getString(media_name, "NA");
    }


    public void setSettingImage(boolean flag) {
        editor.putBoolean(setting_image, flag);
        editor.commit();
    }

    public boolean getSettingImage() {
        return getPreferences().getBoolean(setting_image, true);
    }

    public void setSettingAudio(boolean flag) {
        editor.putBoolean(setting_audio, flag);
        editor.commit();
    }

    public boolean getSettingAudio() {
        return getPreferences().getBoolean(setting_audio, true);
    }


    public void setSettingVideo(boolean flag) {
        editor.putBoolean(setting_video, flag);
        editor.commit();
    }

    public boolean getSettingVideo() {
        return getPreferences().getBoolean(setting_video, true);
    }


    public void setSettingAPK(boolean flag) {
        editor.putBoolean(setting_apk, flag);
        editor.commit();
    }

    public boolean getSettingAPK() {
        return getPreferences().getBoolean(setting_apk, true);
    }
}
