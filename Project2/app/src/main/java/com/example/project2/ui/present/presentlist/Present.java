package com.example.project2.ui.present.presentlist;

import com.example.project2.ui.present.friend.Friend;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Present {
    private int id;
    private int ownerId;
    private String title;
    private int price;
    private int numFilled;
    private int numNeeded;
    private List<Friend> sponsors;
    private List<String> messages;

    public Present(int id, int ownerId, String title, int price, int numFilled, int numNeeded,
                   List<Friend> sponsors, List<String> messages) {
        this.id = id;
        this.ownerId = ownerId;
        this.title = title;
        this.price = price;
        this.numFilled = numFilled;
        this.numNeeded = numNeeded;
        this.sponsors = sponsors;
        this.messages = messages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setNumFilled(int numFilled) {
        this.numFilled = numFilled;
    }

    public int getNumFilled() {
        return numFilled;
    }

    public void setNumNeeded(int numNeeded) {
        this.numNeeded = numNeeded;
    }

    public int getNumNeeded() {
        return numNeeded;
    }

    public void setSponsors(List<Friend> sponsors) {
        this.sponsors = sponsors;
    }

    public List<Friend> getSponsors() {
        return sponsors;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}


