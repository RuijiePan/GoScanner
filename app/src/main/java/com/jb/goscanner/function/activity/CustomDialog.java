package com.jb.goscanner.function.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jb.goscanner.R;

/**
 * Created by liuyue on 2017/9/10.
 */

public class CustomDialog {
    private Context mContext;
    private Dialog mDialog;
    private TextView mNewContactBtn;
    private TextView mUpdateContactBtn;

    private View.OnClickListener mOnNewContactClick;
    private View.OnClickListener mOnUpdateContactClick;
    /**
     * init the dialog
     * @return
     */
    public CustomDialog(Context context, View.OnClickListener listenerNew, View.OnClickListener listenerUpdate) {
        this.mContext = context;
        mDialog = new Dialog(mContext, R.style.CustomDialog);
        mDialog.setContentView(R.layout.dialog_layout);

        mOnNewContactClick = listenerNew;
        mOnUpdateContactClick = listenerUpdate;

        mNewContactBtn = (TextView)mDialog.findViewById(R.id.new_contact_btn);
        mUpdateContactBtn = (TextView)mDialog.findViewById(R.id.update_contact_btn);

        mNewContactBtn.setOnClickListener(mOnNewContactClick);
        mUpdateContactBtn.setOnClickListener(mOnUpdateContactClick);

    }

    public void show() {
        mDialog.show();
    }
}