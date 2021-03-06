package me.b0ne.android.hatebuview.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.models.Util;

/**
 * Created by b0ne on 2015/03/11.
 */
public class AppPreferenceFragment extends PreferenceFragment {
    private Context mContext;
    private PreferenceScreen startPagePref;
    private PreferenceScreen cachePeriodPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        addPreferencesFromResource(R.xml.app_preference);

        startPagePref = (PreferenceScreen)findPreference("app_start_page");
        cachePeriodPref = (PreferenceScreen)findPreference("app_cache_period_time");

        // 設定しているキャッシュ期間の表示
        int periodTime = Util.getCachePeriod(mContext);
        setSummaryCachePeriodPref(periodTime);

        // アプリのバーション表示
        PackageInfo packInfo = null;
        try {
            packInfo = mContext.getPackageManager()
                    .getPackageInfo("me.b0ne.android.hatebuview", PackageManager.GET_META_DATA);
        } catch(Exception e) {
        }
        PreferenceScreen appVersionPref = (PreferenceScreen)findPreference("app_version");
        appVersionPref.setSummary(packInfo.versionName);

        // アプリへの評価（Google Playへ）
        PreferenceScreen appEvaluatePref = (PreferenceScreen)findPreference("app_evaluate");
        appEvaluatePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=me.b0ne.android.hatebuview");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return false;
            }
        });

        // アプリ起動時の設定表示
        String[] bkNameList = getResources().getStringArray(R.array.hatebu_category_name);
        startPagePref.setSummary(bkNameList[Util.getStartPageType(mContext)]);

        startPagePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showStartPageDialog();
                return false;
            }
        });
        cachePeriodPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showCachePeriodDialog();
                return false;
            }
        });

        // Github
        PreferenceScreen appSourceCode = (PreferenceScreen)findPreference("app_source_code");
        appSourceCode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Uri uri = Uri.parse("https://github.com/ng-b0ne/HatebuView");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return false;
            }
        });

    }

    private void showStartPageDialog() {
        final String[] bkNameList = getResources().getStringArray(R.array.hatebu_category_name);
        final String[] bkCategoryKey = getResources().getStringArray(R.array.hatebu_category_key);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setItems(bkNameList,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startPagePref.setSummary(bkNameList[i]);
                        Util.setStartPageType(mContext, String.valueOf(i));
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void showCachePeriodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setItems(new String[]{"30分","1時間","2時間","3時間","4時間","5時間"},
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        int periodTime = Util.DEFAULT_CACHE_PERIOD_TIME;
                        switch (position) {
                            case 0:
                                periodTime = Util.CACHE_PERIOD_HALF_HOUR;
                                break;
                            case 1:
                                periodTime = Util.CACHE_PERIOD_TIME_1HOUR;
                                break;
                            case 2:
                                periodTime = Util.CACHE_PERIOD_TIME_2HOURS;
                                break;
                            case 3:
                                periodTime = Util.CACHE_PERIOD_TIME_3HOURS;
                                break;
                            case 4:
                                periodTime = Util.CACHE_PERIOD_TIME_4HOURS;
                                break;
                            case 5:
                                periodTime = Util.CACHE_PERIOD_TIME_5HOURS;
                                break;
                        }
                        setSummaryCachePeriodPref(periodTime);
                        Util.setCachePeriod(mContext, periodTime);

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void setSummaryCachePeriodPref(int periodTime) {
        switch (periodTime) {
            case Util.DEFAULT_CACHE_PERIOD_TIME:
                cachePeriodPref.setSummary("30分");
                break;
            case Util.CACHE_PERIOD_TIME_1HOUR:
                cachePeriodPref.setSummary("1時間");
                break;
            case Util.CACHE_PERIOD_TIME_2HOURS:
                cachePeriodPref.setSummary("2時間");
                break;
            case Util.CACHE_PERIOD_TIME_3HOURS:
                cachePeriodPref.setSummary("3時間");
                break;
            case Util.CACHE_PERIOD_TIME_4HOURS:
                cachePeriodPref.setSummary("4時間");
                break;
            case Util.CACHE_PERIOD_TIME_5HOURS:
                cachePeriodPref.setSummary("5時間");
                break;
        }
    }
}
