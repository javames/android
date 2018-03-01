package com.hong.mvplib.mvpbase.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hong.mvplib.mvpbase.BasePresenter;
import com.hong.mvplib.mvpbase.BaseView;
import com.hong.mvplib.util.ActivityManager;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2018/3/1.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {


    protected WeakReference<Context> mContext;//使用了弱引用

    protected BasePresenter presenter;

    protected Bundle dataBundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=new WeakReference<Context>(this);
        ActivityManager.getAppInstance().addActivity(this);//将当前activity添加进入管理栈
        presenter=initPresenter();
        dataBundle=getIntent().getBundleExtra("data");

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

    protected abstract BasePresenter initPresenter();


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
