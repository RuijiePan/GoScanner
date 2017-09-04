package com.jb.goscanner.function.main.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jb.goscanner.R;
import com.jb.goscanner.util.ColorUtil;

/**
 * Created by panruijie on 2017/9/4.
 * 主页面底部tab
 */

public class BottomTab extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private TextView mGenerateText;
    private TextView mCollectionText;
    private ImageView mGenerateImg;
    private ImageView mCollectionImg;
    private LinearLayout mGenerateLayout;
    private LinearLayout mCollectionLayout;
    private ImageView mScanImg;
    private OnTabClickListener mListener;

    public BottomTab(Context context) {
        this(context, null);
    }

    public BottomTab(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.activity_main_bottom_layout, this, true);
        mGenerateText = (TextView) findViewById(R.id.main_bottom_generate_text);
        mCollectionText = (TextView) findViewById(R.id.main_bottom_collection_text);
        mGenerateImg = (ImageView) findViewById(R.id.main_bottom_generate_img);
        mCollectionImg = (ImageView) findViewById(R.id.main_bottom_collection_img);
        mScanImg = (ImageView) findViewById(R.id.main_bottom_scan_btn);
        mGenerateLayout = (LinearLayout) findViewById(R.id.main_bottom_generate_layout);
        mCollectionLayout = (LinearLayout) findViewById(R.id.main_bottom_collection_layout);

        mGenerateImg.setImageResource(R.drawable.ic_home_generate_select);
        mGenerateText.setTextColor(ColorUtil.getColor(mContext, R.color.color_main_bottom_select_color));

        mGenerateLayout.setOnClickListener(this);
        mCollectionLayout.setOnClickListener(this);
        mScanImg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bottom_generate_layout:
                if (mListener != null) {
                    mListener.onClickGenerate();
                }
                mGenerateImg.setImageResource(R.drawable.ic_home_generate_select);
                mGenerateText.setTextColor(ColorUtil.getColor(mContext, R.color.color_main_bottom_select_color));
                mCollectionImg.setImageResource(R.drawable.ic_home_collection);
                mCollectionText.setTextColor(ColorUtil.getColor(mContext, R.color.color_main_bottom_normal_color));
                break;
            case R.id.main_bottom_scan_btn:
                if (mListener != null) {
                    mListener.onClickScan();
                }
                break;
            case R.id.main_bottom_collection_layout:
                if (mListener != null) {
                    mListener.onClickCollection();
                }
                mGenerateImg.setImageResource(R.drawable.ic_home_generate);
                mGenerateText.setTextColor(ColorUtil.getColor(mContext, R.color.color_main_bottom_normal_color));
                mCollectionImg.setImageResource(R.drawable.ic_home_collection_select);
                mCollectionText.setTextColor(ColorUtil.getColor(mContext, R.color.color_main_bottom_select_color));
                break;
        }
    }

    public void setTabClickListener(OnTabClickListener listener) {
        this.mListener = listener;
    }

    public interface OnTabClickListener {
        void onClickGenerate();

        void onClickScan();

        void onClickCollection();
    }
}
