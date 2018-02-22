package com.hong.cookbook;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hong.cookbook.bean.CookBean;

/**
 * Created by Administrator on 2018/2/9.
 */

public class HomeListAdapter extends BaseQuickAdapter<CookBean,BaseViewHolder> {

    public HomeListAdapter() {
        super(R.layout.homepage_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookBean item) {
        RecyclerView recyclerView = helper.getView(R.id.list1);
        initRecy(recyclerView,item);
    }

    private void initRecy(RecyclerView recyclerView, CookBean item) {
    }

    private class HomeGridAdapter extends BaseQuickAdapter<CookBean,BaseViewHolder>{
        public HomeGridAdapter() {
            super(R.layout.grid_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, CookBean item) {

        }
    }
}
