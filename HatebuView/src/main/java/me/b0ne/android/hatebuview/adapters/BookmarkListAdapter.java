package me.b0ne.android.hatebuview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.models.BitmapCache;

/**
 * Created by bone on 13/09/17.
 */
public class BookmarkListAdapter extends ArrayAdapter<JsonObject> {
    private ImageLoader mImageLoader;

    public BookmarkListAdapter(Context context, ArrayList<JsonObject> objects) {
        super(context, 0, objects);
        RequestQueue queue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(queue, new BitmapCache());
    }

    public BookmarkListAdapter(Context context) {
        super(context, 0);
        RequestQueue queue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(queue, new BitmapCache());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.bookmark_row_title, null);
//            view = LayoutInflater.from(getContext()).inflate(R.layout.bookmark_row_card, null);
//            view = LayoutInflater.from(getContext()).inflate(R.layout.bookmark_row_full, null);
        }

        JsonObject item = getItem(position);
        TextView titleView = (TextView)view.findViewById(R.id.title);
        TextView dateView = (TextView)view.findViewById(R.id.datetime);
        TextView bkCountView = (TextView)view.findViewById(R.id.bookmark_count);
        // Log.v("TEST", item.getContentImgUrl());

        titleView.setText(item.get("title").getAsString());
        dateView.setText(item.get("date").getAsString());
        bkCountView.setText(item.get("bookmarkCount").getAsString() + " users");

        NetworkImageView faviconView = (NetworkImageView)view.findViewById(R.id.site_favicon);
        faviconView.setImageUrl(item.get("faviconUrl").getAsString(), mImageLoader);
        return view;
    }
}
