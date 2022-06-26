package com.example.myshopee;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int imgSrc;
    private double price;

    public Product(int id, String name, int imgSrc, double price) {
        this.id = id;
        this.name = name;
        this.imgSrc = imgSrc;
        this.price = price;
    }

    public Product() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    public double getPrice() {
        return Math.round(price);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
