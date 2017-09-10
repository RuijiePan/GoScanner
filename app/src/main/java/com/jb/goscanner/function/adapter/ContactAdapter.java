package com.jb.goscanner.function.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.jb.goscanner.R;
import com.jb.goscanner.base.adapter.BaseAdapter;
import com.jb.goscanner.base.adapter.BaseViewHolder;
import com.jb.goscanner.function.bean.ContactInfo;
import com.jb.goscanner.util.FirstLetterUtil;

import java.util.List;

/**
 * Created by panruijie on 2017/9/9.
 * Email : zquprj@gmail.com
 */

public class ContactAdapter extends BaseAdapter<ContactInfo, BaseViewHolder> {

    public ContactAdapter(Context context, List<ContactInfo> list) {
        super(context, R.layout.item_contact, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, ContactInfo item, int position) {
        holder.setText(R.id.item_contact_name, item.getName());
        if (item.getPhone().size() > 0 && item.getPhone().get(0) != null && item.getPhone().get(0).getValue() != null) {
            holder.setText(R.id.item_contact_phone, item.getPhone().get(0).getValue());
        }
    }

    public int getFirstLetterPosition(String string) {
        if (TextUtils.isEmpty(string) || string.equals("☆")) {
            string = "0";
        }

        int index = -1;
        for (ContactInfo info : mData) {
            index++;
            String name = info.getName();
            if (FirstLetterUtil.getFirst(name).equals(string)) {
                return index;
            }
        }
        return -1;
    }
}
