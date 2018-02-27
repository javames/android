package com.hong.cookbook.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/2/23.
 */

public class FilterTab extends HorizontalScrollView {

    private String[] items;
    private int sTab;
    private Context mCtx;
    private int itemWidth;
    private int selectedColor = Color.RED;
    private int unSelectedColor = Color.GRAY;

    private int selectPosition = 0;

    private LinearLayout parent;

    private OnItemClick onItemClick;

    public FilterTab(Context context) {
        this(context, null);
    }

    public FilterTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mCtx = context;
    }

    public void setData(String[] items) {
        this.items = items;
    }

    public void setShowTabCount(int sTab) {
        this.sTab = sTab;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }

    public void setUnSelectedColor(int unSelectedColor) {
        this.unSelectedColor = unSelectedColor;
    }

    public void invalite() {


        final ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int height = getMeasuredHeight();

                if (null == parent) {
                    parent = new LinearLayout(mCtx);

                    ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
                    parent.setLayoutParams(params);
                    addView(parent);
                }
                itemWidth = getMeasuredWidth() / sTab;
                Log.i("test", "itemWidth= " + itemWidth + " height= " + height);
                for (int i = 0; i < items.length; i++) {
                    TextView textView = new TextView(mCtx);
                    textView.setGravity(Gravity.CENTER);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, height));
                    parent.addView(textView);
                    textView.setText(items[i]);
                    if (i == selectPosition) {
                        textView.setTextColor(selectedColor);
                        textView.setTextSize(12);
                    } else {
                        textView.setTextColor(unSelectedColor);
                        textView.setTextSize(11);
                    }
                    textView.setTag(i);
                    textView.setOnClickListener(clickListener);
                }

            }
        });

    }

    public void setSelectPostion(int position) {
        this.selectPosition = position;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                TextView child = (TextView) parent.getChildAt(i);
                child.setTextColor(unSelectedColor);
                child.setTextSize(11);
            }

            TextView text = (TextView) v;
            text.setTextColor(selectedColor);
            text.setTextSize(12);

            int position = (int) v.getTag();
            if (onItemClick != null) {
                onItemClick.itemClick(position, v);
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

        }
        return super.onTouchEvent(ev);
    }

    public void setOnItemClickListener(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        void itemClick(int position, View view);
    }

}
