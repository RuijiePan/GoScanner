package com.jb.goscanner.function.adapter;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jb.goscanner.R;
import com.jb.goscanner.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panruijie on 2017/9/10.
 * Email : zquprj@gmail.com
 */

public class IndexAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageView> mImageViewList;
    private int mSelectIndex;
    private int mSize;
    private OnIndexClickListener mListener;

    public IndexAdapter(Context context, int size, int index) {
        mContext = context;
        mSelectIndex = index;
        mSize = size;
        mImageViewList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new LinearLayoutCompat.LayoutParams(DensityUtil.dip2px(mContext, 10), DensityUtil.dip2px(mContext, 10)));
            if (mSelectIndex == i) {
                imageView.setBackground(ActivityCompat.getDrawable(mContext, R.drawable.card_index_backgound_select));
            } else {
                imageView.setBackground(ActivityCompat.getDrawable(mContext, R.drawable.card_index_backgound_unselect));
            }
            int finalI = i;
            imageView.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onClick(finalI);
                }
            });
            mImageViewList.add(imageView);
        }
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mImageViewList.get(position), position);
        return mImageViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mImageViewList.get(position));
        //super.destroyItem(container, position, object);
    }

    public void setSelectedInex(int index) {
        mImageViewList.get(mSelectIndex).setBackground(ActivityCompat.getDrawable(mContext, R.drawable.card_index_backgound_unselect));
        mSelectIndex = index;
        mImageViewList.get(mSelectIndex).setBackground(ActivityCompat.getDrawable(mContext, R.drawable.card_index_backgound_select));
    }

    public void setIndexClickListener(OnIndexClickListener listener) {
        this.mListener = listener;
    }

    public interface OnIndexClickListener {
        void onClick(int index);
    }
}
