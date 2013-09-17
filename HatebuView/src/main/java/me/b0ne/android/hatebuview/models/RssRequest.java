package me.b0ne.android.hatebuview.models;

import android.util.Log;
import android.util.Xml;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by bone on 13/09/15.
 */
public class RssRequest extends Request<ArrayList<RssItem>> {
    private final Response.Listener<ArrayList<RssItem>> mListener;

    public RssRequest(int method, String url, Response.Listener<ArrayList<RssItem>> mListener,
                      Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = mListener;
    }

    @Override
    protected Response<ArrayList<RssItem>> parseNetworkResponse(NetworkResponse response) {
        InputStream is = new ByteArrayInputStream(response.data);
        ArrayList<RssItem> itemList = new ArrayList<RssItem>();
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(is, null);
            int eventType = parser.getEventType();
            RssItem currentItem = null;
            int itemCount = 0;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag;
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")) {
                            currentItem = new RssItem();
                        } else if (currentItem != null) {
                            if (tag.equals("title")) {
                                currentItem.setTitle(parser.nextText());
                            } else if (tag.equals("description")) {
                                currentItem.setText(parser.nextText());
                            } else if (tag.equals("link")) {
                                currentItem.setLink(parser.nextText());
                            } else if (tag.equals("date")) {
                                currentItem.setDate(parser.nextText());
                            } else if (tag.equals("subject")) {
                                currentItem.setCategory(parser.nextText());
                            } else if (tag.equals("bookmarkcount")) {
                                currentItem.setBookmarkCount(Integer.valueOf(parser.nextText()));
                            }

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = parser.getName();
                        if (tag.equals("item")) {
                            currentItem.setItemCount(itemCount);
                            itemList.add(currentItem);
                            itemCount++;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            Log.v("ERROR", "on parseXML = " + e.getMessage());
        }

        return Response.success(itemList, getCacheEntry());
    }

    @Override
    protected void deliverResponse(ArrayList<RssItem> response) {
        this.mListener.onResponse(response);
    }

}
