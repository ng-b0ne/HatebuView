package me.b0ne.android.hatebuview.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Response;

import java.util.ArrayList;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.adapters.HeadLineListAdapter;
import me.b0ne.android.hatebuview.models.HateBook;
import me.b0ne.android.hatebuview.models.RssItem;

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
    private RssItem loadingItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookmark_list, container, false);
        ProgressLayout = (LinearLayout)view.findViewById(R.id.progress_layout);
        ProgressLayout.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mContext = getActivity().getApplicationContext();
        mAdapter = new HeadLineListAdapter(mContext);

        // add loading view
        loadingItem = new RssItem();
        loadingItem.setRowType(2);
        mAdapter.add(loadingItem);

        categoryNameList = getResources().getStringArray(R.array.hatebu_category_name);
        categoryHotentryRssList = getResources().getStringArray(R.array.hatebu_category_hotentry_rssurl);

        getRssData(1);
        setListAdapter(mAdapter);
    }

    private void getRssData(int position) {
        mPosition = position;
        HateBook.getRss(mContext, categoryHotentryRssList[position], rssListener);
    }

    private Response.Listener<ArrayList<RssItem>> rssListener = new Response.Listener<ArrayList<RssItem>>(){
        @Override
        public void onResponse(ArrayList<RssItem> response) {
            mAdapter.remove(loadingItem);
            RssItem item = new RssItem();
            item.setRowType(1);
            item.setCategory(categoryNameList[mPosition]);
            mAdapter.add(item);

            for (int i = 0; i < 3; i++) {
                item = response.get(i);
                mAdapter.add(item);
            }

            if (mPosition < (categoryNameList.length - 1)) {
                mAdapter.add(loadingItem);
                mPosition++;
                getRssData(mPosition);
            }
            mAdapter.notifyDataSetChanged();
        }
    };
}
