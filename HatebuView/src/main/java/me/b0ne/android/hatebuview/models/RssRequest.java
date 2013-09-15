package me.b0ne.android.hatebuview.models;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by bone on 13/09/15.
 */
public class RssRequest extends Request<InputStream> {
    private final Response.Listener<InputStream> mListener;

    public RssRequest(int method, String url, Response.Listener<InputStream> mListener,
                      Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = mListener;
    }

    @Override
    protected Response<InputStream> parseNetworkResponse(NetworkResponse response) {
        InputStream is = new ByteArrayInputStream(response.data);

        return Response.success(is, getCacheEntry());
    }

    @Override
    protected void deliverResponse(InputStream response) {
        this.mListener.onResponse(response);
    }

}
