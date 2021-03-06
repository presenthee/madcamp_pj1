package com.example.madcamp_pj1;

public class User {
    private String name;
    private String pw;
    private String school;
    private String sit;
    private String time;

    public User(){}

    public User(String name, String pw, String school, String sit, String time){
        this.name = name;
        this.pw = pw ;
        this.school = school;
        this.sit = sit;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSit() {
        return sit;
    }

    public void setSit(String sit) {
        this.sit = sit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
