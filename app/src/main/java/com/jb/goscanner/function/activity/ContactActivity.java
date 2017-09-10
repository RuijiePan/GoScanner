package com.jb.goscanner.function.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jb.goscanner.R;
import com.jb.goscanner.base.fragment.BaseFragmentActivity;
import com.jb.goscanner.base.fragment.BaseFragmentManager;
import com.jb.goscanner.function.fragment.ContactFragment;
import com.jb.goscanner.function.fragment.ContactFragmentManager;

/**
 * Created by panruijie on 2017/9/7.
 * Email : zquprj@gmail.com
 */

public class ContactActivity extends BaseFragmentActivity {

    private ContactFragmentManager mFragmentManager;
    private ImageView mBackImg;
    private TextView mCenterTextView;
    private ImageView mSaveImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        mBackImg = (ImageView) findViewById(R.id.activity_top_back);
        mCenterTextView = (TextView) findViewById(R.id.activiy_top_text);
        mSaveImg = (ImageView) findViewById(R.id.top_save_btn);
    }

    private void initData() {
        mCenterTextView.setText(getString(R.string.contact));
        mSaveImg.setVisibility(View.INVISIBLE);
        startFragment(ContactFragment.class, null);
    }

    private void setListener() {
        mBackImg.setOnClickListener(l -> finish());
    }

    @Override
    protected BaseFragmentManager createBaseFragmentManager() {
        mFragmentManager = new ContactFragmentManager(this);
        return mFragmentManager;
    }
}
