package com.jb.goscanner.function.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jb.goscanner.R;
import com.jb.goscanner.base.fragment.BaseFragmentActivity;
import com.jb.goscanner.base.fragment.BaseFragmentManager;
import com.jb.goscanner.function.adapter.CardPagerAdapter;
import com.jb.goscanner.function.bean.ContactBean;

import java.util.List;

/**
 * Created by panruijie on 2017/9/10.
 * Email : zquprj@gmail.com
 */

public class CardActivity extends BaseFragmentActivity {

    private List<ContactBean> mContactList;
    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private ImageView mBackImg;
    private TextView mCenterText;
    private ImageView mSaveImg;
    public static final String EXTRA_CONTACT_LIST = "extra_contact_list";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        initView();
        initData();
        setListener();
    }

    @Override
    protected BaseFragmentManager createBaseFragmentManager() {
        return null;
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.card_viewpager);
        mBackImg = (ImageView) findViewById(R.id.activity_top_back);
        mCenterText = (TextView) findViewById(R.id.activiy_top_text);
        mSaveImg = (ImageView) findViewById(R.id.top_save_btn);

        mSaveImg.setVisibility(View.INVISIBLE);
        mCenterText.setText(getString(R.string.card));
    }

    private void initData() {
        mContactList = (List<ContactBean>) getIntent().getSerializableExtra(EXTRA_CONTACT_LIST);
        mPagerAdapter = new CardPagerAdapter(getSupportFragmentManager(), mContactList);
        mViewPager.setAdapter(mPagerAdapter);
    }

    private void setListener() {
        mBackImg.setOnClickListener(v -> finish());
    }
}
