package com.jb.goscanner.function.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jb.goscanner.R;
import com.jb.goscanner.function.activity.RecordDetailActivity;
import com.jb.goscanner.function.bean.DetailItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyue on 2017/9/3.
 */

/**
 * mData在编辑完成后没有获得EditText的值
 */
public class DetailItemAdapter extends RecyclerView.Adapter {
    private static final String TAG = "DetailItemAdapter";
    private Context mContext;
    private List<DetailItem> mData;
    private int mCurMode;
    private ClipboardManager mClipboardManager;
    private List<SwitchModeListener> mSwitchModeListeners = new ArrayList<>();

    private int TYPE_DETAIL = 1;
    private int TYPE_HEAD = 0;
    private int TYPE_GROUP = 2;

    interface SwitchModeListener {
        void onSwitchMode(int mode);
    }

    public DetailItemAdapter(Context context, int mode)  {
        mContext = context;
        mCurMode = mode;
        mData = new ArrayList<>();
        mClipboardManager = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_DETAIL) {
            View view = inflater.inflate(R.layout.detail_item, parent, false);
            return new DetailItemViewHolder(view);
        } else if (viewType == TYPE_HEAD) {
            View view = inflater.inflate(R.layout.detail_head, parent, false);
            return new DetailHeadViewHolder(view);
        } else if (viewType == TYPE_GROUP) {
            View view = inflater.inflate(R.layout.detail_group, parent, false);
            return new DetailGroupViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        String group = mData.get(position).getGroup();
        if (position == 0) {
            return TYPE_HEAD;
        } else if (mData.get(position).getTag() != null) {
            return TYPE_DETAIL;
        } else {
            return TYPE_GROUP;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewholder, int position) {
        if (viewholder instanceof DetailItemViewHolder) { // 记录详情的view
            DetailItemViewHolder holder = (DetailItemViewHolder)viewholder;
            holder.removeTagWatcher();
            holder.removeValueWatcher();
            DetailItem item = mData.get(position);
            holder.mTagEditText.setText(item.getTag());
            if (mCurMode == RecordDetailActivity.MODE_EDITABLE) {
                holder.mValueEditText.setText(item.getValue());
            } else {
                holder.mTagUneditableText.setText(item.getValue());
            }

            holder.mPasteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dealOnPaste(holder);
                }
            });

