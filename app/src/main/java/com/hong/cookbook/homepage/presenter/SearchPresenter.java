package com.hong.cookbook.homepage.presenter;


import android.content.Context;

import com.hong.cookbook.bean.CookBean;
import com.hong.cookbook.bean.CookMenuBean;
import com.hong.cookbook.bean.HistorySelect;
import com.hong.cookbook.bean.HotSelect;
import com.hong.cookbook.bean.Menu;
import com.hong.cookbook.greendao.BeanDaoUtil;
import com.hong.cookbook.greendao.MenuDaoUtil;
import com.hong.cookbook.homepage.contact.HomeContact;
import com.hong.cookbook.homepage.contact.SearchContact;
import com.hong.cookbook.http.Api;
import com.hong.cookbook.http.CommonApi;
import com.hong.mvplib.mvpbase.impl.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/1.
 */

public class SearchPresenter extends BasePresenterImpl<SearchContact.View> implements SearchContact.Presenter{


    public SearchPresenter(Context context, SearchContact.View view) {
        super(context, view);

    }

    @Override
    public void getData() {
        ArrayList<HistorySelect> historyList = BeanDaoUtil.getInstance().QueryAllBean(HistorySelect.class);
        ArrayList<HotSelect> hotList = BeanDaoUtil.getInstance().QueryAllBean(HotSelect.class);
        view.setData(historyList,hotList);
    }

    @Override
    public void searchKeys(int page,String key,String name,String cid,int size) {

    }
}
