package me.b0ne.android.hatebuview.models;

import com.android.volley.toolbox.ImageLoader;;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by b0ne on 13/08/30.
 */
public class BitmapCache implements ImageLoader.ImageCache {

    /** cache size(Bitmap数) */
    private static final int CACHE_SIZE_MAX = 10 * 1024 * 1024;

    private LruCache<String, Bitmap> mCache;

    public BitmapCache() {
        mCache = new LruCache<String, Bitmap>(CACHE_SIZE_MAX){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }
}
