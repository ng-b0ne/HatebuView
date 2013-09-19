package me.b0ne.android.hatebuview.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.android.volley.Response;

import java.util.ArrayList;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.adapters.BookmarkListAdapter;
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
        String rssUrl = getArguments().getString(Util.KEY_BK_RSS_URL);
        HateBook.getRss(mContext, rssUrl, rssListener);

    }

    private Response.Listener<ArrayList<RssItem>> rssListener = new Response.Listener<ArrayList<RssItem>>(){
        @Override
        public void onResponse(ArrayList<RssItem> response) {
            if (response.size() < 1) return;
            ProgressLayout.setVisibility(View.GONE);

            mAdapter = new BookmarkListAdapter(mContext, response);
            setListAdapter(mAdapter);
        }
    };
}