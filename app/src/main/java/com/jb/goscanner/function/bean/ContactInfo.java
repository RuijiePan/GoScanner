package com.jb.goscanner.function.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by liuyue on 2017/9/3.
 */

public class ContactInfo implements Serializable {
    private String name;
    private String phoneNum;
    private String skype;
    private String facebook;

    public ContactInfo() {
    }

    public ContactInfo(String name, String phoneNum, String skype, String facebook) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.skype = skype;
        this.facebook = facebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "name='" + name + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", skype='" + skype + '\'' +
                ", facebook='" + facebook + '\'' +
                '}';
    }

    public ArrayList<DetailItem> parseToDetailItems() {
        ArrayList<DetailItem> itemList = new ArrayList();
        itemList.add(new DetailItem("Name", name));
        itemList.add(new DetailItem("PhoneNum", phoneNum));
        itemList.add(new DetailItem("Wechat", skype));
        itemList.add(new DetailItem("QQ", facebook));

        return itemList;
    }
}
