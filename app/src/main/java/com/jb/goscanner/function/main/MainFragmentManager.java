package com.jb.goscanner.function.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jb.goscanner.base.fragment.BaseFragment;
import com.jb.goscanner.base.fragment.BaseFragmentManager;
import com.jb.goscanner.base.fragment.FragmentManagerHelper;
import com.jb.goscanner.function.generate.GenerateFragment;
import com.jb.goscanner.function.record.RecordFragment;

/**
 * Created by panruijie on 2017/9/2.
 * Email : zquprj@gmail.com
 */

public class MainFragmentManager extends BaseFragmentManager {

    private GenerateFragment mGenerateFragment;
    private RecordFragment mRecordFragment;

    public MainFragmentManager(FragmentActivity act) {
        super(act);
        mGenerateFragment = new GenerateFragment();
        mRecordFragment = new RecordFragment();
    }

    @Override
    protected void startFragment(BaseFragment from, Class<? extends BaseFragment> cls, Bundle extras) {
        if (GenerateFragment.class.equals(cls)) {
            if (mGenerateFragment.isAdded()) {
                FragmentManagerHelper.showFragment(this, mGenerateFragment, extras, false);
            } else {
                FragmentManagerHelper.startFragment(this, mGenerateFragment, extras, false);
            }
        } else if (RecordFragment.class.equals(cls)) {
            if (mRecordFragment.isAdded()) {
                FragmentManagerHelper.showFragment(this, mRecordFragment, extras, false);
            } else {
                FragmentManagerHelper.startFragment(this, mRecordFragment, extras, false);
            }
        }
    }
}
