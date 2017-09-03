package com.jb.goscanner.function.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.activity.CaptureActivity;
import com.jb.goscanner.R;
import com.jb.goscanner.base.fragment.BaseFragmentActivity;
import com.jb.goscanner.base.fragment.BaseFragmentManager;
import com.jb.goscanner.function.generate.GenerateFragment;
import com.jb.goscanner.function.record.RecordFragment;
import com.jb.goscanner.util.log.Loger;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {

    public static final String LOG_TAG = "MainActivity";
    private MainFragmentManager mFragmentManager;
    private LinearLayout mGenerateView;
    private LinearLayout mCollectionView;
    private ImageView mScanImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setListener();
    }

    @Override
    protected BaseFragmentManager createBaseFragmentManager() {
        mFragmentManager = new MainFragmentManager(this);
        return mFragmentManager;
    }


    private void initView() {
        mGenerateView = (LinearLayout) findViewById(R.id.main_bottom_generate_layout);
        mCollectionView = (LinearLayout) findViewById(R.id.main_bottom_collection_layout);
        mScanImg = (ImageView) findViewById(R.id.main_bottom_scan_btn);
    }

    private void initData() {
        /*new CardInterface().getCardInfo("/storage/emulated/0/UCDownloads/128160980.png", new CardInterface.IUploadListener() {
            @Override
            public void uploadStart() {

            }

            @Override
            public void uploadFailure(String error) {
                Loger.w("ruijie", error);
            }

            @Override
            public void uploadSuccess(CardInfo.ResultBean cardInfo) {
                if (cardInfo != null) {
                    Loger.w("ruijie", cardInfo.toString());
                }
            }
        });*/
    }

    private void setListener() {
        mGenerateView.setOnClickListener(this);
        mCollectionView.setOnClickListener(this);
        mScanImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bottom_generate_layout:
                startFragment(GenerateFragment.class, null);
                Loger.w("ruijie", "click generate");
                break;
            case R.id.main_bottom_scan_btn:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivity(intent);
                break;
            case R.id.main_bottom_collection_layout:
                startFragment(RecordFragment.class, null);
                Loger.w("ruijie", "click record");
                break;
        }
    }
}
