package com.feicui.edu.newsapp.entity;

/**
 * Created by Administrator on 2016/10/24 0024.
 * 新闻数据解析处理
 * subId,stamp,icon,title,summary,link
 */
public class News {
    private int type;
    private int nid;
    private String stamp;
    private String icon;
    private String title;
    private String summary;
    private String link;

    public int getType() {
        return type;
    }

    public int getNid() {
        return nid;
    }

    public String getStamp() {
        return stamp;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getLink() {
        return link;
    }

    public News(int type, int nid, String stamp, String icon, String title, String summary, String link) {
        this.type = type;
        this.nid = nid;
        this.stamp = stamp;
        this.icon = icon;
        this.title = title;
        this.summary = summary;
        this.link = link;
    }

    @Override
    public String toString() {
        return "News{" +
                "type=" + type +
                ", nid=" + nid +
                ", stamp='" + stamp + '\'' +
                ", icon=" + icon +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
