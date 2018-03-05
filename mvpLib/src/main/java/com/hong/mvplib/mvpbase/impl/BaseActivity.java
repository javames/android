package com.hong.mvplib.mvpbase.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hong.mvplib.mvpbase.BasePresenter;
import com.hong.mvplib.mvpbase.BaseView;
import com.hong.mvplib.util.ActivityManager;

import java.lang.ref.WeakReference;

import static android.view.Gravity.CENTER_HORIZONTAL;

/**
 * Created by Administrator on 2018/3/1.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {


    protected WeakReference<Context> mContext;//使用了弱引用

    protected T presenter;

    protected Bundle dataBundle;

    protected int layoutResId;

    protected ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutResId();
        setContentView(layoutResId);
        progressBar = createProgressBar(this, null);
        mContext=new WeakReference<Context>(this);
        ActivityManager.getAppInstance().addActivity(this);//将当前activity添加进入管理栈
        presenter=initPresenter();
        dataBundle=getIntent().getBundleExtra("data");

        initView();
        initData();

    }

    protected void initView() {}


    protected void initData() {}


    protected abstract void setLayoutResId();

    /**
     * 在屏幕上添加一个转动的小菊花（传说中的Loading），默认为隐藏状态
     * 注意：务必保证此方法在setContentView()方法后调用，否则小菊花将会处于最底层，被屏幕其他View给覆盖
     *
     * @param activity                    需要添加菊花的Activity
     * @param customIndeterminateDrawable 自定义的菊花图片，可以为null，此时为系统默认菊花
     * @return {ProgressBar}    菊花对象
     */
    protected ProgressBar createProgressBar(AppCompatActivity activity, Drawable customIndeterminateDrawable) {
        // activity根部的ViewGroup，其实是一个FrameLayout
        FrameLayout rootContainer =activity.findViewById(android.R.id.content);
        // 给progressbar准备一个FrameLayout的LayoutParams
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置对其方式为：屏幕居中对其
        lp.gravity = Gravity.CENTER;

        ProgressBar progressBar = new ProgressBar(activity);
        progressBar.setVisibility(View.GONE);
        progressBar.setLayoutParams(lp);

        // 自定义小菊花
        if (customIndeterminateDrawable != null) {
            progressBar.setIndeterminateDrawable(customIndeterminateDrawable);
        }
        // 将菊花添加到FrameLayout中
        rootContainer.addView(progressBar);
        return progressBar;
    }


    @Override
    protected void onDestroy() {
        mContext.clear();
        mContext=null;
        ActivityManager.getAppInstance().removeActivity(this);
        if(null!=presenter){
            presenter.detach();
        }
        super.onDestroy();
    }

    protected abstract T initPresenter();


    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    protected void toast(@Nullable String msg){
        Toast.makeText(mContext.get(),msg,Toast.LENGTH_LONG).show();
    }

    protected void toast(@Nullable int resId){
        Toast.makeText(mContext.get(),getResources().getString(resId),Toast.LENGTH_LONG).show();
    }


    protected void toActivity(Context context,Bundle bundle,Class clazz){
        Intent intent=new Intent(context,clazz);
        intent.putExtra("data",bundle);
        context.startActivity(intent);
    }
    protected void toActivity(Context context,Class clazz){
        toActivity(context,null,clazz);
    }

    protected <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
}
