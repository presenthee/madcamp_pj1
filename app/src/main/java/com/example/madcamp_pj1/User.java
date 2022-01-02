package com.example.madcamp_pj1;

public class User {
    private String id;
    private int pw;
    private String school;
    private int sit;

    public User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPw() {
        return pw;
    }

    public void setPw(int pw) {
        this.pw = pw;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getSit() {
        return sit;
    }

    public void setSit(int sit) {
        this.sit = sit;
    }
}
