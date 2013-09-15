package me.b0ne.android.hatebuview.models;

/**
 * Created by bone on 13/09/15.
 */
public class RssItem {
    private String title;
    private String text;
    private String link;


    public void setTitle(String _title) {
        this.title = _title;
    }

    public String getTitle() {
        return title;
    }

    public void setText(String _text) {
        this.text = _text;
    }

    public String getText() {
        return text;
    }

    public void setLink(String _link) {
        this.link = _link;
    }

    public String getLink() {
        return link;
    }
}
