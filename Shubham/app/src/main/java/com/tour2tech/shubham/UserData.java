package com.tour2tech.shubham;

public class UserData {

    String name,phoneno,email;

    public UserData() {
    }

    public UserData(String name, String phoneno, String email) {
        this.name = name;
        this.phoneno = phoneno;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
