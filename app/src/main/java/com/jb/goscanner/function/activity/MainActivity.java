package com.jb.goscanner.function.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.jb.goscanner.R;
import com.jb.goscanner.base.fragment.BaseFragmentActivity;
import com.jb.goscanner.base.fragment.BaseFragmentManager;
import com.jb.goscanner.function.bean.CardInfo;
import com.jb.goscanner.function.bean.ContactInfo;
import com.jb.goscanner.function.fragment.MainFragment;
import com.jb.goscanner.function.fragment.MainFragmentManager;
import com.jb.goscanner.network.CardInterface;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends BaseFragmentActivity {

    public static final String LOG_TAG = "MainActivity";
    private MainFragmentManager mFragmentManager;
    private TakePictureBroadcastReceiver mBroadReceiver;
    private RxPermissions mRxPermissions;
    private ProgressDialog mProgressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    @Override
    protected BaseFragmentManager createBaseFragmentManager() {
        mFragmentManager = new MainFragmentManager(this);
        return mFragmentManager;
    }


    private void initView() {
        startFragment(MainFragment.class, null);

        mRxPermissions = new RxPermissions(this);
        mRxPermissions
                .request(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        // All requested permissions are granted
                    } else {
                        Toast.makeText(MainActivity.this, "请手动赋予权限，否则无法正常使用", Toast.LENGTH_LONG).show();
                        // At least one permission is denied
                    }
                });
    }

    private void setListener() {
        mBroadReceiver = new TakePictureBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("take_picture");
        registerReceiver(mBroadReceiver, intentFilter);
    }

    public class TakePictureBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String path = intent.getStringExtra("path");
            new CardInterface().getCardInfo(path, new CardInterface.IUploadListener() {
                @Override
                public void uploadStart() {
                    Intent takePictureIntent = new Intent();
                    takePictureIntent.setAction("getInfoFailure");
                    sendBroadcast(takePictureIntent);
                }

                @Override
                public void uploadFailure(String error) {
                    Intent takePictureIntent = new Intent();
                    takePictureIntent.setAction("getInfoFailure");
                    sendBroadcast(takePictureIntent);
                    Toast.makeText(MainActivity.this, "识别名片失败", Toast.LENGTH_LONG).show();
                }

                @Override
                public void uploadSuccess(CardInfo.ResultBean bean) {
                    Intent takePictureIntent = new Intent();
                    takePictureIntent.setAction("getInfoFailure");
                    sendBroadcast(takePictureIntent);
                    ContactInfo info = new ContactInfo();
                    if (bean.getName() != null && bean.getName().size() > 0 && !TextUtils.isEmpty(bean.getName().get(0))) {
                        info.setName(bean.getName().get(0));
                    }
                    if (bean.getTel() != null && bean.getTel().size() > 0 && !TextUtils.isEmpty(bean.getTel().get(0))) {
                        info.setName(bean.getTel().get(0));
                    }
                    Intent intent = new Intent(MainActivity.this, RecordDetailActivity.class);
                    intent.putExtra(RecordDetailActivity.EXTRA_CONTACT_INFO, info);
                    startActivity(intent);
                }
            });
        }
    }

}
