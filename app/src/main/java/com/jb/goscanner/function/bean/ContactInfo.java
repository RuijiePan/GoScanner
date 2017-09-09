package com.jb.goscanner.function.bean;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyue on 2017/9/3.
 */

public class ContactInfo implements Serializable {
    private static final String TAG = "ContactInfo";
    private String id;
    private String name;
    private String remark;
    private String imgUrl;
    private ArrayList<DetailItem> mPhone;
    private ArrayList<DetailItem> mEmail;
    private ArrayList<DetailItem> mWechat;
    private ArrayList<DetailItem> mOther;



    public ContactInfo() {
        mPhone = new ArrayList<>();
        mEmail = new ArrayList<>();
        mWechat = new ArrayList<>();
        mOther = new ArrayList<>();
    }


    public ContactInfo(String id, String name, String remark, String imgUrl, ArrayList<DetailItem> phone, ArrayList<DetailItem> email, ArrayList<DetailItem> wechat, ArrayList<DetailItem> other) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.imgUrl = imgUrl;
        mPhone = phone;
        mEmail = email;
        mWechat = wechat;
        mOther = other;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ArrayList<DetailItem> getPhone() {
        return mPhone;
    }

    public void setPhone(ArrayList<DetailItem> phone) {
        mPhone = phone;
    }

    public ArrayList<DetailItem> getEmail() {
        return mEmail;
    }

    public void setEmail(ArrayList<DetailItem> email) {
        mEmail = email;
    }

    public ArrayList<DetailItem> getWechat() {
        return mWechat;
    }

    public void setWechat(ArrayList<DetailItem> wechat) {
        mWechat = wechat;
    }

    public ArrayList<DetailItem> getOther() {
        return mOther;
    }

    public void setOther(ArrayList<DetailItem> other) {
        mOther = other;
    }

    public void setPhone(DetailItem detailItem) {
        mPhone.add(detailItem);
    }

    public void setEmail(DetailItem detailItem) {
        mEmail.add(detailItem);
    }

    public void setWechat(DetailItem detailItem) {
        mWechat.add(detailItem);
    }

    public void setOther(DetailItem detailItem) {
        mOther.add(detailItem);
    }

    // 将ContactInfo解析成ArrayList<DetailItem>用于RecyclerView展示
    public static ArrayList<DetailItem> parseToDetailItem(ContactInfo info) { // 带有group
        ArrayList<DetailItem> items = new ArrayList<>();

        // 注意头部内容的放置
        DetailItem head = new DetailItem();
        head.setId(info.getId());
        head.setGroup(info.getRemark());
        head.setTag(info.getName());
        head.setValue(info.getImgUrl());
        head.setContactId(DetailItem.GROUP_HEAD); // 这是标志
        items.add(head);

        items.add(new DetailItem(DetailItem.GROUP_PHONE)); // 这是分组（没有实际内容）
        items.addAll(info.getPhone());  // 实际详情内容
        items.add(new DetailItem(DetailItem.GROUP_EMAIL));
        items.addAll(info.getEmail());
        items.add(new DetailItem(DetailItem.GROUP_WECHAT));
        items.addAll(info.getWechat());
        items.add(new DetailItem(DetailItem.GROUP_OTHER));
        items.addAll(info.getOther());
        return items;
    }

    // 用于将RecyclerView的内容组装成ContactInfo (items包含group类型，组装成的info是对象没有group类型）
    public static ContactInfo combineToContactInfo(List<DetailItem> items, String contactId) {
        ContactInfo info = new ContactInfo();
        if (items.get(0).getContactId().equals(DetailItem.GROUP_HEAD)) {
            DetailItem head = items.get(0);
            info.setId(contactId);
            info.setRemark(head.getGroup());
            info.setImgUrl(head.getValue());
            info.setName(head.getTag());
        }
        for (DetailItem item : items) {
            if (item.getValue() == null || item.getGroup().equals(DetailItem.GROUP_HEAD)) {
                continue;
            }

            item.setContactId(contactId);
            if (item.getGroup().equals(DetailItem.GROUP_EMAIL)) {
                info.setEmail(item);
            }
            if (item.getGroup().equals(DetailItem.GROUP_WECHAT)) {
                info.setWechat(item);
            }
            if (item.getGroup().equals(DetailItem.GROUP_OTHER)) {
                info.setOther(item);
            }
            if (item.getGroup().equals(DetailItem.GROUP_PHONE)) {
                info.setPhone(item);
            }
        }
        return info;
    }

    public static String toJson(ContactInfo contact) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            JSONObject jsonContact = new JSONObject();
            jsonContact.put("name", contact.getName());
            jsonContact.put("remark", contact.getRemark());
            jsonContact.put("imgUrl", contact.getImgUrl());

            JSONObject phone = new JSONObject();
            for (int i = 0; i < contact.getPhone().size(); i++) {
                phone.put(contact.getPhone().get(i).getTag(), contact.getPhone().get(i).getValue());
            }
            jsonContact.put("phone", phone);

            JSONObject email = new JSONObject();
            for (int i = 0; i < contact.getEmail().size(); i++) {
                phone.put(contact.getEmail().get(i).getTag(), contact.getEmail().get(i).getValue());
            }
            jsonContact.put("email", email);

            JSONObject wechat = new JSONObject();
            for (int i = 0; i < contact.getWechat().size(); i++) {
                phone.put(contact.getWechat().get(i).getTag(), contact.getWechat().get(i).getValue());
            }
            jsonContact.put("wechat", wechat);

            JSONObject other = new JSONObject();
            for (int i = 0; i < contact.getOther().size(); i++) {
                phone.put(contact.getOther().get(i).getTag(), contact.getOther().get(i).getValue());
            }
            jsonContact.put("other", other);
            jsonArray.put(jsonContact);
            jsonObject.put("contact", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "toJson: " + jsonObject.toString());
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "ContactInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", mPhone=" + mPhone +
                ", mEmail=" + mEmail +
                ", mWechat=" + mWechat +
                ", mOther=" + mOther +
                '}';
    }
}


