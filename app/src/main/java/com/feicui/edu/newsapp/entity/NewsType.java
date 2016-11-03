package com.feicui.edu.newsapp.entity;

/**
 * Created by Administrator on 2016/11/3 0003.
 */

/*{"subgroup":"社会",
        "subid":2},*/

public class NewsType {
    private String subgroup;
    private int subid;

    public NewsType() {
    }

    public NewsType(String subgroup, int subid) {
        this.subgroup = subgroup;
        this.subid = subid;
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }

    @Override
    public String toString() {
        return "NewsType{" +
                "subgroup='" + subgroup + '\'' +
                ", subid=" + subid +
                '}';
    }
}
