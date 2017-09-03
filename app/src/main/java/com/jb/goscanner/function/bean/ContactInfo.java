package com.jb.goscanner.function.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by liuyue on 2017/9/3.
 */

public class ContactInfo implements Serializable {
    private String name;
    private String phoneNum;
    private String wechat;
    private String qq;

    public ContactInfo() {
    }

    public ContactInfo(String name, String phoneNum, String wechat, String qq) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.wechat = wechat;
        this.qq = qq;
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

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "name='" + name + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", wechat='" + wechat + '\'' +
                ", qq='" + qq + '\'' +
                '}';
    }

    public ArrayList<DetailItem> parseToDetailItems() {
        ArrayList<DetailItem> itemList = new ArrayList();
        itemList.add(new DetailItem("Name", name));
        itemList.add(new DetailItem("PhoneNum", phoneNum));
        itemList.add(new DetailItem("Wechat", wechat));
        itemList.add(new DetailItem("QQ", qq));

        return itemList;
    }
}
