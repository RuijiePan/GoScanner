package com.jb.goscanner.function.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jb.goscanner.R;
import com.jb.goscanner.base.activity.BaseActivity;
import com.jb.goscanner.function.adapter.DetailItemAdapter;
import com.jb.goscanner.function.bean.ContactInfo;

/**
 * Created by liuyue on 2017/9/3.
 */

public class RecordDetailActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mBackBtn;
    private ImageView mEditBtn;
    private ImageView mSaveBtn;
    private ImageView mQRCodeSaveBtn;
    private ImageView mQRCodeShareBtn;

    private RecyclerView mDetailRecyclerView;
    private DetailItemAdapter mDetailItemAdapter;

    private int mCurMode;
    public static int MODE_EDITABLE = 0;
    public static int MODE_UNEDITABLE = 1;

    private ContactInfo mContactInfo;

    public static final String EXTRA_CONTACT_INFO = "extra_contact_info";
    public static final String EXTRA_MODE = "extra_mode";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_record_detail);

        initView();
        init();
    }

    private void initView() {
        mBackBtn = (ImageView)findViewById(R.id.activity_top_back);
        mEditBtn = (ImageView)findViewById(R.id.top_edit_btn);
        mSaveBtn = (ImageView)findViewById(R.id.top_save_btn);
        mQRCodeSaveBtn = (ImageView)findViewById(R.id.qrcode_save_btn);
        mQRCodeShareBtn = (ImageView)findViewById(R.id.qrcode_share_btn);

        mBackBtn.setOnClickListener(this);
        mEditBtn.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);
        mQRCodeSaveBtn.setOnClickListener(this);
        mQRCodeShareBtn.setOnClickListener(this);

        mDetailRecyclerView = (RecyclerView)findViewById(R.id.detail_recyclerview);
    }

    private void init() {
        Intent intent = getIntent();
        mContactInfo = (ContactInfo)intent.getSerializableExtra(EXTRA_CONTACT_INFO);
        mCurMode = intent.getIntExtra(EXTRA_MODE, MODE_UNEDITABLE);
        if (mContactInfo == null) {
            mContactInfo = new ContactInfo();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDetailRecyclerView.setLayoutManager(layoutManager);
        mDetailItemAdapter = new DetailItemAdapter(this, mCurMode);
        mDetailRecyclerView.setAdapter(mDetailItemAdapter);
        mDetailItemAdapter.setData(mContactInfo.parseToDetailItems());
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * @param context
     * @param info
     * @param mode MODE_EDITABLE/MODE_UNEDITABLE
     */
    public static void startRecordDetailActivity(Context context, ContactInfo info, int mode) {
        Intent intent  = new Intent(context, RecordDetailActivity.class);
        intent.putExtra(EXTRA_CONTACT_INFO, info);
        intent.putExtra(EXTRA_MODE, mode);
        context.startActivity(intent);
    }
}
