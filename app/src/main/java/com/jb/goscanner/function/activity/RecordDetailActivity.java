package com.jb.goscanner.function.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jb.goscanner.R;
import com.jb.goscanner.base.activity.BaseActivity;
import com.jb.goscanner.function.adapter.DetailItemAdapter;
import com.jb.goscanner.function.bean.ContactInfo;
import com.jb.goscanner.function.bean.DetailItem;
import com.jb.goscanner.function.sqlite.ContactDBUtils;
import com.jb.goscanner.function.sqlite.ContactDetailDBUtils;

import java.util.List;

/**
 * Created by liuyue on 2017/9/3.
 */

public class RecordDetailActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "RecordDetailActivity";
    private Context mContext;

    private ImageView mBackBtn;
    private ImageView mEditBtn;
    private ImageView mSaveBtn;

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

        mContext = this;
        Intent intent = getIntent();
        mContactInfo = (ContactInfo)intent.getSerializableExtra(EXTRA_CONTACT_INFO);
        mCurMode = intent.getIntExtra(EXTRA_MODE, MODE_EDITABLE);

        initView();
        init();
    }

    private void initView() {
        mBackBtn = (ImageView)findViewById(R.id.activity_top_back);
        mEditBtn = (ImageView)findViewById(R.id.top_edit_btn);
        mSaveBtn = (ImageView)findViewById(R.id.top_save_btn);

        if (mCurMode == MODE_EDITABLE) {
            mSaveBtn.setVisibility(View.VISIBLE);
        } else {
            mEditBtn.setVisibility(View.VISIBLE);
        }

        mBackBtn.setOnClickListener(this);
        mEditBtn.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);
        mDetailRecyclerView = (RecyclerView)findViewById(R.id.detail_recyclerview);
    }

    private void init() {

        if (mContactInfo == null) {
            mContactInfo = new ContactInfo();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDetailRecyclerView.setLayoutManager(layoutManager);
        mDetailItemAdapter = new DetailItemAdapter(this, mCurMode, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode();
                Intent intent = new Intent(mContext, ShowQRCodeActivity.class);
                intent.putExtra(ShowQRCodeActivity.EXTRA_CONTACTINFO, mContactInfo);
                startActivity(intent);
            }
        });
        mDetailRecyclerView.setAdapter(mDetailItemAdapter);
        List items = mContactInfo.parseToDetailItem(mContactInfo);
        mDetailItemAdapter.setData(items);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.top_save_btn) {
            CustomDialog dialog = new CustomDialog(mContext, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchMode();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchMode();
                }
            });
            dialog.show();
        } else if (id == R.id.top_edit_btn) {
            switchMode();
        } else if (id == R.id.activity_top_back) {
            ActivityCompat.finishAfterTransition(RecordDetailActivity.this);
        } else if (id == R.id.qrcode_create_btn) {
            Intent intent = new Intent(mContext, ShowQRCodeActivity.class);
            startActivity(intent);
        }
    }

    private void saveContent() {
        List<DetailItem> items = mDetailItemAdapter.getData();
        String contactId = mContactInfo.getId();
        if (contactId == null) {
            contactId = System.currentTimeMillis() + "";
        }
        mContactInfo.setId(contactId);
        mContactInfo = ContactInfo.combineToContactInfo(items, contactId);
        mContactInfo.setId(contactId);
        ContactDBUtils.getInstance(mContext).insertContact(mContactInfo);
        for (int i = 1; i < items.size(); i++) {
            ContactDetailDBUtils.getInstance(mContext).insertDetail(items.get(i));
        }
    }

    // 改变编辑状态同时保存联系人
    private void switchMode() {
        if (mCurMode == MODE_EDITABLE) {
            switchMode(MODE_UNEDITABLE);
            mSaveBtn.setVisibility(View.GONE);
            mEditBtn.setVisibility(View.VISIBLE);
        } else {
            switchMode(MODE_EDITABLE);
            mSaveBtn.setVisibility(View.VISIBLE);
            mEditBtn.setVisibility(View.GONE);
        }
        saveContent();
    }

    private void switchMode(int mode) {
        mCurMode = mode;
        mDetailItemAdapter.setCurMode(mode);
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
