package com.hong.cookbook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by Administrator on 2018/2/9.
 */

public class HomeListAdapter extends BaseQuickAdapter<CookBean,BaseViewHolder> {

    public HomeListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookBean item) {

    }
}
