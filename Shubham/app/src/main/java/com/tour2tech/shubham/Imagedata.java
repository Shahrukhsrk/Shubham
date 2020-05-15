package com.tour2tech.shubham;

public class Imagedata {

    private String iname,image;

    public Imagedata(String iname, String image) {
        this.iname = iname;
        this.image = image;
    }

    public Imagedata() {
    }

    public String getIname() {
        return iname;
    }

    public void setIname(String iname) {
        this.iname = iname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
