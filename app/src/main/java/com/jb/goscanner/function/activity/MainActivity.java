package com.jb.goscanner.function.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import com.jb.goscanner.R;
import com.jb.goscanner.base.fragment.BaseFragmentActivity;
import com.jb.goscanner.base.fragment.BaseFragmentManager;
import com.jb.goscanner.function.bean.CardInfo;
import com.jb.goscanner.function.fragment.MainFragment;
import com.jb.goscanner.function.fragment.MainFragmentManager;
import com.jb.goscanner.network.CardInterface;
import com.jb.goscanner.util.log.Loger;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends BaseFragmentActivity {

    public static final String LOG_TAG = "MainActivity";
    private MainFragmentManager mFragmentManager;
    private TakePictureBroadcastReceiver mBroadReceiver;
    private RxPermissions mRxPermissions;

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
            Loger.w("ruijie", "get path = " + path);
            new CardInterface().getCardInfo(path, new CardInterface.IUploadListener() {
                @Override
                public void uploadStart() {
                    Loger.w("ruijie", "start upload");
                }

                @Override
                public void uploadFailure(String error) {
                    Loger.w("ruijie", "error : " + error);
                }

                @Override
                public void uploadSuccess(CardInfo.ResultBean bean) {

                }
            });
        }
    }
}
