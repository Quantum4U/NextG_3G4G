package app.pnd.fourg;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class MediaPreferences {
    private android.content.SharedPreferences preferences;
    private Editor editor;

    private static final String media_path = "media_path";

    private static final String media_name = "media_name";

    private static final String setting_image = "enable_image";
    private static final String setting_audio = "enable_audio";
    private static final String setting_video = "enable_video";
    private static final String setting_apk = "enable_apk";

    private static final String setting_incoming = "enable_incoming";
    private static final String setting_outoging = "enable_outoging";

    private static final String is_allowed = "_is_allowed";

    private Context context;

    public MediaPreferences(Context context) {
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

    public void setIncomingCaller(boolean flag) {
        editor.putBoolean(setting_incoming, flag);
        editor.commit();
    }

    public boolean getIncomingCaller() {
        return getPreferences().getBoolean(setting_incoming, true);
    }


    public void setOutgoingCaller(boolean flag) {
        editor.putBoolean(setting_outoging, flag);
        editor.commit();
    }

    public boolean getOutgoingCaller() {
        return getPreferences().getBoolean(setting_outoging, true);
    }

    public void setPermissionAllowed(boolean flag){
        editor.putBoolean(is_allowed,flag);
        editor.commit();
    }

    public boolean isAllowed(){
        return getPreferences().getBoolean(is_allowed,false);
    }

}
