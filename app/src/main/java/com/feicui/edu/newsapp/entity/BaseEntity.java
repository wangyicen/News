package com.feicui.edu.newsapp.entity;

import java.util.List;

/**
 * Created by asus on 2016/10/27.
 */
public class BaseEntity {

    private String message;
    private int status;
    private List<News> data;

    public List<News> getData() {
        return data;
    }

    public void setData(List<News> data) {
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

    public BaseEntity(List<News> data, String message, int status) {
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
