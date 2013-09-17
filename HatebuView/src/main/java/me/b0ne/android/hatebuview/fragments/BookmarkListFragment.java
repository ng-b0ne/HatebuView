package me.b0ne.android.hatebuview.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.adapters.BookmarkListAdapter;

/**
 * Created by bone on 13/09/17.
 */
public class BookmarkListFragment extends ListFragment {

    private Context mContext;
    private BookmarkListAdapter mAdapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bookmark_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }
}
