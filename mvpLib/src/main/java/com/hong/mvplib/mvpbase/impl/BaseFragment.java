package com.hong.mvplib.mvpbase.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hong.mvplib.mvpbase.BasePresenter;
import com.hong.mvplib.mvpbase.BaseView;

/**
 * Created by Administrator on 2018/3/1.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void dismissLoadingDialog() {

    }

    private boolean isViewCreate = false;//view是否创建
    private boolean isViewVisible = false;//view是否可见
    public Context context;
    private boolean isFirst = true;//是否第一次加载
    protected P presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreate = true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isViewVisible = isVisibleToUser;
        if (isVisibleToUser && isViewCreate) {
            visibleToUser();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isViewVisible) {
            visibleToUser();
        }
    }

    /**
     * 懒加载
     * 让用户可见
     * 第一次加载
     */
    protected void firstLoad() {

    }

    /**
     * 懒加载
     * 让用户可见
     */
    protected void visibleToUser() {
        if (isFirst) {
            firstLoad();
            isFirst = false;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(null==presenter){
            presenter = initPresenter();
        }
    }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.detach();
            presenter=null;
        }
        isViewCreate = false;
        super.onDestroyView();
    }

    public abstract P initPresenter();


}
