package com.gpp.Model;

import android.provider.ContactsContract;

public class User {
    String uid,name, pimage,mail,mob,status;

    public User() {
    }

    public User(String uid, String name, String pimage, String mail, String mob, String status) {
        this.uid = uid;
        this.name = name;
        this.pimage = pimage;
        this.mail = mail;
        this.mob = mob;
        this.status=status;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
