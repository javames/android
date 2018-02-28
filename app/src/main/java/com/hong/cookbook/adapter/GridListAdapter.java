package com.hong.cookbook.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hong.cookbook.GlideUtil;
import com.hong.cookbook.R;
import com.hong.cookbook.bean.CookMenuBean;

/**
 * Created by Administrator on 2018/2/28.
 */

public class GridListAdapter extends BaseQuickAdapter<CookMenuBean.ResultBean.ListBean, BaseViewHolder> {

    public GridListAdapter() {
        super(R.layout.grid_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, CookMenuBean.ResultBean.ListBean item) {
        ImageView imageView = helper.getView(R.id.food_icon);
        String thumbnail = item.getThumbnail();
        if (!TextUtils.isEmpty(thumbnail)) {
            GlideUtil.INSTANCE.loadImg(mContext.getApplicationContext(), thumbnail, imageView);
        }else{
            Glide.with(mContext.getApplicationContext()).load(mContext.getResources().getDrawable(R.mipmap.def_background)).into(imageView);
        }

        TextView title = helper.getView(R.id.food_txt);
        title.setText(item.getName());

        TextView lab = helper.getView(R.id.food_lab);
        lab.setText(item.getCtgTitles());

        TextView make = helper.getView(R.id.food_make);
        CookMenuBean.ResultBean.ListBean.RecipeBean recipe = item.getRecipe();
        if(null!=recipe){
            String ingredients = recipe.getIngredients();
            if (!TextUtils.isEmpty(ingredients)) {
                String items=ingredients.replaceAll("[\\[\\]]", "");
                items = items.replace("\"", "").replace("\"", "");
                make.setText(items);
            } else {
                make.setText(recipe.getSumary());
            }
        }


    }
}
