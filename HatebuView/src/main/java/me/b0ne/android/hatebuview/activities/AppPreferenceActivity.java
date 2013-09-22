package me.b0ne.android.hatebuview.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import me.b0ne.android.hatebuview.R;

/**
 * Created by bone on 13/09/22.
 */
public class AppPreferenceActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_preference);

        setTitle("アプリの設定");
    }


}
