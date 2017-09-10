package com.jb.goscanner.network;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.jb.goscanner.function.bean.CardInfo;
import com.jb.goscanner.util.Base64Util;
import com.jb.goscanner.util.BitmapUtil;
import com.jb.goscanner.util.log.Loger;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by panruijie on 2017/9/3.
 * Email : zquprj@gmail.com
 * 名片识别接口地址：
 * https://www.juhe.cn/docs/api/id/139
 */

public class CardInterface {

    public static final String CARD_URL = "http://op.juhe.cn/hanvon/bcard/query?key=f1c2f7a50f77d91978db14ff63b79672&&color=orignal";

    public void getCardInfo(String imageUrl, IUploadListener listener) {
        if (listener != null) {
            listener.uploadStart();
        }
        Flowable.just(imageUrl)
                //拿bitmap
                .map(BitmapUtil::getBitmap)
                //压缩到小于200kb
                .map(BitmapUtil::compressImage)
                //转灰度图
                .map(BitmapUtil::toGray)
                //base64编码
                .map(Base64Util::encodeBitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(base64 -> OkGo.<String>post(CARD_URL)
                        .params("image", base64)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                String result = response.body();
                                Loger.w("ruijie", result);
                                if (!TextUtils.isEmpty(result)) {
                                    CardInfo info = new Gson().fromJson(result, CardInfo.class);
                                    if (listener != null) {
                                        if (info.getResult() != null) {
                                            listener.uploadSuccess(info.getResult());
                                        } else {
                                            listener.uploadFailure("result is null");
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onError(com.lzy.okgo.model.Response<String> response) {
                                if (listener != null) {
                                    listener.uploadFailure(response.body());
                                }
                                super.onError(response);
                            }
                        }), throwable -> {
                    if (listener != null) {
                        listener.uploadFailure(throwable.toString());
                    }
                    throwable.printStackTrace();
                });
    }

    public interface IUploadListener {
        void uploadStart();

        void uploadFailure(String error);

        void uploadSuccess(CardInfo.ResultBean bean);
    }

}
