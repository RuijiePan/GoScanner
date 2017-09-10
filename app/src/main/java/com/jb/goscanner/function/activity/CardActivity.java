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
import com.jb.goscanner.function.adapter.IndexAdapter;
import com.jb.goscanner.function.bean.ContactBean;
import com.jb.goscanner.util.DensityUtil;

import java.util.List;

/**
 * Created by panruijie on 2017/9/10.
 * Email : zquprj@gmail.com
 */

public class CardActivity extends BaseFragmentActivity {

    private List<ContactBean> mContactList;
    private FragmentPagerAdapter mPagerAdapter;
    private IndexAdapter mIndexAdapter;
    private ViewPager mViewPager;
    private ViewPager mIndexViewPager;
    private ImageView mBackImg;
    private TextView mCenterText;
    private ImageView mSaveImg;
    private TextView mPersonName;
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
        mIndexViewPager = (ViewPager) findViewById(R.id.card_viewpager_index);
        mBackImg = (ImageView) findViewById(R.id.activity_top_back);
        mCenterText = (TextView) findViewById(R.id.activiy_top_text);
        mSaveImg = (ImageView) findViewById(R.id.top_save_btn);
        mPersonName = (TextView) findViewById(R.id.card_my_name);

        mSaveImg.setVisibility(View.INVISIBLE);
        mCenterText.setText(getString(R.string.card));
        mPersonName.setText("666");
    }

    private void initData() {
        mContactList = (List<ContactBean>) getIntent().getSerializableExtra(EXTRA_CONTACT_LIST);
        mPagerAdapter = new CardPagerAdapter(getSupportFragmentManager(), mContactList);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setPageMargin(DensityUtil.dip2px(this, 12));

        int index = 0;
        for (ContactBean bean : mContactList) {
            if (bean.isSelected()) {
                break;
            }
            index++;
        }
        mViewPager.setCurrentItem(index);

        mIndexAdapter = new IndexAdapter(this, mContactList.size(), index);
        mIndexViewPager.setAdapter(mIndexAdapter);
        mIndexViewPager.setPageMargin(DensityUtil.dip2px(this, 10));
        mIndexViewPager.setOffscreenPageLimit(mContactList.size() - 1);
    }

    private void setListener() {
        mBackImg.setOnClickListener(v -> finish());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndexAdapter.setSelectedInex(position);
                mIndexViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mIndexAdapter.setIndexClickListener(index -> mViewPager.setCurrentItem(index));
    }
}
