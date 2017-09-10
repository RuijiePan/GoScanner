package com.jb.goscanner.function.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.jb.goscanner.R;
import com.jb.goscanner.base.adapter.BaseAdapter;
import com.jb.goscanner.base.adapter.BaseViewHolder;
import com.jb.goscanner.function.bean.ContactBean;
import com.jb.goscanner.util.log.Loger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panruijie on 2017/9/9.
 * Email : zquprj@gmail.com
 */

public class ContactAdapter extends BaseAdapter<ContactBean, BaseViewHolder> {

    private OnItemClickListener mListener;

    public ContactAdapter(Context context, List<ContactBean> list) {
        super(context, R.layout.item_contact, list);
    }

    @Override
    protected void convert(BaseViewHolder holder, ContactBean item, int position) {
        holder.setText(R.id.item_contact_name, item.getName());
        if (item.getPhone().size() > 0 && item.getPhone().get(0) != null && item.getPhone().get(0).getValue() != null) {
            holder.setText(R.id.item_contact_phone, item.getPhone().get(0).getValue());
        }

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                Loger.w("ruijie", "here");
                mListener.onItemClick(getFistLetterSameList(item));
            }
        });
    }

    public int getFirstLetterPosition(String string) {
        if (TextUtils.isEmpty(string) || string.equals("☆")) {
            string = "0";
        }

        int index = -1;
        for (ContactBean info : mData) {
            index++;
            if (info.getFirstLetter().equals(string)) {
                return index;
            }
        }
        return -1;
    }

    public List<ContactBean> getFistLetterSameList(ContactBean contactBean) {
        List<ContactBean> list = new ArrayList<>();
        for (ContactBean bean : mData) {
            bean.setSelected(false);
        }

        for (ContactBean bean : mData) {
            if (contactBean.equals(bean)) {
                bean.setSelected(true);
            }
            if (contactBean.getFirstLetter().equals(bean.getFirstLetter())) {
                list.add(bean);
            }
        }
        return list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(List<ContactBean> list);
    }
}
