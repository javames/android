package com.hong.cookbook.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hong.cookbook.GlideUtil;
import com.hong.cookbook.R;
import com.hong.cookbook.ResetApplication;
import com.hong.cookbook.bean.MethodBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/28.
 */

public class MethodAdapter extends BaseQuickAdapter<MethodBean,BaseViewHolder>{

    public MethodAdapter(@Nullable List<MethodBean> data) {
        super(R.layout.method_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MethodBean item) {
        TextView text = helper.getView(R.id.item);
        text.setText("  "+item.getStep());

        ImageView imageView = helper.getView(R.id.pic_item);
        String img=item.getImg();
        if(!TextUtils.isEmpty(item.getImg())){
            GlideUtil.INSTANCE.loadImg(ResetApplication.getContext(),img,imageView);
        }
    }
}
