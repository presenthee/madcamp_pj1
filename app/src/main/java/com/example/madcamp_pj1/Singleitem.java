package com.example.madcamp_pj1;

public class Singleitem {
    String name;
    String mobile;
    int resld;

    //생성자
    public Singleitem(String name,String mobile, int resld ) {
        this.name=name;
        this.mobile=mobile;
        this.resld=resld;
    }

    //필드 접근 메소드
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getResld() {
        return resld;
    }

    @Override
    public String toString() {
        return "SinglerItem{"+ "name='"+name+'\''+
                ", mobile='"+mobile+'\''+'}';
    }

}
