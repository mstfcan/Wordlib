package com.mstfcn.wordlib.objects;

public class Categories {

    public Categories() {
    }

    public Categories(String catName, String catDesc) {
        this.catName = catName;
        this.catDesc = catDesc;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public String getCatName() {
        return catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String catName;
    private String catDesc;
    private Integer id;
}
