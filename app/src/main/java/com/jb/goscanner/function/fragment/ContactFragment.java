package com.jb.goscanner.function.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jb.goscanner.R;
import com.jb.goscanner.base.GoApplication;
import com.jb.goscanner.base.fragment.BaseFragment;
import com.jb.goscanner.function.adapter.ContactAdapter;
import com.jb.goscanner.function.bean.ContactInfo;
import com.jb.goscanner.function.bean.DetailItem;
import com.jb.goscanner.function.sqlite.ContactDBUtils;
import com.jb.goscanner.function.widget.WaveSideBarView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by panruijie on 2017/9/7.
 * Email : zquprj@gmail.com
 */

public class ContactFragment extends BaseFragment {

    private View mRootView;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private WaveSideBarView mSideBarView;
    private ContactAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragement_contact, container, false);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.contact_recyclerview);
        mSideBarView = (WaveSideBarView) mRootView.findViewById(R.id.contact_wavesidebarview);
        mContext = getContext();

        initData();
        setListener();
        return mRootView;
    }

    private void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);

        for (int i = 0; i < 26; i++) {
            ContactInfo info = new ContactInfo();
            info.setId(i + "");
            info.setImgUrl("www.baidu.com");
            info.setName(String.valueOf((char) (i + 'a')));
            info.setRemark("Remark" + i);
            DetailItem item = new DetailItem();
            item.setId(i + "");
            item.setContactId(i + "");
            item.setGroup(DetailItem.GROUP_PHONE);
            item.setTag(DetailItem.GROUP_PHONE);
            item.setValue("18888888888");
            info.setPhone(item);
            ContactDBUtils.getInstance(GoApplication.getContext())
                    .insertContact(info);
        }

        Flowable.just(ContactDBUtils.getInstance(GoApplication.getContext()).queryExistContact())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    mAdapter = new ContactAdapter(mContext, list);
                    mRecyclerView.setAdapter(mAdapter);
                }, Throwable::printStackTrace);

    }

    private void setListener() {
        mSideBarView.setOnTouchLetterChangeListener(letter -> {
            int position = mAdapter.getFirstLetterPosition(letter);
            if (position != -1) {
                mRecyclerView.scrollToPosition(position);
            }
        });
    }
}