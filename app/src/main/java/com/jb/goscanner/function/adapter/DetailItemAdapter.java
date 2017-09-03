package com.jb.goscanner.function.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jb.goscanner.R;
import com.jb.goscanner.function.activity.RecordDetailActivity;
import com.jb.goscanner.function.bean.DetailItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyue on 2017/9/3.
 */

public class DetailItemAdapter extends RecyclerView.Adapter {
    private static final String TAG = "DetailItemAdapter";
    private Context mContext;
    private List<DetailItem> mData;
    private int mCurMode;
    private ClipboardManager mClipboardManager;

    public DetailItemAdapter(Context context, int mode)  {
        mContext = context;
        mCurMode = mode;
        mData = new ArrayList<>();
        mClipboardManager = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.detail_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new DetailItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        DetailItemViewHolder holder = (DetailItemViewHolder)viewholder;
        DetailItem item = mData.get(position);
        holder.mTag.setText(item.getTag());
        if (mCurMode == RecordDetailActivity.MODE_EDITABLE) {
            holder.mTagEditText.setText(item.getValue());
        } else {
            holder.mTagUneditableText.setText(item.getValue());
        }

        holder.mCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealOnCopy(holder);
            }
        });

        holder.mPasteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealOnPaste(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class DetailItemViewHolder extends RecyclerView.ViewHolder{
        private Button mCopyBtn;
        private Button mPasteBtn;
        private TextView mTag;
        private TextView mTagUneditableText;
        private EditText mTagEditText;

        public DetailItemViewHolder(View itemView) {
            super(itemView);
            mCopyBtn = (Button)itemView.findViewById(R.id.detail_copy_btn);
            mPasteBtn = (Button)itemView.findViewById(R.id.detail_paste_btn);
            mTag = (TextView)itemView.findViewById(R.id.detail_tag_textview);
            mTagUneditableText = (TextView) itemView.findViewById(R.id.detail_uneditable_text);
            mTagEditText = (EditText) itemView.findViewById(R.id.detail_edit_text);

            if (mCurMode == RecordDetailActivity.MODE_EDITABLE) { // 可编辑
                mTagEditText.setVisibility(View.VISIBLE);
                mPasteBtn.setVisibility(View.VISIBLE);

                mTagUneditableText.setVisibility(View.GONE);
            } else { // 不可编辑
                mTagUneditableText.setVisibility(View.VISIBLE);

                mTagEditText.setVisibility(View.GONE);
                mPasteBtn.setVisibility(View.GONE);
            }
        }
    }

    private void dealOnCopy(DetailItemViewHolder holder) {
        String text;
        if (mCurMode == RecordDetailActivity.MODE_EDITABLE) {
            text = holder.mTagEditText.getText().toString();
        } else {
            text = holder.mTagUneditableText.getText().toString();
        }
        ClipData clip = ClipData.newPlainText(DetailItemAdapter.class.getCanonicalName(), text);
        mClipboardManager.setPrimaryClip(clip);
    }

    private void dealOnPaste(DetailItemViewHolder holder) {
        if (mClipboardManager.hasPrimaryClip()) {
            ClipData clipData = mClipboardManager.getPrimaryClip();
            int index = holder.mTagEditText.getSelectionStart();
            Editable editable = holder.mTagEditText.getText();
            editable.insert(index, clipData.getItemAt(0).getText());
        }
    }

    public List<DetailItem> getData() {
        return mData;
    }

    public void setData(List<DetailItem> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public int getCurMode() {
        return mCurMode;
    }

    public void setCurMode(int curMode) {
        mCurMode = curMode;
    }

}
