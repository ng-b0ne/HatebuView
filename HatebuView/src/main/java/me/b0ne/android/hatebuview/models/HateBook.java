package me.b0ne.android.hatebuview.models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by bone on 13/08/21.
 */
public class HateBook {

    public static void getRss(Context context, String url, Response.Listener<ArrayList<RssItem>> listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        RssRequest rssRequest = new RssRequest(Request.Method.GET, url, listener, null);
        queue.add(rssRequest);
    }
}
