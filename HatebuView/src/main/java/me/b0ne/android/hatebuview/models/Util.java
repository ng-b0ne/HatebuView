package me.b0ne.android.hatebuview.models;

import android.content.Context;
import android.util.Log;

import me.b0ne.android.hatebuview.R;

/**
 * Created by bone on 13/09/15.
 */
public class Util {

    public static final String KEY_DRAWER_POSITION = "drawer_position";

    public static final String KEY_BK_RSS_URL = "rss_url";
    public static final String KEY_BK_CATEGORY_KEY = "category_key";
    public static final String KEY_BK_CATEGORY_NAME = "category_name";
    public static final String KEY_BK_CATEGORY_TYPE = "category_type";

    public static final String CATEGORY_TYPE_HOTENTRY = "hotentry";
    public static final String CATEGORY_TYPE_ENTRYLIST = "entrylist";

    public static final String BK_WEBVIEW_URL = "bk_webview_url";
    public static final String BK_WEBVIEW_TITLE = "bk_webview_title";

    public static final String CACHE_PERIOD_TIME = "cache_period_time";
    public static final String CACHE_UPDATE_TIME = "cache_update_time";
    public static final String APP_START_PAGE_TYPE = "start_page_type";

    public static final String KEY_NOW_SHOW_WEBVIEW_URL = "now_show_webview_url";

    public static final int DEFAULT_CACHE_PERIOD_TIME = 1800; // 30分
    public static final int CACHE_PERIOD_HALF_HOUR = 1800;
    public static final int CACHE_PERIOD_TIME_1HOUR = 3600;
    public static final int CACHE_PERIOD_TIME_2HOURS = 3600 * 2;
    public static final int CACHE_PERIOD_TIME_3HOURS = 3600 * 3;
    public static final int CACHE_PERIOD_TIME_4HOURS = 3600 * 4;
    public static final int CACHE_PERIOD_TIME_5HOURS = 3600 * 5;

    public static String getUpdateTime(Context context) {
        // now time
        int currentTime = Integer.valueOf(String.valueOf(System.currentTimeMillis() / 1000));

        String updateTime = AppData.get(context, CACHE_UPDATE_TIME);
        // set update time
        if (updateTime == null || updateTime.equals("")) {
            setUpdateTime(context, String.valueOf(currentTime));
            return String.valueOf(currentTime);
        }

        // edit new update time
        int cachedUpdateTime = Integer.valueOf(updateTime);
        int periodTime = cachedUpdateTime + getCachePeriod(context);
        if (currentTime > periodTime) {
            setUpdateTime(context, String.valueOf(currentTime));
            return String.valueOf(currentTime);
        }

        return updateTime;
    }

    public static void setUpdateTime(Context context, String _time) {
        AppData.save(
                context,
                CACHE_UPDATE_TIME,
                _time);
    }

    /**
     * キャッシュ期間を設定する（秒）
     * @param context
     * @param _perid
     */
    public static void setCachePeriod(Context context, int _perid) {
        AppData.save(context,
                CACHE_PERIOD_TIME,
                String.valueOf(_perid));
    }

    /**
     * キャッシュ期間を取得する
     * @param context
     * @return
     */
    public static int getCachePeriod(Context context) {
        String periodTime = AppData.get(context, CACHE_PERIOD_TIME);
        int period =  (periodTime != null) ? Integer.valueOf(periodTime) : 0;
        if (period == 0) {
            setCachePeriod(context, DEFAULT_CACHE_PERIOD_TIME);
            return DEFAULT_CACHE_PERIOD_TIME;
        }
        return period;
    }

    /**
     * アプリ起動時の表示するカテゴリーのindexを設定する
     * @param context
     * @param _typeIndex
     */
    public static void setStartPageType(Context context, String _typeIndex) {
        AppData.save(context, APP_START_PAGE_TYPE, _typeIndex);
    }

    /**
     * アプリ起動時の表示するカテゴリーのindexを取得する
     * @return
     */
    public static int getStartPageType(Context context) {
        String startPageType = AppData.get(context, APP_START_PAGE_TYPE);
        if (startPageType == null) {
            String[] bkCategoryKey = context.getResources().getStringArray(R.array.hatebu_category_key);
            setStartPageType(context, "0");
            return 0;
        }
        return Integer.valueOf(startPageType);
    }
}
