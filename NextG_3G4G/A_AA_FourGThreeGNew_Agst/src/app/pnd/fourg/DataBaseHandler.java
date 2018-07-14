package app.pnd.fourg;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class DataBaseHandler {

    SharedPreferences preferences;
    Editor editor;
    Context context;

    public DataBaseHandler(Context con) {
        context = con;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public String getPattern() {
        return preferences.getString("pattern", "NA");
    }

    public void setPattern(String val) {
        editor.putString("pattern", val);
        editor.commit();
    }


    public String getHintqn() {
        return preferences.getString("qn", "");
    }

    public void setHintqn(String val) {
        editor.putString("qn", val);
        editor.commit();
    }


    public String getHintans() {
        return preferences.getString("ans", "");
    }

    public void setHintans(String val) {
        editor.putString("ans", val);
        editor.commit();
    }

    public String getVideoPKG() {
        return preferences.getString("sms", "NA");
    }

    public void setVideoPKG(String val) {
        editor.putString("sms", val);
        editor.commit();
    }

    public String getGalleryPKG() {
        return preferences.getString("gallery", "NA");
    }

    public void setGalleryPKG(String val) {
        editor.putString("gallery", val);
        editor.commit();
    }

    public String getAllPkges() {
        return preferences.getString("pkg", "NA");
    }

    public void setAllPkges(String val) {
        editor.putString("pkg", val);
        editor.commit();
    }

    public void set_stealth(Context context, boolean data) {
        SharedPreferences stealth = context.getSharedPreferences("stealth",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = stealth.edit();
        editor.putBoolean("stealth", data);
        editor.commit();
    }

    public boolean get_stealth(Context context) {

        SharedPreferences stealth = context.getSharedPreferences("stealth",
                Activity.MODE_WORLD_WRITEABLE);
        return stealth.getBoolean("stealth", false);

    }

    public void setRootFragAdsCount(Context context, int counter) {
        SharedPreferences stealth = context.getSharedPreferences("root_frag_count",
                Activity.MODE_WORLD_WRITEABLE);
        Editor editor = stealth.edit();
        editor.putInt("root_frag_count", counter);
        editor.commit();
    }

    public int getRootFragAdsCount(Context context) {

        SharedPreferences stealth = context.getSharedPreferences("root_frag_count",
                Activity.MODE_WORLD_WRITEABLE);
        return stealth.getInt("root_frag_count", 0);
    }

}
