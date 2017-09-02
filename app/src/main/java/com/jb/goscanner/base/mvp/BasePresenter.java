package com.jb.goscanner.base.mvp;

import android.support.annotation.NonNull;

/**
 * Created by panruijie on 16/11/12.
 */
public interface BasePresenter<T extends BaseView> {

    void attachView(@NonNull T view);

    void detachView();
}
