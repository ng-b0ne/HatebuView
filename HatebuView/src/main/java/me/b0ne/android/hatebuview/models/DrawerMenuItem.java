package me.b0ne.android.hatebuview.models;

/**
 * Created by bone on 13/09/17.
 */
public class DrawerMenuItem {
    private String key;
    private String name;
    private String hotentryUrl;
    private String entrylistUrl;

    public void setKey(String _key) {
        this.key = _key;
    }

    public String getKey() {
        return key;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public String getName() {
        return name;
    }

    public void setHotentryUrl(String _url) {
        this.hotentryUrl = _url;
    }

    public String getHotentryUrl() {
        return this.hotentryUrl;
    }

    public void setEntrylistUrl(String _url) {
        this.entrylistUrl = _url;
    }

    public String getEntrylistUrl() {
        return this.entrylistUrl;
    }
}
