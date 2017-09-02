package com.jb.goscanner.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseFragment管理者<br>
 *
 * @author laojiale
 */
public abstract class BaseFragmentManager implements FragmentStateListener {

    protected FragmentActivity mActivity;

    public BaseFragmentManager(FragmentActivity act) {
        mActivity = act;
    }

    protected FragmentManager getSupportFragmentManager() {
        return mActivity.getSupportFragmentManager();
    }

    @Override
    public void onFragmentInflate(Fragment f) {

    }

    @Override
    public void onFragmentAttach(Fragment f) {

    }

    @Override
    public void onFragmentCreate(Fragment f) {

    }

    @Override
    public void onFragmentViewCreated(Fragment f) {

    }

    @Override
    public void onFragmentActivityCreated(Fragment f) {

    }

    @Override
    public void onFragmentStart(Fragment f) {

    }

    @Override
    public void onFragmentResume(Fragment f) {

    }

    @Override
    public void onFragmentPause(Fragment f) {

    }

    @Override
    public void onFragmentStop(Fragment f) {

    }

    @Override
    public void onFragmentDestroy(Fragment f) {

    }

    @Override
    public void onFragmentDetach(Fragment f) {

    }

    @Override
    public void onFragmentHiddenChanged(Fragment f) {

    }

    /**
     * 在所在的activity的同名方法中调用<br>
     */
    public void onNewIntent(Intent intent) {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        final List<Fragment> fragments = fragmentManager.getFragments();
        if (null != fragments) {
            final int size = fragments.size();
            for (int i = size - 1; i >= 0; i--) {
                Fragment fragment = fragments.get(i);
                if (fragment instanceof BaseFragment) {
                    ((BaseFragment) fragment).onNewIntent(intent);
                }
            }
        }
    }

    /**
     * 对于一个已活动的fragment, 使用此方法将args传给其方法
     * {@link BaseFragment#onNewArguments(Bundle)}
     *
     * @param fragment
     * @param args
     * @see #startFragment(BaseFragment, Class, Bundle)
     */
    final protected void onNewArguments(BaseFragment fragment, Bundle args) {
        if (null == args) {
            return;
        }
        fragment.onNewArguments(args);
    }

    /**
     * 分发来自{@code FragmentActivity}的后退事件<br>
     *
     * @return 是否处理了事件, true表示已处理,否则false<br>
     */
    public boolean dispatchBackPressed() {
        final FragmentManager fm = getSupportFragmentManager();
        if (null != fm && null != fm.getFragments()) {
            List<Fragment> fragments = new ArrayList<Fragment>(
                    fm.getFragments());
            if (fragments.size() > 0) {
                final int size = fragments.size();
                for (int i = size - 1; i >= 0; i--) {
                    Fragment fragment = fragments.get(i); // may be null!
                    if (null != fragment && fragment.isVisible()
                            && fragment instanceof BaseFragment) {
                        if (((BaseFragment) fragment).dispatchBackPressed()) {
                            return true;
                        }
                        if (((BaseFragment) fragment).isInterceptBackPressed()) {
                            break;
                        }
                    }
                }
            }
        }
        return onBackPressed();
    }

    /**
     * 默认情况下对于来自{@code FragmentActivity}的后退事件先分派给Fragment处理,<br>
     *
     * @param f
     * @return 是否打断, 若为ture, 当前fragment的{@link BaseFragment#onBackPressed()}
     * 不会被调用,<br>
     * 并且分发流程结束,方法{ #onBackPressed()}会被调用<br>
     */
    protected boolean onInterceptBackPressed(BaseFragment f) {
        return false;
    }

    /**
     * 来自{@code FragmentActivity}的后退事件<br>
     *
     * @return 是否处理了事件, true表示已处理,否则false<br>
     */
    protected boolean onBackPressed() {
        return false;
    }

    /**
     * fragment后退处理
     *
     * @param fragment
     */
    protected void backFragment(BaseFragment fragment) {
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            mActivity.finish();
        }
    }

    /**
     * 处理结束fragment.<br>
     *
     * @param fragment
     */
    protected void finishFragment(BaseFragment fragment) {
        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 跳转到指定的Fragment<br>
     *
     * @param from   当前fragment
     * @param cls    Fragment的类名
     * @param extras 附带的数据, 如无可为null
     */
    protected abstract void startFragment(BaseFragment from,
                                          Class<? extends BaseFragment> cls, Bundle extras);


}