            holder.setValueWatcher(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mData.get(position).setValue(holder.mValueEditText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    mData.get(position).setValue(holder.mValueEditText.getText().toString());
                }
            });

            holder.setTagWatcher(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    /*if (holder.mTagEditText.getText().toString() == null || holder.mTagEditText.getText().toString().equals("")) {
                        mData.get(position).setTag(holder.mTagEditText.getText().toString());
                        holder.mTagEditText.setHint(mData.get(position).getGroup());
                    } else {
                        mData.get(position).setTag(holder.mTagEditText.getText().toString());
                    }*/
                    mData.get(position).setTag(holder.mTagEditText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    mData.get(position).setTag(holder.mTagEditText.getText().toString());
                }
            });

            SwitchModeListener listener = new SwitchModeListener() {
                @Override
                public void onSwitchMode(int mode) {
                    if (mode == RecordDetailActivity.MODE_EDITABLE) {
                        holder.mValueEditText.setVisibility(View.VISIBLE);
                        holder.mPasteBtn.setVisibility(View.VISIBLE);
                        holder.mTagUneditableText.setVisibility(View.GONE);
                    } else {
                        holder.mValueEditText.setVisibility(View.GONE);
                        holder.mPasteBtn.setVisibility(View.GONE);
                        holder.mTagUneditableText.setVisibility(View.VISIBLE);
                    }
                }
            };
            holder.setSwitchModeListener(listener);
            mSwitchModeListeners.add(listener);
            holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else if (viewholder instanceof DetailHeadViewHolder) {
            DetailHeadViewHolder holder = (DetailHeadViewHolder)viewholder;
            Log.d(TAG, "onBindViewHolder: " + position);
            holder.mRemarkEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: ");
                }
            });
        } else if (viewholder instanceof DetailGroupViewHolder) {
            DetailGroupViewHolder holder = (DetailGroupViewHolder)viewholder;
            holder.mGroupAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailItem item = new DetailItem();
                    item.setGroup(mData.get(position).getGroup());
                    item.setTag(mData.get(position).getGroup());
                    mData.add(position + 1, item);
                    for (int i = 0; i < mData.size(); i++) {
                        Log.d(TAG, "onClick: " + i + " = " + mData.get(i).toString());
                    }
                    Log.d(TAG, "onClick: ====================================================");
                    notifyDataSetChanged();
                }
            });
            holder.mGroupText.setText(mData.get(position).getGroup());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class DetailItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView mDeleteBtn;
        private Button mPasteBtn;
        private EditText mTagEditText;
        private TextView mTagUneditableText;
        private EditText mValueEditText;
        private TextWatcher mTagWatcher;
        private TextWatcher mValueWatcher;
        private SwitchModeListener mSwitchModeListener;

        public DetailItemViewHolder(View itemView) {
            super(itemView);
            mDeleteBtn = (ImageView)itemView.findViewById(R.id.detail_delete_btn);
            mPasteBtn = (Button)itemView.findViewById(R.id.detail_paste_btn);
            mTagEditText = (EditText)itemView.findViewById(R.id.detail_tag_textview);
            mTagUneditableText = (TextView) itemView.findViewById(R.id.detail_uneditable_text);
            mValueEditText = (EditText) itemView.findViewById(R.id.detail_edit_text);

            if (mCurMode == RecordDetailActivity.MODE_EDITABLE) { // 可编辑
                mValueEditText.setVisibility(View.VISIBLE);
                mTagUneditableText.setVisibility(View.GONE);
            } else { // 不可编辑
                mTagUneditableText.setVisibility(View.VISIBLE);

                mValueEditText.setVisibility(View.GONE);
            }
        }

        public void setSwitchModeListener(SwitchModeListener switchModeListener) {
            mSwitchModeListener = switchModeListener;
        }

        public void removeTagWatcher() {
            mTagEditText.removeTextChangedListener(mTagWatcher);
            mTagWatcher = null;
        }

        public void setTagWatcher(TextWatcher tagWatcher) {
            mTagWatcher = tagWatcher;
            mTagEditText.addTextChangedListener(mTagWatcher);
        }

        public void removeValueWatcher() {
            mValueEditText.removeTextChangedListener(mValueWatcher);
            mValueWatcher = null;
        }

        public void setValueWatcher(TextWatcher valueWatcher) {
            mValueWatcher = valueWatcher;
            mValueEditText.addTextChangedListener(mValueWatcher);
        }
    }

    class DetailHeadViewHolder extends RecyclerView.ViewHolder{
        private ImageView mSelfImage;
        private EditText mNameEditText;
        private EditText mRemarkEditText;
        private Button mNamePasteBtn;
        private Button mRemarkPasteBtn;

        public DetailHeadViewHolder(View itemView) {
            super(itemView);
            mSelfImage = (ImageView)itemView.findViewById(R.id.self_image);
            mNameEditText = (EditText) itemView.findViewById(R.id.detail_name_edit_text);
            mRemarkEditText = (EditText) itemView.findViewById(R.id.detail_remark_edit_text);
            mNamePasteBtn = (Button)itemView.findViewById(R.id.detail_name_paste_btn);
            mRemarkPasteBtn = (Button)itemView.findViewById(R.id.detail_remark_paste_btn);

            mNameEditText.setBackground(null);
            mRemarkEditText.setBackground(null);
        }
    }

    class DetailGroupViewHolder extends RecyclerView.ViewHolder{
        private ImageView mGroupAddBtn;
        private TextView mGroupText;

        public DetailGroupViewHolder(View itemView) {
            super(itemView);
            mGroupAddBtn = (ImageView)itemView.findViewById(R.id.group_add_btn);
            mGroupText = (TextView) itemView.findViewById(R.id.detail_group_textview);
        }
    }


    private void dealOnCopy(DetailItemViewHolder holder) {
        String text;
        if (mCurMode == RecordDetailActivity.MODE_EDITABLE) {
            text = holder.mValueEditText.getText().toString();
        } else {
            text = holder.mTagUneditableText.getText().toString();
        }
        ClipData clip = ClipData.newPlainText(DetailItemAdapter.class.getCanonicalName(), text);
        mClipboardManager.setPrimaryClip(clip);
    }

    private void dealOnPaste(DetailItemViewHolder holder) {
        if (mClipboardManager.hasPrimaryClip()) {
            ClipData clipData = mClipboardManager.getPrimaryClip();
            int index = holder.mValueEditText.getSelectionStart();
            Editable editable = holder.mValueEditText.getText();
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
        for (SwitchModeListener listener : mSwitchModeListeners) {
            listener.onSwitchMode(curMode);
        }
    }

}
