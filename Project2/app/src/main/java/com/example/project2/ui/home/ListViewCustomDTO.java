package com.example.project2.ui.home;


import java.io.Serializable;

public class ListViewCustomDTO implements Serializable {
    private int resId;
    private String Name;
    private String content;
    private String number;

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNumber(String number) {
        this.number=number;
    }

    public String getNumber() {
        return number;
    }

}