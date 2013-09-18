package me.b0ne.android.hatebuview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.models.RssItem;

/**
 * Created by bone on 13/09/17.
 */
public class BookmarkListAdapter extends ArrayAdapter<RssItem> {

    public BookmarkListAdapter(Context context, ArrayList<RssItem> objects) {
        super(context, 0, objects);
    }

    public BookmarkListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.bookmark_row_title, null);
//            view = LayoutInflater.from(getContext()).inflate(R.layout.bookmark_row_card, null);
//            view = LayoutInflater.from(getContext()).inflate(R.layout.bookmark_row_full, null);
        }

        RssItem item = getItem(position);
        TextView titleView = (TextView)view.findViewById(R.id.title);
        titleView.setText(item.getTitle());
        return view;
    }
}
