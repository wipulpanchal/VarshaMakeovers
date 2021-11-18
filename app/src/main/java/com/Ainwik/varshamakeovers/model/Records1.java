package com.example.varshamakeovers.model;

public class Records1
{

    String name;
    int cost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;

    public Records1(String name, int cost, String image) {
        this.name = name;
        this.cost = cost;
        this.image = image;
    }



}
