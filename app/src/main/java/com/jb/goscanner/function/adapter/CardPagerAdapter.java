package com.jb.goscanner.function.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.jb.goscanner.function.bean.ContactBean;
import com.jb.goscanner.function.fragment.CardFragment;

import java.util.List;

/**
 * Created by panruijie on 2017/9/10.
 * Email : zquprj@gmail.com
 */

public class CardPagerAdapter extends FragmentPagerAdapter {

    private List<ContactBean> mBeanList;

    public CardPagerAdapter(FragmentManager fm, List<ContactBean> list) {
        super(fm);
        mBeanList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return CardFragment.newInstance(mBeanList.get(position));
    }

    @Override
    public int getCount() {
        return mBeanList != null ? mBeanList.size() : 0;
    }

}
