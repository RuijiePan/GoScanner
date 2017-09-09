package com.jb.goscanner.function.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitmapUtils;
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
    private Button mQRCreateBtn;

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
        mQRCreateBtn = (Button)findViewById(R.id.qrcode_create_btn);

        if (mCurMode == MODE_EDITABLE) {
            mSaveBtn.setVisibility(View.VISIBLE);
        } else {
            mEditBtn.setVisibility(View.VISIBLE);
        }

        mBackBtn.setOnClickListener(this);
        mEditBtn.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);
        mQRCreateBtn.setOnClickListener(this);
        mDetailRecyclerView = (RecyclerView)findViewById(R.id.detail_recyclerview);
    }

    private void init() {

        if (mContactInfo == null) {
            mContactInfo = new ContactInfo();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDetailRecyclerView.setLayoutManager(layoutManager);
        mDetailItemAdapter = new DetailItemAdapter(this, mCurMode);
        mDetailRecyclerView.setAdapter(mDetailItemAdapter);
        List items = mContactInfo.parseToDetailItem(mContactInfo);
        mDetailItemAdapter.setData(items);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.top_save_btn) {
            saveContent();
            switchMode();
        } else if (id == R.id.top_edit_btn) {
            switchMode();
        } else if (id == R.id.activity_top_back) {
            ActivityCompat.finishAfterTransition(RecordDetailActivity.this);
        } else if (id == R.id.qrcode_create_btn) {
            try {
                saveContent();
                Bitmap bitmap = BitmapUtils.create2DCode(ContactInfo.toJson(mContactInfo));//根据内容生成二维码
                List<ContactInfo> retry = ContactDBUtils.getInstance(mContext).queryExistContact();
                Log.d(TAG, "onClick: query all" + retry.size());
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveContent() {
        List<DetailItem> items = mDetailItemAdapter.getData();
        /*for (DetailItem item : items) {

        }*/
        String contactId = mContactInfo.getId();
        if (contactId == null) {
            contactId = System.currentTimeMillis() + "";
        }
        Log.d(TAG, "saveContent: " + contactId);
        mContactInfo.setId(contactId);
        mContactInfo = ContactInfo.combineToContactInfo(items, contactId);
        mContactInfo.setId(contactId);
        ContactDBUtils.getInstance(mContext).insertContact(mContactInfo);
        for (int i = 1; i < items.size(); i++) {
            ContactDetailDBUtils.getInstance(mContext).insertDetail(items.get(i));
        }
    }
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
    }

    private void switchMode(int mode) {
//        mCurMode = mode;
//        mDetailItemAdapter.setCurMode(mode);
//        mDetailItemAdapter.setData(mContactInfo.parseToDetailItem(new ContactInfo()));
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
