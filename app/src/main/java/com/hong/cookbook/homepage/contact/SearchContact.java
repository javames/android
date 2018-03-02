package com.hong.cookbook.homepage.contact;

import com.hong.cookbook.bean.CookBean;
import com.hong.mvplib.mvpbase.BasePresenter;
import com.hong.mvplib.mvpbase.BaseView;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface SearchContact {

    interface View extends BaseView{

        void setData(List<CookBean.ResultBean.ChildsBeanX> cookId);
    }

    interface Presenter extends BasePresenter{
        void getData();
    }
}
