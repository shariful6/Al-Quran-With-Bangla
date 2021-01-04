package com.shariful.alquran2;

public class DetailsModel {

    String id,tag,verseid,arbitxt,banglatxt,banglameaning,englishmeaning;

    public DetailsModel() {
    }

    public DetailsModel(String id, String tag, String verseid, String arbitxt, String banglatxt, String banglameaning, String englishmeaning) {
        this.id = id;
        this.tag = tag;
        this.verseid = verseid;
        this.arbitxt = arbitxt;
        this.banglatxt = banglatxt;
        this.banglameaning = banglameaning;
        this.englishmeaning = englishmeaning;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVerseid() {
        return verseid;
    }

    public void setVerseid(String verseid) {
        this.verseid = verseid;
    }

    public String getArbitxt() {
        return arbitxt;
    }

    public void setArbitxt(String arbitxt) {
        this.arbitxt = arbitxt;
    }

    public String getBanglatxt() {
        return banglatxt;
    }

    public void setBanglatxt(String banglatxt) {
        this.banglatxt = banglatxt;
    }

    public String getBanglameaning() {
        return banglameaning;
    }

    public void setBanglameaning(String banglameaning) {
        this.banglameaning = banglameaning;
    }

    public String getEnglishmeaning() {
        return englishmeaning;
    }

    public void setEnglishmeaning(String englishmeaning) {
        this.englishmeaning = englishmeaning;
    }
}
