package com.jb.goscanner.function.bean;

import java.io.Serializable;

/**
 * Created by liuyue on 2017/9/3.
 */

public class DetailItem implements Serializable {
    private String id; // 主键
    private String contactId; // 外键
    private String tag;
    private String value; // value为null只有一种情况：记录的内容是分组
    private String group; // 分组：phone／email/wechat/...

    public static final String GROUP_PHONE = "PHONE";
    public static final String GROUP_EMAIL = "EMAIL";
    public static final String GROUP_WECHAT = "WECHATE";
    public static final String GROUP_OTHER = "OTHER";
    public static final String GROUP_HEAD = "HEAD";
    public DetailItem() {
    }

    public DetailItem(String group) {
        this.group = group;
    }

    public DetailItem(String id, String tag, String value, String group, String contactId) {
        this.id = id;
        this.tag = tag;
        this.value = value;
        this.group = group;
        this.contactId = contactId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @Override
    public String toString() {
        return "DetailItem{" +
                "tag='" + tag + '\'' +
                ", value='" + value + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
