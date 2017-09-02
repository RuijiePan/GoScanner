package com.jb.goscanner.base.activity;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/**
 * Activity基类，所有Activity继承该基类实现，方便以后对Activity的方法修改.<br>
 */
public class BaseActivity extends AppCompatActivity {

    protected boolean mDestroyed;
    private boolean mIsVisible;
    private boolean mIsSetText = false;
    private View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mContentView = view;
    }

    @Override
    public void setContentView(int layoutResID) {
        View v = LayoutInflater.from(this).inflate(layoutResID, null);
        setContentView(v);
        mContentView = v;
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        mContentView = view;

    }

    public boolean isVisible() {
        return mIsVisible;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mIsVisible = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsSetText) {
            mIsSetText = true;
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIsVisible = false;
    }

    @Override
    public void onBackPressed() {
        try {
            super.onBackPressed();
        } catch (Exception e) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        mDestroyed = true;
        super.onDestroy();
    }

}
