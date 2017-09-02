package com.jb.goscanner.function.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import com.google.zxing.activity.CaptureActivity;
import com.jb.goscanner.R;
import com.jb.goscanner.base.fragment.BaseFragmentActivity;
import com.jb.goscanner.base.fragment.BaseFragmentManager;
import com.jb.goscanner.function.create.CreateFragment;
import com.jb.goscanner.function.record.RecordFragment;
import com.jb.goscanner.util.log.Loger;

public class MainActivity extends BaseFragmentActivity {

    public static final String LOG_TAG = "MainActivity";
    private BottomNavigationView mBottomNavigationView;
    private MainFragmentManager mFragmentManager;

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
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_navigation);
    }

    private void initData() {

    }

    private void setListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_crate:
                    Loger.w(LOG_TAG, "点击了 crate");
                    startFragment(CreateFragment.class, null);
                    break;
                case R.id.menu_scan:
                    Intent intent = new Intent(this, CaptureActivity.class);
                    startActivity(intent);
                    break;
                case R.id.menu_record:
                    Loger.w(LOG_TAG, "点击了 record");
                    startFragment(RecordFragment.class, null);
                    break;
            }
            //false的话不会点击会被取消
            return true;
        });
    }
}
