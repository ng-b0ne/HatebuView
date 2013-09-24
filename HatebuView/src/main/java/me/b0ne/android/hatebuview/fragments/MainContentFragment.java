package me.b0ne.android.hatebuview.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.activities.BkWebViewActivity;
import me.b0ne.android.hatebuview.adapters.HeadLineListAdapter;
import me.b0ne.android.hatebuview.models.AppData;
import me.b0ne.android.hatebuview.models.HateBook;
import me.b0ne.android.hatebuview.models.RssItem;
import me.b0ne.android.hatebuview.models.Util;

/**
 * Created by bone on 13/09/17.
 */
public class MainContentFragment extends ListFragment {

    private Context mContext;
    private HeadLineListAdapter mAdapter;
    private LinearLayout ProgressLayout;

    private int mPosition;
    private String[] categoryNameList;
    private String[] categoryHotentryRssList;
    private String[] categoryKeyList;
    private JsonObject loadingItem;

    private boolean isDualPane;

    private String rssCategoryTypeKey;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookmark_list, container, false);
        ProgressLayout = (LinearLayout)view.findViewById(R.id.progress_layout);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity().getApplicationContext();
        mAdapter = new HeadLineListAdapter(mContext);

        categoryNameList = getResources().getStringArray(R.array.hatebu_category_name);
        categoryHotentryRssList = getResources().getStringArray(R.array.hatebu_category_hotentry_rssurl);
        categoryKeyList = getResources().getStringArray(R.array.hatebu_category_key);

        View webviewFrame = getView().findViewById(R.id.bk_webview_frame);
        isDualPane = webviewFrame != null && webviewFrame.getVisibility() == View.VISIBLE;

        getRssData(1);
    }

    private void getRssData(int position) {
        mPosition = position;
        // カテゴリー、タイプ別のキャッシュキーを作る
        String rssCacheKey = categoryKeyList[position]
                + Util.CATEGORY_TYPE_HOTENTRY
                + Util.getUpdateTime(mContext);

        // キャッシュがあったら
        if (AppData.get(mContext, rssCacheKey) != null) {
            JsonParser parser = new JsonParser();
            setDataToList(parser.parse(AppData.get(mContext, rssCacheKey)).getAsJsonArray());
        } else { // キャッシュがなかったらリクエストする
            HateBook.getRss(mContext, categoryHotentryRssList[position], rssListener);
        }
    }

    // set data to list view
    private void setDataToList(JsonArray jsonArray) {
        if (mPosition > 1) {
            mAdapter.remove(loadingItem);
        }

        // add category bar name
        RssItem item = new RssItem();
        item.setRowType(1);
        item.setCategory(categoryNameList[mPosition]);
        mAdapter.add(item.toJsonObject());

        // add rss content to row
        for (int i=0; i<3; i++) {
            JsonObject jsonObj = jsonArray.get(i).getAsJsonObject();
            mAdapter.add(jsonObj);
        }

        if (mPosition < (categoryNameList.length - 1)) {
            if (mPosition == 1) {
                ProgressLayout.setVisibility(View.GONE);
                // add loading view
                RssItem rssloadingItem = new RssItem();
                rssloadingItem.setRowType(2);
                loadingItem = rssloadingItem.toJsonObject();
                mAdapter.add(loadingItem);
                setListAdapter(mAdapter);

            } else {
                mAdapter.add(loadingItem);
            }

            mPosition++;
            getRssData(mPosition);
        }
        mAdapter.notifyDataSetChanged();
    }

    private Response.Listener<ArrayList<RssItem>> rssListener = new Response.Listener<ArrayList<RssItem>>(){
        @Override
        public void onResponse(ArrayList<RssItem> response) {

            String rssCacheKey = categoryKeyList[mPosition]
                    + Util.CATEGORY_TYPE_HOTENTRY
                    + Util.getUpdateTime(mContext);
            Gson gson = new Gson();
            AppData.save(mContext, rssCacheKey, gson.toJson(response));
            JsonParser parser = new JsonParser();
            setDataToList(parser.parse(gson.toJson(response)).getAsJsonArray());
        }
    };

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        JsonObject item = mAdapter.getItem(position);
        int rowType = item.get("rowType").getAsInt();
        if (rowType == 1 || rowType == 2) return;

        String webviewUrl = item.get("link").getAsString();
        String webviewTitle = item.get("title").getAsString();
        if (isDualPane) {
            ActionBar actionBar = ((ActionBarActivity) getActivity()).getSupportActionBar();
            actionBar.setTitle(webviewTitle);
            actionBar.setSubtitle(webviewUrl);

            BkWebViewFragment webviewFragment = new BkWebViewFragment();
            Bundle args = new Bundle();
            args.putString(Util.BK_WEBVIEW_URL, webviewUrl);
            webviewFragment.setArguments(args);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.bk_webview_frame, webviewFragment).commit();
        } else {
            Intent intent = new Intent(mContext, BkWebViewActivity.class);
            intent.putExtra(Util.BK_WEBVIEW_URL, webviewUrl);
            intent.putExtra(Util.BK_WEBVIEW_TITLE, webviewTitle);
            startActivity(intent);
        }
    }
}
