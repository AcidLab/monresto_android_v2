package com.monresto.acidlabs.monresto.Model;

public class HomepageConfig {
    private static HomepageConfig instance;
    private int id;
    private String cover_image;
    private String busket_image;
    private String created_at;
    private String updated_at;

    public static HomepageConfig getInstance(){
        return instance;
    }
    public static void setInstance(HomepageConfig insta) {
        instance = insta;
    }

    public HomepageConfig(int id, String cover_image, String busket_image, String created_at, String updated_at) {
        this.id = id;
        this.cover_image = cover_image;
        this.busket_image = busket_image;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getBusket_image() {
        return busket_image;
    }

    public void setBusket_image(String busket_image) {
        this.busket_image = busket_image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
