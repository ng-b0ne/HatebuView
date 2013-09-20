package me.b0ne.android.hatebuview.adapters;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.models.BitmapCache;
import me.b0ne.android.hatebuview.models.RssItem;

/**
 * Created by bone on 13/09/19.
 */
public class HeadLineListAdapter  extends ArrayAdapter<RssItem> {
    private ImageLoader mImageLoader;

    public HeadLineListAdapter(Context context, ArrayList<RssItem> objects) {
        super(context, 0, objects);
        RequestQueue queue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(queue, new BitmapCache());
    }

    public HeadLineListAdapter(Context context) {
        super(context, 0);
        RequestQueue queue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(queue, new BitmapCache());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null || view.getId() != R.layout.bookmark_row_title) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.bookmark_row_title, null);
//            view = LayoutInflater.from(getContext()).inflate(R.layout.bookmark_row_card, null);
//            view = LayoutInflater.from(getContext()).inflate(R.layout.bookmark_row_full, null);
        }
        RssItem item = getItem(position);

        int rowType = item.getRowType();
        if (rowType == 1) { // category barの表示、ヘッドライト画面用
            view = LayoutInflater.from(getContext()).inflate(R.layout.category_bar_row, null);
            TextView nameView = (TextView)view.findViewById(R.id.category_name);
            nameView.setText(item.getCategory());
            return view;
        } else if (rowType == 2) { // loading view
            view = LayoutInflater.from(getContext()).inflate(R.layout.loading_row, null);
            return view;
        } else {
            TextView titleView = (TextView)view.findViewById(R.id.title);
            TextView dateView = (TextView)view.findViewById(R.id.datetime);
            TextView bkCountView = (TextView)view.findViewById(R.id.bookmark_count);
            // Log.v("TEST", item.getContentImgUrl());

            titleView.setText(item.getTitle());
            dateView.setText(item.getDate());
            bkCountView.setText(item.getBookmarkCount() + " users");

            NetworkImageView faviconView = (NetworkImageView)view.findViewById(R.id.site_favicon);
            faviconView.setImageUrl(item.getFaviconUrl(), mImageLoader);
        }
        return view;
    }
}
