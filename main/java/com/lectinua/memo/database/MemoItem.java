package com.lectinua.memo.database;

public class MemoItem {
    private int index;
    private String title, content, time, date, place;

    public MemoItem(int index, String title, String content) {
        this.index = index;
        this.title = title;
        this.content = content;
    }

    public MemoItem(int index, String title, String content, String time, String date, String place) {
        this.index = index;
        this.title = title;
        this.content = content;
        this.time = time;
        this.date = date;
        this.place = place;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
