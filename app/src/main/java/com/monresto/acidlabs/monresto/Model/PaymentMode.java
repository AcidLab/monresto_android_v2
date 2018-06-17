package com.monresto.acidlabs.monresto.Model;

public class PaymentMode {
    private int id;
    private String title;
    private String image;

    public PaymentMode(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}