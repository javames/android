package com.hong.cookbook.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * 自定义的RecyclerView的分割线（GridLayout模式时使用）
 * @author Chen
 */
public class CustomGridItemDecoration extends RecyclerView.ItemDecoration {
    private final int spacingVertical;//垂直距离
    private int spanCount;//列数
    private int spacingHorizontal;//水平距离
    private boolean includeEdge;//左右边缘是否设置距离

    public CustomGridItemDecoration(int spanCount, int spacingHorizontal,int spacingVertical,boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacingHorizontal = spacingHorizontal;
        this.spacingVertical = spacingVertical;
        this.includeEdge = includeEdge;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % this.spanCount;
        if(this.includeEdge) {
            outRect.left = this.spacingHorizontal - column * this.spacingHorizontal / this.spanCount;
            outRect.right = (column + 1) * this.spacingHorizontal / this.spanCount;
            if(position < this.spanCount) {
                outRect.top = this.spacingVertical;
            }

            outRect.bottom = this.spacingVertical;
        } else {
            outRect.left = column * this.spacingHorizontal / this.spanCount;
            outRect.right = this.spacingHorizontal - (column + 1) * this.spacingHorizontal / this.spanCount;
            if(position < this.spanCount) {
                outRect.top = this.spacingVertical;
            }

            outRect.bottom = this.spacingVertical;
        }

    }
}