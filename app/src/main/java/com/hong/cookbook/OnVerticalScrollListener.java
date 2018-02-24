package com.hong.cookbook;


import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/7/11.
 */

public class OnVerticalScrollListener extends RecyclerView.OnScrollListener {

    public OnVerticalScrollListener() {
        super();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (!recyclerView.canScrollVertically(-1)) {
            onScrolledToTop();
        } else if (!recyclerView.canScrollVertically(1)) {
            onScrolledToBottom();
        } else if (dy < 0) {
            onScrolledUp();
        } else if (dy > 0) {
            onScrolledDown();
        }
    }

    public void onScrolledUp() {}

    public void onScrolledDown() {}

    public void onScrolledToTop() {}

    public void onScrolledToBottom() {}
}
