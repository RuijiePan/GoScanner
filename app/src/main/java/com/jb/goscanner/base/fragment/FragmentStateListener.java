package com.jb.goscanner.base.fragment;

import android.support.v4.app.Fragment;

/**
 * fragment的状态监听接口<br>
 *
 * @author laojiale
 */
public interface FragmentStateListener {

    void onFragmentInflate(Fragment f);

    void onFragmentAttach(Fragment f);

    void onFragmentCreate(Fragment f);

    void onFragmentViewCreated(Fragment f);

    void onFragmentActivityCreated(Fragment f);

    void onFragmentStart(Fragment f);

    void onFragmentResume(Fragment f);

    void onFragmentPause(Fragment f);

    void onFragmentStop(Fragment f);

    void onFragmentDestroy(Fragment f);

    void onFragmentDetach(Fragment f);

    void onFragmentHiddenChanged(Fragment f);

}
