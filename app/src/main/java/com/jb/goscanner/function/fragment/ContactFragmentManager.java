package com.jb.goscanner.function.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jb.goscanner.base.fragment.BaseFragment;
import com.jb.goscanner.base.fragment.BaseFragmentManager;
import com.jb.goscanner.base.fragment.FragmentManagerHelper;

/**
 * Created by panruijie on 2017/9/7.
 * Email : zquprj@gmail.com
 */

public class ContactFragmentManager extends BaseFragmentManager {

    private ContactFragment mContactFragment;

    public ContactFragmentManager(FragmentActivity act) {
        super(act);
        mContactFragment = new ContactFragment();
    }

    @Override
    protected void startFragment(BaseFragment from, Class<? extends BaseFragment> cls, Bundle extras) {
        if (cls.equals(ContactFragment.class)) {
            if (mContactFragment.isAdded()) {
                FragmentManagerHelper.showFragment(this, mContactFragment, extras, false);
            } else {
                FragmentManagerHelper.startFragment(this, mContactFragment, extras, false);
            }
        }
    }
}
