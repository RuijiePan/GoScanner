package com.jb.goscanner.function.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jb.goscanner.base.fragment.BaseFragment;
import com.jb.goscanner.base.fragment.BaseFragmentManager;
import com.jb.goscanner.base.fragment.FragmentManagerHelper;

/**
 * Created by panruijie on 2017/9/2.
 * Email : zquprj@gmail.com
 */

public class MainFragmentManager extends BaseFragmentManager {

    private MainFragment mMainFragment;

    public MainFragmentManager(FragmentActivity act) {
        super(act);
        mMainFragment = new MainFragment();
    }

    @Override
    protected void startFragment(BaseFragment from, Class<? extends BaseFragment> cls, Bundle extras) {
        if (MainFragment.class.equals(cls)) {
            if (mMainFragment.isAdded()) {
                FragmentManagerHelper.showFragment(this, mMainFragment, extras, false);
            } else {
                FragmentManagerHelper.startFragment(this, mMainFragment, extras, false);
            }
        }
    }
}
