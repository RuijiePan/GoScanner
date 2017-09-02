package com.jb.goscanner.base.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * fragment基类, 所有fragment继承此类以便以后扩展共性功能.<br>
 *
 * @author laojiale
 */
public class BaseFragment extends Fragment {

    private BaseFragmentManager mBaseFragmentManager;

    public BaseFragment(BaseFragmentManager baseFragmentManager) {
        mBaseFragmentManager = baseFragmentManager;
    }

    /**
     * 使用此构造方法表示不需要BaseFragmentManager提供的管理功能.<br>
     *
     * @see #BaseFragment(BaseFragmentManager)
     */
    public BaseFragment() {
        this(new NoneBaseFragmentManager());
    }

    /**
     * 后退事件是否需被打断
     */
    private boolean mIsNeedToInterceptBackPressed;

    /**
     * 后退事件是否需被打断
     */
    boolean isInterceptBackPressed() {
        return mIsNeedToInterceptBackPressed;
    }

    /**
     * 跳转到指定的Fragment<br>
     *
     * @param cls    Fragment的类名
     * @param extras 附带的数据, 如无可为null
     */
    final public void startFragment(Class<? extends BaseFragment> cls,
                                    Bundle extras) {
        mBaseFragmentManager.startFragment(this, cls, extras);
    }

    /**
     * activity的同名方法中回调<br>
     */
    protected void onNewIntent(Intent intent) {

    }

    /**
     * 当这个fragment已创建, 使用方法{@link BaseFragment#startFragment(Class <? extends
     * BaseFragment>, Bundle )} 打开时, 其中的extras会通过些方法回调给当前fragment<br>
     *
     * @param args
     */
    protected void onNewArguments(Bundle args) {

    }

    /**
     * 从这个fragment的根view(the one returned by {@link #onCreateView}
     * )中通过id查找指定的view<br>
     * 注意这个方法必须在{@link #onCreateView}调用后才能使用, 并且{@link #onCreateView}不返加null<br>
     *
     * @param id
     * @return
     */
    public final View findViewById(int id) {
        if (getView() == null) {
            return null;
        }
        return getView().findViewById(id);
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs,
                          Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        checkInitBaseFragmentManager(activity);
        mBaseFragmentManager.onFragmentInflate(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        checkInitBaseFragmentManager(activity);
        mBaseFragmentManager.onFragmentAttach(this);
    }

    private void checkInitBaseFragmentManager(Activity activity) {
        // 我们只在BaseFragmentActivity使用BaseFragment
        final BaseFragmentActivity<?> bfAct = (BaseFragmentActivity<?>) activity;
        final BaseFragmentManager baseFragmentManager = bfAct
                .getBaseFragmentManager();
        if (baseFragmentManager != null
                && baseFragmentManager != mBaseFragmentManager) {
            mBaseFragmentManager = baseFragmentManager;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseFragmentManager.onFragmentCreate(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBaseFragmentManager.onFragmentViewCreated(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBaseFragmentManager.onFragmentActivityCreated(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mBaseFragmentManager.onFragmentStart(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBaseFragmentManager.onFragmentResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBaseFragmentManager.onFragmentPause(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mBaseFragmentManager.onFragmentStop(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBaseFragmentManager.onFragmentDestroy(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBaseFragmentManager.onFragmentDetach(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mBaseFragmentManager.onFragmentHiddenChanged(this);
    }

    /**
     * 分发来自{@code FragmentActivity}的后退事件<br>
     *
     * @return 是否处理了事件, true表示已处理,否则false<br>
     */
    protected boolean dispatchBackPressed() {
        mIsNeedToInterceptBackPressed = false;
        final FragmentManager childFm = getChildFragmentManager();
        if (null != childFm && null != childFm.getFragments()) {
            List<Fragment> fragments = new ArrayList<Fragment>(
                    childFm.getFragments());
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
                            mIsNeedToInterceptBackPressed = true;
                            return false;
                        }
                    }
                }
            }
        }
        if (mBaseFragmentManager.onInterceptBackPressed(this)) {
            mIsNeedToInterceptBackPressed = true;
            return false;
        }
        return onBackPressed();
    }

    /**
     * 来自{@code FragmentActivity}的后退事件<br>
     *
     * @return 是否处理了事件, true表示已处理,否则false<br>
     */
    protected boolean onBackPressed() {
        final FragmentManager childFm = getChildFragmentManager();
        if (null != childFm && isResumed() && isVisible()
                && childFm.getBackStackEntryCount() > 0) {
            return childFm.popBackStackImmediate();
        }
        return false;
    }

    /**
     * 后退, 退出当前界面<br>
     * 使用情景为此界面为用户当前交互的界面<br>
     */
    public void back() {
        if (isAdded() && isResumed()) {
            mBaseFragmentManager.backFragment(this);
        }
    }

    /**
     * 立即移除fragment结束.<br>
     * 注意：如果此fragment在添加时指定了后退处理
     * {@link FragmentTransaction#addToBackStack(String)},<br>
     * 这个back stack无法被一同移除.<br>
     */
    public void finish() {
        mBaseFragmentManager.finishFragment(this);
    }
}
