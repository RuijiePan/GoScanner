package com.jb.goscanner.base.fragment;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;


/**
 * FragmentActivity基类, 所有FragmentActivity继承该基类实现, 以便共同扩展.<br>
 * 应与{@link BaseActivity} 同步扩展适用于一般Activity的功能.<br>
 *
 * @param <T>
 * @author laojiale
 * @see BaseActivity
 */
public abstract class BaseFragmentActivity<T extends BaseFragmentManager>
        extends FragmentActivity {

    private T mBaseFragmentManager;

    private boolean mIsSetText = false;
    //private GlobalEventMonitor mMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseFragmentManager = createBaseFragmentManager();
        //mMonitor = new GlobalEventMonitor(this);
        //mMonitor.register();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getBaseFragmentManager().onNewIntent(intent);
    }

    /**
     * 创建一个用于管理当前Activity中的BaseFragment的BaseFragmentManager.<br>
     * 在 {@link #onCreate(Bundle)} 中被调用.
     */
    protected abstract T createBaseFragmentManager();

    /**
     * 获取通过方法 {@link #createBaseFragmentManager()} 方法创建的BaseFragmentManager实例.<br>
     */
    public final T getBaseFragmentManager() {
        return mBaseFragmentManager;
    }

    /**
     * 跳转到指定的Fragment<br>
     *
     * @param cls    Fragment的类名
     * @param extras 附带的数据, 如无可为null
     */
    final public void startFragment(Class<? extends BaseFragment> cls,
                                    Bundle extras) {
        mBaseFragmentManager.startFragment(null, cls, extras);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsSetText) {
            mIsSetText = true;
            onLanguageChange();
        }
    }

    @Override
    protected void onDestroy() {
        //mMonitor.unregister();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 语言切换时候触发的方法
     * 注意：务必在oncreate时初始化所有的textview对象，该方法将会在第一次onResume()时调用一次
     */
    protected void onLanguageChange() {

    }
}
