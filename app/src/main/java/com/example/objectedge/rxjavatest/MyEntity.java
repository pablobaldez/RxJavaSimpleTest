package com.example.objectedge.rxjavatest;

/**
 * Created by objectedge on 3/21/16.
 */
public class MyEntity {

    private long userId;
    private long id;
    private String title;
    private String body;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "title: " + title + " id: " + id;
    }
}
