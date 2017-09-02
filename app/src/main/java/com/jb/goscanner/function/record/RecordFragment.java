package com.jb.goscanner.function.record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jb.goscanner.R;
import com.jb.goscanner.base.fragment.BaseFragment;

/**
 * Created by panruijie on 2017/9/2.
 * Email : zquprj@gmail.com
 */

public class RecordFragment extends BaseFragment {

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.main_record_fragment, container, false);
        return mRootView;
    }

    @Override
    protected boolean dispatchBackPressed() {
        return super.dispatchBackPressed();
    }
}
