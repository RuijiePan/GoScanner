package com.jb.goscanner.function.main;

import android.Manifest;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Toast;

import com.jb.goscanner.R;
import com.jb.goscanner.base.activity.BaseActivity;
import com.jb.goscanner.util.log.Loger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements QRCodeView.Delegate {

    private QRCodeView mQRCodeView;
    public static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQRCodeView = (QRCodeView) findViewById(R.id.zxingview);

        new RxPermissions(this)
                .request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            mQRCodeView.setDelegate(MainActivity.this);
                        } else {
                            Toast.makeText(MainActivity.this, "please open camera!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQRCodeView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQRCodeView.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        vibrate();
        //延迟1.5s开始识别
        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Loger.w(LOG_TAG, "open camera error");
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
