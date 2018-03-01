package com.hong.mvplib.mvpbase;



import io.reactivex.disposables.Disposable;
/**
 * Created by Administrator on 2018/3/1.
 */

public interface BasePresenter<V extends BaseView> {

    void start();

    void attachView(V view);

    //在合适时机销毁view
    void detach();

    //将网络请求的每一个disposable添加进入CompositeDisposable，再退出时候一并注销
    void addDisposable(Disposable disposable);

    //注销所有的dispose
    void unDisposable();

}