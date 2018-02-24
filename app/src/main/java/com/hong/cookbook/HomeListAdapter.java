package com.hong.cookbook;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hong.cookbook.bean.CookBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/9.
 */

public class HomeListAdapter extends BaseQuickAdapter<CookBean.ResultBean.ChildsBeanX,BaseViewHolder> {

    public HomeListAdapter() {
        super(R.layout.homepage_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookBean.ResultBean.ChildsBeanX item) {
        TextView txt = helper.getView(R.id.item_lab);
        String lab=null;
        String ctgId=item.getCategoryInfo().getCtgId();
        switch (ctgId){
            case "0010001002":
                lab="菜品";
                break;
            case "0010001003":
                lab="工艺";
                break;
            case "0010001004":
                lab="菜系";
                break;
            case "0010001005":
                lab="人群";
                break;
            case "0010001006":
                lab="功能";
                break;
        }
        txt.setText(lab);
        RecyclerView recyclerView = helper.getView(R.id.list1);
        initRecy(recyclerView,item);
    }

    private void initRecy(RecyclerView recyclerView, CookBean.ResultBean.ChildsBeanX item) {
        List<CookBean.ResultBean.ChildsBeanX.ChildsBean> childs = item.getChilds();

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
