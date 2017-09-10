package com.jb.goscanner.function.bean;

import com.jb.goscanner.util.FirstLetterUtil;

import java.io.Serializable;

/**
 * Created by panruijie on 2017/9/10.
 * Email : zquprj@gmail.com
 */

public class ContactBean extends ContactInfo implements Serializable {

    private String firstLetter;
    private boolean isSelected;

    public ContactBean(ContactInfo info) {
        setId(info.getId());
        setRemark(info.getRemark());
        setName(info.getName());
        setImgUrl(info.getImgUrl());
        setPhone(info.getPhone());
        setEmail(info.getEmail());
        setOther(info.getOther());
        setWechat(info.getWechat());
        firstLetter = FirstLetterUtil.getFirst(getName());
        isSelected = false;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "firstLetter='" + firstLetter + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
