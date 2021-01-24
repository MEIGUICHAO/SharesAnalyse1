package com.mgc.sharesanalyse.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by CZG on 2018/7.
 */

public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected SparseArray<T> mDatas;
    protected List<T> othterDatas = new ArrayList<>();

    public interface OnItemClick<T> {
        void onItemClick(ViewGroup parent, View view, T t, int position);

        boolean onItemLongClick(ViewGroup parent, View view, T t, int position);
    }

    private OnItemClick mOnItemClick;

    public void setOnItemClickListener(OnItemClick onItemClick) {
        this.mOnItemClick = onItemClick;
    }

    public RecyclerAdapter(Context context, int layoutId, SparseArray<T> datas) {
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    int position = getPosition(viewHolder);
                    mOnItemClick.onItemClick(parent, v, mDatas.get(position), position);
                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClick != null) {
                    int position = getPosition(viewHolder);
                    return mOnItemClick.onItemLongClick(parent, v, mDatas.get(position), position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updatePosition(position);
        convert(holder, mDatas.get(position), position);
    }

    public abstract void convert(ViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public SparseArray<T> getData() {
        return mDatas;
    }

    public List<T> getOthterData() {
        return othterDatas;
    }

    public void chooseItem(int classifyId) {
        notifyDataSetChanged();
    }

}