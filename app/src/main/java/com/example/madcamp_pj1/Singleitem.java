package com.example.madcamp_pj1;

import android.graphics.Bitmap;

public class Singleitem {
    String name;
    String mobile;
    String id;
    Bitmap image;

    //생성자
    public Singleitem(String name,String mobile,String id, Bitmap image ) {
        this.name=name;
        this.mobile=mobile;
        this.id=id;
        this.image=image;
    }

    //필드 접근 메소드
    public String getName() {
        return name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getId() { return id; }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "SinglerItem{"+ "name='"+name+'\''+ ", mobile='"+mobile+'\''+'}';
    }

}