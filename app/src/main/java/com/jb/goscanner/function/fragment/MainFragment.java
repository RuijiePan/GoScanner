package com.jb.goscanner.function.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.activity.CaptureActivity;
import com.jb.goscanner.R;
import com.jb.goscanner.base.fragment.BaseFragment;
import com.jb.goscanner.function.activity.ContactActivity;
import com.jb.goscanner.function.activity.RecordDetailActivity;
import com.jb.goscanner.function.bean.ContactInfo;
import com.jb.goscanner.function.widget.BottomTab;

/**
 * Created by panruijie on 2017/9/2.
 * Email : zquprj@gmail.com
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private View mRootView;
    private BottomTab mBottomTab;
    private ImageView mPersonImg;
    private View mBusinessCard;
    private View mWebPage;
    private View mTextContent;
    private View mOtherView;
    private int mIndex;
    public static final int GENERATE = 0;
    public static final int COLLECTION = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.main_fragment, container, false);
        mContext = getContext();
        initView();
        initData();
        setListener();
        return mRootView;
    }

    private void initView() {
        mBottomTab = (BottomTab) mRootView.findViewById(R.id.main_bottom_tab);
        mBusinessCard = mRootView.findViewById(R.id.main_business_card);
        mWebPage = mRootView.findViewById(R.id.main_home_web);
        mTextContent = mRootView.findViewById(R.id.main_text_content);
        mOtherView = mRootView.findViewById(R.id.main_other);
        mPersonImg = (ImageView) mRootView.findViewById(R.id.main_top_person_icon);
    }

    private void initData() {
        mIndex = GENERATE;
    }

    private void setListener() {
        mBusinessCard.setOnClickListener(this);
        mWebPage.setOnClickListener(this);
        mTextContent.setOnClickListener(this);
        mOtherView.setOnClickListener(this);
        mPersonImg.setOnClickListener(this);
        mBottomTab.setTabClickListener(new BottomTab.OnTabClickListener() {
            @Override
            public void onClickGenerate() {
                mIndex = GENERATE;
            }

            @Override
            public void onClickScan() {
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickCollection() {
                mIndex = COLLECTION;
                //Loger.w("ruijie", "click collection");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_business_card:
                if (mIndex == GENERATE) {
                    RecordDetailActivity.startRecordDetailActivity(mContext, new ContactInfo(), RecordDetailActivity.MODE_EDITABLE);
                } else {
                    Intent intent = new Intent(mContext, ContactActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.main_home_web:
                goTo();
                break;
            case R.id.main_text_content:
                goTo();
                break;
            case R.id.main_other:
                goTo();
                break;
            case R.id.main_top_person_icon:
                goTo();
                break;
        }
    }

    public void goTo() {
        if (mIndex == GENERATE) {

        } else {
            Intent intent = new Intent(mContext, ContactActivity.class);
            startActivity(intent);
        }
    }
}
