package me.b0ne.android.hatebuview;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

import me.b0ne.android.hatebuview.models.RssItem;
import me.b0ne.android.hatebuview.models.RssRequest;

public class MainActivity extends Activity {
    private static RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);
        String rssUrl = "http://b.hatena.ne.jp/hotentry.rss";
//        RssRequest rssRequest = new RssRequest(Request.Method.GET, rssUrl, rssListener, null);
//        mQueue.add(rssRequest);
    }

    private Response.Listener<InputStream> rssListener = new Response.Listener<InputStream>() {
        @Override
        public void onResponse(InputStream response) {
            InputStream is = response;

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
                                    // currentItem.setTitle(parser.nextText());
                                    Log.v("TEST", "title = " + parser.nextText());
                                } else if (tag.equals("description")) {
//                                    currentItem.setText(parser.nextText());
                                    Log.v("TEST", "desc = " + parser.nextText());
                                } else if (tag.equals("link")) {
//                                    currentItem.setLink(parser.nextText());
                                    Log.v("TEST", "link = " + parser.nextText());
                                }

                            }
                            break;
                        case XmlPullParser.END_TAG:
                            tag = parser.getName();
                            if (tag.equals("item")) {
                                itemCount++;
                            }
                            break;
                    }
                    eventType = parser.next();
                }
            } catch (Exception e) {
                Log.v("TEST ERROR", "on parseXML = " + e.getMessage());
            }
        }

    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
