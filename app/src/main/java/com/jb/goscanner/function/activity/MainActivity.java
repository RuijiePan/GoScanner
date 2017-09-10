package com.jb.goscanner.function.activity;

import android.os.Bundle;

import com.jb.goscanner.R;
import com.jb.goscanner.base.fragment.BaseFragmentActivity;
import com.jb.goscanner.base.fragment.BaseFragmentManager;
import com.jb.goscanner.function.fragment.MainFragmentManager;
import com.jb.goscanner.function.main.MainFragment;

public class MainActivity extends BaseFragmentActivity {

    public static final String LOG_TAG = "MainActivity";
    private MainFragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected BaseFragmentManager createBaseFragmentManager() {
        mFragmentManager = new MainFragmentManager(this);
        return mFragmentManager;
    }


    private void initView() {
        startFragment(MainFragment.class, null);
    }

}