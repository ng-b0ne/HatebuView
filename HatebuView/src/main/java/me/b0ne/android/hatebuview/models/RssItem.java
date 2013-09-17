package me.b0ne.android.hatebuview.models;

/**
 * Created by bone on 13/09/15.
 */
public class RssItem {
    private String title;
    private String text;
    private String link;
    private String date;
    private String category;
    private int bookmarkCount;
    private int itemCount;


    public void setTitle(String _title) {
        this.title = _title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setText(String _text) {
        this.text = _text;
    }

    public String getText() {
        return this.text;
    }

    public void setLink(String _link) {
        this.link = _link;
    }

    public String getLink() {
        return this.link;
    }

    public void setDate(String _date) {
        this.date = _date;
    }

    public String getDate() {
        return this.date;
    }

    public void setCategory(String _category) {
        this.category = _category;
    }

    public String getCategory() {
        return this.category;
    }

    public void setBookmarkCount(int _count) {
        this.bookmarkCount = _count;
    }

    public int getBookmarkCount() {
        return this.bookmarkCount;
    }

    public void setItemCount(int _count) {
        this.itemCount = _count;
    }

    public int getItemCount() {
        return this.itemCount;
    }
}
