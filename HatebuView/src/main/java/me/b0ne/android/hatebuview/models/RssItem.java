package me.b0ne.android.hatebuview.models;

import android.util.Log;

import com.google.android.gms.internal.fa;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bone on 13/09/15.
 */
public class RssItem {
    private String title;
    private String text;
    private String content;
    private String link;
    private String date;
    private String category;
    private int bookmarkCount;
    private int itemCount;
    private int rowType = 0;


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

    public void setContent(String _content) {
        this.content = _content;
    }

    public String getContent() {
        return this.content;
    }

    public void setLink(String _link) {
        this.link = _link;
    }

    public String getLink() {
        return this.link;
    }

    public void setDate(String _date) {
        _date = _date.replace("+09:00", "");
        _date = _date.replace("T", " ");
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

    public void setRowType(int _type) {
        this.rowType = _type;
    }

    public int getRowType() {
        return this.rowType;
    }

    public String getFaviconUrl() {
        String favicon = null;
        String content = this.content;
        Pattern p = Pattern.compile("<img.+?src=\"((.*)favicon.*?)\".*? />", Pattern.MULTILINE);
        Matcher m = p.matcher(content);
        while (m.find()) {
            for (int i = 0; i < m.groupCount(); i++) {
                favicon = m.group(1);
            }
        }
        return favicon;
    }

    public String getContentImgUrl() {
        String imgUrl = "";
        String content = this.content;
        Pattern p = Pattern.compile("<img .* src=\"((.*)entryimage.*?)\".*? />", Pattern.MULTILINE);
        Matcher m = p.matcher(content);
        while (m.find()) {
            for (int i = 0; i < m.groupCount(); i++) {
                imgUrl = m.group(1);
            }
        }
        return imgUrl;
    }

}
