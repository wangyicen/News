package com.feicui.edu.newsapp.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3 0003.
 */

/*{"subgrp":[
        {"subgroup":"社会",
        "subid":2},
        {"subgroup":"军事",
        "subid":1}],
   "gid":1,
   "group":"新闻"},*/
public class NewsGroup {

    private List<NewsType> subgrp;
    private int gid;
    private String group;

    public NewsGroup() {
    }

    public NewsGroup(List<NewsType> types, int gid, String group) {
        this.subgrp = types;
        this.gid = gid;
        this.group = group;
    }

    public List<NewsType> getSubgrp() {
        return subgrp;
    }

    public void setSubgrp(List<NewsType> subgrp) {
        this.subgrp = subgrp;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "NewsGroup{" +
                "subgrp=" + subgrp +
                ", gid=" + gid +
                ", group='" + group + '\'' +
                '}';
    }
}
