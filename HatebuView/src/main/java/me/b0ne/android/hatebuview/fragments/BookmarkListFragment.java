package me.b0ne.android.hatebuview.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Response;
import com.google.android.gms.internal.bu;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.activities.BkWebViewActivity;
import me.b0ne.android.hatebuview.adapters.BookmarkListAdapter;
import me.b0ne.android.hatebuview.models.AppData;
import me.b0ne.android.hatebuview.models.HateBook;
import me.b0ne.android.hatebuview.models.RssItem;
import me.b0ne.android.hatebuview.models.Util;

/**
 * Created by bone on 13/09/17.
 */
public class BookmarkListFragment extends ListFragment {

    private Context mContext;
    private BookmarkListAdapter mAdapter;
    private LinearLayout ProgressLayout;

    private String rssCacheKey;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookmark_list, container, false);
        ProgressLayout = (LinearLayout)view.findViewById(R.id.progress_layout);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity().getApplicationContext();
        Bundle args = getArguments();

        rssCacheKey = args.getString(Util.KEY_BK_CATEGORY_KEY)
                + args.getString(Util.KEY_BK_CATEGORY_TYPE)
                + Util.getUpdateTime(mContext);
        String rssUrl = args.getString(Util.KEY_BK_RSS_URL);

        if (AppData.get(mContext, rssCacheKey) != null) {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(AppData.get(mContext, rssCacheKey)).getAsJsonArray();
            setDataToList(jsonArray);
        } else {
            HateBook.getRss(mContext, rssUrl, rssListener);
        }
    }

    private void setDataToList(JsonArray jsonArray) {
        ProgressLayout.setVisibility(View.GONE);
        mAdapter = new BookmarkListAdapter(mContext);
        for (int i = 0; i<jsonArray.size(); i++) {
            mAdapter.add(jsonArray.get(i).getAsJsonObject());
        }
        setListAdapter(mAdapter);
    }

    private Response.Listener<ArrayList<RssItem>> rssListener = new Response.Listener<ArrayList<RssItem>>(){
        @Override
        public void onResponse(ArrayList<RssItem> response) {
            if (response.size() < 1) return;

            Gson gson = new Gson();
            AppData.save(mContext, rssCacheKey, gson.toJson(response));
            JsonParser parser = new JsonParser();
            setDataToList(parser.parse(gson.toJson(response)).getAsJsonArray());
        }
    };

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        JsonObject item = mAdapter.getItem(position);
        Intent intent = new Intent(mContext, BkWebViewActivity.class);
        intent.putExtra(Util.BK_WEBVIEW_URL, item.get("link").getAsString());
        intent.putExtra(Util.BK_WEBVIEW_TITLE, item.get("title").getAsString());
        startActivity(intent);
    }
}