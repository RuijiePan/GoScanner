package com.jb.goscanner.base.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.jb.goscanner.R;


/**
 * Fragment管理器辅助类
 *
 * @author chenbenbin
 */
public class FragmentManagerHelper {

    /**
     * 作为主Fragment加入到Activity中
     *
     * @param manager  Fragment管理器
     * @param activity 容器Activity
     * @param fragment 主Fragment
     */
    public static void addMainFragment(BaseFragmentManager manager, BaseFragmentActivity activity, BaseFragment fragment) {
        addMainFragment(manager, activity, fragment, null);
    }

    /**
     * 作为主Fragment加入到Activity中
     *
     * @param manager  Fragment管理器
     * @param activity 容器Activity
     * @param fragment 主Fragment
     * @param extras   附带参数
     */
    public static void addMainFragment(BaseFragmentManager manager, BaseFragmentActivity activity, BaseFragment fragment, Bundle extras) {
        activity.setContentView(R.layout.common_fragment_activity_layout);
        FragmentTransaction transaction = manager.getSupportFragmentManager()
                .beginTransaction();
        if (extras != null) {
            fragment.setArguments(extras);
        }
        transaction.add(R.id.common_activity_fragment_container, fragment,
                fragment.getClass().getName());
        transaction.commitAllowingStateLoss();
    }

    /**
     * 打开新的Fragment界面：默认加入到后台任务栈中
     *
     * @param manager  Fragment管理器
     * @param fragment 打开的Fragment
     * @param extras   传递的参数值
     */
    public static void startFragment(BaseFragmentManager manager, BaseFragment fragment, Bundle extras) {
        startFragment(manager, fragment, extras, true);
    }

    /**
     * 打开新的Fragment界面
     *
     * @param manager          Fragment管理器
     * @param fragment         打开的Fragment
     * @param extras           传递的参数值
     * @param isAddToBackStack 是否加入到后台任务栈中
     */
    public static void startFragment(BaseFragmentManager manager, BaseFragment fragment, Bundle extras, boolean isAddToBackStack) {
        FragmentTransaction transaction = manager.getSupportFragmentManager()
                .beginTransaction();
        fragment.setArguments(extras);
        String className = fragment.getClass().getName();
        transaction.add(R.id.common_activity_fragment_container, fragment,
                className);
        if (isAddToBackStack) {
            transaction.addToBackStack(className);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 打开隐藏的Fragment界面
     *
     * @param manager  Fragment管理器
     * @param fragment 打开的Fragment
     * @param extras   传递的参数值
     */
    public static void showFragment(BaseFragmentManager manager, BaseFragment fragment, Bundle extras) {
        if (fragment.isHidden()) {
            FragmentTransaction transaction = manager.getSupportFragmentManager()
                    .beginTransaction();
            transaction.show(fragment);
            String className = fragment.getClass().getName();
            transaction.addToBackStack(className);
            transaction.commitAllowingStateLoss();
        }
        fragment.onNewArguments(extras);
    }

    /**
     * 打开隐藏的Fragment界面
     *
     * @param manager          Fragment管理器
     * @param fragment         打开的Fragment
     * @param extras           传递的参数值
     * @param isAddToBackStack 是否加入到后台任务栈中
     */
    public static void showFragment(BaseFragmentManager manager, BaseFragment fragment, Bundle extras, boolean isAddToBackStack) {
        if (fragment.isHidden()) {
            FragmentTransaction transaction = manager.getSupportFragmentManager()
                    .beginTransaction();
            transaction.show(fragment);
            String className = fragment.getClass().getName();
            if (isAddToBackStack) {
                transaction.addToBackStack(className);
            }
            transaction.commitAllowingStateLoss();
        }
        fragment.onNewArguments(extras);
    }

}
