package me.b0ne.android.hatebuview.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 * Created by bone on 13/09/22.
 */
public class AppData {

    private static final String PREF_APP_KEY = "hatebu_view_preferences";

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREF_APP_KEY, Context.MODE_PRIVATE);
        return sp.edit();
    }

    private static SharedPreferences getSharedPerf(Context context) {
        return context.getSharedPreferences(PREF_APP_KEY, Context.MODE_PRIVATE);
    }

    public static void save(Context context, String key, String jsonStr) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, jsonStr).commit();
    }

    public static String get(Context context, String key) {
        String result = getSharedPerf(context).getString(key, null);
//        Gson gson = new Gson();
//        gson.toJson(result);
//        return gson.toJson(result);
        return result;
    }

}
