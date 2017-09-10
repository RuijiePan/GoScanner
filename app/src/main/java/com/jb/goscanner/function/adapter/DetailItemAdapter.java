package com.jb.goscanner.function.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
    private View.OnClickListener mOnCreateBtnClickListener;

    private int TYPE_DETAIL = 1;
    private int TYPE_HEAD = 0;
    private int TYPE_GROUP = 2;
    private int TYPE_FOOTER = 3;

    interface SwitchModeListener {
        void onSwitchMode(int mode);
    }

    public DetailItemAdapter(Context context, int mode, View.OnClickListener listener)  {
        mContext = context;
        mCurMode = mode;
        mData = new ArrayList<>();
        mClipboardManager = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        mOnCreateBtnClickListener = listener;
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
        } else if (viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.detail_footer_btn, parent, false);
            return new FooterViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == mData.size()) {
            return TYPE_FOOTER;
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
            holder.mValueEditText.setText(item.getValue());

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
                        holder.mTagEditText.setFocusable(true);
                        holder.mValueEditText.setFocusable(true);

                        holder.mDeleteBtn.setVisibility(View.VISIBLE);
                    } else {
                        holder.mTagEditText.setFocusable(false);
                        holder.mValueEditText.setFocusable(false);

                        holder.mDeleteBtn.setVisibility(View.GONE);
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
            holder.setNameWatcher(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mData.get(0).setTag(holder.mNameEditText.getText().toString());
                }
            });
            holder.setRemarkWatcher(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mData.get(0).setGroup(holder.mRemarkEditText.getText().toString());
                }
            });
            holder.mNamePasteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dealOnPaste(holder, "Name");
                }
            });
            holder.mRemarkPasteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dealOnPaste(holder, "Remark");
                }
            });
            SwitchModeListener listener = new SwitchModeListener() {
                @Override
                public void onSwitchMode(int mode) {
                    if (mode == RecordDetailActivity.MODE_EDITABLE) {
                        holder.mNameEditText.setFocusable(true);
                        holder.mRemarkEditText.setFocusable(true);
                    } else {
                        holder.mNameEditText.setFocusable(false);
                        holder.mRemarkEditText.setFocusable(false);
                    }
                }
            };
            holder.setSwitchModeListener(listener);
            mSwitchModeListeners.add(listener);
            holder.mNameEditText.setText(mData.get(0).getTag());
            holder.mRemarkEditText.setText(mData.get(0).getGroup());
        } else if (viewholder instanceof DetailGroupViewHolder) {
            DetailGroupViewHolder holder = (DetailGroupViewHolder)viewholder;
            holder.mGroupAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailItem item = new DetailItem();
                    item.setGroup(mData.get(position).getGroup());
                    item.setTag(mData.get(position).getGroup());
                    mData.add(position + 1, item);
                    notifyDataSetChanged();
                }
            });
            holder.mGroupText.setText(mData.get(position).getGroup());
            SwitchModeListener listener = new SwitchModeListener() {
                @Override
                public void onSwitchMode(int mode) {
                    if (mode == RecordDetailActivity.MODE_EDITABLE) {
                        holder.mGroupAddBtn.setVisibility(View.VISIBLE);
                    } else {
                        holder.mGroupAddBtn.setVisibility(View.GONE);
                    }
                }
            };
            holder.setSwitchModeListener(listener);
            mSwitchModeListeners.add(listener);
        } else if (viewholder instanceof FooterViewHolder) {
            FooterViewHolder holder = (FooterViewHolder)viewholder;
            holder.mCreateBtn.setOnClickListener(mOnCreateBtnClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    class DetailItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView mDeleteBtn;
        private Button mPasteBtn;
        private EditText mTagEditText;
        private EditText mValueEditText;
        private TextWatcher mTagWatcher;
        private TextWatcher mValueWatcher;
        private SwitchModeListener mSwitchModeListener;

        public DetailItemViewHolder(View itemView) {
            super(itemView);
            mDeleteBtn = (ImageView)itemView.findViewById(R.id.detail_delete_btn);
            mPasteBtn = (Button)itemView.findViewById(R.id.detail_paste_btn);
            mTagEditText = (EditText)itemView.findViewById(R.id.detail_tag_edittext);
            mValueEditText = (EditText) itemView.findViewById(R.id.detail_edit_text);

            mTagEditText.setBackground(null);
            mValueEditText.setBackground(null);
            if (mCurMode == RecordDetailActivity.MODE_EDITABLE) { // 可编辑
                mValueEditText.setVisibility(View.VISIBLE);
            } else { // 不可编辑
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
        private TextWatcher mNameWatcher;
        private TextWatcher mRemarkWatcher;
        private SwitchModeListener mSwitchModeListener;

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

        public void removeNameWatcher() {
            mNameEditText.removeTextChangedListener(mNameWatcher);
            mNameWatcher = null;
        }

        public void setNameWatcher(TextWatcher nameWatcher) {
            mNameWatcher = nameWatcher;
            mNameEditText.addTextChangedListener(mNameWatcher);
        }

        public void removeRemarkWatcher() {
            mRemarkEditText.removeTextChangedListener(mRemarkWatcher);
            mRemarkWatcher = null;
        }

        public void setRemarkWatcher(TextWatcher remarkWatcher) {
            mRemarkWatcher = remarkWatcher;
            mRemarkEditText.addTextChangedListener(mRemarkWatcher);
        }

        public void setSwitchModeListener(SwitchModeListener switchModeListener) {
            mSwitchModeListener = switchModeListener;
        }
    }

    class DetailGroupViewHolder extends RecyclerView.ViewHolder{
        private ImageView mGroupAddBtn;
        private TextView mGroupText;
        private SwitchModeListener mSwitchModeListener;

        public DetailGroupViewHolder(View itemView) {
            super(itemView);
            mGroupAddBtn = (ImageView)itemView.findViewById(R.id.group_add_btn);
            mGroupText = (TextView) itemView.findViewById(R.id.detail_group_textview);
        }

        public void setSwitchModeListener(SwitchModeListener switchModeListener) {
            mSwitchModeListener = switchModeListener;
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{
        private Button mCreateBtn;

        public FooterViewHolder(View itemView) {
            super(itemView);
            mCreateBtn = (Button)itemView.findViewById(R.id.qrcode_create_btn);
        }
    }

    /*private void dealOnCopy(DetailItemViewHolder holder) {
        String text;
        if (mCurMode == RecordDetailActivity.MODE_EDITABLE) {
            text = holder.mValueEditText.getText().toString();
        } else {
            text = holder.mValueUneditableText.getText().toString();
        }
        ClipData clip = ClipData.newPlainText(DetailItemAdapter.class.getCanonicalName(), text);
        mClipboardManager.setPrimaryClip(clip);
    }*/

    private void dealOnPaste(DetailItemViewHolder holder) {
        if (mCurMode == RecordDetailActivity.MODE_UNEDITABLE)
            return;
        if (mClipboardManager.hasPrimaryClip()) {
            ClipData clipData = mClipboardManager.getPrimaryClip();
            int index = holder.mValueEditText.getSelectionStart();
            Editable editable = holder.mValueEditText.getText();
            editable.insert(index, clipData.getItemAt(0).getText());
        }
    }

    private void dealOnPaste(DetailHeadViewHolder holder, String tag) {
        if (mCurMode == RecordDetailActivity.MODE_UNEDITABLE)
            return;
        if (mClipboardManager.hasPrimaryClip()) {
            ClipData clipData = mClipboardManager.getPrimaryClip();
            if (tag.equals("Name")) {
                int index = holder.mNameEditText.getSelectionStart();
                Editable editable = holder.mNameEditText.getText();
                editable.insert(index, clipData.getItemAt(0).getText());
            } else if (tag.equals("Remark")) {
                int index = holder.mRemarkEditText.getSelectionStart();
                Editable editable = holder.mRemarkEditText.getText();
                editable.insert(index, clipData.getItemAt(0).getText());
            }
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
