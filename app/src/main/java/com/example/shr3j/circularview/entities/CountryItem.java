package com.example.shr3j.circularview.entities;

import org.json.JSONObject;

public class CountryItem {
    private String name;
    private String picture;

    public CountryItem(JSONObject json) {
        this.picture = json.optString("picture");
        this.name = json.optString("name");
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}