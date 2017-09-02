package com.jb.goscanner.base.fragment;

import android.os.Bundle;

/**
 * 一个空壳, 用来避免处理过多的null判断<br>
 *
 * @author laojiale
 */
public class NoneBaseFragmentManager extends BaseFragmentManager {

    public NoneBaseFragmentManager() {
        super(null);
    }

    @Override
    protected void startFragment(BaseFragment from, Class<? extends BaseFragment> cls,
                                 Bundle extras) {
    }

}
