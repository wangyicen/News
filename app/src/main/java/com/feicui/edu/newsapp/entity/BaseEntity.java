package com.feicui.edu.newsapp.entity;

import java.util.List;

/**
 * Created by asus on 2016/10/27.
 */

/**{"message":"OK",
 "status":0,
 "data":[
 {"summary":"英超将于本月15号重燃战火，前七轮最火的球员是谁？非孙兴民莫属。热刺本赛季至今也以5胜2平的不败战绩排名英超第二，仅次于曼城。在热刺2：1击败米德尔斯堡的比赛中，孙兴民独中两元，帮助球队在客场全取三分。在欧冠小组赛第二轮的比赛中，也是孙兴民的进球帮助球队1：0取得胜利。 \t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t",
 "icon":"http:\/\/118.244.212.82:9092\/Images\/20161009031226.jpg",
 "stamp":"2016-10-09 08:21:45.0",
 "title":"英超球员场均得分排行榜，韩国一哥优势明显",
 "nid":44,
 "link":"http:\/\/mini.eastday.com\/a\/161009082150384.html",
 "type":1}
 ]
 }*/

/**{"message":"OK",
   "status":0,
   "data":[
          {"subgrp":[
                {"subgroup":"社会",
                 "subid":2},
                {"subgroup":"军事",
                 "subid":1}],
           "gid":1,
           "group":"新闻"},
        ]
  }*/

public class BaseEntity<T> {

    private String message;
    private int status;
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BaseEntity(List<T> data, String message, int status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public BaseEntity() {
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
