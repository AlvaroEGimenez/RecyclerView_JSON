package com.example.alva.recyclerview_json;

public class ItemEjemplo {

    private String mIMagenUrl;
    private String mCreador;
    private String mFans;
    private String mLanzamiento;
    private String urlTracklist;

    public ItemEjemplo(String mIMagenUrl, String mCreador, String fans,String lanzamineto, String url) {
        this.mIMagenUrl = mIMagenUrl;
        this.mCreador = mCreador;
        this.mFans = fans;
        this.mLanzamiento = lanzamineto;
        this.urlTracklist = url;
    }

    public String getmIMagenUrl() {
        return mIMagenUrl;
    }

    public String getmCreador() {
        return mCreador;
    }

    public String getmLikes() {
        return mFans;
    }

    public String getmLanzamiento() {
        return mLanzamiento;
    }

    public String getUrlTracklist() {
        return urlTracklist;
    }
}
