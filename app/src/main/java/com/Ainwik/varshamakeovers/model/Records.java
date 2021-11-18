package com.example.varshamakeovers.model;

public class Records
{

    public Records(String imageUurl) {
        this.imageUurl = imageUurl;
    }

    public Records()

    {

    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Records(int price, String name) {
        this.price = price;
        this.name = name;
    }

    int price;
    String imageUurl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;


    public String getImageUurl() {
        return imageUurl;
    }

    public void setImageUurl(String imageUurl) {
        this.imageUurl = imageUurl;
    }

}
