package com.example.project2.ui.present.presentlist;

import java.util.List;

public class Present {
    private String title;
    private int price;
    private int numFilled;
    private int numNeeded;
    private List<Present> sponsors;

    public Present(String title, int price, int numFilled, int numNeeded,
                   List<Present> sponsors) {
        this.title = title;
        this.price = price;
        this.numFilled = numFilled;
        this.numNeeded = numNeeded;
        this.sponsors = sponsors;
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

    public void setSponsors(List<Present> sponsors) {
        this.sponsors = sponsors;
    }

    public List<Present> getSponsors() {
        return sponsors;
    }
}


