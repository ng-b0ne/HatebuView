package me.b0ne.android.hatebuview.models;

/**
 * Created by bone on 13/09/17.
 */
public class DrawerMenuItem {
    private String key;
    private String name;
    private String url;

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

    public void setUrl(String _url) {
        this.url = _url;
    }

    public String getUrl() {
        return this.url;
    }
}
