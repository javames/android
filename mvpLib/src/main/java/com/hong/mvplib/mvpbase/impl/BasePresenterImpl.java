package com.hong.mvplib.mvpbase.impl;

import android.content.Context;

import com.hong.mvplib.mvpbase.BasePresenter;
import com.hong.mvplib.mvpbase.BaseView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/1.
 */

public class BasePresenterImpl<V extends BaseView> implements BasePresenter {

    protected CompositeDisposable compositeDisposable;
    protected WeakReference<Context> mContext;//使用了弱引用
    protected V view;
    public BasePresenterImpl(Context context,V view) {
        this.view = view;
        mContext=new WeakReference<Context>(context);
        start();
    }

    @Override
    public void start() {

    }

    @Override
    public void attachView(BaseView v) {

    }

    @Override
    public void detach() {
        this.view=null;
        unDisposable();
    }

    @Override
    public void addDisposable(Disposable disposable) {
        if(null==compositeDisposable||compositeDisposable.isDisposed()){
            compositeDisposable=new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void unDisposable() {
        if(null!=compositeDisposable){
            compositeDisposable.dispose();
        }
    }
}
