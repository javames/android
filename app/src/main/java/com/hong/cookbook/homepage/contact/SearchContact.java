package com.hong.cookbook.homepage.contact;

import com.hong.cookbook.bean.CookBean;
import com.hong.cookbook.bean.HistorySelect;
import com.hong.cookbook.bean.HotSelect;
import com.hong.mvplib.mvpbase.BasePresenter;
import com.hong.mvplib.mvpbase.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface SearchContact {

    interface View extends BaseView{

        void setData(ArrayList<HistorySelect> historyList,ArrayList<HotSelect> hotList);
        void respMenuCooks(List<CookBean.ResultBean.ChildsBeanX> cookId);
    }

    interface Presenter extends BasePresenter{
        void getData();
        void searchKeys(int page,String key,String name,String cid,int size);
    }
}
