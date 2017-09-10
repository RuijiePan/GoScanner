package com.jb.goscanner.function.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jb.goscanner.R;
import com.jb.goscanner.base.fragment.BaseFragment;
import com.jb.goscanner.function.bean.ContactBean;

/**
 * Created by panruijie on 2017/9/10.
 * Email : zquprj@gmail.com
 */

public class CardFragment extends BaseFragment {

    public static final String EXTRA_CONTACT_BEAN = "extra_contact_bean";
    private ContactBean mBean;
    private View mRootView;
    private ImageView mPersonImg;
    private TextView mName;
    private TextView mRemarks;
    private ImageView mQrCode;
    private TextView mPhone;
    private TextView mEmail;
    private TextView mWechat;
    private TextView mAddress;
    private Button mCopyButton;

    public static CardFragment newInstance(ContactBean bean) {
        CardFragment instance = new CardFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CONTACT_BEAN, bean);
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBean = getArguments() != null ? (ContactBean) getArguments().getSerializable(EXTRA_CONTACT_BEAN) : null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_card, container, false);
        mPersonImg = (ImageView) mRootView.findViewById(R.id.card_person_img);
        mName = (TextView) mRootView.findViewById(R.id.card_name);
        mRemarks = (TextView) mRootView.findViewById(R.id.card_remarks);
        mQrCode = (ImageView) mRootView.findViewById(R.id.card_qrcode);
        mPhone = (TextView) mRootView.findViewById(R.id.card_phone);
        mEmail = (TextView) mRootView.findViewById(R.id.card_email);
        mWechat = (TextView) mRootView.findViewById(R.id.card_wechat);
        mAddress = (TextView) mRootView.findViewById(R.id.card_address);
        mCopyButton = (Button) mRootView.findViewById(R.id.card_copy_button);

        initData();
        setListener();

        mCopyButton.setOnClickListener(v -> {

        });
        return mRootView;
    }

    private void initData() {
        if (!TextUtils.isEmpty(mBean.getName())) {
            mName.setText(mBean.getName());
        }
        if (!TextUtils.isEmpty(mBean.getRemark())) {
            mRemarks.setText(mBean.getRemark());
        }
        if (mBean.getPhone() != null
                && mBean.getPhone().size() > 0
                && mBean.getPhone().get(0) != null
                && mBean.getPhone().get(0).getValue() != null) {
            mName.setText(mBean.getPhone().get(0).getValue());
        }
        if (mBean.getWechat() != null
                && mBean.getWechat().size() > 0
                && mBean.getWechat().get(0) != null
                && mBean.getWechat().get(0).getValue() != null) {
            mWechat.setText(mBean.getWechat().get(0).getValue());
        }
        if (mBean.getEmail() != null
                && mBean.getEmail().size() > 0
                && mBean.getEmail().get(0) != null
                && mBean.getEmail().get(0).getValue() != null) {
            mEmail.setText(mBean.getEmail().get(0).getValue());
        }
    }

    private void setListener() {

    }

}
