package com.shariful.alquran2;

public class SuraListModel {

    private int id;
    private String serial;
    private String name_bangla;
    private String meaning_bangla;
    private String name_english;
    private String meaning_english;
    private String ayatNum;
    private String place;

    private String arbi_audioUrl;
    private String bangla_audioUrl;


    public SuraListModel() {
    }

    public SuraListModel(int id, String serial, String name_bangla, String meaning_bangla, String name_english, String meaning_english, String ayatNum, String place, String arbi_audioUrl, String bangla_audioUrl) {
        this.id = id;
        this.serial = serial;
        this.name_bangla = name_bangla;
        this.meaning_bangla = meaning_bangla;
        this.name_english = name_english;
        this.meaning_english = meaning_english;
        this.ayatNum = ayatNum;
        this.place = place;
        this.arbi_audioUrl = arbi_audioUrl;
        this.bangla_audioUrl = bangla_audioUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName_bangla() {
        return name_bangla;
    }

    public void setName_bangla(String name_bangla) {
        this.name_bangla = name_bangla;
    }

    public String getMeaning_bangla() {
        return meaning_bangla;
    }

    public void setMeaning_bangla(String meaning_bangla) {
        this.meaning_bangla = meaning_bangla;
    }

    public String getName_english() {
        return name_english;
    }

    public void setName_english(String name_english) {
        this.name_english = name_english;
    }

    public String getMeaning_english() {
        return meaning_english;
    }

    public void setMeaning_english(String meaning_english) {
        this.meaning_english = meaning_english;
    }

    public String getAyatNum() {
        return ayatNum;
    }

    public void setAyatNum(String ayatNum) {
        this.ayatNum = ayatNum;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getArbi_audioUrl() {
        return arbi_audioUrl;
    }

    public void setArbi_audioUrl(String arbi_audioUrl) {
        this.arbi_audioUrl = arbi_audioUrl;
    }

    public String getBangla_audioUrl() {
        return bangla_audioUrl;
    }

    public void setBangla_audioUrl(String bangla_audioUrl) {
        this.bangla_audioUrl = bangla_audioUrl;
    }
}

