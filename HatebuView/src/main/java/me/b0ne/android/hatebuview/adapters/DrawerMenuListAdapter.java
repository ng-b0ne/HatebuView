package me.b0ne.android.hatebuview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import me.b0ne.android.hatebuview.R;
import me.b0ne.android.hatebuview.models.DrawerMenuItem;

/**
 * Created by bone on 13/09/17.
 */
public class DrawerMenuListAdapter extends ArrayAdapter<DrawerMenuItem> {

    public DrawerMenuListAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.drawer_tool_row, null);
        }
        TextView viewName = (TextView)view.findViewById(R.id.row_name);
        DrawerMenuItem item = getItem(position);
        viewName.setText(item.getName());
        return view;
    }
}
