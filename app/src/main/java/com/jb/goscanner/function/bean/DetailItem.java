package com.jb.goscanner.function.bean;

import java.io.Serializable;

/**
 * Created by liuyue on 2017/9/3.
 */

public class DetailItem implements Serializable{
    private String tag;
    private String value;

    public DetailItem() {
    }

    public DetailItem(String tag, String value) {
        this.tag = tag;
        this.value = value;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DetailItem{" +
                "tag='" + tag + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
