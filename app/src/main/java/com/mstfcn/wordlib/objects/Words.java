package com.mstfcn.wordlib.objects;

public class Words {

    public Words(){}

    public Words(String fromWord, String toWord, Integer cid, String fromLanguage, String toLanguage) {

        this.fromWord = fromWord;
        this.toWord = toWord;
        this.cid = cid;
        this.fromLanguage = fromLanguage;
        this.toLanguage = toLanguage;
    }

    public String getFromWord() {
        return fromWord;
    }

    public String getToWord() {
        return toWord;
    }

    public String getFromLanguage() {
        return fromLanguage;
    }

    public String getToLanguage() {
        return toLanguage;
    }

    public void setFromWord(String fromWord) {
        this.fromWord = fromWord;
    }

    public void setToWord(String toWord) {
        this.toWord = toWord;
    }

    public void setFromLanguage(String fromLanguage) {
        this.fromLanguage = fromLanguage;
    }

    public void setToLanguage(String toLanguage) {
        this.toLanguage = toLanguage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }


    String fromWord;
    String toWord;
    Integer cid;
    String fromLanguage;
    String toLanguage;
    Integer id;


}
