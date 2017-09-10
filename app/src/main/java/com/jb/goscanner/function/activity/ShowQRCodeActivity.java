package com.jb.goscanner.function.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitmapUtils;
import com.jb.goscanner.R;
import com.jb.goscanner.base.activity.BaseActivity;
import com.jb.goscanner.function.bean.ContactInfo;

/**
 * Created by liuyue on 2017/9/9.
 */

public class ShowQRCodeActivity extends BaseActivity implements View.OnClickListener {
    public static final String EXTRA_CONTACTINFO = "extra_contact_info";
    private ImageView mQRCodeImageView;
    private LinearLayout mSaveBtn;
    private LinearLayout mShareBtn;
    private TextView mNameText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_show_qrcode);

        initView();
        ContactInfo info = (ContactInfo)getIntent().getSerializableExtra(EXTRA_CONTACTINFO);
        String contactString = ContactInfo.parseToString(info);
        try {
            Bitmap bitmap = BitmapUtils.create2DCode(contactString);//根据内容生成二维码
            mQRCodeImageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        mNameText.setText(info.getName());
    }

    private void initView() {
        mQRCodeImageView = (ImageView)findViewById(R.id.qrcode_imageview);
        mSaveBtn = (LinearLayout)findViewById(R.id.save_qrcode_btn);
        mShareBtn = (LinearLayout)findViewById(R.id.share_qrcode_btn);
        mNameText = (TextView)findViewById(R.id.name_show_text);

        ((ImageView)findViewById(R.id.activity_top_back)).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.save_qrcode_btn) {

        } else if (id == R.id.share_qrcode_btn) {

        } else if (id == R.id.activity_top_back) {
            ActivityCompat.finishAfterTransition(ShowQRCodeActivity.this);
        }
    }
}
