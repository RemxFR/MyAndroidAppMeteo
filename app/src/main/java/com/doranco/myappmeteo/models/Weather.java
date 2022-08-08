package com.doranco.myappmeteo.models;

import com.google.gson.annotations.SerializedName;

public class Weather {

    private int id;
    private String main;
    @SerializedName("description")
    private String description;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
