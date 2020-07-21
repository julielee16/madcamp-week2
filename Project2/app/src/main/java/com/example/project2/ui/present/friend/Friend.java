package com.example.project2.ui.present.friend;

import java.io.Serializable;
import java.util.List;

public class Friend implements Serializable {
    private int id;
    private String name;
    private List<Friend> friends;

    public Friend(int id, String name, List<Friend> friends){
        this.id = id;
        this.name = name;
        this.friends = friends;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFriends(List<Friend> friends) {
        this.friends = friends;
    }

    public List<Friend> getFriends() {
        return friends;
    }
}
