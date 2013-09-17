package me.b0ne.android.hatebuview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import me.b0ne.android.hatebuview.models.BookmarkItem;

/**
 * Created by bone on 13/09/17.
 */
public class BookmarkListAdapter extends ArrayAdapter<BookmarkItem> {

    public BookmarkListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            // view = LayoutInflater.from(getContext()).inflate(R.layout.entry_row, null);
        }
        return view;
    }
}
